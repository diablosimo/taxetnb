/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author ACER
 */
public class SearchUtil {

    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public static String addConstraint(String beanAbrev, String atributeName, String operator, Object value) {
        boolean condition = value != null;
        if (value != null && value.getClass().getSimpleName().equals("String")) {
            condition = condition && !value.equals("");
        }
        if (condition && !atributeName.equals("")) {
            return " AND " + beanAbrev + "." + atributeName + " " + operator + " '" + value + "'";
        }
        return "";
    }

    public static String addConstraintOr(String beanAbrev, String atributeName, String operator, Object value) {
        boolean condition = value != null;
        if (value != null && value.getClass().getSimpleName().equals("String")) {
            condition = condition && !value.equals("");
        }
        if (condition) {
            return " OR " + beanAbrev + "." + atributeName + " " + operator + " '" + value + "'";
        }
        return "";
    }

    public static String addConstraintOr(String beanAbrev, String atributeName, String operator, List values) {
        String query = " AND ( 1=0 ";
        if (values != null && !values.isEmpty()) {
            for (Object value : values) {
                query += addConstraintOr(beanAbrev, atributeName, operator, value);
            }
            return query + ")";
        }
        return "";
    }

    public static String addConstraintMinMax(String beanAbrev, String atributeName, Object valueMin, Object valueMax) {
        String requette = "";
        if (valueMin != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " >= '" + valueMin + "'";
        }
        if (valueMax != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " <= '" + valueMax + "'";
        }
        return requette;
    }

    public static String addConstraintMinMaxStrict(String beanAbrev, String atributeName, Object valueMin, Object valueMax) {
        String requette = "";
        if (valueMin != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " > '" + valueMin + "'";
        }
        if (valueMax != null) {
            requette += " AND " + beanAbrev + "." + atributeName + " < '" + valueMax + "'";
        }
        return requette;
    }

    public static String addConstraintDate(String beanAbrev, String atributeName, String operator, Date value) {
        return addConstraint(beanAbrev, atributeName, operator, DateUtil.convertFormUtilToSql(value));
    }

    public static String addConstraintMinMaxDate(String beanAbrev, String atributeName, Date valueMin, Date valueMax) {
        return addConstraintMinMax(beanAbrev, atributeName, DateUtil.convertFormUtilToSql(valueMin), DateUtil.convertFormUtilToSql(valueMax));
    }

    public static String supprimerCleEtranger(String beanAbrev, String atributeName, String condition, Object value) {
        String requet = "";
        if (value != null) {
            requet = "UPDATE " + beanAbrev + " SET " + atributeName + " = NULL WHERE " + condition + " = " + value;
        }
        return requet;
    }

    public static String isTaxPaid(String abreviationAnnuel, String beanTrim, String abreviationNumTrim, int annee, int trim) {
        String requet = "";
        if (!abreviationAnnuel.equals("") && !beanTrim.equals("") && !abreviationNumTrim.equals("")) {
            requet = "SELECT item FROM " + beanTrim + " item WHERE item." + abreviationAnnuel + ".annee=" + annee + " AND item." + abreviationNumTrim + "=" + trim;
        }
        return requet;
    }

    // méthode pour ajouter une liste des contraintes dans une requete
    public static String findByAllString(String abreviationBean, List<String> attributes, List<String> data) {
        String requete = "";
        if (!abreviationBean.equals("") && !attributes.isEmpty() && attributes.size() == data.size()) {
            for (int i = 0; i < attributes.size(); i++) {
                requete += addConstraint(abreviationBean, attributes.get(i), "=", data.get(i));
            }
        }
        return requete;
    }

    public static boolean isStringNullOrVide(String str) {
        return (str == null || str.equals(""));
    }

    public static BigDecimal generateMaxList(List<BigDecimal> list) {
        BigDecimal max = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            BigDecimal nombre = list.get(i);
            if (nombre.compareTo(max) > 0) {
                max = nombre;
            }

        }
        return max;
    }

    public static BigDecimal generateMaxListObject(List<Object[]> list) {
        List<BigDecimal> listOfBigDecimal = objectListToBigDecimalList(list);
        BigDecimal max = generateMaxList(listOfBigDecimal);
        return max;
    }

    public static List<BigDecimal> objectListToBigDecimalList(List<Object[]> list) {
        List<BigDecimal> listOfBigDecimal = new ArrayList();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                BigDecimal nombre = (BigDecimal) list.get(i)[1];
                listOfBigDecimal.add(nombre);

            }
        }
        return listOfBigDecimal;
    }

    public static List<Integer> objectListToIntegerList(List<Object[]> list) {
        List<Integer> listOfInt = new ArrayList();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                int nombre = (int) list.get(i)[1];
                listOfInt.add(nombre);

            }
        }
        return listOfInt;
    }

    public static List<String> objectListToStringList(List<Object[]> list) {
        List<String> listOfString = new ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                String nom = (String) list.get(i)[0];
                listOfString.add(nom);
            }
        }
        return listOfString;
    }

}
