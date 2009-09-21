package hudson.plugins.rubyMetrics;

import hudson.Plugin;
import hudson.plugins.rubyMetrics.RubyMetricsPublisher;
import hudson.tasks.BuildStep;

/**
 * Entry point of a plugin.
 *
 * @author Joseph Wilk
 * @plugin
 */
public class PluginImpl extends Plugin {
    public void start() throws Exception {    	
        BuildStep.PUBLISHERS.addRecorder(RubyMetricsPublisher.DESCRIPTOR);
    }
}
