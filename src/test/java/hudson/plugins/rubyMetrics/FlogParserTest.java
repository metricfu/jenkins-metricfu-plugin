package hudson.plugins.rubyMetrics;

import hudson.plugins.rubyMetrics.model.MetricFuResults;
import junit.framework.TestCase;

import java.io.File;
import java.io.InputStream;

public class FlogParserTest extends TestCase {

    public void testParse() throws Exception {
        File root = new File(this.getClass().getResource("report.yml").toURI());

        MetricFuParser parser = new MetricFuParser(root);
        MetricFuResults metrics = parser.parse();

        assertNotNull(metrics);

        assertEquals("Flog total was incorrect", metrics.getFlogTotal(), "325.7");
        assertEquals("Flog average was incorrect", "6.6", metrics.getFlogMethodAverage());
    }
}
