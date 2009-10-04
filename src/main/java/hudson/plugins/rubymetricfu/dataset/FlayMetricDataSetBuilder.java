package hudson.plugins.rubymetricfu.dataset;

import hudson.plugins.rubymetricfu.model.MetricFuResults;
import hudson.util.ChartUtil;
import hudson.util.DataSetBuilder;

public class FlayMetricDataSetBuilder extends AbstractMetricDataSetBuilder{

    @Override
    protected String extractMetric(MetricFuResults results) {
        return results.getFlayTotal();
    }

    @Override
    protected String legend() {
        return "Flay average";
    }

}
