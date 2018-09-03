package com.weido.engineer.pojo;

import org.java_websocket.drafts.Draft;

public class DraftInfo {
    public final String draftName;
    public final Draft draft;

    public DraftInfo(String draftName, Draft draft) {
        this.draftName = draftName;
        this.draft = draft;
    }

    @Override
    public String toString() {
        return draftName;
    }
}
