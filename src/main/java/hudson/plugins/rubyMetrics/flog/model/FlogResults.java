package hudson.plugins.rubyMetrics.flog.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FlogResults {
	
    private Map<String, Map<FlogMetrics, Integer>> metrics = new HashMap<String, Map<FlogMetrics,Integer>>();
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

    private String flogMethodAverage;
    private String flogTotal;	
	
    public Collection<String> getHeaders() {
        Collection<String> headers = new ArrayList<String>();
        headers.add("Name");
		
        for (FlogMetrics metric : FlogMetrics.values()) {
            headers.add(metric.prettyPrint());
        }
		
        return headers;
    }
	
    public void addMetric(String classType, Map<FlogMetrics, Integer> metric) {
        metrics.put(classType, metric);
        if (!sortedLabels.contains(classType)) {
            sortedLabels.add(classType);
        }
    }
	
    public Map<FlogMetrics, Integer> getTotal() {		
        return metrics.get("Total");
    }
	
    public Map<String, Map<FlogMetrics, Integer>> getMetrics() {
        Comparator<String> comparator = new SortLabelsComparator(sortedLabels);
		
        Map<String, Map<FlogMetrics, Integer>> response = 
            new TreeMap<String, Map<FlogMetrics,Integer>>(comparator);
		
        for (Map.Entry<String, Map<FlogMetrics, Integer>> entry : metrics.entrySet()) {
            response.put(entry.getKey(), entry.getValue());
        }
		
        return response;
    }
    public void setMetrics(Map<String, Map<FlogMetrics, Integer>> metrics) {
        this.metrics = metrics;
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
