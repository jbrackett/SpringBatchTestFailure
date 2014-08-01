package com.batch.failure.config;

import com.batch.failure.writer.DoNothingWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MinimumJobConfig {

  @Bean
  public Job testJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {

    JobBuilderFactory jobs = new JobBuilderFactory(jobRepository);

    StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository,
        transactionManager);
    Step step = stepBuilderFactory.get("test").chunk(1).reader(new FlatFileItemReader<Object>())
        .writer(new DoNothingWriter()).build();
    Step step2 = stepBuilderFactory.get("test2").chunk(1).reader(new FlatFileItemReader<Object>())
        .writer(new DoNothingWriter()).build();

    // Fails with exception("No Step found with name: [test]
    return jobs.get("testJob").incrementer(new RunIdIncrementer()).start(step).on("*").to(step2)
        .build().build();

    // Runs successfully
    /* return jobs.get("testJob").incrementer(new RunIdIncrementer()).start(step).next(step2).build();*/

  }

  @Bean
  public JobLauncherTestUtils jobLauncherTestUtils(Job job, JobLauncher jobLauncher,
      JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    JobLauncherTestUtils utils = new JobLauncherTestUtils();
    utils.setJob(job);
    utils.setJobLauncher(jobLauncher);
    utils.setJobRepository(jobRepository);

    return utils;
  }

}
