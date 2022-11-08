package presentation

import androidx.compose.runtime.*
import data.ListRepositoryMock
import domain.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    val scope = CoroutineScope(Dispatchers.Main)
    val model = ViewModel(ListRepositoryMock())
    var state: State by mutableStateOf(model.listState.value)

    scope.launch {
        model.listState.collect { newState ->
            state = newState
        }
    }

    renderComposable(rootElementId = "root") {
        renderPage(state, model::onEvent)
    }
}

@Composable
fun renderPage(
    state: State,
    onEvent: (Event) -> Unit
) {
    Div({ style { padding(25.px) } }) {
        loading(state.isLoading)
        displayList(
            state.list,
            onNew = { onEvent(Event.Add(it)) },
            onDelete = { onEvent(Event.Delete(it)) }
        )
    }
}

@Composable
fun loading(isLoading: Boolean) {
    Text(if (isLoading) "Loading..." else "")
}
@Composable
fun displayList(
    list: List<Item>,
    onNew: (Item?) -> Unit,
    onDelete: (Item) -> Unit
) {
    Button(
        attrs = {
            onClick { onNew(null) }
        }
    ) { Text("New item") }
    list.forEachIndexed { index, item -> listItem(index, item, onDelete) }
}

@Composable
fun listItem(
    index: Int,
    item: Item,
    onDelete: (Item) -> Unit
) {
    Div {
        H4 {
            Text("$index. ${item.text}")
        }
        Button(
            attrs = {
                onClick { onDelete(item) }
            }
        ) { Text("-") }
    }
}

