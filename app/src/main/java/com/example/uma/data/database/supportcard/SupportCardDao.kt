package com.example.uma.data.database.supportcard

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SupportCardDao {
    @Query("SELECT * from support_cards ORDER BY id ASC")
    fun getAllSupportCards(): Flow<List<SupportCardEntity>>

    @Query("SELECT * from support_cards WHERE id = :id")
    fun  getSupportCardById(id: Int): Flow<SupportCardEntity>

    @Query("SELECT * from support_cards WHERE characterId = :characterId")
    fun  getSupportCardsByCharacterId(characterId: Int): Flow<List<SupportCardEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnoreExisting(supportCards: List<SupportCardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(supportCard: SupportCardEntity)
}