require("dotenv").config();

const express = require("express");
const app = express();
const secretKey = process.env.STRIPE_SECRET_KEY;

if (!secretKey) {
  throw new Error("Missing STRIPE_SECRET_KEY in environment variables.");
}

const stripe = require("stripe")(secretKey);

app.use(express.static("public"));
app.use(express.json());

const calculateOrderAmount = (items) => {
    // Calcul du total des billets 
    let total = 0;
    items.forEach(item => {
        total += item.price * item.quantity;
    });
    return total;
};

app.post("/create-payment-intent", async (req, res) => {
  const { items } = req.body;

  // Create a PaymentIntent with the order amount and currency
  const paymentIntent = await stripe.paymentIntents.create({
    amount: calculateOrderAmount(items),
    currency: "cad",
    // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
    automatic_payment_methods: {
      enabled: true,
    },
  });

  res.send({
    clientSecret: paymentIntent.client_secret,
  });
});


app.listen(4242, () => console.log("Node server listening on port 4242!"));
