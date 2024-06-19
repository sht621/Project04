/*******************************************************************
***  File Name		: MyBatisConfig.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.06.18
***  Purpose       	: SQLiteのとの接続設定
***
*******************************************************************/

package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

@Configuration
public class MyBatisConfig {
    @Autowired
    private DataSourceProperties dataSourceProperties;
    
    /****************************************************************************
     *** Method Name         : dataSource
     *** Designer            : 東野　魁耶
     *** Date                : 2024.06.18
     *** Function            : SQLiteのデータソースを設定し、SpringコンテナにBeanとして登録する
     *** Return              : DataSource - SQLiteのデータソースを返す
     ****************************************************************************/
    @Bean(name="dataSource")
    public DataSource dataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        return dataSource;
    }
}
