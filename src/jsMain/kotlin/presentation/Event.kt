package presentation

import domain.Item

sealed class Event {
    class Delete(val item: Item): Event()
    class Add(val item: Item?): Event()
}
