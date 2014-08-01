package com.batch.failure.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class TestBatchConfig implements BatchConfigurer {

  @Bean(name = "batchDataSource", destroyMethod = "shutdown")
  public DataSource batchDataSource() {
    return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName("batchDB")
        .addScript("org/springframework/batch/core/schema-hsqldb.sql").build();
  }

  @Bean
  @Override
  public JobExplorer getJobExplorer() throws Exception {
    JobExplorerFactoryBean explorer = new JobExplorerFactoryBean();
    explorer.setDataSource(batchDataSource());
    explorer.afterPropertiesSet();

    return explorer.getObject();
  }

  @Override
  public JobLauncher getJobLauncher() throws Exception {
    SimpleJobLauncher launcher = new SimpleJobLauncher();
    launcher.setJobRepository(getJobRepository());
    launcher.afterPropertiesSet();

    return launcher;
  }

  @Override
  public JobRepository getJobRepository() throws Exception {
    JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
    factory.setDataSource(batchDataSource());
    factory.setTransactionManager(getTransactionManager());
    factory.afterPropertiesSet();
    return (JobRepository) factory.getObject();
  }

  @Override
  public PlatformTransactionManager getTransactionManager() throws Exception {
    return new DataSourceTransactionManager(batchDataSource());
  }

}
