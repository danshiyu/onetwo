package org.onetwo.common.fish.plugin;

import java.util.List;

import org.springframework.web.context.WebApplicationContext;

/****
 * jfish plugin manager interface
 * @author way
 *
 */
public interface JFishPluginManager {

	public String JFISH_PLUGIN_MANAGER_KEY = "org.onetwo.common.fish.plugin.JFishPluginManager";

	public PluginNameParser getPluginNameParser();
	
	/****
	 * scan plugins on webapp application start 
	 * {@linkplain org.onetwo.common.fish.web.JFishWebApplicationContext JFishWebApplicationContext},
	 * it common start by web context listener.
	 */
	public void scanPlugins();
	
	/****
	 * called when jfish dispatcher servlet(spring mvc) initialize mvc context
	 * {@linkplain org.onetwo.common.spring.web.JFishDispatcher#initWebApplicationContext initWebApplicationContext}
	 * it will start JFishMvcApplicationContext
	 * @param appContext
	 */
	public void onInitWebApplicationContext(final WebApplicationContext appContext);
	
//	public JFishList<JFishPluginMeta> getPluginMetas();
	
	public JFishPluginMeta getJFishPluginMetaOf(Class<?> objClass);
	
	public JFishPluginMeta getJFishPluginMeta(String name);
	
//	public List<Class<?>> getPluginContextClasses();

	/****
	 * called when jfish initialize mvc context {@linkplain org.onetwo.common.spring.web.mvc.config.JFishMvcApplicationContext JFishMvcApplicationContext}
	 * @param contextClasses
	 */
	public void registerPluginMvcContextClasses(List<Class<?>> contextClasses);
	/***
	 * called when jfish instance a {@linkplain org.onetwo.common.fish.web.JFishWebApplicationContext JFishWebApplicationContext} .
	 * @param contextClasses
	 */
	public void registerPluginJFishContextClasses(List<Class<?>> contextClasses);
	
	public void destroy();

}