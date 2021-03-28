package com.github.wwkarev.jirasharktask.core.tasktype

import com.github.wwkarev.sharktask.api.tasktype.TaskTypeManager as API_TaskTypeManager

class TaskTypeManager implements API_TaskTypeManager, TaskTypeAccessor, TaskTypeCreator {
    private static TaskTypeManager instance

    static TaskTypeManager getInstance() {
        if (!instance) {
            instance = new TaskTypeManager()
        }
        return instance
    }
}
