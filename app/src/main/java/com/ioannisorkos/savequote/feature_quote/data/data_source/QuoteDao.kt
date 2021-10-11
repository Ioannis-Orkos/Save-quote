package com.ioannisorkos.savequote.feature_quote.data.data_source

import androidx.room.*
import com.ioannisorkos.savequote.feature_quote.domain.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quote")
    fun getNotes(): Flow<List<Quote>>

    @Query("SELECT * FROM quote WHERE id = :id")
    suspend fun getNoteById(id: Int): Quote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(quote: Quote)

    @Delete
    suspend fun deleteNote(quote: Quote)

}