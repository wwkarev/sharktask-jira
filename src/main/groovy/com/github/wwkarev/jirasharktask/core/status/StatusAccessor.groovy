package com.github.wwkarev.jirasharktask.core.status

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.status.Status as JIRA_Status
import com.github.wwkarev.sharktask.api.status.StatusAccessor as API_StatusAccessor
import com.github.wwkarev.sharktask.api.status.StatusNotFoundException

trait StatusAccessor implements API_StatusAccessor {
    @Override
    Status getById(Long id) {
        Status status = getAtById(id)
        if (status == null) {
            throw new StatusNotFoundException()
        }
        return status
    }

    @Override
    Status getAtById(Long id) {
        JIRA_Status jiraStatus = ComponentAccessor.getConstantsManager().getStatus(id.toString())
        return jiraStatus ? new Status(jiraStatus) : null
    }
}
