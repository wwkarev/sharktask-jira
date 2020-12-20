package com.github.wwkarev.jirasharktask.core.status

import com.github.wwkarev.sharktask.api.status.Status as API_Status
import com.atlassian.jira.issue.status.Status as Atl_Status

class Status implements API_Status {
    private Atl_Status status

    Status(com.atlassian.jira.issue.status.Status status) {
        this.status = status
    }

    @Override
    Long getId() {
        return status.getId()
    }

    @Override
    String getName() {
        return status.getName()
    }
}
