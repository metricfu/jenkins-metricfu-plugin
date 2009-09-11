package hudson.plugins.rubyMetrics.flog;

import hudson.plugins.rubyMetrics.flog.model.FlogResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.plexus.util.StringOutputStream;

public class FlogParser {
	
	public FlogResults parse(StringOutputStream output) {		
		return parse(output.toString());
	}
	
	public FlogResults parse(String output) {
		FlogResults response = new FlogResults();
		
		String[] aux = output.split("[\n\r]");
		Collection<String> lines = new LinkedHashSet<String>(Arrays.asList(aux));
				
		lines = removeSeparators(lines);		
		
		Iterator<String> linesIterator = lines.iterator();		
		String total_header = linesIterator.next();
		String average_header = linesIterator.next();                		

    response.setFlogTotal(getScore(total_header));
    response.setFlogMethodAverage(getScore(average_header));
		
		return response;
	}
	
	private Collection<String> removeSeparators(Collection<String> lines) {
		Collection<String> response = new LinkedHashSet<String>();
		for (String line : lines) {
		  response.add(line.replaceAll("[\\r\\n+-]+", ""));
		}
		
		response.remove("");		
		return response;
	}
	
    private String getScore(String line){
        String[] score_and_info = line.split(":");
        return clean(score_and_info[0]);
    }

    private String clean(String s){
        return s.replaceAll("[\\s]+","");
    }
}
