package com.app.service;

import java.util.List;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.app.entity.Cates;
import com.app.mapper.CatesDAO;

@Service
public class CatesServiceImpl implements CatesService {
    @Resource
    CatesDAO catesDao;

    public List<Cates> getCatesPager(@Param("skip") int skip,
                                     @Param("size") int size) {
        return catesDao.getCatesPager(skip, size);
    }

    public Cates getCatesById(int id) {
        return catesDao.getCatesById(id);
    }

    public int getCatesCount() {
        return catesDao.getCatesCount();
    }

    public int insert(Cates entity) {
        return catesDao.insert(entity);
    }

    public int delete(int id) {
        return catesDao.delete(id);
    }

    public int update(Cates entity) {
        return catesDao.update(entity);
    }

    public List<Cates> getAllCates() {
        return catesDao.getAllCates();
    }

    public List<Cates> getByPid(String pid) {
        // TODO Auto-generated method stub
        return catesDao.getByPid(pid);
    }

    
    public List<Cates> getRange(int val) {
        return catesDao.getRange(val);
    }

}
