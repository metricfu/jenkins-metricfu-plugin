package hudson.plugins.rubyMetrics.flog.model;

import java.util.Arrays;
import java.util.Comparator;

public enum FlogMetrics {
	LINES, LOC, CLASSES, METHODS, M_C, LOC_M;
	
	public String prettyPrint() {
		switch (this) {			
			case LOC:
				return this.toString();
			case M_C:
				return slashedPrint();
			case LOC_M:
				return slashedPrint();				
		}
		return defaultPrettyPrint();
	}
	
	private String defaultPrettyPrint() {
		String prettyString = this.toString().toLowerCase();
		return prettyString.substring(0, 1).toUpperCase() + prettyString.substring(1);
	}
	
	private String slashedPrint() {
		return this.toString().replaceAll("_", "/");
	}
	
	public static FlogMetrics toRailsStatsMetrics(String name) {
		try {
			return FlogMetrics.valueOf(name.toUpperCase().replaceAll("/", "_"));
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getOrder() {
		return Arrays.asList(FlogMetrics.values()).indexOf(this);
	}
	
	public static class COMPARATOR implements Comparator<FlogMetrics> {
		public int compare(FlogMetrics o1, FlogMetrics o2) {
			return new Integer(o1.getOrder()).compareTo(new Integer(o2.getOrder()));
		}
		
	}
}
