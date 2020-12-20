package com.github.wwkarev.jirasharktask.core.user

import com.atlassian.jira.user.ApplicationUser
import com.github.wwkarev.sharktask.api.user.User as API_User
import com.atlassian.jira.user.ApplicationUser as Atl_User

class User implements API_User {
    private Atl_User user

    User(Atl_User user) {
        this.user = user
    }

    @Override
    Long getId() {
        return user.getId()
    }

    @Override
    String getKey() {
        return user.getKey()
    }

    @Override
    String getFirstName() {
        return user.getName()
    }

    @Override
    String getLastName() {
        return user.getName()
    }

    @Override
    String getFullName() {
        return user.getDisplayName()
    }

    ApplicationUser getUser() {
        return user
    }
}
