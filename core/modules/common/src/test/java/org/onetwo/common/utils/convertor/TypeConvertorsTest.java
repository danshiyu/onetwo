package org.onetwo.common.utils.convertor;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.onetwo.common.convert.Types;
import org.onetwo.common.date.DateUtil;
import org.onetwo.common.utils.LangUtils;
import org.onetwo.common.utils.StringUtils;

public class TypeConvertorsTest {
	

public enum JNamedQueryKey {

	ParserContext,
	ResultClass,
	ASC,
	DESC;
//	countClass;
	
	public static JNamedQueryKey ofKey(Object qkey){
		if(JNamedQueryKey.class.isInstance(qkey))
			return (JNamedQueryKey)qkey;
		String key = qkey.toString();
		if(!key.startsWith(":"))
			return null;
		String keyStr = key.substring(1);
		JNamedQueryKey queryKey = null;
		try {
			queryKey = JNamedQueryKey.valueOf(keyStr);
		} catch (Exception e) {
			System.out.println("no JNamedQueryKey: " + key);
		}
		return queryKey;
	}
}

	@Test
	public void testEnum(){
		System.out.println("String[]: " + String[].class);
		System.out.println("Object[]: " + Object[].class);
		System.out.println(JNamedQueryKey.ASC.getClass());
		System.out.println(JNamedQueryKey.ASC.getClass().getClass());
		System.out.println(JNamedQueryKey.ASC.getClass().getSuperclass());
		System.out.println(Enum.class.isAssignableFrom(JNamedQueryKey.ASC.getClass()));
		
		JNamedQueryKey jqk = Types.asValue("ASC", JNamedQueryKey.class);
		Assert.assertTrue(jqk==JNamedQueryKey.ASC);
		jqk = Types.asValue("ResultClass", JNamedQueryKey.class);
		Assert.assertTrue(jqk==JNamedQueryKey.ResultClass);
		

		jqk = Types.asValue(0, JNamedQueryKey.class);
		Assert.assertTrue(jqk==JNamedQueryKey.ParserContext);
	}
	
	@Test
	public void testInteger(){
		Assert.assertTrue(1==Types.convertValue("1", Integer.class));
		Assert.assertTrue(1==Types.convertValue(true, Integer.class));
		Assert.assertTrue(0==Types.convertValue(false, Integer.class));
		Assert.assertTrue(111==Types.convertValue(111.11, Integer.class));
	}
	
	@Test
	public void testBoolean(){
		Assert.assertTrue(Types.convertValue("1", boolean.class));
		Assert.assertTrue(Types.convertValue("aa", boolean.class));
		Assert.assertTrue(Types.convertValue("", boolean.class));
		Assert.assertTrue(Types.convertValue(true, boolean.class));
		Assert.assertTrue(Types.convertValue(111.11, boolean.class));
		Assert.assertFalse(Types.convertValue(false, boolean.class));
		Assert.assertFalse(Types.convertValue(null, boolean.class));
		Assert.assertFalse(Types.convertValue("false", boolean.class));
		Assert.assertFalse(Types.convertValue("no", boolean.class));
		Assert.assertFalse(Types.convertValue(null, boolean.class));
	}
	
	@Test
	public void testShort(){
		Assert.assertTrue(1==Types.convertValue("1", Short.class));
		Assert.assertTrue(1==Types.convertValue(true, Short.class));
		Assert.assertTrue(0==Types.convertValue(false, Short.class));
		Assert.assertTrue(111==Types.convertValue(111.11, Short.class));
	}
	
	@Test
	public void testLong(){
		Assert.assertTrue(1==Types.convertValue("1", Long.class));
		Assert.assertTrue(1==Types.convertValue(true, Long.class));
		Assert.assertTrue(0==Types.convertValue(false, Long.class));
		Assert.assertTrue(111==Types.convertValue(111.11, Long.class));
		Assert.assertTrue(111==Types.convertValue(111, long.class));
	}
	
	@Test
	public void testFloat(){
		Assert.assertTrue(Types.convertValue("1.1", Float.class).equals(1.1f));
		Assert.assertTrue(Types.convertValue(true, Float.class).equals(1.0f));
		Assert.assertTrue(Types.convertValue(false, Float.class).equals(0.0f));
		Assert.assertTrue(Types.convertValue(1.1, Float.class).equals(1.1f));
	}
	
	@Test
	public void testDouble(){
		Assert.assertTrue(1.1==Types.convertValue("1.1", Double.class));
		Assert.assertTrue(1.0==Types.convertValue(true, Double.class));
		Assert.assertTrue(0.0==Types.convertValue(false, Double.class));
		Assert.assertTrue(111.11==Types.convertValue(111.11, Double.class));

		Assert.assertTrue(Types.convertValue("1.1", Double.class).equals(1.1d));
		Assert.assertTrue(Types.convertValue(true, Double.class).equals(1.0d));
		Assert.assertTrue(Types.convertValue(false, Double.class).equals(0.0d));
		Assert.assertTrue(Types.convertValue(1.1, Double.class).equals(1.1d));
	}

	@Test
	public void testListConvert(){
		Integer sint = 3;
		Boolean sbool = true;
		Long slong = Long.MAX_VALUE;
		Float sfloat = Float.MAX_VALUE;
		List<String> list = LangUtils.asList(new String[]{"aaa", "bbb", "ccc"});
		Assert.assertEquals(sint, Types.convertValue(sint+"", Integer.class));
		Assert.assertEquals(sbool, Types.convertValue(sbool+"", Boolean.class));
		Assert.assertEquals(slong, Types.convertValue(slong+"", Long.class));
		Assert.assertEquals(sfloat, Types.convertValue(sfloat+"", Float.class));
		Assert.assertEquals(list, Types.asList(StringUtils.join(list, ","), String.class));
		Assert.assertEquals(new Integer[]{1,2,3}, Types.asArray("1,2,3", Integer.class));
		Assert.assertEquals(LangUtils.newArrayList(1,2,3), Types.asList("1,2,3", Integer.class));
		Assert.assertEquals(LangUtils.newArrayList(1.0,2.0,3.0), Types.asList("1,2,3", Double.class));
	}
	
	@Test
	public void testToDate(){
		String datestr = "2012-05-05";
		Date cdate = Types.convertValue(datestr, Date.class);
		Assert.assertEquals(datestr, DateUtil.formatDate(cdate));
		
		datestr = "21:21:21";
		cdate = Types.convertValue(datestr, Date.class);
		Assert.assertEquals(datestr, DateUtil.formatTime(cdate));
		
		cdate = Types.convertValue(cdate.getTime(), Date.class);
		Assert.assertEquals(datestr, DateUtil.formatTime(cdate));
	}
}
