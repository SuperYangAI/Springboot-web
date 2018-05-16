package com.actec.pnmp.service;

import com.actec.pnmp.entity.sys.User;
import com.actec.pnmp.dao.UserDao;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * Created by Yangchao on 2018-4-10.
 */
@Service
public class UserService {
    @Autowired
	private UserDao userDao;
	
	public List<User> getByMap(Map<String,Object> map) {
	    return userDao.getByMap(map);
	}
	
	public User getById(Integer id) {
		return userDao.getById(id);
	}
	
	public User create(User user) {
		userDao.create(user);
		return user;
	}
	
	public User update(User user) {
		userDao.update(user);
		return user;
	}
	
	public int delete(Integer id) {
		return userDao.deleteByPrimaryKey(id);
	}

	public User getByUserName(String userName) {
		return userDao.getByUserName(userName);
	}

	public List<User> findAll(){
		return userDao.selectAll();
	}

	public Page<User> findPage(User user){
		int unm = userDao.selectCount(user);
		if (user.getPage() != null && user.getRows() != null) {
			PageHelper.startPage(user.getPage(), user.getRows());
		}
		List<User> users = userDao.select(user);
		Page<User> userPage = new Page<>(user.getPage(), user.getRows());
		return userPage;
	}

}