package test.java.hudson.plugins.rubymetricfu.dataset;

import hudson.model.Build;
import hudson.plugins.rubymetricfu.RubyMetricsBuildAction;
import hudson.plugins.rubymetricfu.dataset.AbstractMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.dataset.FlayMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.model.MetricFuResults;
import junit.framework.TestCase;
import org.jfree.data.category.CategoryDataset;

import static org.mockito.Mockito.*;

/**
 *
 * @author josephwilk
 */
public abstract class AbstractMetricDataSetBuilderTest extends TestCase{

    public Build mockOwner;

    public abstract <T extends AbstractMetricDataSetBuilder> T builder();

    public void setUp(){
        mockOwner = mock(Build.class);
    }

    public void testItShouldHandleResultsNotBeingSet() throws Exception {
        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, null);

        CategoryDataset data = builder().buildDataSet(action);
    }

    public void testItShouldIgnoreNullMetricScores() throws Exception {
        MetricFuResults results = new MetricFuResults();

        RubyMetricsBuildAction action = new RubyMetricsBuildAction(mockOwner, results);

        CategoryDataset data = builder().buildDataSet(action);

        assertEquals("Data should not contain any values",  data.getColumnCount(), 0);
    }


}
