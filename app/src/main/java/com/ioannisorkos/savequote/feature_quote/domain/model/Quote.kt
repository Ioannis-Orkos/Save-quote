package com.ioannisorkos.savequote.feature_quote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ioannisorkos.savequote.ui.theme.*


@Entity
data class Quote(
    val content:String,
    val by:String,
    val source:String,
    val timestamp:String,
    val color:Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message: String): Exception(message)