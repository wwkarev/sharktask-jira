package com.github.wwkarev.jirasharktask.core.status

import com.github.wwkarev.jirasharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.status.StatusCreator as API_StatusCreator

trait StatusCreator implements API_StatusCreator {
    @Override
    Status create(Long id, String name, Params params) {
        throw new MethodNotImplementedException()
    }
    @Override
    Status create(String name, Params params) {
        throw new MethodNotImplementedException()
    }
}
