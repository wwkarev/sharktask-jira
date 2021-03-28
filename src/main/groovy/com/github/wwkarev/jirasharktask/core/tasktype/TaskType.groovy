package com.github.wwkarev.jirasharktask.core.tasktype

import com.atlassian.jira.issue.issuetype.IssueType
import com.atlassian.jira.issue.issuetype.IssueType as JIRA_IssueType
import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType

class TaskType implements API_TaskType {
    private JIRA_IssueType jiraIssueType

    TaskType(JIRA_IssueType jiraIssueType) {
        this.jiraIssueType = jiraIssueType
    }

    @Override
    Long getId() {
        return jiraIssueType.getId().toLong()
    }

    @Override
    String getName() {
        return jiraIssueType.getName()
    }

    IssueType getJiraIssueType() {
        return jiraIssueType
    }
}
