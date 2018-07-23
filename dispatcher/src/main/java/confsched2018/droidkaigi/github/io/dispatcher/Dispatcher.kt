package confsched2018.droidkaigi.github.io.dispatcher

import io.github.droidkaigi.confsched2019.session.model.Action
import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.RendezvousChannel
import kotlinx.coroutines.experimental.channels.filter
import kotlinx.coroutines.experimental.channels.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Dispatcher @Inject constructor() {
    private val _actions = RendezvousChannel<Action>()
    val events: ReceiveChannel<Action> get() = _actions

    inline fun <reified T : Action> subscrive(): ReceiveChannel<T> {
        return events.filter { it is T }.map { it as T }
    }

    suspend fun send(action: Action) {
        _actions.send(action)
    }
}