package com.github.wwkarev.jirasharktask.core.test

import com.github.wwkarev.jirasharktask.core.field.FC
import com.github.wwkarev.jirasharktask.core.field.FC as CORE_F

class FC_TEST implements FC {
    private static FC_TEST fieldConstants
    static final Long TEST_SELECT = 25200
    static final String TEST_SELECT_NAME = 'TEST SELECT'

    final Map OPTIONS = [
            (TEST_SELECT): [FIRST: 17900, SECOND: 17901]
    ]

    static FC_TEST getInstance() {
        if (!fieldConstants) {
            fieldConstants = new FC_TEST()
        }
        return fieldConstants
    }

    @Override
    Long getSummary() {
        return 0
    }

    @Override
    Long getAssignee() {
        return 1
    }

    @Override
    Long getCreator() {
        return 2
    }

    @Override
    Long getDescription() {
        return 3
    }

    @Override
    String getSummaryName() {
        return 'summary'
    }

    @Override
    String getAssigneeName() {
        return 'assignee'
    }

    @Override
    String getCreatorName() {
        return 'creator'
    }

    @Override
    String getDescriptionName() {
        return 'description'
    }
}
