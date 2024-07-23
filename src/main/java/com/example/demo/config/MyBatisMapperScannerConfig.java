/*******************************************************************
***  File Name		: MyBatisMapperScannerConfig.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.06.18
***  Purpose       	: MyBatisのMapperインターフェースをスキャンし、自動的に検出する設定を行う
***
*******************************************************************/
/*
*** Revision :
*** V1.0 : 東野魁耶, 2024.06.18
*
*/

package com.example.demo.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyBatisMapperScannerConfig {
	
	/****************************************************************************
     *** Method Name         : mapperScannerConfigurer()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.07.23
     *** Function            : このメソッドは、MapperScannerConfigurerを設定し、MyBatisのMapperインターフェースを指定されたパッケージからスキャンします。
     ***                       sqlSessionFactoryの名前を設定し、Mapperのベースパッケージを指定します。
     *** Return              : MapperScannerConfigurer
     ****************************************************************************/
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");

        mapperScannerConfigurer.setBasePackage("com.example.demo.mapper");

        return mapperScannerConfigurer;
    }

}
