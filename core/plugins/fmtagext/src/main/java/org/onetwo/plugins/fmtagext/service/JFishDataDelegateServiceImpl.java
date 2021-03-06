package org.onetwo.plugins.fmtagext.service;

import java.util.List;

import javax.annotation.Resource;

import org.onetwo.common.jfishdbm.mapping.JFishMappedEntry;
import org.onetwo.common.jfishdbm.mapping.MappedEntryManager;
import org.onetwo.common.jfishdbm.support.JFishEntityManager;
import org.onetwo.common.utils.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class JFishDataDelegateServiceImpl implements JFishDataDelegateService  {

	@Resource
	private JFishEntityManager jfishEntityManager; 
	
	@Resource
	private MappedEntryManager mappedEntryManager;

	@Override
	public <T> List<T> findListByQName(String queryName, Object... params) {
//		return jfishEntityManager.getJfishFileQueryDao().findListByQName(queryName, params);
		return jfishEntityManager.getFileNamedQueryFactory().createQuery(queryName, params).getResultList();
	}
	
	public <T> Page<T> findPage(Class<T> entityClass, Page<T> page, Object...params){
		jfishEntityManager.findPage(entityClass, page, params);
		return page;
	}
	
	public JFishMappedEntry getJFishMappedEntry(Object clsName){
		return this.mappedEntryManager.getEntry(clsName);
	}
	
}
