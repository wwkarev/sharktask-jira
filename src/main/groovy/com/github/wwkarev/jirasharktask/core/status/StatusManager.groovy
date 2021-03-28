package com.github.wwkarev.jirasharktask.core.status

import com.github.wwkarev.sharktask.api.status.StatusManager as API_StatusManager

class StatusManager implements API_StatusManager, StatusAccessor, StatusCreator {
    private static StatusManager statusManager

    static StatusManager getInstance() {
        if (!statusManager) {
            statusManager = new StatusManager()
        }
        return statusManager
    }
}
