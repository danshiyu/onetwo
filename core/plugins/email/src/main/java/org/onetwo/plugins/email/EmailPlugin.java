package org.onetwo.plugins.email;

import java.util.List;

import org.onetwo.common.spring.plugin.AbstractContextPlugin;
import org.onetwo.common.spring.plugin.DefaultContextPluginMeta;


public class EmailPlugin extends AbstractContextPlugin<EmailPlugin, DefaultContextPluginMeta<EmailPlugin>> {

	private static EmailPlugin instance;
	
	
	public static EmailPlugin getInstance() {
		return instance;
	}
	
	@Override
	public void onJFishContextClasses(List<Class<?>> annoClasses) {
		annoClasses.add(EmailPluginContext.class);
	}


	public void setPluginInstance(EmailPlugin plugin){
		instance = plugin;
	}

}