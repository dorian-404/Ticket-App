package com.example.ticketapp



import android.app.Application
import com.example.ticketapp.database.AppDatabase
import com.stripe.android.PaymentConfiguration

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getDatabase(this)
    }
}