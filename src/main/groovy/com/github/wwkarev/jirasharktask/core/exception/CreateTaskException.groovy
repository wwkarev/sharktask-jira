package com.github.wwkarev.jirasharktask.core.exception

import com.atlassian.jira.bc.issue.IssueService
import com.github.wwkarev.sharktask.api.exception.CreateTaskException as API_CreateTaskException

class CreateTaskException extends API_CreateTaskException {
    private IssueService.CreateValidationResult validationResult

    CreateTaskException(IssueService.CreateValidationResult validationResult) {
        this.validationResult = validationResult
    }

    IssueService.CreateValidationResult getValidationResult() {
        return validationResult
    }
}
