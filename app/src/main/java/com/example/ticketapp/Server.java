package com.example.ticketapp;

import java.nio.file.Paths;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.port;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class Server {
    private static final Gson gson = new Gson();

    // Creer un element de paiement
    static class CreatePaymentItem {
        @SerializedName("id")
        String id;

        public String getId() {
            return id;
        }
    }

    // Creer un paiement
    static class CreatePayment {
        @SerializedName("items")
        CreatePaymentItem[] items;

        public CreatePaymentItem[] getItems() {
            return items;
        }
    }

    // Creer une reponse de paiement
    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    // Calculer le montant de la commande
    static int calculateOrderAmount(Object[] items) {
        // Replace this constant with a calculation of the order's amount
        // Calculate the order total on the server to prevent
        // people from directly manipulating the amount on the client
        return 1400;
    }


    public static void main(String[] args) {
        // ca c'est pour le port et le fichier static
        port(4242);
        staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());

        // This is your test secret API key.
        Stripe.apiKey = "sk_test_51PMBiqHhqi17tliDpabLg79EqIg2dwuqemz5IHWkMIBG4QeU1SDeMj39y4aIiPeqc4RsCr4ZcV5YqDxk0tlmYnK900WCpC1dHo";

        post("/create-payment-intent", (request, response) -> {
            response.type("application/json");
            CreatePayment postBody = gson.fromJson(request.body(), CreatePayment.class);

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(new Long(calculateOrderAmount(postBody.getItems())))
                            .setCurrency("usd")
                            // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods
                                            .builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();

            // Create a PaymentIntent with the order amount and currency
            PaymentIntent paymentIntent = PaymentIntent.create(params);

            CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
            return gson.toJson(paymentResponse);
        });
    }
}