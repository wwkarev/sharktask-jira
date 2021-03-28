package com.github.wwkarev.jirasharktask.core.project

import com.github.wwkarev.sharktask.api.project.Project as API_Project

import com.atlassian.jira.project.Project as JiRA_Project

class Project implements API_Project {
    private JiRA_Project jiraProject

    Project(JiRA_Project jiraProject) {
        this.jiraProject = jiraProject
    }

    @Override
    Long getId() {
        jiraProject.getId()
    }

    @Override
    String getKey() {
        jiraProject.getKey()
    }

    @Override
    String getName() {
        jiraProject.getName()
    }

    JiRA_Project getJiraProject() {
        return jiraProject
    }
}
