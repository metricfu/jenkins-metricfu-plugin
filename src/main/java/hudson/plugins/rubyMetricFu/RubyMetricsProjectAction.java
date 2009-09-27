package hudson.plugins.rubyMetricFu;

import hudson.model.AbstractProject;
import hudson.model.Actionable;
import hudson.model.ProminentProjectAction;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Result;

@SuppressWarnings("unchecked")
public class RubyMetricsProjectAction extends Actionable
        implements ProminentProjectAction {

  protected final AbstractProject<?, ?> project;

  public RubyMetricsProjectAction(AbstractProject<?, ?> project) {
    this.project = project;
  }

  public AbstractProject<?, ?> getProject() {
    return project;
  }

  public String getIconFileName() {
    return "graph.gif";
  }

  public String getSearchUrl() {
    return getUrlName();
  }

  public void doFlogGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
    if (getLastResult() != null) {
      getLastResult().doFlogGraph(req, rsp);
    }
  }

  public void doFlayGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
    if (getLastResult() != null) {
      getLastResult().doFlayGraph(req, rsp);
    }
  }

  public void doRcovGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
    if (getLastResult() != null) {
      getLastResult().doRcovGraph(req, rsp);
    }
  }

  public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException {
    Integer buildNumber = getLastResultBuild();
    if (buildNumber == null) {
      rsp.sendRedirect2("nodata");
    } else {
      rsp.sendRedirect2("../" + buildNumber + "/" + getUrlName());
    }
  }

  public String getDisplayName() {
    return "Ruby Metrics Report";
  }

  public String getUrlName() {
    return "rubymetrics";
  }

  public RubyMetricsBuildAction getLastResult() {
    for (AbstractBuild<?, ?> b = project.getLastStableBuild(); b != null; b = b.getPreviousNotFailedBuild()) {
      if (b.getResult() == Result.FAILURE) {
        continue;
      }
      RubyMetricsBuildAction r = b.getAction(RubyMetricsBuildAction.class);
      if (r != null) {
        return r;
      }
    }
    return null;
  }

  public Integer getLastResultBuild() {
    for (AbstractBuild<?, ?> b = project.getLastStableBuild(); b != null; b = b.getPreviousNotFailedBuild()) {
      if (b.getResult() == Result.FAILURE) {
        continue;
      }
      RubyMetricsBuildAction r = b.getAction(RubyMetricsBuildAction.class);
      if (r != null) {
        return b.getNumber();
      }
    }
    return null;
  }
}
