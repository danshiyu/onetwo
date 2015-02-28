package org.onetwo.common.web.config;

import java.net.URL;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.onetwo.common.log.MyLoggerFactory;
import org.onetwo.common.utils.FileUtils;
import org.onetwo.common.utils.StringUtils;
import org.onetwo.common.utils.propconf.AppConfig;
import org.onetwo.common.web.utils.RequestUtils;
import org.slf4j.Logger;

public class BaseSiteConfig extends AppConfig { 
	public static enum SessionRepository {
		CONTAINER,
		REDIS
	}
	
	protected static final Logger logger = MyLoggerFactory.getLogger(BaseSiteConfig.class);

	public static final String WEB_CONFIG_NAME = "webConfig";
	public static final String CONFIG_NAME = "siteConfig";

	public static final String BASEURL = "baseURL";
	public static final String LANGUAGE_SUPPORT = "language.support";
	public static final String CONFIG_CLASS = "config.class";
	
	public static final String ERROR_PAGE = "error.page";
	public static final String PATH_JS = "path.js";
	public static final String PATH_RS = "path.rs";
	public static final String PATH_CSS = "path.css";
	public static final String PATH_IMAGE = "path.image";

	public static final String THEME_SETTING = "theme.setting";
	public static final String THEME_TAG = "theme.tag";
	public static final String THEME_VIEW = "theme.view";
	public static final String THEME_LAYOUT_DEFAULT_PAGE = "theme.layout.defaultpage";
	public static final String THEME_JSUI_KEY = "theme.jsui";
	
	public static final String FILTER_INITIALIZERS = "filter.initializers";
	
	//sso
	public static final String TOKEN_TIMEOUT = "token.timeout";
	public static final String TOKEN_TIMEOUT_CHECKTIME = "token.timeout.checktime";
	public static final Integer DEFAULT_TOKEN_TIMEOUT = 60*4;

	public static final String COOKIE_DOMAIN = "cookie.domain";
	public static final String COOKIE_PATH = "cookie.path";
	public static final String COOKIE_P3P = "cookie.p3p";
	public static final String APP_URL_POSTFIX = "app.url.postfix";
	public static final String SESSION_REPOSITORY = "session.repository";
	

	public static final String JDBC_SQL_LOG = "jdbc.sql.log";
	public static final String TIME_PROFILER = "time.profiler";
	public static final String LOG_OPERATION = "log.operation";

	public static final String SAFE_REQUEST = "safe.request";//csrf
	public static final String PREVENT_XSS_REQUEST = "prevent.xss.request";//xss
	public static final String PREVENT_REPEATE_SUBMIT = "prevent.repeate.sbumit";
	/***
	 * 当启用防止重复提交时，未标注注解的controller的默认策略，true检查是否重复提交，false则不会检查
	 * 默认为false，没有注解的不检查
	 */
	public static final String PREVENT_REPEATE_SUBMIT_DEFAULT = "prevent.repeate.sbumit.default";
	public static final String LOGIN_URL = "login.url";
	public static final String LOGOUT_URL = "logout.url";
	public static final String SECURITY_NOPERMISSION_VIEW = "security.nopermission.view";
//	public static final String DATASOURCE_MASTER_SLAVE = "datasource.master.slave";
	

	protected static final String CONFIG_FILE = "siteConfig.properties";
	private static BaseSiteConfig baseSiteConfig = new BaseSiteConfig(CONFIG_FILE);
	
	private Object webAppConfigurator;
	private ServletContext servletContext;
	private String contextPath;
	private String appUrlPostfix;
	
	/******************
	 * fuck ugly
	 * **********************/
	/*static {
		try {
			VariableConfig config = new VariablePropConifg();
			config.load(CONFIG_FILE);
			String clsName = config.getOriginalProperty(CONFIG_CLASS, "").trim();
			if(StringUtils.isBlank(clsName)){
				clsName = BaseSiteConfig.class.getPackage().getName()+"." + StringUtils.capitalize(CONFIG_NAME);
			}
			Class<?> configClass = ReflectUtils.loadClass(clsName);
			Method staticFactoryMethod = ReflectUtils.findMethod(configClass, "getInstance");
			siteConfig = (BaseSiteConfig)ReflectUtils.invokeMethod(staticFactoryMethod, configClass);
//			ReflectUtils.findMethod(objClass, name, paramTypes)
		} catch (Exception e) {
			logger.error("load config.class error, use default config class.");
		}
		if(siteConfig==null){
			siteConfig = new BaseSiteConfig(CONFIG_FILE);
		}
	}*/
 
