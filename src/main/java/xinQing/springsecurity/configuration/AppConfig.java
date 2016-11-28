package xinQing.springsecurity.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * ApplicationContext
 *
 * Created by xuan on 16-11-23.
 */
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@ComponentScan({"xinQing.springsecurity.dao", "xinQing.springsecurity.service"})
@PropertySource("classpath:application.properties")
public class AppConfig implements EnvironmentAware {

    @Autowired
    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    /**
     * 数据源
     *
     * @return  DruidDataSource
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(env.getProperty("mysql.jdbc.driverClass"));
        dataSource.setUrl(env.getProperty("mysql.jdbc.url"));
        dataSource.setUsername(env.getProperty("mysql.jdbc.username"));
        dataSource.setPassword(env.getProperty("mysql.jdbc.password"));
        return dataSource;
    }

    /**
     * mybatis sqlSessionFactory
     *
     * @throws Exception 异常
     * @return SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        // 设置别名
        bean.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));

        // 分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        // 添加PageHelper分页插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        // 扫描*mapper.xml
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(env.getProperty("mybatis.mapperLocations")));
        return bean.getObject();
    }

    /**
     * mybatis MapperScanner
     * 扫描mapper接口
     *
     * @return MapperScannerConfigurer
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage(env.getProperty("mybatis.mapperScannerBasePackage"));
        return mapperScannerConfigurer;
    }

    /**
     * 事务
     *
     * @return DataSourceTransactionManager
     */
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
