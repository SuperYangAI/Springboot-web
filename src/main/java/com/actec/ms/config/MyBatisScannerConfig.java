package com.actec.ms.config;
/**
 * 通用Mapper导入
 * 1、maven引入tk mapper包
 * 2、org.mybatis.spring.mapper.MapperScannerConfigurer改成
 * tk.mybatis.spring.mapper.MapperScannerConfigurer
 * ***/
import tk.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Yangchao on 2018-4-10.
 */
@Configuration
public class MyBatisScannerConfig {

    @Bean
    public MapperScannerConfigurer MapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.actec.ms.dao");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }
}
