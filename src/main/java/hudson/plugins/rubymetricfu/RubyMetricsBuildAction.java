package hudson.plugins.rubymetricfu;

import hudson.plugins.rubymetricfu.dataset.FlayMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.dataset.FlogMetricDataSetBuilder;
import hudson.plugins.rubymetricfu.dataset.RcovMetricDataSetBuilder;
import hudson.model.AbstractBuild;
import hudson.model.HealthReportingAction;
import hudson.model.Result;

import hudson.util.ColorPalette;

import hudson.util.ShiftedCategoryAxis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.util.ChartUtil;

import hudson.model.AbstractBuild;
import hudson.model.HealthReport;
import hudson.plugins.rubymetricfu.model.MetricFuResults;

import hudson.util.DataSetBuilder;

@SuppressWarnings("unchecked")
public class RubyMetricsBuildAction implements HealthReportingAction {

  private final MetricFuResults results;
  protected final AbstractBuild<?, ?> owner;

  public RubyMetricsBuildAction(AbstractBuild<?, ?> owner, MetricFuResults results) {
    this.owner = owner;
    this.results = results;
  }

  public RubyMetricsBuildAction getPreviousResult() {
    AbstractBuild<?, ?> b = owner;
    while (true) {
      b = b.getPreviousBuild();
      if (b == null) {
        return null;
      }
      if (b.getResult() == Result.FAILURE) {
        continue;
      }
      RubyMetricsBuildAction r = b.getAction(this.getClass());
      if (r != null) {
        return r;
      }
    }
  }

  public AbstractBuild<?, ?> getOwner() {
    return owner;
  }
  public HealthReport getBuildHealth() {
    return null;
  }

  public MetricFuResults getResults() {
    return results;
  }

  public String getDisplayName() {
    return "Ruby Metrics Report";
  }

  public String getIconFileName() {
    return "graph.gif";
  }

  public String getUrlName() {
    return "rubymetricfu";
  }

  public void doFlayGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
    FlayMetricDataSetBuilder builder = new FlayMetricDataSetBuilder();
    doGraph(req, rsp, builder.buildDataSet(this));
  }

  public void doFlogGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
    FlogMetricDataSetBuilder builder = new FlogMetricDataSetBuilder();
    doGraph(req, rsp, builder.buildDataSet(this));
  }

  public void doRcovGraph(StaplerRequest req, StaplerResponse rsp) throws IOException {
    RcovMetricDataSetBuilder builder = new RcovMetricDataSetBuilder();
    doGraph(req, rsp, builder.buildDataSet(this));
  }

  private void doGraph(StaplerRequest req, StaplerResponse rsp, CategoryDataset data) throws IOException {
    ifAwtProblemRedirect(req, rsp);
    if (shouldGenerateGraph(req, rsp)){
      ChartUtil.generateGraph(req, rsp, createChart(data, ""), 500, 200);
    }
  }

  private void ifAwtProblemRedirect(StaplerRequest req, StaplerResponse rsp) throws IOException{
    if (ChartUtil.awtProblem) {
      rsp.sendRedirect2(req.getContextPath() + "/images/headless.png");
    }    
  }

  private boolean shouldGenerateGraph(StaplerRequest req, StaplerResponse rsp){
    if (ChartUtil.awtProblem) {
      return false;
    }

    Calendar t = owner.getTimestamp();

    if (req.checkIfModified(t, rsp)) {
      return false; // up to date
    }
    return true;
  }

  private JFreeChart createChart(CategoryDataset dataset, String rangeAxisLabel) {

    final JFreeChart chart = ChartFactory.createLineChart(
            null, // chart title
            null, // unused
            rangeAxisLabel, // range axis label
            dataset, // data
            PlotOrientation.VERTICAL, // orientation
            true, // include legend
            true, // tooltips
            false // urls
            );

    // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

    final LegendTitle legend = chart.getLegend();
    legend.setPosition(RectangleEdge.RIGHT);

    chart.setBackgroundPaint(Color.white);

    final CategoryPlot plot = chart.getCategoryPlot();

    // plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
    plot.setBackgroundPaint(Color.WHITE);
    plot.setOutlinePaint(null);
    plot.setRangeGridlinesVisible(true);
    plot.setRangeGridlinePaint(Color.black);

    CategoryAxis domainAxis = new ShiftedCategoryAxis(null);
    plot.setDomainAxis(domainAxis);
    domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
    domainAxis.setLowerMargin(0.0);
    domainAxis.setUpperMargin(0.0);
    domainAxis.setCategoryMargin(0.0);

    final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());

    final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
    renderer.setBaseStroke(new BasicStroke(2.0f));
    renderer.setShapesVisible(true);
    renderer.setLinesVisible(true);
    
    ColorPalette.apply(renderer);

    // crop extra space around the graph
    plot.setInsets(new RectangleInsets(5.0, 0, 0, 5.0));

    return chart;
  }
}
