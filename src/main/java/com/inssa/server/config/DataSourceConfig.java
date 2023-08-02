package com.inssa.server.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
/**
 * @EnableTransactionManagement : XML의 <tx:annotation-driven/>와 동일, Spring의 선언적 트랜잭션 처리 기능 활성화
 * 해당 어노테이션을 사용하면 DataSourceTransactionManager로 구성되기 때문에 @Scheduled가 동작하지 않는 이슈가
 * 발생한다. 그래서 트랜잭션 매니저를 JpaTransactionManage로 구현한다
 * @Primary 를 사용해 우선적으로 등록할 트랜잭션 매니져 Bean을 지정
 */
@EnableTransactionManagement
@MapperScan(basePackages = {"com.inssa.server"}, sqlSessionFactoryRef = "testSqlSessionFactory")

/**
 * JpaRepository를 상속받아서 사용할 경우 해당 인터페이스가 존재하는 경로를
 * 명시해줘야 사용가능하다.
 */
@EnableJpaRepositories(
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.inssa.server"}
)
public class DataSourceConfig {

    /**
     * Datasource : Connection Pool을 지원하는 인터페이스
     */
    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * application.yaml의 spring.jpa에 설정한 jpa 설정을 읽음
     */
    @Primary
    @Bean(name = "jpaProperties")
    @ConfigurationProperties(prefix = "spring.jpa")
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    /**
     * SqlSessionFactory : SqlSession을 찍어내는 역할
     * Datasourc를 참조하여 MyBatis와 Mysql 서버를 연동한다. SqlSession을 사용하기 위해 사용한다.
     *
     * @param dataSource
     * @param applicationContext
     */
    @Primary
    @Bean(name = "testSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:config/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * SqlSessionTemplate : SqlSession을 구현하고 코드에서 SqlSession을 대체하는 역할을 한다. 마이바티스 예외처리나 세션의 생명주기 관리
     *
     * @param sqlSessionFactory
     */
    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate apiSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * LocalContainerEntityManagerFactoryBean
     * EntityManager를 생성하는 팩토리
     * SessionFactoryBean과 동일한 역할, Datasource와 mapper를 스캔할 .xml 경로를 지정하듯이
     * datasource와 엔티티가 저장된 폴더 경로를 매핑해주면 된다.
     *
     * @param builder
     * @param dataSource
     * @return
     */
    @Primary
    @Bean(name = "jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("dataSource") DataSource dataSource,
            @Qualifier("jpaProperties") JpaProperties jpaProperties
    ) {
        Map<String, String> properties = jpaProperties.getProperties();
        properties.put("hibernate.physical_naming_strategy", CustomNamingStrategy.class.getName());

        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.inssa.server")
                .persistenceUnit("default")
                .build();
    }

    /**
     * JpaTransactionManager : EntityManagerFactory를 전달받아 JPA에서 트랜잭션을 관리
     */
    @Primary
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(
            @Qualifier("jpaEntityManagerFactory") LocalContainerEntityManagerFactoryBean mfBean) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mfBean.getObject());
        return transactionManager;
    }
}
