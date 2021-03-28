package com.github.wwkarev.jirasharktask.core.tasktype


import com.github.wwkarev.jirasharktask.core.test.C
import com.github.wwkarev.sharktask.api.tasktype.TaskTypeNotFoundException
import com.onresolve.scriptrunner.canned.common.admin.SrSpecification
import spock.lang.Shared

class TaskTypeManagerSpec extends SrSpecification {
    @Shared
    TaskTypeManager manager = TaskTypeManager.getInstance()

    def "getById"() {
        when:
        TaskType taskType = manager.getById(C.TEST_ISSUE_TYPE_ID)
        then:
        taskType.getName() == C.TEST_ISSUE_TYPE_NAME
    }

    def "getAtById. Null"() {
        expect:
        manager.getAtById(-1.toLong()) == null
    }

    def "getById. Exception"() {
        when:
        manager.getById(-1.toLong())
        then:
        thrown TaskTypeNotFoundException
    }
}
