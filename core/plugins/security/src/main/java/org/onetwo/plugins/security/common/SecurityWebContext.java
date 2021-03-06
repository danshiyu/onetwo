package org.onetwo.plugins.security.common;

import javax.annotation.Resource;

import org.onetwo.common.fish.spring.config.JFishContextConfig.ContextBeanNames;
import org.onetwo.common.spring.web.authentic.SecurityWebExceptionResolver;
import org.onetwo.common.spring.web.authentic.SpringSecurityInterceptor;
import org.onetwo.common.spring.web.authentic.SsoSpringSecurityInterceptor;
import org.onetwo.common.spring.web.mvc.MvcSetting;
import org.onetwo.plugins.security.utils.SecurityPluginUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;


@Configuration
//@ComponentScan(basePackageClasses = { SpringAuthenticationInvocation.class })
public class SecurityWebContext {

	@Resource
	private MvcSetting mvcSetting;
	
	@Bean
	public SpringSecurityInterceptor securityInterceptor(){
		if(SecurityPluginUtils.existServerConfig() || SecurityPluginUtils.existClientConfig())
			return new SsoSpringSecurityInterceptor();
		//SimpleNotSSOServiceImpl
		return new SpringSecurityInterceptor();
	}

	@Bean(name=DispatcherServlet.HANDLER_EXCEPTION_RESOLVER_BEAN_NAME)
	public SecurityWebExceptionResolver webExceptionResolver(@Qualifier(ContextBeanNames.EXCEPTION_MESSAGE)MessageSource exceptionMessage){
		SecurityWebExceptionResolver webExceptionResolver = new SecurityWebExceptionResolver();
		webExceptionResolver.setExceptionMessage(exceptionMessage);
		webExceptionResolver.setMvcSetting(mvcSetting);
		return webExceptionResolver;
	}
	
	/*@Bean
	public SSOService simpleNotSSOServiceImpl(){
	}*/
	
}
