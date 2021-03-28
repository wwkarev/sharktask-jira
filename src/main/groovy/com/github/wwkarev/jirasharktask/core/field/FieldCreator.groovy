package com.github.wwkarev.jirasharktask.core.field

import com.github.wwkarev.jirasharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.api.field.FieldCreator as API_FieldCreator
import com.github.wwkarev.sharktask.api.params.Params

trait FieldCreator implements API_FieldCreator {
    @Override
    Field create(Long id, String name, Params params) {
        throw new MethodNotImplementedException()
    }
    @Override
    Field create(String name, Params params) {
        throw new MethodNotImplementedException()
    }
}
