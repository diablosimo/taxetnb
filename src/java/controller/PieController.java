package controller;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class PieController implements Serializable {

    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private PieChartModel pieModel3;

    public PieChartModel getPieModel3() {
        return pieModel3;
    }

    public void setPieModel3(PieChartModel pieModel3) {
        this.pieModel3 = pieModel3;
    }

    @PostConstruct
    public void init() {
        List<Object[]> listOfObjects = (List<Object[]>) controller.util.SessionUtil.getAttribute("listOfObjects");
        if (listOfObjects != null) {
            createPieModel1(listOfObjects, "revenueParQuartier");
        }
        List<Object[]> NbTerrainParCategorie = (List<Object[]>) controller.util.SessionUtil.getAttribute("NbTerrainParCategorie");
        if (NbTerrainParCategorie != null) {
            createPieModel2(NbTerrainParCategorie, "categorieParQuartier");
        }
        List<Object[]> resultsOfCategories = (List<Object[]>) controller.util.SessionUtil.getAttribute("resultsOfCategories");
        if (resultsOfCategories != null) {
            createPieModel3(resultsOfCategories, "categorieParQuartier");
        }
    }

    public PieChartModel getPieModel1() {
        return pieModel1;
    }

    public PieChartModel getPieModel2() {
        return pieModel2;
    }

    private void createPieModel1(List<Object[]> listOfObjects, String title) {
        pieModel2 = new PieChartModel();
        controller.util.ChartsUtil.createPieSerieBigDecimal(listOfObjects, pieModel2);
        pieModel2.setTitle(title);
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }
    private void createPieModel3(List<Object[]> listOfObjects, String title) {
        pieModel3 = new PieChartModel();
        controller.util.ChartsUtil.createPieSerieBigDecimal(listOfObjects, pieModel3);
        pieModel3.setTitle(title);
        pieModel3.setLegendPosition("e");
        pieModel3.setFill(false);
        pieModel3.setShowDataLabels(true);
        pieModel3.setDiameter(150);
    }

    private void createPieModel2(List<Object[]> listOfObjects, String title) {
        pieModel1 = new PieChartModel();
        controller.util.ChartsUtil.createPieSerieInteger(listOfObjects, pieModel1);

        pieModel1.setTitle(title);
        pieModel1.setLegendPosition("e");
        pieModel1.setFill(false);
        pieModel1.setShowDataLabels(true);
        pieModel1.setDiameter(150);
    }
}
