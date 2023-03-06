package org.ferbator;

import java.util.Date;

public class Note {
    private String title;
    private String content;
    private Date created;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.created = new Date();
    }

    public Note(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.created = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format("%s '%s'\n%s\n", created, title, content);
    }
}

