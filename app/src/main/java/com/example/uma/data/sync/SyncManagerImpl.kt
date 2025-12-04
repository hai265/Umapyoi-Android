package com.example.uma.data.sync

import com.example.uma.data.repository.character.CharacterRepository
import com.example.uma.data.repository.supportcard.SupportCardRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncManagerImpl @Inject constructor(
    private val supportCardRepository: SupportCardRepository,
    private val characterRepository: CharacterRepository,
): SyncManager {
    override suspend fun syncAll() {
        coroutineScope {
            launch {
                supportCardRepository.sync()
            }
            launch {
                characterRepository.sync()
            }
        }
    }
}