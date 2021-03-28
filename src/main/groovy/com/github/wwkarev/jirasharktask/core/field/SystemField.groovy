package com.github.wwkarev.jirasharktask.core.field

class SystemField implements Field {
    private Long id
    private String name

    SystemField(Long id, String name) {
        this.id = id
        this.name = name
    }

    @Override
    Long getId() {
        return id
    }

    @Override
    String getName() {
        return name
    }
}
