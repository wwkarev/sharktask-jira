package com.github.wwkarev.jirasharktask.core.task

import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption as Jira_EventDispatchOption
import com.atlassian.jira.issue.AttachmentManager
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.issue.fields.CustomField as JIRA_CustomField
import com.atlassian.jira.issue.MutableIssue as Jira_MutableIssue
import com.atlassian.jira.issue.attachment.CreateAttachmentParamsBean
import com.atlassian.jira.issue.fields.config.FieldConfig
import com.atlassian.jira.issue.link.IssueLink
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.user.ApplicationUser
import com.github.wwkarev.jirasharktask.core.exception.DeleteTaskException
import com.github.wwkarev.jirasharktask.core.exception.TransitionException
import com.github.wwkarev.jirasharktask.core.field.CustomField
import com.github.wwkarev.jirasharktask.core.field.FC
import com.github.wwkarev.jirasharktask.core.field.Field
import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.sharktask.api.eventdispatchoption.EventDispatchOption
import com.github.wwkarev.sharktask.api.task.MutableTask as API_MutableTask
import com.github.wwkarev.sharktask.api.user.User as API_User

class MutableTask extends Task implements API_MutableTask {
    MutableTask(Jira_MutableIssue jiraIssue, FieldManager fieldManager) {
        super(jiraIssue, fieldManager)
    }

    @Override
    void updateFieldValue(Long fieldId, Object value, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        switch (fieldId) {
            case fieldConstants.getCreator():
                updateCreator(value, user, eventDispatchOption)
                break
            case fieldConstants.getAssignee():
                updateAssignee(value, user, eventDispatchOption)
                break
            case fieldConstants.getSummary():
                updateSummary(value, user, eventDispatchOption)
                break
            case fieldConstants.getDescription():
                updateDescription(value, user, eventDispatchOption)
                break
            default:
                updateCustomField(fieldId, value, user, eventDispatchOption)
        }
    }

    @Override
    List<MutableTask> getInwardLinkedTaskId(Long linkTypeId) {
        return ComponentAccessor.getIssueLinkManager().getInwardLinks(jiraIssue.getId())
                .findAll{ IssueLink issueLink ->
                    return issueLink.getLinkTypeId() == linkTypeId
                }.collect{IssueLink issueLink ->
            return issueLink.getSourceObject()
        }.collect{MutableIssue linkedIssue ->
            return new MutableTask(linkedIssue, fieldManager)
        }
    }

    @Override
    List<MutableTask> getOutwardLinkedTaskId(Long linkTypeId) {
        return ComponentAccessor.getIssueLinkManager().getOutwardLinks(jiraIssue.getId())
                .findAll{IssueLink issueLink ->
                    return issueLink.getLinkTypeId() == linkTypeId
                }.collect{IssueLink issueLink ->
            return issueLink.getDestinationObject()
        }.collect{MutableIssue linkedIssue ->
            return new MutableTask(linkedIssue, fieldManager)
        }
    }

    @Override
    void addInwardLinkedTask(API_MutableTask sourceTask, Long linkTypeId, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        Long sequence = 1
        ComponentAccessor.getIssueLinkManager().createIssueLink(sourceTask.getId(), getIssue().getId(), linkTypeId, sequence, getApplicationUser(user))
    }

    @Override
    void addOutwardLinkedTask(API_MutableTask destinationTask, Long linkTypeId, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        Long sequence = 1
        ComponentAccessor.getIssueLinkManager().createIssueLink(getIssue().getId(), destinationTask.getId(), linkTypeId, sequence, getApplicationUser(user))
    }

    @Override
    void removeAllOutwardLinks(Long linkTypeId, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        IssueLinkManager issueLinkManager = ComponentAccessor.getIssueLinkManager()
        issueLinkManager.getOutwardLinks(getIssue().getId())
                .findAll{it.getLinkTypeId() == linkTypeId}
                .each{issueLinkManager.removeIssueLink(it, getApplicationUser(user))}
    }

    @Override
    void transit(Long transtionId, API_User user) {
        IssueService issueService = ComponentAccessor.getIssueService()

        IssueService.TransitionValidationResult validationResult = issueService.validateTransition(
                getApplicationUser(user),
                getIssue().getId(),
                (int)transtionId.toInteger(),
                issueService.newIssueInputParameters()
        )
        if (!validationResult.isValid()) {
            throw new TransitionException(validationResult)
        }
        issueService.transition(getApplicationUser(user), validationResult)
    }

    @Override
    void addAttachment(String filePath, String attachmentName, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        File file = new File(filePath)
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException("File not found or File is directory")
        }

