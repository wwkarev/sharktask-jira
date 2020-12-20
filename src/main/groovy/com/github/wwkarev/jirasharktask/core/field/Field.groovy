package com.github.wwkarev.jirasharktask.core.field

import com.atlassian.jira.issue.fields.CustomField
import com.github.wwkarev.sharktask.api.field.Field as API_Field

class Field implements API_Field {
    private CustomField field

    Field(CustomField field) {
        this.field = field
    }

    @Override
    Long getId() {
        field.getIdAsLong()
    }

    @Override
    String getName() {
        field.getName()
    }
}
