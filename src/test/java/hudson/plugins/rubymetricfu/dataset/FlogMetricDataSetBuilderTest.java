package test.java.hudson.plugins.rubymetricfu.dataset;

import hudson.model.Build;
import hudson.plugins.rubymetricfu.RubyMetricsBuildAction;
import hudson.plugins.rubymetricfu.dataset.FlogMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.model.MetricFuResults;

import org.jfree.data.category.CategoryDataset;

import junit.framework.TestCase;
import test.java.hudson.plugins.rubymetricfu.dataset.AbstractMetricDataSetBuilderTest;
import static org.mockito.Mockito.*;

/**
 *
 * @author josephwilk
 */
public class FlogMetricDataSetBuilderTest extends AbstractMetricDataSetBuilderTest{

    public FlogMetricDataSetBuilder builder(){
        return new FlogMetricDataSetBuilder();
    } 

    public void testItShouldAddMetricsToDataSet() throws Exception {
        MetricFuResults results = new MetricFuResults();
        results.setFlogMethodAverage("100.0");

        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, results);

        CategoryDataset data = builder().buildDataSet(action);

        assertEquals("Flog value was not stored",  (Float)data.getValue("Flog average", data.getColumnKey(0)), new Float(100.0));
    }

  
}
