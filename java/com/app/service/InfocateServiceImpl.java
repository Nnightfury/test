package com.app.service;

import java.util.List;
import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.app.entity.Infocate;
import com.app.mapper.InfocateDAO;

@Service
public class InfocateServiceImpl implements InfocateService {
    @Resource
    InfocateDAO infocateDao;

    public List<Infocate> getInfocatePager(@Param("skip") int skip, @Param("size") int size) {
        return infocateDao.getInfocatePager(skip, size);
    }

    public Infocate getInfocateById(int id) {
        return infocateDao.getInfocateById(id);
    }

    public int getInfocateCount() {
        return infocateDao.getInfocateCount();
    }

    public int insert(Infocate entity) {
        return infocateDao.insert(entity);
    }

    public int delete(int id) {
        return infocateDao.delete(id);
    }

    public int update(Infocate entity) {
        return infocateDao.update(entity);
    }

    public List<Infocate> getAllInfocate() {
        return infocateDao.getAllInfocate();
    }

}
