package hudson.plugins.rubymetricfu.dataset;

import hudson.plugins.rubymetricfu.model.MetricFuResults;

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
