package com.github.wwkarev.jirasharktask.core.field

import com.atlassian.jira.component.ComponentAccessor
import com.github.wwkarev.sharktask.api.field.FieldAccessor as API_FieldAccessor
import com.atlassian.jira.issue.fields.CustomField as JIRA_CustomField
import com.github.wwkarev.sharktask.api.field.FieldNotFoundException

trait FieldAccessor implements API_FieldAccessor {
    abstract FC getFieldConstants()

    @Override
    Field getById(Long id) {
        Field field = getAtById(id)
        if (field == null) {
            throw new FieldNotFoundException()
        }
        return field
    }

    @Override
    Field getAtById(Long id) {
        if (getFieldConstants().isSystemField(id)) {
            return new SystemField(id, getFieldConstants().getSystemFieldName(id))
        } else {
            JIRA_CustomField jiraCustomField = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(id)
            return jiraCustomField ? new CustomField(jiraCustomField) : null
        }
    }
}
