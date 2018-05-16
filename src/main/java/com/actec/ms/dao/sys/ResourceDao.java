package com.actec.ms.dao.sys;

import com.actec.ms.dao.CreateTableDao;
import com.actec.ms.utils.CommonMapper;
import com.actec.ms.vo.sys.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by donglin.wang on 2017/4/20.
 */
@Repository
public interface ResourceDao extends CommonMapper<Resource>,CreateTableDao {

   List<Resource> list(@Param("userId") int userId);

   List<String> listName(@Param("userId") int userId);

   List<String> listAllName(@Param("userId") int userId);

   void addAuth(@Param("userId") int userId, @Param("resourceId") int resourceId);

   void deleteAuth(@Param("userId") int userId, @Param("resourceId") int resourceId);

   Integer findIdByUrl(@Param("url") String url, @Param("method") String method);

   Integer userResourceCount(@Param("userId") int userId, @Param("resourceId") int resourceId);

}
