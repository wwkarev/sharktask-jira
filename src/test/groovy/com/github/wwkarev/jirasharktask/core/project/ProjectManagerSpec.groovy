package com.github.wwkarev.jirasharktask.core.project

import com.github.wwkarev.jirasharktask.core.test.C
import com.github.wwkarev.jirasharktask.core.test.FC_TEST
import com.github.wwkarev.sharktask.api.project.ProjectNotFoundException
import com.onresolve.scriptrunner.canned.common.admin.SrSpecification
import spock.lang.Shared

class ProjectManagerSpec extends SrSpecification {
    @Shared
    ProjectManager projectManager = ProjectManager.getInstance()

    def "getById"() {
        expect:
        projectManager.getById(projectId).getId() == projectId
        projectManager.getById(projectId).getKey() == projectKey
        projectManager.getById(projectId).getName() == projectName
        where:
        projectId |projectKey|projectName
        C.TEST_PROJECT_ID |C.TEST_PROJECT_KEY|C.TEST_PROJECT_NAME
    }

    def "getByKey"() {
        expect:
        projectManager.getByKey(projectKey).getId() == projectId
        projectManager.getByKey(projectKey).getKey() == projectKey
        projectManager.getByKey(projectKey).getName() == projectName
        where:
        projectId |projectKey|projectName
        C.TEST_PROJECT_ID |C.TEST_PROJECT_KEY|C.TEST_PROJECT_NAME
    }

    def "getAtById. Null"() {
        expect:
        projectManager.getAtById(-1.toLong()) == null
    }

    def "getAtByKey. Null"() {
        expect:
        projectManager.getAtByKey("-1") == null
    }

    def "getById. Exception"() {
        when:
        projectManager.getById(-1.toLong())
        then:
        thrown ProjectNotFoundException
    }

    def "getByKey. Exception"() {
        when:
        projectManager.getByKey("-1")
        then:
        thrown ProjectNotFoundException
    }
}
