package com.github.wwkarev.jirasharktask.core.exception

import com.atlassian.jira.bc.issue.IssueService
import com.github.wwkarev.sharktask.api.exception.TransitionException as API_TransitionException
import groovy.transform.InheritConstructors

@InheritConstructors
class TransitionException extends API_TransitionException {
    private IssueService.TransitionValidationResult validationResult

    TransitionException(IssueService.TransitionValidationResult validationResult) {
        this.validationResult = validationResult
    }

    IssueService.TransitionValidationResult getValidationResult() {
        return validationResult
    }
}
