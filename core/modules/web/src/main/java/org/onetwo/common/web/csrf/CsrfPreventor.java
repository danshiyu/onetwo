package org.onetwo.common.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CsrfPreventor {

	public static final String DEFAULT_CSRF_TOKEN_FIELD = "_jfish_token";
	public static class Token {
//		private final String fieldOfFieldName;
		private final String fieldName;
		private final String value;
		protected Token(String fieldName, String value) {
			super();
//			this.fieldOfFieldName = fieldOfFieldName;
			this.fieldName = fieldName;
			this.value = value;
		}
		public String getFieldName() {
			return fieldName;
		}
		public String getValue() {
			return value;
		}
		
	}
	
	public String getFieldOfTokenFieldName();

	public abstract void validateToken(Object controller,
			HttpServletRequest request, HttpServletResponse response);

//	public abstract Token generateToken(HttpServletRequest request, HttpServletResponse response);

	/****
	 * 生产token
	 * @param ptokenFieldName
	 * @param request
	 * @param response
	 * @return
	 */
	public abstract Token generateToken(String ptokenFieldName,
			HttpServletRequest request, HttpServletResponse response);

}