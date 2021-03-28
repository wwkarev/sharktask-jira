package com.github.wwkarev.jirasharktask.core.task

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.MutableIssue as Jira_MutableIssue
import com.atlassian.jira.issue.fields.CustomField as JIRA_CustomField
import com.atlassian.jira.issue.link.IssueLink

import com.github.wwkarev.jirasharktask.core.attachment.Attachment
import com.github.wwkarev.jirasharktask.core.comment.Comment
import com.github.wwkarev.jirasharktask.core.field.FC
import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.jirasharktask.core.status.Status
import com.github.wwkarev.jirasharktask.core.tasktype.TaskType
import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.sharktask.api.task.Task as API_Task

import com.github.wwkarev.jirasharktask.core.project.Project

abstract class Task implements API_Task {
    protected Jira_MutableIssue jiraIssue
    protected FieldManager fieldManager
    protected FC fieldConstants

    Task(Jira_MutableIssue jiraIssue, FieldManager fieldManager) {
        this.jiraIssue = jiraIssue
        this.fieldManager = fieldManager
        this.fieldConstants = fieldManager.getFieldConstants()
    }

    @Override
    Long getId() {
        return jiraIssue.getId()
    }

    @Override
    String getKey() {
        return jiraIssue.getKey()
    }

    @Override
    Project getProject() {
        return new Project(jiraIssue.getProjectObject())
    }

    @Override
    TaskType getTaskType() {
        return new TaskType(jiraIssue.getIssueType())
    }

    @Override
    Status getStatus() {
        return new Status(jiraIssue.getStatus())
    }

    @Override
    String getSummary() {
        return jiraIssue.getSummary()
    }

    @Override
    User getCreator() {
        return jiraIssue.getReporter() ? new User(jiraIssue.getReporter()) : null
    }

    @Override
    User getAssignee() {
        return jiraIssue.getAssignee() ? new User(jiraIssue.getAssignee()) : null
    }

    @Override
    Date getCreatedDate() {
        return jiraIssue.getCreated()
    }

    @Override
    Object getFieldValue(Long fieldId) {
        Object value
        switch (fieldId) {
            case fieldConstants.getCreator():
                value = getCreator()
                break
            case fieldConstants.getAssignee():
                value = getAssignee()
                break
            case fieldConstants.getSummary():
                value = getSummary()
                break
            case fieldConstants.getDescription():
                value = jiraIssue.getDescription()
                break
            default:
                JIRA_CustomField customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(fieldId)
                value = jiraIssue.getCustomFieldValue(customField)
        }
        return value
    }

    @Override
    List<Attachment> getAttachments() {
        return ComponentAccessor.getAttachmentManager().getAttachments(jiraIssue).collect{new Attachment(it)}
    }

    @Override
    List<Comment> getComments() {
        return ComponentAccessor.getCommentManager().getComments(jiraIssue).collect{new Comment(it)}
    }

    Jira_MutableIssue getIssue() {
        return jiraIssue
    }
}
