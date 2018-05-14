package com.actec.pnmp.dao;


import com.actec.pnmp.bean.sys.User;
import com.actec.pnmp.utils.CommonMapper;

import java.util.List;
import java.util.Map;
/**
 * Created by Yangchao on 2018-4-10.
 */
public interface UserDao extends CommonMapper<User> {

	List<User> getByMap(Map<String, Object> map);
	User getById(Integer id);
	Integer create(User user);
	int update(User user);
//	int delete(Integer id);
	User getByUserName(String userName);
}