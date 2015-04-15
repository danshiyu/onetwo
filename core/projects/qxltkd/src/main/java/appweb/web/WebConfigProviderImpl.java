package appweb.web;

import org.onetwo.common.web.config.BaseSiteConfig;
import org.onetwo.common.web.filter.WebConfigProvider;
import org.springframework.stereotype.Component;

import appweb.utils.WebConfig;

@Component
public class WebConfigProviderImpl implements WebConfigProvider {

	@Override
	public Object createWebConfig(BaseSiteConfig siteConfig) {
		return WebConfig.getInstance();
	}

}
