package hudson.plugins.rubymetricfu.dataset;

import hudson.plugins.rubymetricfu.model.MetricFuResults;

public class FlogMetricDataSetBuilder extends AbstractMetricDataSetBuilder{

    @Override
    protected String extractMetric(MetricFuResults results) {
        return results.getFlogMethodAverage();
    }

    @Override
    protected String legend() {
        return "Flog average";
    }
}
