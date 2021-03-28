package com.github.wwkarev.jirasharktask.core.task

import com.github.wwkarev.jirasharktask.core.field.FieldManager
import com.github.wwkarev.jirasharktask.core.params.ParamId
import com.github.wwkarev.jirasharktask.core.params.Params
import com.github.wwkarev.jirasharktask.core.test.C
import com.github.wwkarev.jirasharktask.core.test.FC_TEST
import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.jirasharktask.core.user.UserManager
import com.github.wwkarev.sharktask.api.task.TaskNotFoundException
import com.onresolve.scriptrunner.canned.common.admin.SrSpecification
import spock.lang.Shared

class TaskManagerSpec extends SrSpecification {
    @Shared
    FC_TEST fc

    @Shared
    TaskManager manager
    @Shared
    FieldManager fieldManager

    def setup() {
        fc = FC_TEST.getInstance()
        fieldManager = FieldManager.newInstance(fc)
        manager = TaskManager.getInstance(fieldManager)
    }

    def "create. getById. getByKey."() {
        given:
        UserManager userManager = UserManager.newInstance()
        User user = userManager.getByKey(C.TEST_USER_KEY)
        Params params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'XXX')

        when:
        MutableTask createdTask = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)
        MutableTask fetchedTaskById = manager.getById(createdTask.getId())
        MutableTask fetchedTaskByKey = manager.getByKey(createdTask.getKey())

        then:
        createdTask.getId() == fetchedTaskById.getId()
        createdTask.getKey() == fetchedTaskById.getKey()
        createdTask.getSummary() == fetchedTaskById.getSummary()
        createdTask.getId() == fetchedTaskByKey.getId()
        createdTask.getKey() == fetchedTaskByKey.getKey()
        createdTask.getSummary() == fetchedTaskByKey.getSummary()

        cleanup:
        createdTask.delete(user)
    }

    def "create. Assignee, description."() {
        given:
        UserManager userManager = UserManager.newInstance()
        User user = userManager.getByKey(C.TEST_USER_KEY)
        Params params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'XXX')
        params.add(ParamId.DESCRIPTION, 'YYY')
        params.add(ParamId.ASSIGNEE, user)

        when:
        MutableTask createdTask = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)

        then:
        createdTask.getFieldValue(fc.getDescription()) == 'YYY'
        createdTask.getAssignee().getKey() == user.getKey()

        cleanup:
        createdTask.delete(user)
    }

    def "getAtById. Null"() {
        expect:
        manager.getAtById(-1.toLong()) == null
    }

    def "getAtByKey. Null"() {
        expect:
        manager.getAtByKey("-1") == null
    }

    def "getById. Exception"() {
        when:
        manager.getById(-1.toLong())
        then:
        thrown TaskNotFoundException
    }

    def "getByKey. Exception"() {
        when:
        manager.getByKey("-1")
        then:
        thrown TaskNotFoundException
    }
}
