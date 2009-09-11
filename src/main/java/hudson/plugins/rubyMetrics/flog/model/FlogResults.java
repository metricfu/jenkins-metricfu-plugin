package hudson.plugins.rubyMetrics.flog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FlogResults {
    private String flogMethodAverage;
    private String flogTotal;
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

    public String getFlogMethodAverage(){
        return this.flogMethodAverage;
    }

    public void setFlogMethodAverage(String average){
        this.flogMethodAverage = average;
    }

    public String getFlogTotal(){
        return this.flogTotal;
    }

    public void setFlogTotal(String total){
        this.flogTotal = total;
    }
}
