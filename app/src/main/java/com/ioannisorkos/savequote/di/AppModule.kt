package com.ioannisorkos.savequote.di

import android.app.Application
import androidx.room.Room
import com.ioannisorkos.savequote.feature_quote.data.data_source.QuoteDatabase
import com.ioannisorkos.savequote.feature_quote.data.repository.QuoteRepositoryImpl
import com.ioannisorkos.savequote.feature_quote.domain.repository.QuoteRepository
import com.ioannisorkos.savequote.feature_quote.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuoteDatabase(app: Application): QuoteDatabase {
        return Room.databaseBuilder(
            app,
            QuoteDatabase::class.java,
            QuoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideQuoteRepository(db: QuoteDatabase): QuoteRepository {
        return QuoteRepositoryImpl(db.quoteDao)
    }

    @Provides
    @Singleton
    fun provideQuoteUseCases(repository: QuoteRepository): QuoteUseCases {
        return QuoteUseCases(
            getQuotes = GetQuotes(repository),
            deleteQuote = DeleteQuote(repository),
            addQuote = AddQuote(repository),
            getQuote = GetQuote(repository)
        )
    }
}