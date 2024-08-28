package org.cherrygirl.domain;

import lombok.AllArgsConstructor;

/**
 * @author nannanness
 */
@AllArgsConstructor
public class LiveItemDO {
    private long uid;
    private String uname;
    private String face;
    private String link;
    private String title;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
