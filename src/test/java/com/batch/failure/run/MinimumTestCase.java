package com.batch.failure.run;

import static org.junit.Assert.assertEquals;

import com.batch.failure.config.MinimumJobConfig;
import com.batch.failure.config.TestBatchConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBatchConfig.class, MinimumJobConfig.class })
public class MinimumTestCase {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void test() {
    JobExecution jobExecution = jobLauncherTestUtils.launchStep("test");
    assertEquals("FAILED", jobExecution.getExitStatus().getExitCode());
  }

}
