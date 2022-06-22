package io.github.alemazzo.sushime.model.database.piatto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "piatti")
data class Piatto(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "categoriaId")
    val categoriaId: Int,

    @ColumnInfo(name = "nome")
    val name: String,

    @ColumnInfo(name = "descrizione")
    val descrizione: String,

    @ColumnInfo(name = "immagine")
    val immagine: String
)
