package com.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.app.entity.*;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface InfocateDAO {
    public List<Infocate> getInfocatePager(@Param("skip") int skip, @Param("size") int size);

    public Infocate getInfocateById(int id);

    public int getInfocateCount();

    public int insert(Infocate entity);

    public int delete(int id);

    public int update(Infocate entity);

    public List<Infocate> getAllInfocate();
}
