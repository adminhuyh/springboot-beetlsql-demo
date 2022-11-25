package com.eli.springbootbeetlsqldemo.config;

import com.zaxxer.hikari.HikariDataSource;
import org.beetl.sql.core.ClasspathLoader;
import org.beetl.sql.core.Interceptor;
import org.beetl.sql.core.UnderlinedNameConversion;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.ext.DebugInterceptor;
import org.beetl.sql.ext.spring4.BeetlSqlDataSource;
import org.beetl.sql.ext.spring4.BeetlSqlScannerConfigurer;
import org.beetl.sql.ext.spring4.SqlManagerFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name="datasource")
    public DataSource datasource(Environment env) {

        HikariDataSource hds = new HikariDataSource();
        hds.setJdbcUrl(env.getProperty("spring.datasource.url"));
        hds.setUsername(env.getProperty("spring.datasource.username"));
        hds.setPassword(env.getProperty("spring.datasource.password"));
        hds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        return hds;
    }

//    @Bean(name = "beetlSqlScannerConfigurer")
//    public BeetlSqlScannerConfigurer getBeetlSqlScannerConfigurer() {
//        BeetlSqlScannerConfigurer conf = new BeetlSqlScannerConfigurer();
//        conf.setBasePackage("com.eli.springbootbeetlsqldemo");
//        conf.setDaoSuffix("Dao");
//        conf.setSqlManagerFactoryBeanName("sqlManagerFactoryBean");
//        return conf;
//    }
//
//    @Bean(name = "sqlManagerFactoryBean")
//    @Primary
//    public SqlManagerFactoryBean getSqlManagerFactoryBean(@Qualifier("datasource") DataSource datasource) {
//        SqlManagerFactoryBean factory = new SqlManagerFactoryBean();
//
//        BeetlSqlDataSource source = new BeetlSqlDataSource();
//        source.setMasterSource(datasource);;
//        factory.setCs(source);
//        factory.setDbStyle(new MySqlStyle());
//        factory.setInterceptors(new Interceptor[]{new DebugInterceptor()});
//        factory.setNc(new UnderlinedNameConversion());
//        factory.setSqlLoader(new ClasspathLoader("/sql"));
//        return factory;
//    }
//
//
//    @Bean(name="txManager")
//    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("datasource") DataSource datasource) {
//        DataSourceTransactionManager dsm = new DataSourceTransactionManager();
//        dsm.setDataSource(datasource);
//        return dsm;
//    }
}
