package io.github.alemazzo.sushime.model.database.piatto

import androidx.room.Embedded
import androidx.room.Relation
import io.github.alemazzo.sushime.model.database.categoria.Categoria


data class CategoriaConPiatti(
    @Embedded
    val categoria: Categoria,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoriaId"
    )
    val piatti: List<Piatto>,
)
