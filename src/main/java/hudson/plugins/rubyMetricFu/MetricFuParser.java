package hudson.plugins.rubyMetricFu;

import hudson.plugins.rubyMetricFu.model.MetricFuResults;

import org.yaml.snakeyaml.*;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;

import java.util.Map;

public class MetricFuParser {

  private File yamlReport;

  public MetricFuParser(File yamlReport) {
    this.yamlReport = yamlReport;
  }

  public MetricFuResults parse() {
    MetricFuResults result = new MetricFuResults();

    try {
      InputStream input = new FileInputStream(yamlReport);
      Yaml yaml = new Yaml();
      Map data = (Map) yaml.load(input);

      Map flogData = (Map) data.get(":flog");
      Map flayData = (Map) data.get(":flay");
      Map rcovData = (Map) data.get(":rcov");

      result.setFlogMethodAverage((String) (flogData.get(":average").toString()));
      result.setFlogTotal((String) (flogData.get(":total").toString()));

      result.setFlayTotal((String) flayData.get(":total_score").toString());

      result.setRcovCoverage((String) rcovData.get(":global_percent_run").toString());
    } catch (Exception e) {
      System.out.println(e);
    }
    return result;
  }
}
