package com.github.wwkarev.jirasharktask.core.project

import com.github.wwkarev.jirasharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.project.ProjectCreator as API_ProjectCreator

trait ProjectCreator implements API_ProjectCreator {
    @Override
    Project create(Long id, String key, String name, Params params) {
        throw new MethodNotImplementedException()
    }
    @Override
    Project create(String key, String name, Params params) {
        throw new MethodNotImplementedException()
    }
}
