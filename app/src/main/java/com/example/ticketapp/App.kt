package com.example.ticketapp



import android.app.Application
import com.example.ticketapp.database.AppDatabase
import com.stripe.android.PaymentConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)

        // Configurer le SDK Stripe avec votre clé publiable
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51PMBiqHhqi17tliDpXxmLr01lMit5ukwVxu3WjGhtftikvfdSIWxyG1vnOhebN5M4pexbpY9q8yaUtm4fIN2wHbK00RMV0y6Vj"
        )
    }
}