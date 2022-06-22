package io.github.alemazzo.sushime.model.database.ristorante

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ristoranti")
data class Ristorante(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val descrizione: String,
)
