package com.github.wwkarev.jirasharktask.core.tasktype

import com.github.wwkarev.jirasharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeCreator as API_TaskTypeCreator

trait TaskTypeCreator implements API_TaskTypeCreator {
    @Override
    TaskType create(Long id, String name, Params params) {
        throw new MethodNotImplementedException()
    }
    @Override
    TaskType create(String name, Params params) {
        throw new MethodNotImplementedException()
    }
}
