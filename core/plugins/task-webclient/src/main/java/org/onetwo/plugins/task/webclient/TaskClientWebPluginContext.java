package org.onetwo.plugins.task.webclient;

import org.onetwo.plugins.task.webclient.web.TaskArchivedController;
import org.onetwo.plugins.task.webclient.web.taskqueue.TaskQueueController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TaskClientWebPluginContext {

	@Bean
	public TaskQueueController taskQueueController(){
		return new TaskQueueController();
	}
	
	@Bean
	public TaskArchivedController taskArchivedController(){
		return new TaskArchivedController();
	}
	
}
