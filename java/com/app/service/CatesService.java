package com.app.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.app.entity.Cates;

public interface CatesService {
    public List<Cates> getCatesPager(@Param("skip") int skip,
                                     @Param("size") int size);

    public Cates getCatesById(int id);

    public int getCatesCount();

    public int insert(Cates entity);

    public int delete(int id);

    public int update(Cates entity);

    public List<Cates> getAllCates();

    public List<Cates> getByPid(String pid);

    public List<Cates> getRange(int val);
}
