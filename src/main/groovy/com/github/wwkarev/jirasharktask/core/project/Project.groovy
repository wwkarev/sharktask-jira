package com.github.wwkarev.jirasharktask.core.project

import com.github.wwkarev.sharktask.api.project.Project as API_Project

import com.atlassian.jira.project.Project as Atl_Project

class Project implements API_Project {
    private Atl_Project project

    Project(Atl_Project project) {
        this.project = project
    }

    @Override
    Long getId() {
        project.getId()
    }

    @Override
    String getKey() {
        project.getKey()
    }

    @Override
    String getName() {
        project.getName()
    }
}
