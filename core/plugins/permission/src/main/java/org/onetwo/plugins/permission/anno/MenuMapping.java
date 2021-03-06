package org.onetwo.plugins.permission.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.onetwo.plugins.permission.MenuInfoParser;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuMapping {

//	String code();
	Class<?> parent() default MenuInfoParser.class;
	
}
