package com.app.entity;

import java.util.List;

public class Infocate {
    public int id;
    public String title;
    public List<News> list;
    public int showtype;
    public String attach;
public News f;


    public News getF() {
	return f;
}

public void setF(News f) {
	this.f = f;
}

	public int getShowtype() {
        return showtype;
    }

    public void setShowtype(int showtype) {
        this.showtype = showtype;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public List<News> getList() {
        return list;
    }

    public void setList(List<News> list) {
        this.list = list;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String created;

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

}