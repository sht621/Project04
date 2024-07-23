/*******************************************************************
***  File Name		: MyBatisConfig.java
***  Version		: V1.0
***  Designer		: 東野　魁耶
***  Date		: 2024.07.23
***  Purpose       	: DataSourceの設定を行い、SQLiteデータベースを利用可能にする
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
     *** Method Name         : dataSource()
     *** Designer            : 東野　魁耶
     *** Date                : 2024.07.23
     *** Function            : このメソッドは、SQLiteのDataSourceを設定し返します。
     *** Return              : DataSource
     ****************************************************************************/
    @Bean(name="dataSource")
    public DataSource dataSource() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        return dataSource;
    }
}
