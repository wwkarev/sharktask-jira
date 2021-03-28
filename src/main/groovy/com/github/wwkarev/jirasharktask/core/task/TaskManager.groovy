package com.github.wwkarev.jirasharktask.core.task

import com.github.wwkarev.jirasharktask.core.field.FC
import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.sharktask.api.task.TaskManager as API_TaskManager


class TaskManager implements API_TaskManager, TaskAccessor, TaskCreator {
    private static TaskManager instance
    private FieldManager fieldManager

    TaskManager(FieldManager fieldManager) {
        this.fieldManager = fieldManager
    }

    static TaskManager getInstance(FieldManager fieldManager) {
        if (!instance) {
            instance = new TaskManager(fieldManager)
        }
        return instance
    }

    @Override
    FieldManager getFieldManager() {
        return fieldManager
    }
}
