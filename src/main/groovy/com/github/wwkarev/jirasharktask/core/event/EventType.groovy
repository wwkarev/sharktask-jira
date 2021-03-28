package com.github.wwkarev.jirasharktask.core.event

import com.github.wwkarev.sharktask.api.event.EventType as API_EventType

class EventType implements API_EventType {
    private id

    EventType(id) {
        this.id = id
    }

    @Override
    Long getId() {
        return id
    }
}
