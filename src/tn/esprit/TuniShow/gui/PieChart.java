/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import java.util.ArrayList;
import tn.esprit.TuniShow.entity.Produit;
import tn.esprit.TuniShow.services.ProduitServices;

public class PieChart extends Form {

//    private ChartService dogS;
    private Produit high;
    private Produit low;
    private Produit mid;
    Form current;
    Form t;

    /**
     *
     * Creates a renderer for the specified colors.
     *
     */
    public PieChart(Form previous) {
        //current = this;
        
        t = createPieChartForm();
        add(t);
        
        getToolbar().addMaterialCommandToLeftBar("Statistique produit", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
//                getToolbar().setUIID("Button");

    }

    /**
     * Creates a renderer for the specified colors.
     */
    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(70);
        renderer.setLegendTextSize(70);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    /**
     * Builds a category series using the provided values.
     *
     * @param titles the series titles
     * @param values the values
     * @return the category series
     */
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        
        high = ProduitServices.getInstance().getHigh();
        
        mid = ProduitServices.getInstance().getAvrage();
        
        low = ProduitServices.getInstance().getLow();
        
        series.add("high", high.getPrix());
        
        series.add("mid", mid.getPrix());
        
        series.add("low", low.getPrix());

        
        
        return series;
    }

    public Form createPieChartForm() {
        // Generate the values
        double[] values = new double[]{33, 33, 34};

        // Set up the renderer
        int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsColor(ColorUtil.BLACK);
        renderer.setLabelsTextSize(50);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setGradientEnabled(true);
        r.setGradientStart(0, ColorUtil.BLUE);
        r.setGradientStop(0, ColorUtil.GREEN);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        com.codename1.charts.views.PieChart chart = new com.codename1.charts.views.PieChart(buildCategoryDataset("Statistique produit", values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        Form f = new Form("", new BorderLayout());
        f.add(BorderLayout.CENTER, c);
        return f;

   }

}
