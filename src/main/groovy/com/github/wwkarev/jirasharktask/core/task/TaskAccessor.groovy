package com.github.wwkarev.jirasharktask.core.task

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue as JIRA_MutableIssue
import com.github.wwkarev.jirasharktask.core.field.FC
import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.sharktask.api.task.TaskAccessor as API_TaskAccessor
import com.github.wwkarev.sharktask.api.task.TaskNotFoundException

trait TaskAccessor implements API_TaskAccessor {
    abstract FieldManager getFieldManager()

    @Override
    MutableTask getById(Long id) {
        MutableTask mutableTask = getAtById(id)
        if (mutableTask == null) {
            throw new TaskNotFoundException()
        }
        return mutableTask
    }

    @Override
    MutableTask getByKey(String key) {
        MutableTask mutableTask = getAtByKey(key)
        if (mutableTask == null) {
            throw new TaskNotFoundException()
        }
        return mutableTask
    }
    @Override
    MutableTask getAtById(Long id) {
        JIRA_MutableIssue jiraMutableIssue = ComponentAccessor.getIssueManager().getIssueObject(id)
        return jiraMutableIssue ? new MutableTask(jiraMutableIssue, getFieldManager()) : null
    }

    @Override
    MutableTask getAtByKey(String key) {
        JIRA_MutableIssue jiraMutableIssue = ComponentAccessor.getIssueManager().getIssueByCurrentKey(key)
        return jiraMutableIssue ? new MutableTask(jiraMutableIssue, getFieldManager()) : null
    }
}
