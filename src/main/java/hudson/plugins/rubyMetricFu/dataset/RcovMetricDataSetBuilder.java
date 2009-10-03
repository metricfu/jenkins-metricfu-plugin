package hudson.plugins.rubyMetricFu.dataset;

import hudson.plugins.rubyMetricFu.model.MetricFuResults;

public class RcovMetricDataSetBuilder extends AbstractMetricDataSetBuilder{

    @Override
    protected String extractMetric(MetricFuResults results) {
        return results.getRcovCoverage();
    }

    @Override
    protected String legend() {
        return "Rcov coverage";
    }

}
