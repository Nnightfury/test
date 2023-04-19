package com.app.service;
import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.app.entity.Catlog;
import com.app.mapper.CatlogDAO;
import com.app.service.CatlogService;
@Service
public class CatlogServiceImpl implements CatlogService{
	@Resource CatlogDAO catlogDao;
public List<Catlog> getCatlogPager(@Param("skip") int skip,@Param("size") int size){
return catlogDao.getCatlogPager(skip, size);
}
public  Catlog getCatlogById(int id){
return catlogDao.getCatlogById(id);
}
public int getCatlogCount(){
return catlogDao.getCatlogCount();
}
public int insert(Catlog entity){
return catlogDao.insert(entity);
}
public int delete(int id){
return catlogDao.delete(id);
}
public int update(Catlog entity){
return catlogDao.update(entity);
}
public List<Catlog> getAllCatlog(){
return catlogDao.getAllCatlog();
}

public List<Catlog> getCatlogByPid(int pid) {
	// TODO Auto-generated method stub
	return catlogDao.getCatlogByPid(pid);
}

}
