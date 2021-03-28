package com.github.wwkarev.jirasharktask.core.field

import com.github.wwkarev.sharktask.api.field.FieldManager as API_FieldManager

final class FieldManager implements API_FieldManager, FieldAccessor, FieldCreator {
    private static FieldManager fieldManager
    private FC fieldConstants

    FieldManager(FC fieldConstants) {
        this.fieldConstants = fieldConstants
    }

    static FieldManager getInstance(FC fieldConstants) {
        if (!fieldManager) {
            fieldManager = new FieldManager(fieldConstants)
        }
        return fieldManager
    }

    @Override
    FC getFieldConstants() {
        return fieldConstants
    }
}
