package hudson.plugins.rubyMetricFu.dataset;

import hudson.plugins.rubyMetricFu.model.MetricFuResults;

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
