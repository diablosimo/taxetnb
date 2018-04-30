/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.math.BigDecimal;
import java.util.List;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author user
 */
public class ChartsUtil {
    
    
    
    public  static void createCategorySerie(List<Object[]> listOfObjects ,LineChartModel model) {
       LineChartSeries series = new LineChartSeries();
        List<BigDecimal> BigDecimalList = controller.util.SearchUtil.objectListToBigDecimalList(listOfObjects);
        List<String> StringList = controller.util.SearchUtil.objectListToStringList(listOfObjects);
        if (listOfObjects != null) {

            if (StringList.size() == BigDecimalList.size()) {
                for (int i = 0; i < listOfObjects.size(); i++) {
                    series.set(StringList.get(i), BigDecimalList.get(i));
                }
         
}
        }
         model.addSeries(series);
    }
    
    public  static void CreateLinearSerie(LineChartModel model, List<BigDecimal> list) {
      
        LineChartSeries series = new LineChartSeries();
       
        for (int i = 0; i < list.size(); i++) {
            BigDecimal get = list.get(i);
            series.set(i + 1, get);
        }
        model.addSeries(series);
      
    }
     public  static void createPieSerieBigDecimal(List<Object[]> listOfObjects ,PieChartModel model) {
      
        List<BigDecimal> BigDecimalList = controller.util.SearchUtil.objectListToBigDecimalList(listOfObjects);
        List<String> StringList = controller.util.SearchUtil.objectListToStringList(listOfObjects);
        if (listOfObjects != null) {

            if (StringList.size() == BigDecimalList.size()) {
                for (int i = 0; i < listOfObjects.size(); i++) {
                    model.set(StringList.get(i), BigDecimalList.get(i));
                }
         
}
        }}
        public  static void createPieSerieInteger(List<Object[]> listOfObjects ,PieChartModel model) {
      
        List<Integer> IntegerList = controller.util.SearchUtil.objectListToIntegerList(listOfObjects);
        List<String> StringList = controller.util.SearchUtil.objectListToStringList(listOfObjects);
        if (listOfObjects != null) {

            if (StringList.size() == IntegerList.size()) {
                for (int i = 0; i < listOfObjects.size(); i++) {
                    model.set(StringList.get(i), IntegerList.get(i));
                }
         
}
        }
       
    }
}