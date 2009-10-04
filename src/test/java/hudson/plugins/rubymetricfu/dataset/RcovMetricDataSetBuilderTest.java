package test.java.hudson.plugins.rubymetricfu.dataset;

import hudson.model.Build;
import hudson.plugins.rubymetricfu.RubyMetricsBuildAction;
import hudson.plugins.rubymetricfu.dataset.RcovMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.model.MetricFuResults;

import org.jfree.data.category.CategoryDataset;

import junit.framework.TestCase;
import test.java.hudson.plugins.rubymetricfu.dataset.AbstractMetricDataSetBuilderTest;
import static org.mockito.Mockito.*;

/**
 *
 * @author josephwilk
 */
public class RcovMetricDataSetBuilderTest extends AbstractMetricDataSetBuilderTest{

    public RcovMetricDataSetBuilder builder(){
        return new RcovMetricDataSetBuilder();
    } 

    public void testItShouldAddMetricsToDataSet() throws Exception {
        MetricFuResults results = new MetricFuResults();
        results.setRcovCoverage("100.0");

        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, results);

        CategoryDataset data = builder().buildDataSet(action);

        assertEquals("Rcov value was not stored",  (Float)data.getValue("Rcov coverage", data.getColumnKey(0)), new Float(100.0));
    }

  
}
