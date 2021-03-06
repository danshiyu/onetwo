package org.onetwo.plugins.fmtag.directive;

import org.onetwo.common.fish.plugin.JFishPlugin;
import org.onetwo.common.ftl.directive.PluginDirective;
import org.onetwo.plugins.fmtag.FmtagWebPlugin;

abstract public class FmtagBaseDirective extends PluginDirective {

	@Override
	protected JFishPlugin getPlugin() {
		return FmtagWebPlugin.getInstance();
	}

}
