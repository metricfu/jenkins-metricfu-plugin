package hudson.plugins.rubymetricfu.dataset;

import hudson.model.Build;
import hudson.plugins.rubymetricfu.RubyMetricsBuildAction;
import hudson.plugins.rubymetricfu.dataset.FlayMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.model.MetricFuResults;

import org.jfree.data.category.CategoryDataset;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

/**
 *
 * @author josephwilk
 */
public class FlayMetricDataSetBuilderTest extends TestCase{

    public FlayMetricDataSetBuilder builder;
    public Build mockOwner;

    public void setUp(){
        builder = new FlayMetricDataSetBuilder();
        mockOwner = mock(Build.class);
    }

    public void testItShouldAddMetricsToDataSet() throws Exception {
        MetricFuResults results = new MetricFuResults();
        results.setFlayTotal("100.0");

        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, results);

        CategoryDataset data = builder.buildDataSet(action);

        assertEquals("Flay value was not stored",  (Float)data.getValue("Flay average", data.getColumnKey(0)), new Float(100.0));
    }

    public void testItShouldHandleResultsNotBeingSet() throws Exception {
        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, null);

        CategoryDataset data = builder.buildDataSet(action);
    }

    public void testItShouldIgnoreNullMetricScores() throws Exception {
        MetricFuResults results = new MetricFuResults();

        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, results);

        CategoryDataset data = builder.buildDataSet(action);

        assertEquals("Data should not contain any values",  data.getColumnCount(), 0);
    }

}
