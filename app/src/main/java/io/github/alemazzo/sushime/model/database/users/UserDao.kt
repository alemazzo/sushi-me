package io.github.alemazzo.sushime.model.database.users

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: Long): User?

    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun getByEmailAndPassword(email: String, password: String): User?

    @Insert
    fun insert(vararg users: User)

    @Delete
    fun delete(user: User)
}
