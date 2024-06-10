package com.example.ticketapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.ticketapp.dao.BookingDao
import com.example.ticketapp.dao.EventDao
import com.example.ticketapp.dao.TicketDao
import com.example.ticketapp.dao.UserDao
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.Payment
import com.example.ticketapp.models.Ticket
import com.example.ticketapp.models.User

@Database(entities = [User::class, Event::class, Ticket::class, Booking::class, Payment::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun userDao(): UserDao
    abstract fun ticketDao(): TicketDao
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Migration from version 1 to version 2
        val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // For example, if you added a new column to the User table:
                    database.execSQL("ALTER TABLE events RENAME TO events_old")

                    // Créez la nouvelle table avec la structure mise à jour
                    database.execSQL("""
                CREATE TABLE events (
                    eventId INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    description TEXT NOT NULL,
                    date TEXT NOT NULL,
                    hour TEXT NOT NULL,
                    location TEXT NOT NULL,
                    PRIMARY KEY(eventId)
                )
            """.trimIndent())

                    // Copiez les données de l'ancienne table vers la nouvelle
                    database.execSQL("""
                INSERT INTO events (eventId, name, description, date, hour, location)
                SELECT eventId, name, description, substr(dateTime, 1, 10) AS date, substr(dateTime, 12, 5) AS hour, location
                FROM events_old
            """.trimIndent())

                    // Supprimez l'ancienne table
                    database.execSQL("DROP TABLE events_old")

                // Migration pour la table 'tickets'
                database.execSQL("ALTER TABLE tickets RENAME TO tickets_old")
                database.execSQL("""
            CREATE TABLE tickets (
                ticketId INTEGER NOT NULL,
                typeTicket TEXT NOT NULL,
                price REAL NOT NULL,
                seatNumber INTEGER NOT NULL,
                section TEXT NOT NULL,
                eventCreatorId INTEGER NOT NULL DEFAULT 0,
                PRIMARY KEY(ticketId)
            )
        """.trimIndent())
                database.execSQL("""
            INSERT INTO tickets (ticketId, typeTicket, price, seatNumber, section, eventCreatorId)
            SELECT ticketId, typeTicket, price, seatNumber, CAST(section AS TEXT), 0
            FROM tickets_old
        """.trimIndent())
                database.execSQL("DROP TABLE tickets_old")


                // Migration pour la table 'bookings'
                database.execSQL("ALTER TABLE bookings RENAME TO bookings_old")
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS bookings (
                bookingId INTEGER NOT NULL PRIMARY KEY,
                userId INTEGER NOT NULL,
                eventId INTEGER NOT NULL,
                quantityTickets INTEGER NOT NULL,
                FOREIGN KEY(userId) REFERENCES users(userId) ON DELETE CASCADE,
                FOREIGN KEY(eventId) REFERENCES events(eventId) ON DELETE CASCADE
            )
        """.trimIndent())
                database.execSQL("""
            INSERT INTO bookings (bookingId, userId, eventId, quantityTickets)
            SELECT bookingId, userId, eventId, quantityTickets
            FROM bookings_old
        """.trimIndent())
                database.execSQL("DROP TABLE bookings_old")

                // Migration pour la table 'payment'
                database.execSQL("ALTER TABLE payment RENAME TO payment_old")
                database.execSQL("""
            CREATE TABLE payment (
                paymentId INTEGER NOT NULL PRIMARY KEY,
                bookingId INTEGER NOT NULL,
                amount REAL NOT NULL,
                paymentMethod TEXT NOT NULL,
                FOREIGN KEY(bookingId) REFERENCES bookings(bookingId) ON DELETE CASCADE
            )
        """.trimIndent())
                database.execSQL("""
            INSERT INTO payment (paymentId, bookingId, amount, paymentMethod)
            SELECT paymentId, bookingId, amount, paymentMethod
            FROM payment_old
        """.trimIndent())
                database.execSQL("DROP TABLE payment_old")

            }


        }

            fun getDatabase(context: Context): AppDatabase {
                val tempInstance = INSTANCE
                if (tempInstance != null) {
                    return tempInstance
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .addMigrations(MIGRATION_2_3)  // Add the migration here // You can remove this if you want to enforce migrations
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
