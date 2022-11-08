package data

import androidx.compose.runtime.mutableStateOf
import domain.Item
import domain.ListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext

class ListRepositoryMock: ListRepository {

    private val list = MutableStateFlow(
        mutableListOf(
            Item("holy"),
            Item("diver"),
            Item("you`ve"),
            Item("been"),
            Item("down"),
            Item("too"),
            Item("long"),
            Item("in"),
            Item("the"),
            Item("midnight"),
            Item("sea"),
        )
    )

    override fun getAll(): Flow<List<Item>> {
        return list.asStateFlow()
    }

    override suspend fun save(item: Item): Unit = withContext(Dispatchers.Default) {
        list.update { list ->
            val newList = list.toMutableList()
            newList.add(item)
            newList
        }
    }

    override suspend fun delete(item: Item): Unit = withContext(Dispatchers.Default) {
        list.update { list ->
            val newList = list.toMutableList()
            newList.remove(item)
            newList
        }
    }
}