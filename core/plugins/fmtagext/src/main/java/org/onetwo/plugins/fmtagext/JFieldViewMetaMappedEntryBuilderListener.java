package org.onetwo.plugins.fmtagext;

import org.onetwo.common.jfishdb.orm.JFishMappedEntry;
import org.onetwo.common.jfishdb.orm.JFishMappedField;
import org.onetwo.common.jfishdb.orm.MappedEntryBuilderListener;

public class JFieldViewMetaMappedEntryBuilderListener implements MappedEntryBuilderListener {

	@Override
	public void afterCreatedMappedEntry(JFishMappedEntry entry) {
	}

	@Override
	public void afterBuildMappedField(JFishMappedEntry entry, JFishMappedField mfield) {
//		JFieldViewMeta jfm = mfield.getPropertyInfo().getAnnotation(JFieldViewMeta.class);
	}

	@Override
	public void afterBuildMappedEntry(JFishMappedEntry entry) {
	}

}
