package org.onetwo.common.hibernate.sql;

import java.util.List;
import java.util.Map;

import org.onetwo.common.db.SelectExtQuery;
import org.onetwo.common.db.SelectExtQueryImpl;
import org.onetwo.common.db.sqlext.DefaultSQLSymbolManagerImpl;
import org.onetwo.common.db.sqlext.ExtQueryListener;
import org.onetwo.common.db.sqlext.SQLDialet;
import org.onetwo.common.db.sqlext.SQLSymbolManager;
import org.onetwo.common.hibernate.HibernateUtils;

public class HibernateSQLSymbolManagerImpl extends DefaultSQLSymbolManagerImpl {

	public HibernateSQLSymbolManagerImpl(SQLDialet sqlDialet) {
		super(sqlDialet);
	}

	@Override
	public SelectExtQuery createSelectQuery(Class<?> entityClass, String alias, Map<Object, Object> properties){
		SelectExtQuery q = new HibernateSelectExtQueryImpl(entityClass, alias, properties, this, this.getListeners());
		q.initQuery();
		return q;
	}
	
	public static class HibernateSelectExtQueryImpl extends SelectExtQueryImpl {

		public HibernateSelectExtQueryImpl(Class<?> entityClass, String alias, Map params, SQLSymbolManager symbolManager,
				List<ExtQueryListener> listeners) {
			super(entityClass, alias, params, symbolManager, listeners);
		}

		protected String getDefaultCountField(){
			String idName = HibernateUtils.getClassMeta(entityClass).getIdentifierPropertyName();
			return getFieldName(idName);
		}
		
	}
}
