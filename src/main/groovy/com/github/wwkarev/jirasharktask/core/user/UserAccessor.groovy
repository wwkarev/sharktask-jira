package com.github.wwkarev.jirasharktask.core.user

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.user.ApplicationUser as JIRA_User
import com.github.wwkarev.sharktask.api.user.UserAccessor as API_UserAccessor
import com.github.wwkarev.sharktask.api.user.UserNotFoundException

trait UserAccessor implements API_UserAccessor {
    @Override
    User getById(Long id) {
        User user = getAtById(id)
        if (user == null) {
            throw new UserNotFoundException()
        }
        return user
    }

    @Override
    User getByKey(String key) {
        User user = getAtByKey(key)
        if (user == null) {
            throw new UserNotFoundException()
        }
        return user
    }

    @Override
    User getAtById(Long id) {
        Optional<JIRA_User> optionalJiraUser = ComponentAccessor.getUserManager().getUserById(id)
        JIRA_User jiraUser = optionalJiraUser.isPresent() ? optionalJiraUser.get() : null
        return jiraUser ? new User(jiraUser) : null
    }

    @Override
    User getAtByKey(String key) {
        JIRA_User jiraUser = ComponentAccessor.getUserManager().getUserByKey(key)
        return jiraUser ? new User(jiraUser) : null
    }
}
