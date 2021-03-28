package com.github.wwkarev.jirasharktask.core.field

trait FC {
    abstract Long getSummary()
    abstract Long getAssignee()
    abstract Long getCreator()
    abstract Long getDescription()

    abstract String getSummaryName()
    abstract String getAssigneeName()
    abstract String getCreatorName()
    abstract String getDescriptionName()

    Boolean isSystemField(Long fieldId) {
        return [getSummary(), getAssignee(), getCreator(), getDescription()].contains(fieldId)
    }

    String getSystemFieldName(Long fieldId) {
        String fieldName
        switch (fieldId) {
            case getSummary():
                fieldName = getSummaryName()
                break
            case getAssignee():
                fieldName = getAssigneeName()
                break
            case getCreator():
                fieldName = getCreatorName()
                break
            case getDescription():
                fieldName = getDescriptionName()
                break
        }

        return fieldName
    }

    List<String> getSelectCustomFieldTypes() {
        return [
            'com.atlassian.jira.plugin.system.customfieldtypes:select'
        ]
    }
}
