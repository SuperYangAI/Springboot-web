package com.actec.ms.dao.sys;

import com.actec.ms.dao.CreateTableDao;
import com.actec.ms.utils.CommonMapper;
import com.actec.ms.vo.sys.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by donglin.wang on 2017/4/20.
 */
@Repository
public interface UserDao extends CommonMapper<User>,CreateTableDao {

   User login(@Param("name") String name, @Param("pwd") String password);

}
