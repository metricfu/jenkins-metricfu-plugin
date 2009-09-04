package hudson.plugins.rubyMetrics.flog;

import hudson.model.AbstractBuild;
import hudson.model.HealthReport;
import hudson.plugins.rubyMetrics.AbstractRubyMetricsBuildAction;
import hudson.plugins.rubyMetrics.flog.model.FlogMetrics;
import hudson.plugins.rubyMetrics.flog.model.FlogResults;
import hudson.util.ChartUtil;
import hudson.util.DataSetBuilder;
import hudson.util.ChartUtil.NumberOnlyBuildLabel;

import java.util.Map;

public class FlogBuildAction extends AbstractRubyMetricsBuildAction {
	
	private final FlogResults results;
		
	public FlogBuildAction(AbstractBuild<?, ?> owner, FlogResults results) {		
		super(owner);
		this.results = results;
	}
	
	public HealthReport getBuildHealth() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public FlogResults getResults() {
		return results;
	}

	public String getDisplayName() {
		return "Flog stats";
	}

	public String getIconFileName() {
		return "graph.gif";
	}

	public String getUrlName() {
		return "flog";
	}
	
	@Override
	protected DataSetBuilder<String, NumberOnlyBuildLabel> getDataSetBuilder() {
		DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel> dsb = new DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel>();
		//Map<FlogMetrics, Integer> total = results.getTotal();		
		
		for (FlogBuildAction a = this; a != null; a = a.getPreviousResult()) {
			ChartUtil.NumberOnlyBuildLabel label = new ChartUtil.NumberOnlyBuildLabel(a.owner);
			Float flogFloatTotal = Float.parseFloat(results.getFlogTotal());
			dsb.add(flogFloatTotal.intValue(), "total", label);
		}		
		
        return dsb;
	}

	@Override
	protected String getRangeAxisLabel() {
		return "";
	}    

}
