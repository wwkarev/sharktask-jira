package com.github.wwkarev.jirasharktask.core.attachment

import com.github.wwkarev.sharktask.api.attachment.Attachment as API_Attachment
import com.atlassian.jira.issue.attachment.Attachment as Atl_Attachment

class Attachment implements API_Attachment  {
    private Atl_Attachment attachment

    Attachment(com.atlassian.jira.issue.attachment.Attachment attachment) {
        this.attachment = attachment
    }

    @Override
    Long getId() {
        return attachment.getId()
    }

    @Override
    String getName() {
        return attachment.getFilename()
    }

    @Override
    File getFile() {
        return null
    }

    Atl_Attachment getAttachment() {
        return attachment
    }
}
