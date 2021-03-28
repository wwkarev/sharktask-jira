package com.github.wwkarev.jirasharktask.core.tasktype

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.issuetype.IssueType as JIRA_IssueType
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeAccessor as API_TaskTypeAccessor
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeNotFoundException

trait TaskTypeAccessor implements API_TaskTypeAccessor {
    @Override
    TaskType getById(Long id) {
        TaskType taskType = getAtById(id)
        if (taskType == null) {
            throw new TaskTypeNotFoundException()
        }
        return taskType
    }
    @Override
    TaskType getAtById(Long id) {
        JIRA_IssueType issueType = ComponentAccessor.getConstantsManager().getIssueType(id.toString())
        return issueType ? new TaskType(issueType) : null
    }
}
