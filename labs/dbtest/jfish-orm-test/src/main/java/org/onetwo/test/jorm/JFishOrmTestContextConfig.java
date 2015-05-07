package org.onetwo.test.jorm;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.onetwo.common.jfishdbm.spring.JFishdbmSpringConfiguration;
import org.onetwo.common.spring.cache.JFishSimpleCacheManagerImpl;
import org.onetwo.common.spring.config.JFishProfile;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@JFishProfile
@ImportResource("classpath:applicationContext.xml")
@Import(JFishdbmSpringConfiguration.class)
public class JFishOrmTestContextConfig {

	@Resource
	private DataSource dataSource;
	@Bean
	public JFishOrmTestConfig tasksysConfig(){
		return JFishOrmTestConfig.getInstance();
	}
	
	@Bean
	public CacheManager cacheManager() {
		JFishSimpleCacheManagerImpl cache = new JFishSimpleCacheManagerImpl();
		return cache;
	}
	
}
