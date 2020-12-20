package com.github.wwkarev.jirasharktask.core.task

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.link.IssueLink

import com.github.wwkarev.jirasharktask.core.attachment.Attachment
import com.github.wwkarev.jirasharktask.core.comment.Comment
import com.github.wwkarev.jirasharktask.core.status.Status
import com.github.wwkarev.jirasharktask.core.tasktype.TaskType
import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.sharktask.api.attachment.Attachment as API_Attachment
import com.github.wwkarev.sharktask.api.comment.Comment as API_Comment
import com.github.wwkarev.sharktask.api.project.Project as API_Project
import com.github.wwkarev.sharktask.api.task.Task as API_Task
import com.github.wwkarev.sharktask.api.tasktype.TaskType as API_TaskType
import com.github.wwkarev.sharktask.api.status.Status as API_Status
import com.github.wwkarev.sharktask.api.user.User as API_User

import com.github.wwkarev.jirasharktask.core.project.Project

trait Task implements API_Task {
    @Override
    Long getId() {
        return getIssue().getId()
    }

    @Override
    String getKey() {
        return getIssue().getKey()
    }

    @Override
    API_Project getProject() {
        return new Project(getIssue().getProjectObject())
    }

    @Override
    API_TaskType getTaskType() {
        return new TaskType(getIssue().getIssueType())
    }

    @Override
    API_Status getStatus() {
        return new Status(getIssue().getStatus())
    }

    @Override
    String getSummary() {
        return getIssue().getSummary()
    }

    @Override
    API_User getCreator() {
        return new User(getIssue().getReporter())
    }

    @Override
    API_User getAssignee() {
        return new User(getIssue().getAssignee())
    }

    @Override
    Date getCreatedDate() {
        return getIssue().getCreated()
    }

    @Override
    Object getFieldValue(Long fieldId) {
        def customField = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(fieldId)
        return issue.getCustomFieldValue(customField)
    }

    @Override
    List<Long> getInwardLinkedTaskId(Long linkTypeId) {
        return ComponentAccessor.getIssueLinkManager().getInwardLinks(getIssue().getId())
                .findAll{IssueLink issueLink ->
                    return issueLink.getLinkTypeId() == linkTypeId
                }.collect{IssueLink issueLink ->
            return issueLink.getSourceId()
        }
    }

    @Override
    List<Long> getOutwardLinkedTaskId(Long linkTypeId) {
        return ComponentAccessor.getIssueLinkManager().getOutwardLinks(getIssue().getId())
                .findAll{IssueLink issueLink ->
                    return issueLink.getLinkTypeId() == linkTypeId
                }.collect{IssueLink issueLink ->
            return issueLink.getDestinationId()
        }
    }

    @Override
    List<API_Attachment> getAttachments() {
        return ComponentAccessor.getAttachmentManager().getAttachments(getIssue()).collect{new Attachment(it)}
    }

    @Override
    List<API_Comment> getComments() {
        return ComponentAccessor.getCommentManager().getComments(getIssue()).collect{new Comment(it)}
    }

    abstract Issue getIssue()
}
