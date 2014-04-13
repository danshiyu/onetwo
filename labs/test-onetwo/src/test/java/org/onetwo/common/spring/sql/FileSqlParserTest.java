package org.onetwo.common.spring.sql;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.onetwo.common.hibernate.sql.HibernateNamedInfo;
import org.onetwo.common.hibernate.sql.HibernateNamedSqlFileManager;
import org.onetwo.common.jdbc.DataBase;

public class FileSqlParserTest {

	private HibernateNamedSqlFileManager fileManager;
	private FileSqlParser parser;
	ParserContext parserContext;
	
	@Before
	public void before(){
		this.fileManager = new HibernateNamedSqlFileManager(DataBase.Oracle, false, HibernateNamedInfo.class);
		fileManager.build();
		DefaultFileSqlParser<HibernateNamedInfo> p = new DefaultFileSqlParser<HibernateNamedInfo>(fileManager);
		p.initialize();
		this.parser = p;
	}
	
	@Test
	public void testPaser(){
		HibernateNamedInfo info = this.fileManager.getNamedQueryInfo("testParserQuery");
		parserContext = ParserContext.create();
		this.parserContext.put(SqlFunctionFactory.CONTEXT_KEY, SqlFunctionFactory.getSqlFunctionDialet(info.getDataBaseType()));
		NamedInfoAttrsParser attrParser = new NamedInfoAttrsParser(parser, parserContext, info);
		this.parserContext.put(JFishNamedFileQueryInfo.TEMPLATE_KEY, attrParser);
		String sql = this.parser.parse(info.getFullName(), parserContext);
		System.out.println("sql: " + sql);
		Assert.assertEquals("select id from ( select id from tableName )", sql);
	}
	
	@Test
	public void testPaser2(){
		HibernateNamedInfo info = this.fileManager.getNamedQueryInfo("testParserQuery2");
		parserContext = ParserContext.create();
		this.parserContext.put(SqlFunctionFactory.CONTEXT_KEY, SqlFunctionFactory.getSqlFunctionDialet(info.getDataBaseType()));
		NamedInfoAttrsParser attrParser = new NamedInfoAttrsParser(parser, parserContext, info);
		this.parserContext.put(JFishNamedFileQueryInfo.TEMPLATE_KEY, attrParser);
		this.parserContext.put("userName", "way");
		String sql = this.parser.parse(info.getFullName(), parserContext);
		System.out.println("sql: " + sql);
		Assert.assertEquals("select * from tableName2 t where t.id in ( select id from tableName )", sql);
	}
	
	@Test
	public void testPaser3(){
		HibernateNamedInfo info = this.fileManager.getNamedQueryInfo("testParserQuery3");
		parserContext = ParserContext.create();
		this.parserContext.put(SqlFunctionFactory.CONTEXT_KEY, SqlFunctionFactory.getSqlFunctionDialet(info.getDataBaseType()));
		NamedInfoAttrsParser attrParser = new NamedInfoAttrsParser(parser, parserContext, info);
		this.parserContext.put(JFishNamedFileQueryInfo.TEMPLATE_KEY, attrParser);
		try {
			String sql = this.parser.parse(info.getFullName(), parserContext);
			Assert.fail();
		} catch (Exception e) {
		}
	}
}