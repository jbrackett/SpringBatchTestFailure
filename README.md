Update `MinimumJobConfig.java` to switch between the two ways of building the job.

When using the `.on` method `JobLauncherTestUtils` can't seem to find any steps in the job. If we just use `.next` however it works as expected.
