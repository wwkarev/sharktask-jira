package com.github.wwkarev.jirasharktask.core.status

import com.github.wwkarev.jirasharktask.core.test.C
import com.github.wwkarev.sharktask.api.status.StatusNotFoundException
import com.onresolve.scriptrunner.canned.common.admin.SrSpecification
import spock.lang.Shared

class StatusManagerSpec extends SrSpecification {
    @Shared
    StatusManager statusManager = StatusManager.getInstance()

    def "getById"() {
        when:
        Status status = statusManager.getById(C.DONE_STATUS_ID)
        then:
        status.getName() == C.DONE_STATUS_NAME
    }

    def "getAtById. Null"() {
        expect:
        statusManager.getAtById(-1.toLong()) == null
    }

    def "getById. Exception"() {
        when:
        statusManager.getById(-1.toLong())
        then:
        thrown StatusNotFoundException
    }
}
