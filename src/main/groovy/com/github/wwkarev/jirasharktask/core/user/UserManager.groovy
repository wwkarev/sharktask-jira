package com.github.wwkarev.jirasharktask.core.user

import com.github.wwkarev.sharktask.api.user.UserManager as API_UserManager

class UserManager implements API_UserManager, UserAccessor, UserCreator {
    private static UserManager instance

    static UserManager getInstance() {
        if (!instance) {
            instance = new UserManager()
        }
        return instance
    }
}
