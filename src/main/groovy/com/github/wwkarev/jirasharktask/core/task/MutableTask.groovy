package com.github.wwkarev.jirasharktask.core.task

import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.event.type.EventDispatchOption as Atl_EventDispatchOption
import com.atlassian.jira.issue.MutableIssue as Atl_MutableIssue
import com.atlassian.jira.issue.attachment.CreateAttachmentParamsBean
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.user.ApplicationUser
import com.github.wwkarev.jirasharktask.core.exception.DeleteTaskException
import com.github.wwkarev.jirasharktask.core.exception.TransitionException
import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.sharktask.api.eventdispatchoption.EventDispatchOption
import com.github.wwkarev.sharktask.api.task.MutableTask as API_MutableTask
import com.github.wwkarev.sharktask.api.user.User as API_User

trait MutableTask implements Task, API_MutableTask {
    abstract Atl_MutableIssue getIssue()

    @Override
    void updateValue(Long fieldId, Object value, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        Atl_EventDispatchOption atl_eventDispatchOption = eventDispatchOption == EventDispatchOption.UPDATE
                ? Atl_EventDispatchOption.ISSUE_UPDATED
                : Atl_EventDispatchOption.DO_NOT_DISPATCH
        getIssue().setCustomFieldValue(fieldId, value)
        ComponentAccessor.getIssueManager().updateIssue(getApplicationUser(user), getIssue(), atl_eventDispatchOption, false)
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
    void addComment(String body, API_User user, EventDispatchOption eventDispatchOption = EventDispatchOption.DO_NOT_DISPATCH) {
        Boolean throwEventBoolean = eventDispatchOption == EventDispatchOption.UPDATE
        ComponentAccessor.getCommentManager().create(getIssue(), getApplicationUser(user), body, throwEventBoolean)
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

    private ApplicationUser getApplicationUser(API_User user) {
        return ((User)user).getUser()
    }
}

