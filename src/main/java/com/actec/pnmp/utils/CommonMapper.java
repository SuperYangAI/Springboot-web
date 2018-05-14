package com.actec.pnmp.utils;

import com.github.pagehelper.Page;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by Yangchao on 2018-4-19.
 */
public interface CommonMapper<T> extends Mapper<T>,MySqlMapper<T> {
    public Page<T> findPage(T entity);
}