	protected BaseSiteConfig(String configName) {
		super(configName);
	}


	public static BaseSiteConfig getInstance() {
		return baseSiteConfig;
	}

	public static BaseSiteConfig inst() {
		return baseSiteConfig;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public String findClasspathResource(String path){
		return findResource("/WEB-INF/classes"+path);
	}
	
	public String findResource(String dir, String name){
		if(!dir.endsWith("/"))
			dir = dir + "/";
		return findResource(dir + name);
	}

	public String getLanguageSupport(){ 
		return getProperty(LANGUAGE_SUPPORT);
	}
	
	public String findResource(String path){
		if(StringUtils.isBlank(path))
			return null;
		if(!path.startsWith("/"))
			path = "/" + path;
		URL url = null;
		try {
			String realpath = getServletContext().getRealPath(path);
//			System.out.println("resource path: " + path);
			if(StringUtils.isNotBlank(realpath) && FileUtils.exists(realpath))
				return realpath;
			url = getServletContext().getResource(path);
		} catch (Exception e) {
			logger.error("get resource form classpath error!", e);
		}
		if(url==null)
			return null;
		return url.getPath();
	}

	public BaseSiteConfig initWeb(FilterConfig fconfig) {
		servletContext = fconfig.getServletContext();
		String cp = servletContext.getContextPath();
		this.setContextPath(cp);
		String realPath = fconfig.getServletContext().getRealPath("");
		this.setProperty("contextRealPath", realPath);
		if(containsKey(APP_URL_POSTFIX)){
			this.appUrlPostfix = getProperty(APP_URL_POSTFIX);
		}/*else{
			this.appUrlPostfix = ".do";
		}*/
		if(logger.isInfoEnabled()){
			logger.info("set contextPath: " + cp );
			logger.info("set ContextRealPath: " + realPath);
			logger.info("set appUrlPostfix: " + appUrlPostfix );
		}
		
		return this;
	}
	
	public static String getConfig(String key) {
		return baseSiteConfig.getProperty(key, "");
	}

	public static int getConfigForInt(String key) {
		return baseSiteConfig.getInt(key, 0);
	}

	public static long getConfigForLong(String key) {
		return baseSiteConfig.getLong(key, 0l);
	}
	
	/***************************************************************************
	 * base
	 */
	
	public String getErrorPage(){
		return this.getProperty(ERROR_PAGE);
	}

	public int getErrorPageCode(){
		String errorPage = getErrorPage();
		int statusCode = -1;
		if(errorPage!=null){
			try {
				statusCode = Integer.parseInt(errorPage.trim());
			} catch (Exception e) {
			}
		}
		return statusCode;
	}
	
	/**
	 * 包含语言版本
	 * 
	 * @return
	 */
	public String getBaseURL() {
		String baseURL = getVariable(BASEURL);
		if (StringUtils.isBlank(baseURL))
			baseURL = contextPath;
		return baseURL;
	}
	

	public String getJsPath(){
//		return getContextPath()+getProperty(PATH_JS, "/js");
		String jspath = getProperty(PATH_JS);
		if(StringUtils.isNotBlank(jspath))
			return getContextPath()+jspath;
		return getBaseURL() + "/js";
	}
	
	public String getRsPath(){
		return getProperty(PATH_RS);
	}
	
	public String getCssPath(){
		String csspath = getProperty(PATH_CSS);
		if(StringUtils.isNotBlank(csspath))
			return getContextPath()+csspath;
		return getBaseURL()+ "/css";
	}
	
	public String getImagePath(){
		String imgpath = getProperty(PATH_IMAGE);
		if(StringUtils.isNotBlank(imgpath))
			return getContextPath()+imgpath;
		return getBaseURL()+"/images";
	}

	public String getJqueryPath(){
		return this.getJsPath()+"/jquery";
	}
	
	public String getJqueryuiPath(){
		return this.getJsPath()+"/jqueryui";
	}
	
	/*public String[] getFilterInitializers(){
		String str = getVariable(FILTER_INITIALIZERS);
		String[] initers = StringUtils.split(str, ",");
		return initers;
	}*/


	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getSiteConfigName() {
		return CONFIG_NAME;
	}
	
	public int getTokenTimeoutChecktime(){
		int time = this.getInt(TOKEN_TIMEOUT_CHECKTIME, 5);
		return time;
	}
	
	public int getTokenTimeout(){
		Integer time = this.getInt(TOKEN_TIMEOUT, DEFAULT_TOKEN_TIMEOUT);
		return time;
	}
	
	/****
	 * 本地时，不需要设置，设置了有些浏览器会读不到cookies
	 * 如设置域名，要以'.'开头，比如 .test.com
	 * @return
	 */
	public String getCookieDomain(){
		return getProperty(COOKIE_DOMAIN, "");
	}
	
	public String getCookiePath(){
		return getProperty(COOKIE_PATH, this.getContextPath());
	}
	
	public boolean isCookieP3p(){
		return getBoolean(COOKIE_P3P, false);
	}

	public String getAppUrlPostfix(){
		return appUrlPostfix;
	}

	public boolean hasAppUrlPostfix(){
		return StringUtils.isNotBlank(appUrlPostfix);
	}
	public String appendAppUrlPostfix(String url){
		String lowerurl = url.toLowerCase();
		if(lowerurl.startsWith(RequestUtils.HTTP_KEY) || lowerurl.startsWith(RequestUtils.HTTPS_KEY))
			return url;
		if(lowerurl.endsWith(appUrlPostfix)){
			return url;
		}
		return url+appUrlPostfix;
	}
	
	public boolean isJdbcSqlLog(){
		if(!this.containsKey(JDBC_SQL_LOG)){
			return !isProduct();
		}
		return getBoolean(JDBC_SQL_LOG);
	}

	public Object getWebAppConfigurator() {
		return webAppConfigurator;
	}

	public <T> T getWebAppConfigurator(Class<T> clazz) {
		if(clazz.isInstance(webAppConfigurator)){
			return clazz.cast(webAppConfigurator);
		}else{
			return null;
		}
	}

	public void setWebAppConfigurator(Object webAppConfigurator) {
		this.getFreezer().checkOperation("setWebAppConfigurator");
		this.webAppConfigurator = webAppConfigurator;
	}
	
	/*****
	 * 当启用后，默认检查
	 * @return
	 */
	public boolean isSafeRequest(){
		return getBoolean(SAFE_REQUEST, true);
	}
	public boolean isPreventRepeateSubmit(){
		return getBoolean(PREVENT_REPEATE_SUBMIT, true);
	}
	/***
	 * default is false
	 * no wrap request
	 * @return
	 */
	public boolean isPreventXssRequest(){
		return getBoolean(PREVENT_XSS_REQUEST, false);
	}
	/***
	 * 当启用防止重复提交时，未标注注解的controller的默认策略，true检查是否重复提交，false则不会检查
	 * 默认为false，没有注解的不检查
	 */
	public boolean isPreventRepeateSubmitDefault(){
		return getBoolean(PREVENT_REPEATE_SUBMIT_DEFAULT, false);
	}
	public boolean isTimeProfiler(){
		return getBoolean(TIME_PROFILER, isDev());
	}
	
	public boolean isLogOperation(){
		return getBoolean(LOG_OPERATION, false);
	}

	public String getThemeSetting(){
		return getProperty(THEME_SETTING, "");
	}
	public String getThemeTag(){
		return getProperty(THEME_TAG, "/tags/");
	}
	public String getThemeView(){
		return getProperty(THEME_VIEW, "/views/");
	}
	public String getThemeLayoutDefaultPage(){
		return getProperty(THEME_LAYOUT_DEFAULT_PAGE, "application.jsp");
	}
	public boolean getThemeJsui(){
		return getBoolean(THEME_JSUI_KEY, false);
	}
	
	public String getLoginUrl(){
		return getProperty(LOGIN_URL);
	}
	
	public String getLogoutUrl(){
		return getProperty(LOGOUT_URL);
	}
	public String getSecurityNopermissionView(){
		return getProperty(SECURITY_NOPERMISSION_VIEW);
	}
	
	public String getExtTheme(){
		return getProperty("ext.theme", "gray");
	}
	
	
	public boolean isContainerSession(){
		return getSessionRepository()==SessionRepository.CONTAINER;
	}

	public SessionRepository getSessionRepository() {
		String sr = getProperty(SESSION_REPOSITORY, SessionRepository.CONTAINER.name());
		return SessionRepository.valueOf(sr.toUpperCase());
	}

}
