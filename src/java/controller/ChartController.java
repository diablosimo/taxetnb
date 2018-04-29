/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
/**
 *
 * @author user
 */
public class ChartController implements Serializable {

    private LineChartModel lineModel1;

    public LineChartModel getLineModel1() {
        return lineModel1;
    }
    private LineChartModel lineModel2;

    public LineChartModel getLineModel2() {
        return lineModel2;
    }
 private LineChartModel lineModel3;

    public LineChartModel getLineModel3() {
        return lineModel3;
    }
    @PostConstruct
    public void init() {
        List<BigDecimal> listRevenue = (List<BigDecimal>) controller.util.SessionUtil.getAttribute("listRevenue");
        if (listRevenue != null) {
            createLineModels("revenueParMois", "revenue", listRevenue);
        }
       
        List<Object[]> listOfObjects = (List<Object[]>) controller.util.SessionUtil.getAttribute("listOfObjects");
        if (listOfObjects != null) {
             System.out.println("cc hani dkhelt"+listOfObjects);
            for (int i = 0; i < listOfObjects.size(); i++) {
                Object[] get = listOfObjects.get(i);
                 System.out.println("ha l'objet"+i);
                System.out.println(get[0]);
                System.out.println(get[1]);
                
            }
            createCategoryModel("revenueParQuartier", "quartier", "quartier", listOfObjects);

        }
        List<Object[]> resultsOfCategories = (List<Object[]>) controller.util.SessionUtil.getAttribute("resultsOfCategories");
       
        if (resultsOfCategories != null) {
            System.out.println("cc hani dkhelt");
            for (int i = 0; i < resultsOfCategories.size(); i++) {
                Object[] get = resultsOfCategories.get(i);
                 System.out.println("ha l'objet"+i);
                System.out.println(get[0]);
                System.out.println(get[1]);
                
            }
             
            createCategoryModel2("revenueParCategorie", "catgorie", "categorie", resultsOfCategories);
        }
    }

    public LineChartModel initLinearModel(String linearlabel, List<BigDecimal> list) {
        LineChartModel model = new LineChartModel();
        controller.util.ChartsUtil.CreateLinearSerie(model, list);
        return model;
    }

    public void createLineModels(String linelabel, String linearlabel, List<BigDecimal> list) {
        lineModel1 = initLinearModel(linearlabel, list);
        lineModel1.setTitle(linelabel);
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        Axis xAxis = lineModel1.getAxis(AxisType.X);
        xAxis.setMin(0);
        xAxis.setMin(12);
        yAxis.setMin(0);
        yAxis.setMax(controller.util.SearchUtil.generateMaxList(list).multiply(new BigDecimal(1.1)));

    }

    public LineChartModel initCategoryModel(String label, List<Object[]> listOfObjects) {
        LineChartModel model = new LineChartModel();
        controller.util.ChartsUtil.createCategorySerie(listOfObjects, model);

        return model;
    }

    public void createCategoryModel(String linelabel, String axisLabel, String label, List<Object[]> listOfObjects) {
        lineModel2 = initCategoryModel(label, listOfObjects);
        lineModel2.setTitle(linelabel);
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis(axisLabel));
        Axis yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("revenues");
        yAxis.setMin(0);
        yAxis.setMax(controller.util.SearchUtil.generateMaxListObject(listOfObjects).multiply(new BigDecimal(1.1)));
    }
    public void createCategoryModel2(String linelabel, String axisLabel, String label, List<Object[]> list) {
        lineModel3 = initCategoryModel(label, list);
        lineModel3.setTitle(linelabel);
        lineModel3.setLegendPosition("e");
        lineModel3.setShowPointLabels(true);
        lineModel3.getAxes().put(AxisType.X, new CategoryAxis(axisLabel));
        Axis yAxis = lineModel3.getAxis(AxisType.Y);
        yAxis.setLabel("revenues");
        yAxis.setMin(0);
        yAxis.setMax(controller.util.SearchUtil.generateMaxListObject(list).multiply(new BigDecimal(1.1)));
    }
 
    

}
