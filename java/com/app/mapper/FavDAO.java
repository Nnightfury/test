package com.app.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.app.entity.*;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface FavDAO {
    public List<Fav> getFavPager(@Param("skip") int skip,@Param("size") int size);
    public  Fav getFavById(int id);   
    public int getFavCount();   
    public int insert(Fav entity);   
    public int delete(int id);   
    public int update(Fav entity);
	public List<Fav> getAllFav();
	public List<Fav> getFav1(int uid);
	public List<Fav> getFav2(Fav entity);
}
