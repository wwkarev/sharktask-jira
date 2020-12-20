package com.github.wwkarev.jirasharktask.core.tasktype

import com.atlassian.jira.issue.issuetype.IssueType
import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType

class TaskType implements API_TaskType {
    private IssueType issueType

    @Override
    Long getId() {
        return issueType.getId().toLong()
    }

    @Override
    String getName() {
        return issueType.getName()
    }
}
