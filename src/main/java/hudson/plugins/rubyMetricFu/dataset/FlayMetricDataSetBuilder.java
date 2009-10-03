package hudson.plugins.rubyMetricFu.dataset;

import hudson.plugins.rubyMetricFu.model.MetricFuResults;

public class FlayMetricDataSetBuilder extends AbstractMetricDataSetBuilder{

    @Override
    protected String extractMetric(MetricFuResults results) {
        return results.getFlayTotal();
    }

    @Override
    protected String legend() {
        return "Flog average";
    }

}
