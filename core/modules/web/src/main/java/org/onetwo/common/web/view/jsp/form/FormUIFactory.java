package org.onetwo.common.web.view.jsp.form;

import org.onetwo.common.exception.BaseException;

public class FormUIFactory {
	
	public static FormFieldTagBean createUIBean(FormFieldType type){
		FormFieldTagBean bean = null;
		switch (type) {
			case input:
			case hidden:
			case radio:
			case checkbox:
			case textarea:
				bean = new FormFieldTagBean();
				break;
			case select:
				bean = new FormItemsTagBean();
				break;
			case button:
			case submit:
				bean = new FormButtonTagBean();
				break;
			case radioGroup:
			case checkboxGroup:
				bean = new FormItemsTagBean();
				break;
	
			default:
				throw new BaseException("unspported type: " + type);
		}
		return bean;
	}

}