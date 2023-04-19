package com.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.app.entity.Infocate;

public interface InfocateService {
    public List<Infocate> getInfocatePager(@Param("skip") int skip, @Param("size") int size);

    public Infocate getInfocateById(int id);

    public int getInfocateCount();

    public int insert(Infocate entity);

    public int delete(int id);

    public int update(Infocate entity);

    public List<Infocate> getAllInfocate();
}
