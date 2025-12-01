package com.example.uma.data.database.supportcard

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SupportCardDao {
    @Query("SELECT * from support_cards ORDER BY id ASC")
    suspend fun getAllSupportCards(): List<SupportCardEntity>
}