        CreateAttachmentParamsBean bean = new CreateAttachmentParamsBean.Builder()
                .file(file)
                .filename(attachmentName)
                .contentType("text/plain")
                .author(getApplicationUser(user))
                .issue(getIssue())
                .copySourceFile(true)
                .build()
        ComponentAccessor.attachmentManager.createAttachment(bean)
    }

    @Override
    void removeAttachment(Long id, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        AttachmentManager attachmentManager = ComponentAccessor.attachmentManager
        attachmentManager.deleteAttachment(attachmentManager.getAttachment(id))
    }

    @Override
    void addComment(String body, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        Boolean throwEventBoolean = eventDispatchOption == EventDispatchOption.UPDATE
        ComponentAccessor.getCommentManager().create(getIssue(), getApplicationUser(user), body, throwEventBoolean)
    }

    @Override
    void removeComment(Long id, API_User user, EventDispatchOption eventDispatchOption) {
        Boolean throwEventBoolean = eventDispatchOption == EventDispatchOption.UPDATE
        CommentManager commentManager = ComponentAccessor.commentManager
        commentManager.delete(commentManager.getCommentById(id), throwEventBoolean, user)
    }

    @Override
    void delete(API_User user) {
        IssueService issueService = ComponentAccessor.getIssueService()
        IssueService.DeleteValidationResult validationResult = issueService.validateDelete(getApplicationUser(user), getIssue().getId())
        if (validationResult.errorCollection.hasAnyErrors()) {
            throw new DeleteTaskException(validationResult)
        }
        issueService.delete(getApplicationUser(user), validationResult)
    }

    @Override
    Jira_MutableIssue getIssue() {
        return jiraIssue
    }

    private void updateAssignee(API_User assigneeUser, API_User user, EventDispatchOption eventDispatchOption) {
        Jira_EventDispatchOption jira_eventDispatchOption = getJiraDispatchOption(eventDispatchOption)
        jiraIssue.setAssignee(getApplicationUser(assigneeUser))
        ComponentAccessor.getIssueManager().updateIssue(getApplicationUser(user), jiraIssue, jira_eventDispatchOption, false)
    }

    private void updateSummary(String summary, API_User user, EventDispatchOption eventDispatchOption) {
        Jira_EventDispatchOption jira_eventDispatchOption = getJiraDispatchOption(eventDispatchOption)
        jiraIssue.setSummary(summary)
        ComponentAccessor.getIssueManager().updateIssue(getApplicationUser(user), jiraIssue, jira_eventDispatchOption, false)
    }

    private void updateDescription(String description, API_User user, EventDispatchOption eventDispatchOption) {
        Jira_EventDispatchOption jira_eventDispatchOption = getJiraDispatchOption(eventDispatchOption)
        jiraIssue.setDescription(description)
        ComponentAccessor.getIssueManager().updateIssue(getApplicationUser(user), jiraIssue, jira_eventDispatchOption, false)
    }

    private void updateCreator(API_User creatorUser, API_User user, EventDispatchOption eventDispatchOption) {
        Jira_EventDispatchOption jira_eventDispatchOption = getJiraDispatchOption(eventDispatchOption)
        jiraIssue.setReporter(getApplicationUser(creatorUser))
        ComponentAccessor.getIssueManager().updateIssue(getApplicationUser(user), jiraIssue, jira_eventDispatchOption, false)
    }

    private void updateCustomField(Long fieldId, Object value, API_User user, EventDispatchOption eventDispatchOption) {
        CustomField field = fieldManager.getById(fieldId)
        JIRA_CustomField jiraCustomField = field.getJiraField()
        Jira_EventDispatchOption jira_eventDispatchOption = getJiraDispatchOption(eventDispatchOption)
        switch (jiraCustomField.getCustomFieldType().getKey()) {
            case fieldConstants.getSelectCustomFieldTypes():
                FieldConfig fieldConfig = jiraCustomField.getRelevantConfig(jiraIssue)
                value = ComponentAccessor.optionsManager.getOptions(fieldConfig)?.find { it.getOptionId() == value }
                break
        }
        jiraIssue.setCustomFieldValue(jiraCustomField, value)
        ComponentAccessor.getIssueManager().updateIssue(getApplicationUser(user), jiraIssue, jira_eventDispatchOption, false)
    }

    private Jira_EventDispatchOption getJiraDispatchOption(EventDispatchOption eventDispatchOption) {
        return eventDispatchOption == EventDispatchOption.UPDATE
                ? Jira_EventDispatchOption.ISSUE_UPDATED
                : Jira_EventDispatchOption.DO_NOT_DISPATCH
    }

    private ApplicationUser getApplicationUser(API_User user) {
        return ((User)user)?.getUser()
    }
}

