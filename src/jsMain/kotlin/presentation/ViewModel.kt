package presentation

import domain.Item
import domain.ListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel(
    private val repository: ListRepository
) {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listState = MutableStateFlow(State())
    val listState = _listState.asStateFlow()

    init {
        scope.launch {
            repository.getAll().collect { list ->
                _listState.update { State(
                    isLoading = false,
                    list = list
                ) }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.Add -> {
                scope.launch {
                    repository.save(event.item ?: Item("Added item"))
                }
            }
            is Event.Delete -> {
                scope.launch {
                    repository.delete(event.item)
                }
            }
        }
    }

}