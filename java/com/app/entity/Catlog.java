package com.app.entity;

import java.util.List;

public class Catlog {
 public int id;
 public String title;
 public List<Catlog> subs;
 
 public List<Catlog> getSubs() {
	return subs;
}
public void setSubs(List<Catlog> subs) {
	this.subs = subs;
}
public Catlog p;
 
 public Catlog getP() {
	return p;
}
public void setP(Catlog p) {
	this.p = p;
}
public void setTitle(String title){ 
 this.title=title;
 } 
 public String getTitle(){
 return title;
 } 
 public String thumb;
 public void setThumb(String thumb){ 
 this.thumb=thumb;
 } 
 public String getThumb(){
 return thumb;
 } 
 public String description;
 public void setDescription(String description){ 
 this.description=description;
 } 
 public String getDescription(){
 return description;
 } 
 public String created;
 public void setCreated(String created){ 
 this.created=created;
 } 
 public String getCreated(){
 return created;
 } 
 public String updated;
 public void setUpdated(String updated){ 
 this.updated=updated;
 } 
 public String getUpdated(){
 return updated;
 } 
 public String minval;
 public void setMinval(String minval){ 
 this.minval=minval;
 } 
 public String getMinval(){
 return minval;
 } 
 public String maxval;
 public void setMaxval(String maxval){ 
 this.maxval=maxval;
 } 
 public String getMaxval(){
 return maxval;
 } 
 public String pid;
 public void setPid(String pid){ 
 this.pid=pid;
 } 
 public String getPid(){
 return pid;
 } 
public void setId(int id) {
this.id = id;
}

public int getId() {
return id;
}

}