package org.onetwo.plugins.task.webclient.web;

import javax.annotation.Resource;

import org.onetwo.common.db.ExtQuery.K;
import org.onetwo.common.db.ExtQuery.K.IfNull;
import org.onetwo.common.fish.plugin.PluginSupportedController;
import org.onetwo.common.utils.Page;
import org.onetwo.plugins.permission.anno.ByFunctionClass;
import org.onetwo.plugins.permission.anno.ByMenuClass;
import org.onetwo.plugins.task.client.service.impl.TaskClientServiceImpl;
import org.onetwo.plugins.task.entity.TaskQueueArchived;
import org.onetwo.plugins.task.webclient.TaskModule.Archived;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/taskarchived")
public class TaskArchivedController extends PluginSupportedController {
	
	@Resource
	private TaskClientServiceImpl taskClientService;
	
	@ByMenuClass(codeClass=Archived.List.class)
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(Page<TaskQueueArchived> page, TaskQueueArchived archived){
		taskClientService.findArchivedPage(page, "result", archived.getResult(), K.DESC, "id", K.IF_NULL, IfNull.Ignore);
		return pluginMv("task-archived-index", "page", page);
	}
	
	@ByFunctionClass(codeClass=Archived.ReQueue.class)
	@RequestMapping(value="/requeue/{id}", method=RequestMethod.PUT)
	public ModelAndView requeue(@PathVariable("id") Long id){
		taskClientService.requeueArchived(id);
		return pluginRedirectTo("/taskarchived", "操作成功，已重新放入对垒！");
	}
	

}
