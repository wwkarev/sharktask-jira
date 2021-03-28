package com.github.wwkarev.jirasharktask.core.project

import com.github.wwkarev.sharktask.api.project.ProjectManager as API_ProjectManager

final class ProjectManager implements API_ProjectManager, ProjectAccessor, ProjectCreator {
    private static ProjectManager projectManager

    ProjectManager() {
    }

    static ProjectManager getInstance() {
        if (!projectManager) {
            projectManager = new ProjectManager()
        }
        return projectManager
    }
}
