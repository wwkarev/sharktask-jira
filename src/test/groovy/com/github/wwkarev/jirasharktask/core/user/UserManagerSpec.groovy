package com.github.wwkarev.jirasharktask.core.user


import com.github.wwkarev.jirasharktask.core.test.C
import com.github.wwkarev.sharktask.api.user.UserNotFoundException
import com.onresolve.scriptrunner.canned.common.admin.SrSpecification
import spock.lang.Shared

class UserManagerSpec extends SrSpecification {
    @Shared
    UserManager manager = UserManager.getInstance()

    def "getById"() {
        expect:
        manager.getById(userId).getId() == userId
        manager.getById(userId).getKey() == userKey
        manager.getById(userId).getFirstName() == userFirstName
        manager.getById(userId).getLastName() == userLastName
        manager.getById(userId).getFullName() == userFullName
        where:
        userId |userKey |userFirstName|userLastName|userFullName
        C.TEST_USER_ID |C.TEST_USER_KEY|C.TEST_USER_FIRST_NAME|C.TEST_USER_LAST_NAME|C.TEST_USER_FULL_NAME
    }

    def "getByKey"() {
        expect:
        manager.getByKey(userKey).getId() == userId
        manager.getByKey(userKey).getKey() == userKey
        manager.getByKey(userKey).getFirstName() == userFirstName
        manager.getByKey(userKey).getLastName() == userLastName
        manager.getByKey(userKey).getFullName() == userFullName
        where:
        userId |userKey |userFirstName|userLastName|userFullName
        C.TEST_USER_ID |C.TEST_USER_KEY|C.TEST_USER_FIRST_NAME|C.TEST_USER_LAST_NAME|C.TEST_USER_FULL_NAME
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
        thrown UserNotFoundException
    }

    def "getByKey. Exception"() {
        when:
        manager.getByKey("-1")
        then:
        thrown UserNotFoundException
    }
}
