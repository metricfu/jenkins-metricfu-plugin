package hudson.plugins.rubymetricfu;

import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.tasks.Publisher;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Action;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.model.Descriptor;
import hudson.model.Project;
import hudson.model.StreamBuildListener;
import hudson.model.Result;
import hudson.plugins.rake.Rake;
import hudson.plugins.rake.RubyInstallation;

import hudson.plugins.rubymetricfu.MetricFuParser;
import hudson.plugins.rubymetricfu.model.MetricFuResults;

import hudson.tasks.Publisher;

import java.io.File;
import java.io.IOException;

import org.codehaus.plexus.util.StringOutputStream;
import org.kohsuke.stapler.DataBoundConstructor;

public class RubyMetricsPublisher extends Publisher {

  private final Rake rake;
  private final String rakeInstallation;
  public static final DescriptorImpl DESCRIPTOR = new DescriptorImpl();

  @DataBoundConstructor
  public RubyMetricsPublisher(String rakeInstallation) {
    this.rakeInstallation = rakeInstallation;
    this.rake = new Rake(this.rakeInstallation, null, "metrics:all", null, null, true);
  }

     public boolean perform(Build<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
      copyMetricsToBuildDir(build);

      listener.getLogger().println("Publishing MetricFu stats...");

      if (build.getResult() == Result.FAILURE) {
        return false;
      }

      BuildListener stringListener = new StreamBuildListener(new StringOutputStream());

      if (rake.perform(build, launcher, stringListener)) {
        MetricFuResults results = parserBuildMetrics(build);
        RubyMetricsBuildAction action = new RubyMetricsBuildAction(build, results);
        build.getActions().add(action);
      }

      return true;
    }

    private MetricFuResults parserBuildMetrics(Build<?, ?> build){
      MetricFuParser parser = new MetricFuParser(reportFile(build));
      MetricFuResults results = parser.parse();
      return results;
    }

    private File reportFile(Build<?, ?> build){
        File rootDir = build.getRootDir();
        File yml = new File(rootDir, "report.yml");
        return yml;
    }

    private void copyMetricsToBuildDir(Build<?, ?> build) throws IOException, InterruptedException{
        final Project<?, ?> project = build.getParent();
        final FilePath workspace = project.getModuleRoot();
        final FilePath metricfuReports = workspace.child("tmp/metric_fu/");

        metricfuReports.copyRecursiveTo("**/*", new FilePath(build.getRootDir()));   
    }

    protected boolean fail(Build<?, ?> build, BuildListener listener, String message) {
      listener.getLogger().println(message);
      build.setResult(Result.FAILURE);
      return true;
    }

    public String getRakeInstallation() {
      return rakeInstallation;
    }

    public Action getProjectAction(Project project) {
      return new RubyMetricsProjectAction(project);
    }

  public static final class DescriptorImpl extends Descriptor<Publisher> {
 
    protected DescriptorImpl() {
      super(RubyMetricsPublisher.class);
    }

    @Override
    public String getHelpFile() {
      return "/plugin/rubymetricfu/rubyMetricFuHelp.html";
    }

    @Override
    public String getDisplayName() {
      return "Ruby Metric-fu reports";
    }

    public RubyInstallation[] getRakeInstallations() {
      return Rake.DESCRIPTOR.getInstallations();
    }
  }

  public Descriptor<Publisher> getDescriptor() {
    return DESCRIPTOR;
  }
}
