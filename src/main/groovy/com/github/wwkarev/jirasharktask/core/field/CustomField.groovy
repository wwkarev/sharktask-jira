package com.github.wwkarev.jirasharktask.core.field

import com.atlassian.jira.issue.fields.CustomField as JIRA_CustomField

class CustomField implements Field {
    private JIRA_CustomField jiraField

    CustomField(JIRA_CustomField jiraField) {
        this.jiraField = jiraField
    }

    @Override
    Long getId() {
        jiraField.getIdAsLong()
    }

    @Override
    String getName() {
        jiraField.getName()
    }

    JIRA_CustomField getJiraField() {
        return jiraField
    }
}
