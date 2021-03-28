package com.github.wwkarev.jirasharktask.core.user


import com.github.wwkarev.sharktask.api.user.User as API_User
import com.atlassian.jira.user.ApplicationUser as Jira_User

class User implements API_User {
    private Jira_User jiraUser

    User(Jira_User jiraUser) {
        this.jiraUser = jiraUser
    }

    @Override
    Long getId() {
        return jiraUser.getId()
    }

    @Override
    String getKey() {
        return jiraUser.getKey()
    }

    @Override
    String getFirstName() {
        return jiraUser.getName()
    }

    @Override
    String getLastName() {
        return jiraUser.getName()
    }

    @Override
    String getFullName() {
        return jiraUser.getDisplayName()
    }

    Jira_User getUser() {
        return jiraUser
    }
}
