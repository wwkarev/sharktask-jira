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

class TaskSpec extends SrSpecification {
    @Shared
    FC_TEST fc
    @Shared
    FieldManager fieldManager

    @Shared
    TaskManager manager

    def setup() {
        fc = FC_TEST.getInstance()
        fieldManager = FieldManager.newInstance(fc)
        manager = TaskManager.getInstance(fieldManager)
    }

    def "updateFieldValue"() {
        given:
        UserManager userManager = UserManager.newInstance()
        User user = userManager.getByKey(C.TEST_USER_KEY)

        String NEW_SUMMARY = "YYY"
        String NEW_DESCRIPTION = "DESCRIPTION"
        User NEW_ASSIGNEE = user
        Long NEW_TEST_SELECT_OPTION_ID = fc.OPTIONS[FC_TEST.TEST_SELECT].FIRST

        Params params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'XXX')
        MutableTask createdTask = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)

        when:
        createdTask.updateFieldValue(fc.getSummary(), NEW_SUMMARY, user)
        createdTask.updateFieldValue(fc.getDescription(), NEW_DESCRIPTION, user)
        createdTask.updateFieldValue(fc.getAssignee(), NEW_ASSIGNEE, user)
        createdTask.updateFieldValue(FC_TEST.TEST_SELECT, NEW_TEST_SELECT_OPTION_ID, user)

        then:
        NEW_SUMMARY == createdTask.getSummary()
        NEW_DESCRIPTION == createdTask.getFieldValue(fc.getDescription())
        NEW_ASSIGNEE.getKey() == createdTask.getAssignee().getKey()
        NEW_TEST_SELECT_OPTION_ID == createdTask.getFieldValue(FC_TEST.TEST_SELECT).getOptionId()

        cleanup:
        createdTask.delete(user)
    }

    def "updateFieldValue by null"() {
        given:
        UserManager userManager = UserManager.newInstance()
        User user = userManager.getByKey(C.TEST_USER_KEY)

        String NEW_DESCRIPTION = null
        User NEW_ASSIGNEE = null
        Long NEW_TEST_SELECT_OPTION_ID = null

        Params params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'XXX')
        params.add(ParamId.DESCRIPTION, 'DESCRIPTION')
        params.add(ParamId.ASSIGNEE, user)
        MutableTask createdTask = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)

        when:
        createdTask.updateFieldValue(fc.getDescription(), NEW_DESCRIPTION, user)
        createdTask.updateFieldValue(fc.getAssignee(), NEW_ASSIGNEE, user)
        createdTask.updateFieldValue(FC_TEST.TEST_SELECT, NEW_TEST_SELECT_OPTION_ID, user)

        then:
        NEW_DESCRIPTION == createdTask.getFieldValue(fc.getDescription())
        NEW_ASSIGNEE == createdTask.getAssignee()
        NEW_TEST_SELECT_OPTION_ID == createdTask.getFieldValue(FC_TEST.TEST_SELECT)

        cleanup:
        createdTask.delete(user)
    }

    def "test add linked task"() {
        given:
        UserManager userManager = UserManager.newInstance()
        User user = userManager.getByKey(C.TEST_USER_KEY)

        Params params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'XXX')
        MutableTask createdTask1 = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)

        params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'YYY')
        MutableTask createdTask2 = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)

        params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'ZZZ')
        MutableTask createdTask3 = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)

        when:
        createdTask1.addOutwardLinkedTask(createdTask2, C.RELATED_TO_ISSUELINK_ID, user)
        createdTask1.addInwardLinkedTask(createdTask3, C.RELATED_TO_ISSUELINK_ID, user)

        Task outwardLinkedTask = createdTask1.getOutwardLinkedTaskId(C.RELATED_TO_ISSUELINK_ID)[0]
        Task inwardLinkedTask = createdTask1.getInwardLinkedTaskId(C.RELATED_TO_ISSUELINK_ID)[0]

        then:
        createdTask2.getKey() == outwardLinkedTask.getKey()
        createdTask3.getKey() == inwardLinkedTask.getKey()

        cleanup:
        createdTask1.delete(user)
        createdTask2.delete(user)
        createdTask3.delete(user)
    }

    def "test delete task"() {
        given:
        UserManager userManager = UserManager.newInstance()
        User user = userManager.getByKey(C.TEST_USER_KEY)

        Params params = new Params()
        params.add(ParamId.CREATOR, user)
        params.add(ParamId.SUMMARY, 'XXX')
        MutableTask createdTask = manager.create(C.TEST_PROJECT_ID, C.TEST_ISSUE_TYPE_ID, params)
        String createdTaskKey = createdTask.getKey()

        when:
        createdTask.delete(user)
        manager.getByKey(createdTaskKey)

        then:
        thrown TaskNotFoundException
    }
}
