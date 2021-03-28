package com.github.wwkarev.jirasharktask.core.status

import com.github.wwkarev.sharktask.api.status.Status as API_Status
import com.atlassian.jira.issue.status.Status as Jira_Status

class Status implements API_Status {
    private Jira_Status jiraStatus

    Status(Jira_Status jiraStatus) {
        this.jiraStatus = jiraStatus
    }

    @Override
    Long getId() {
        return jiraStatus.getId().toLong()
    }

    @Override
    String getName() {
        return jiraStatus.getName()
    }

   Jira_Status getJiraStatus() {
        return jiraStatus
    }
}
