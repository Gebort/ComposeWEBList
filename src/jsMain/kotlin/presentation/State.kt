package presentation

import domain.Item

data class State(
    val isLoading: Boolean = true,
    val list: List<Item> = listOf()
)