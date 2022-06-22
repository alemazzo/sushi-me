package io.github.alemazzo.sushime.model.database.categoria

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categorie")
data class Categoria(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "nome")
    val nome: String,
)
