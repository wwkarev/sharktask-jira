package com.github.wwkarev.jirasharktask.core.user

import com.github.wwkarev.jirasharktask.core.exception.MethodNotImplementedException
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.user.UserCreator as API_UserCreator

trait UserCreator implements API_UserCreator {
    User create(String key, String firstName, String lastName, String fullName, Params params) {
        throw new MethodNotImplementedException()
    }
}
