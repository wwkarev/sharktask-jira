package com.github.wwkarev.jirasharktask.core.project

import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.project.Project as JIRA_Project
import com.github.wwkarev.sharktask.api.project.ProjectAccessor as API_ProjectAccessor
import com.github.wwkarev.sharktask.api.project.ProjectNotFoundException

trait ProjectAccessor implements API_ProjectAccessor {
    @Override
    Project getById(Long id) {
        Project project = getAtById(id)
        if (project == null) {
            throw new ProjectNotFoundException()
        }
        return project
    }

    @Override
    Project getByKey(String key) {
        Project project = getAtByKey(key)
        if (project == null) {
            throw new ProjectNotFoundException()
        }
        return project
    }

    @Override
    Project getAtById(Long id) {
        JIRA_Project jiraProject = ComponentAccessor.getProjectManager().getProjectObj(id)
        return jiraProject ? new Project(jiraProject) : null
    }

    @Override
    Project getAtByKey(String key) {
        JIRA_Project jiraProject = ComponentAccessor.getProjectManager().getProjectObjByKey(key)
        return jiraProject ? new Project(jiraProject) : null
    }
}
