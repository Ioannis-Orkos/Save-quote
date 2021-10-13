package com.ioannisorkos.savequote.feature_quote.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote

@Database(
    entities = [Quote::class],
    version = 1
)
abstract class QuoteDatabase:RoomDatabase() {

    abstract val quoteDao: QuoteDao

    companion object {
        const val DATABASE_NAME = "quotes_db"
    }
}