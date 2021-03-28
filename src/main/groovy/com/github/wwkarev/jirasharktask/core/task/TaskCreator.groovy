package com.github.wwkarev.jirasharktask.core.task

import com.atlassian.jira.bc.issue.IssueService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueInputParameters
import com.github.wwkarev.jirasharktask.core.exception.CreateTaskException
import com.github.wwkarev.jirasharktask.core.field.FC
import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.jirasharktask.core.params.ParamId
import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.sharktask.api.params.Params
import com.github.wwkarev.sharktask.api.task.TaskCreator as API_TaskCreator

trait TaskCreator implements API_TaskCreator {
    abstract FieldManager getFieldManager()

    @Override
    MutableTask create(Long projectId, Long taskTypeId, Params params) {
        User creator = params.get(ParamId.CREATOR)

        IssueService issueService = ComponentAccessor.getIssueService()

        IssueInputParameters issueIP = createIssueInputParameters(projectId, taskTypeId, params)

        IssueService.CreateValidationResult validationResult = issueService.validateCreate(creator.getUser(), issueIP)
        if (!validationResult.isValid()) {
            throw new CreateTaskException(validationResult)
        }

        IssueService.IssueResult result = issueService.create(creator.getUser(), validationResult)
        return new MutableTask(result.getIssue(), getFieldManager())
    }

    private IssueInputParameters createIssueInputParameters(Long projectId, Long taskTypeId, Params params) {
        IssueService issueService = ComponentAccessor.getIssueService()
        IssueInputParameters issueIP = issueService.newIssueInputParameters()
        issueIP.setProjectId(projectId)
        issueIP.setIssueTypeId(taskTypeId.toString())

        params.getIdList().each{Long paramId ->
            Object value = params.get(paramId)
            switch (paramId) {
                case ParamId.CREATOR:
                    issueIP.setReporterId(((User)value).getKey())
                    break
                case ParamId.ASSIGNEE:
                    issueIP.setAssigneeId(((User)value).getKey())
                    break
                case ParamId.DESCRIPTION:
                    issueIP.setDescription(value)
                    break
                case ParamId.SUMMARY:
                    issueIP.setSummary(value)
                    break
                default:
                    issueIP.addCustomFieldValue(paramId.toString(), value)
            }
        }
        return issueIP
    }
}
