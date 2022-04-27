package com.cjh.springbatch.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.util.stream.IntStream;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    private static Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importUserJob ;
    /*@Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                                .incrementer(new RunIdIncrementer())
                                .flow(step1())
                                .end()
                                .build();
    }
//	@Bean
//	public DataSource dataSource() {
//		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:3306/temp");
//		dataSource.setUsername("root");
//		dataSource.setPassword("root");
//
//		return dataSource;
//	}

    // 每5s执行一次任务
    @Scheduled(fixedRate = 5000)
    //每分钟执行一次,支持cron表达式
    //@Scheduled(cron = "0 * * * * ?")
    public void printMessage() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong(
                    "time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importUserJob, jobParameters);
            System.out.println("I have been scheduled with Spring scheduler");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Bean
    public Step step1() {
        //chunksize为每次commit的次数
        return stepBuilderFactory.get("step1").<User, User> chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    @Bean
    public JdbcBatchItemWriter<User> writer(){
        JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
        writer.setSql("INSERT INTO user(name) VALUES (:name)");
        writer.setDataSource(dataSource);

        return writer;
    }

    @Bean
    public FlatFileItemReader<User> reader(){
        FlatFileItemReader<User> reader = new FlatFileItemReader<User>();
        reader.setResource(new ClassPathResource("users.csv"));
        reader.setLineMapper(new DefaultLineMapper<User>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                setTargetType(User.class);
            }});

        }});

        return reader;
    }

    @Bean
    public UserItemProcessor processor(){
        return new UserItemProcessor();
    }*/

    /***********************************并行批量处理*************************/
    @Bean
    public Job parallelStepsJob() {
        //功能：四个流程并行打印1-100
        //这里有四个流程，主流程masterFlow是最先开始，然后并行的是flowJob1, flowJob2, flowJob3
        Flow masterFlow = new FlowBuilder<Flow>("masterFlow").start(taskletStep("step1")).build();


        Flow flowJob1 = new FlowBuilder<Flow>("flow1").start(taskletStep("step2")).build();
        Flow flowJob2 = new FlowBuilder<Flow>("flow2").start(taskletStep("step3")).build();
        Flow flowJob3 = new FlowBuilder<Flow>("flow3").start(taskletStep("step4")).build();

        Flow slaveFlow = new FlowBuilder<Flow>("splitflow")
                .split(new SimpleAsyncTaskExecutor()).add(flowJob1, flowJob2, flowJob3).build();

        return (jobBuilderFactory.get("parallelFlowJob")
                                 .incrementer(new RunIdIncrementer())
                                 .start(masterFlow)
                                 .next(slaveFlow)
                                 .build()).build();

    }


    private TaskletStep taskletStep(String step) {
        return stepBuilderFactory.get(step).tasklet((contribution, chunkContext) -> {
            IntStream.range(1, 100).forEach(token -> logger.info("Step:" + step + " token:" + token));
            return RepeatStatus.FINISHED;
        }).build();

    }

}
