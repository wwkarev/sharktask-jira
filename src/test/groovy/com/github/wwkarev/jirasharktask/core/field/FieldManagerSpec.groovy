package com.github.wwkarev.jirasharktask.core.field

import com.github.wwkarev.jirasharktask.core.test.FC_TEST
import com.github.wwkarev.sharktask.api.field.FieldNotFoundException
import com.onresolve.scriptrunner.canned.common.admin.SrSpecification
import spock.lang.Shared

class FieldManagerSpec extends SrSpecification {
    @Shared
    FC_TEST fc = FC_TEST.getInstance()

    @Shared
    FieldManager fieldManager = FieldManager.getInstance(fc)

    def "getById"() {
        expect:
        fieldManager.getById(fieldId).getName() == fieldName
        where:
        fieldId|fieldName
        fc.getSummary()|fc.getSummaryName()
        fc.getAssignee()|fc.getAssigneeName()
        fc.getCreator()|fc.getCreatorName()
        fc.getDescription()|fc.getDescriptionName()
        fc.TEST_SELECT|fc.TEST_SELECT_NAME
    }

    def "getAtById. null"() {
        expect:
        fieldManager.getAtById(-1.toLong()) == null
    }

    def "getById. Exception"() {
        when:
        fieldManager.getById(-1.toLong())
        then:
        thrown FieldNotFoundException
    }
}
