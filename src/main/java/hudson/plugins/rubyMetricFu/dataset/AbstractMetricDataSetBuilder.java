package hudson.plugins.rubyMetricFu.dataset;

import hudson.plugins.rubyMetricFu.*;
import hudson.plugins.rubyMetricFu.model.MetricFuResults;
import hudson.util.DataSetBuilder;

import hudson.util.ChartUtil.NumberOnlyBuildLabel;
import hudson.util.ChartUtil;

import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author josephwilk
 */
public abstract class AbstractMetricDataSetBuilder {

   public CategoryDataset buildDataSet(RubyMetricsBuildAction action) {
   DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel> dsb = new DataSetBuilder<String, ChartUtil.NumberOnlyBuildLabel>();

    for (RubyMetricsBuildAction a = action; a != null; a = a.getPreviousResult()) {
      ChartUtil.NumberOnlyBuildLabel label = new ChartUtil.NumberOnlyBuildLabel(a.getOwner());
      Float flogFloatMethodAverage = Float.parseFloat(extractMetric(a.getResults()));
      if (flogFloatMethodAverage != null){
        dsb.add(flogFloatMethodAverage, legend(), label);
      }
    }

    return dsb.build();
   }

   protected abstract String extractMetric(MetricFuResults results);
   protected abstract String legend();
}
