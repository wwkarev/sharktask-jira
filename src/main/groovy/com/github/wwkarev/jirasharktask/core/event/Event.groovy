package com.github.wwkarev.jirasharktask.core.event

import com.github.wwkarev.sharktask.api.event.Event as API_Event
import com.atlassian.jira.event.issue.IssueEvent as JIRA_IssueEvent
import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.jirasharktask.core.task.MutableTask

class Event implements API_Event {
    private JIRA_IssueEvent jiraIssueEvent
    private FieldManager fieldManager

    Event(JIRA_IssueEvent jiraIssueEvent, FieldManager fieldManager) {
        this.jiraIssueEvent = jiraIssueEvent
        this.fieldManager = fieldManager
    }

    @Override
    EventType getEventType() {
        return new EventType(jiraIssueEvent.getEventTypeId())
    }

    @Override
    MutableTask getTask() {
        new MutableTask(jiraIssueEvent.getIssue(), fieldManager)
    }

    JIRA_IssueEvent getJiraIssueEvent() {
        return jiraIssueEvent
    }
}
