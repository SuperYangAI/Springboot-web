package com.actec.ms.utils;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by Yangchao on 2018-4-19.
 */
public interface CommonMapper<T> extends Mapper<T>,MySqlMapper<T> {

}
