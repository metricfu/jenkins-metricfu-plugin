package hudson.plugins.rubymetricfu.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MetricFuResults {

  private String flogMethodAverage;
  private String flogTotal;
  private String flayTotal;
  private String rcovCoverage;
  private List<String> sortedLabels = new ArrayList<String>();

  private class SortLabelsComparator implements Comparator<String> {

    private final List<String> sortedLabels;

    public SortLabelsComparator(List<String> coll) {
      sortedLabels = coll;
    }

    public int compare(String o1, String o2) {
      return new Integer(sortedLabels.indexOf(o1)).compareTo(sortedLabels.indexOf(o2));
    }
  }

  public String getFlogMethodAverage() {
    return this.flogMethodAverage;
  }

  public void setFlogMethodAverage(String average) {
    this.flogMethodAverage = average;
  }

  public String getFlogTotal() {
    return this.flogTotal;
  }

  public void setFlogTotal(String total) {
    this.flogTotal = total;
  }

  public String getFlayTotal() {
    return flayTotal;
  }

  public void setFlayTotal(String flayTotal) {
    this.flayTotal = flayTotal;
  }

  public void setRcovCoverage(String coverage){
    this.rcovCoverage = coverage;
  }

  public String getRcovCoverage(){
    return this.rcovCoverage;
  }

}
