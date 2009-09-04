package hudson.plugins.rubyMetrics.flog;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Result;
import hudson.plugins.rubyMetrics.AbstractRubyMetricsProjectAction;

public class FlogProjectAction extends AbstractRubyMetricsProjectAction {

	public FlogProjectAction(AbstractProject<?, ?> project) {
		super(project);
	}
	
	public String getDisplayName() {
		return "Flog stats report";
	}

	public String getUrlName() {		
		return "flog";
	}
	
	public FlogBuildAction getLastResult() {
		for (AbstractBuild<?, ?> b = project.getLastStableBuild(); b != null; b = b.getPreviousNotFailedBuild()) {
	        if (b.getResult() == Result.FAILURE)
	            continue;
	        FlogBuildAction r = b.getAction(FlogBuildAction.class);
	        if (r != null)
	            return r;
	    }
	    return null;
	}
	
	public Integer getLastResultBuild() {
		for (AbstractBuild<?, ?> b = project.getLastStableBuild(); b != null; b = b.getPreviousNotFailedBuild()) {
            if (b.getResult() == Result.FAILURE)
                continue;
            FlogBuildAction r = b.getAction(FlogBuildAction.class);
            if (r != null)
                return b.getNumber();
        }
        return null;
	}

}
