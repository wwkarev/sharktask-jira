package com.github.wwkarev.jirasharktask.core.exception

import com.atlassian.jira.bc.issue.IssueService
import com.github.wwkarev.sharktask.api.exception.DeleteTaskException as API_DeleteTaskException
import groovy.transform.InheritConstructors

@InheritConstructors
class DeleteTaskException extends API_DeleteTaskException {
    private IssueService.DeleteValidationResult validationResult

    DeleteTaskException(IssueService.DeleteValidationResult validationResult) {
        this.validationResult = validationResult
    }

    IssueService.DeleteValidationResult getValidationResult() {
        return validationResult
    }
}
