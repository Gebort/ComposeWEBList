package domain

import kotlinx.coroutines.flow.Flow

interface ListRepository {

    fun getAll(): Flow<List<Item>>
    suspend fun save(item: Item)
    suspend fun delete(item: Item)

}