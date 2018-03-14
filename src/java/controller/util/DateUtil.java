/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author
 */
public class DateUtil {

    public static String formateDate(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (date != null) {
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }

    public static Date parse(String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return simpleDateFormat.parse(date);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static java.sql.Date convertFormUtilToSql(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }
    
    public static Date getDebutAnnee(int annee){
        Date debAnnee=parse(annee+"-01-01");
        return debAnnee;
    }
    
    public static Date getDateLimite(int annee){
        Date dateLimite=parse(annee+"-04-01");
        return dateLimite;
    }
    
    public static int getNombreMoisRetard(int annee){
        Long dateLimite=getDateLimite(annee).getTime();
        Long dateActuelle=new Date().getTime();
        return ((Number)((dateActuelle-dateLimite)/(1000L*60L*60L*24L*30L))).intValue();
    }
}
