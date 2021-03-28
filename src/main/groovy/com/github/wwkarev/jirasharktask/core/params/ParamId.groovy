package com.github.wwkarev.jirasharktask.core.params;

class ParamId {
    private static final Long CREATOR = 1
    private static final Long ASSIGNEE = 2
    private static final Long DESCRIPTION = 3
    private static final Long SUMMARY = 4

    static Long getCREATOR() {
        return CREATOR
    }

    static Long getASSIGNEE() {
        return ASSIGNEE
    }

    static Long getSUMMARY() {
        return SUMMARY
    }

    static Long getDESCRIPTION() {
        return DESCRIPTION
    }
}
