package com.app.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.app.entity.*;
@Mapper
public interface CatlogDAO {
    public List<Catlog> getCatlogPager(@Param("skip") int skip,@Param("size") int size);
    public  Catlog getCatlogById(int id);   
    public int getCatlogCount();   
    public int insert(Catlog entity);   
    public int delete(int id);   
    public int update(Catlog entity);
	public List<Catlog> getAllCatlog();
	public List<Catlog> getCatlogByPid(int pid);
}
