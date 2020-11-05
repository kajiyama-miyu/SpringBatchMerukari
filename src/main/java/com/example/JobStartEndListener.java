package com.example;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class JobStartEndListener extends JobExecutionListenerSupport{
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JobStartEndListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override 
	public void beforeJob(JobExecution jobExecution) {
		super.beforeJob(jobExecution);
		System.out.println("開始");
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		super.afterJob(jobExecution);
		System.out.println("終了");
	}

}
