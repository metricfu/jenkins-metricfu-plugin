package hudson.plugins.rubyMetrics;

import hudson.plugins.rubyMetrics.model.MetricFuResults;
import junit.framework.TestCase;

import java.io.File;
import java.io.InputStream;

public class FlogParserTest extends TestCase {

    MetricFuParser parser;

    public void setUp() throws Exception{
        File reportFile = new File(this.getClass().getResource("report.yml").toURI());
        parser = new MetricFuParser(reportFile);
    }

    public void testParsingFlog() throws Exception {
        MetricFuResults metrics = parser.parse();

        assertNotNull(metrics);

        assertEquals("Flog total was incorrect", metrics.getFlogTotal(), "325.7");
        assertEquals("Flog average was incorrect", "6.6", metrics.getFlogMethodAverage());
    }

   public void testParsingFlay() throws Exception {
        MetricFuResults metrics = parser.parse();

        assertNotNull(metrics);

        assertEquals("Flay total was incorrect", metrics.getFlayTotal(), "36");
    }

    public void testParsingRcov() throws Exception {
        MetricFuResults metrics = parser.parse();

        assertNotNull(metrics);

        assertEquals("Rcov coverage was incorrect", "49.6", metrics.getRcovCoverage());
    }

}
