package org.onetwo.common.db.generator.mapping;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.onetwo.common.db.generator.utils.DBUtils;
import org.onetwo.common.utils.LangUtils;

public class ColumnMapping {
	public static interface ColumnAttrValueFunc {
		Object getAttrValue(ColumnMapping mapping);
	}
	@SuppressWarnings("serial")
	public class ColumnAttrMap extends HashMap<String, Object> {

		@Override
		public Object get(Object key) {
			Object val = super.get(key);
			if(ColumnAttrValueFunc.class.isInstance(val)){
				val = ((ColumnAttrValueFunc)val).getAttrValue(ColumnMapping.this);
			}
			return val;
		}
	}
	private int sqlType = DBUtils.TYPE_UNKNOW;
	private Class<?> javaType;

	private Map<String, Object> attrs = new ColumnAttrMap();

	public ColumnMapping(int sqlType) {
		super();
		this.sqlType = sqlType;
	}

	public int getSqlType() {
		return sqlType;
	}

	public Class<?> getJavaType() {
		return javaType;
	}

	public ColumnMapping javaType(Class<?> javaType) {
		this.javaType = javaType;
		return this;
	}

	public Map<String, Object> getAttrs() {
		return attrs;
	}

	public ColumnMapping attr(String name, Object value) {
		this.attrs.put(name, value);
		return this;
	}

	public boolean isDateType(){
		return Date.class.isInstance(getJavaType());
	}

	public boolean isTimestampType(){
		return Timestamp.class.isInstance(getJavaType());
	}
	
	public boolean isNumberType(){
		return LangUtils.isNumberType(getJavaType());
	}
	
	public boolean isBooleanType(){
		return getJavaType()==boolean.class || getJavaType()==Boolean.class;
	}

	@Override
	public String toString() {
		return "ColumnMapping [sqlType=" + sqlType + ", javaType=" + javaType
				+ ", attrs=" + attrs + "]";
	}
	
	
}
