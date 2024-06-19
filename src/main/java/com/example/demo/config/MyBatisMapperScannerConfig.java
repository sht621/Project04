/*******************************************************************
***  File Name		: MyBatisConfig.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.06.18
***  Purpose       	: MyBatisのMapperインターフェースをスキャンし、登録する
***
*******************************************************************/

package com.example.demo.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyBatisMapperScannerConfig {
	
	/****************************************************************************
	    *** Method Name         : mapperScannerConfigurer
	    *** Designer            : 東野　魁耶
	    *** Date                : 2024.06.18
	    *** Function            : Mapperインターフェースをスキャンし、Springコンテナに登録する
	    *** Return              : MapperScannerConfigurer - Mapperスキャナ設定を返す
	    ****************************************************************************/
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");

        mapperScannerConfigurer.setBasePackage("com.example.demo.mapper");

        return mapperScannerConfigurer;
    }

}
