package org.onetwo.plugins.batch;

import java.util.List;

import org.onetwo.common.spring.plugin.AbstractContextPlugin;


public class BatchPlugin extends AbstractContextPlugin<BatchPlugin> {

	private static BatchPlugin instance;
	
	
	public static BatchPlugin getInstance() {
		return instance;
	}
	

	@Override
	public void onJFishContextClasses(List<Class<?>> annoClasses) {
//		annoClasses.add(BatchPluginContext.class);
	}

	public void setPluginInstance(BatchPlugin plugin){
		instance = plugin;
	}

}
