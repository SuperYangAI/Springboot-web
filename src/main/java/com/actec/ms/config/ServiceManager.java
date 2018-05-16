package com.actec.ms.config;

import com.actec.ms.dao.TableDao;
import com.actec.ms.dao.sys.ResourceDao;
import com.actec.ms.dao.sys.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class ServiceManager {
    private static Logger logger = LoggerFactory.getLogger(ServiceManager.class);

    @Autowired
    private TableDao tableDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ResourceDao resourceDao;


    @PostConstruct
    public void initMySqlTable() {
        createTable();
        tableUpdate();
        logger.info("---------------------init method invoked----------------------");
    }

    private void createTable() {
//        if (tableDao.tableNum("user") == 0){
//            userDao.createTable();
//            userDao.initTable();
//        }
//        if (tableDao.tableNum("resoure") == 0){
//            resourceDao.createTable();
//            resourceDao.initTable();
//        }
    }

    @PreDestroy
    public void preDestroy() {
        logger.info("--------------------preDestroy method invoked----------------------");
    }

    private void tableUpdate() {

    }
}
