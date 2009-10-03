package hudson.plugins.rubymetricfu.dataset;

import hudson.plugins.rubymetricfu.model.MetricFuResults;

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
