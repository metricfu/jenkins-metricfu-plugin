package hudson.plugins.rubyMetrics.flog;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Action;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.model.Project;
import hudson.model.StreamBuildListener;
import hudson.plugins.rake.Rake;
import hudson.plugins.rake.RubyInstallation;
import hudson.plugins.rubyMetrics.AbstractRubyMetricsPublisher;
import hudson.plugins.rubyMetrics.flog.model.FlogResults;

import hudson.tasks.Publisher;

import java.io.IOException;

import org.codehaus.plexus.util.StringOutputStream;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Flog stats {@link Publisher}
 *
 * @author Joseph Wilk
 *
 */
@SuppressWarnings("unchecked")
public class FlogPublisher extends AbstractRubyMetricsPublisher {

    private final Rake rake;
    private final String rakeInstallation;

    @DataBoundConstructor
    public FlogPublisher(String rakeInstallation) {
        this.rakeInstallation = rakeInstallation;
        this.rake = new Rake(this.rakeInstallation, null, "flog", null, null, true);
    }

    public boolean perform(Build<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {

        final Project<?, ?> project = build.getParent();
        FilePath workspace = project.getModuleRoot();

        listener.getLogger().println("Publishing flog stats report...");

        StringOutputStream out = new StringOutputStream();
        BuildListener stringListener = new StreamBuildListener(out);

        if (rake.perform(build, launcher, stringListener)) {
            final FlogParser parser = new FlogParser();
            FlogResults results = parser.parse(out);

            FlogBuildAction action = new FlogBuildAction(build, results);
            build.getActions().add(action);
        }

        return true;
    }

    public String getRakeInstallation() {
        return rakeInstallation;
    }


    @Override
    public Action getProjectAction(Project project) {
        return new FlogProjectAction(project);
    }

    public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    public static final class DescriptorImpl extends Descriptor<Publisher> {

        protected DescriptorImpl() {
            super(FlogPublisher.class);
        }

        @Override
            public String getHelpFile() {
            return "/plugin/rubyMetrics/flogStatsHelp.html";
        }

        @Override
            public String getDisplayName() {
            return "Publish Flog stats report";
        }

        public RubyInstallation[] getRakeInstallations() {
            return Rake.DESCRIPTOR.getInstallations();
        }

    }

    public Descriptor<Publisher> getDescriptor() {
        return DESCRIPTOR;
    }

}
