package com.app.entity;

import java.util.List;

public class Cates {
    public int id;
    public String title;
    public int minval;
    public int maxval;

    public int getMinval() {
        return minval;
    }

    public void setMinval(int minval) {
        this.minval = minval;
    }

    public int getMaxval() {
        return maxval;
    }

    public void setMaxval(int maxval) {
        this.maxval = maxval;
    }

    public List<Cates> subs;

    public List<Cates> getSubs() {
        return subs;
    }

    public void setSubs(List<Cates> subs) {
        this.subs = subs;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String thumb;

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getThumb() {
        return thumb;
    }

    public String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String created;

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public String updated;

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdated() {
        return updated;
    }


    public String pid;

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}