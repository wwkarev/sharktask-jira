package com.github.wwkarev.jirasharktask.core.comment

import com.github.wwkarev.jirasharktask.core.user.User
import com.github.wwkarev.sharktask.api.comment.Comment as API_Comment
import com.github.wwkarev.sharktask.api.user.User as Atl_User

import com.atlassian.jira.issue.comments.Comment as Atl_Comment

class Comment implements API_Comment {
    private Atl_Comment comment

    Comment(API_Comment comment) {
        this.comment = comment
    }

    @Override
    Long getId() {
        return comment.getId()
    }

    @Override
    String getBody() {
        return comment.getBody()
    }

    @Override
    Atl_User getAuthor() {
        return new User(comment.getAuthorApplicationUser())
    }
}
