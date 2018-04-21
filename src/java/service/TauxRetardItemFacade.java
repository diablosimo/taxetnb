/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.CategorieTerrain;
import bean.TauxRetard;
import bean.TauxRetardItem;
import controller.util.SearchUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Aniela
 */
@Stateless
public class TauxRetardItemFacade extends AbstractFacade<TauxRetardItem> {
    
    @EJB
    private TauxRetardFacade tauxRetardFacade;
    
    @PersistenceContext(unitName = "taxeTNBPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
public List<TauxRetardItem> Add(TauxRetardItem tauxRetardItem ,List<TauxRetardItem> tauxRetardItems){
    int x=0;
    for (int i = 0; i < tauxRetardItems.size(); i++) {
      TauxRetardItem item =tauxRetardItems.get(i);
      if(tauxRetardItem.getCategorieTerrain().getId()==item.getCategorieTerrain().getId()){
          System.out.println("in condition of ct");
          x=1;
      }
    }
    if(x==0){
       // tauxTaxeItem.getTauxTaxe().setDateApplication(new Date());
        tauxRetardItems.add(clone(tauxRetardItem));
        
    }
    return tauxRetardItems;
    
   
}
    public TauxRetardItemFacade() {
        super(TauxRetardItem.class);
    }
    
  public int creer(TauxRetardItem tauxRetardItem){
        if (tauxRetardItem==null){
            return -1;
        }else
            if (tauxRetardItem.getTauxPremierMois()==null){
            return -2;
        }else if(tauxRetardItem.getTauxAutreMois()==null){
            return -3;
        }
        else{
               create(tauxRetardItem);
            return 1;
        }
    }
  
  public TauxRetardItem findCurrentOneByCategorie(CategorieTerrain categorieTerrain){
      List<TauxRetardItem> tauxRetardItems=tauxRetardFacade.findCurrentOne().getTauxRetardItems();
      for (int i = 0; i < tauxRetardItems.size(); i++) {
          TauxRetardItem get = tauxRetardItems.get(i);
          if(get.getCategorieTerrain().getNom().equals(categorieTerrain.getNom())){
              return get;
          }
      }
      return null;
  }
  
  
    
    public List<TauxRetardItem> findByCrit(Double premierMin,Double premierMax,Double autreMin,Double autreMax) {

        String reqette = "SELECT t FROM TauxTaxeRetardItem t WHERE 1=1 ";

        reqette += SearchUtil.addConstraintMinMax("t", "tauxPremierMois", premierMin, premierMax);
        reqette += SearchUtil.addConstraintMinMax("t", "tauxAutreMois", autreMin, autreMax);
        return em.createQuery(reqette).getResultList();

    }
    public List<TauxRetardItem> findByTauxRetard(TauxRetard tauxRetard) {

        String reqette = "SELECT t FROM TauxRetardItem t WHERE t.tauxRetard.id="+tauxRetard.getId()+"";
 return em.createQuery(reqette).getResultList();

    }

  
  public void clone(TauxRetardItem tauxRetardItemOriginal, TauxRetardItem tauxTaxeItemCloned) {
        tauxRetardItemOriginal.setCategorieTerrain(tauxRetardItemOriginal.getCategorieTerrain());
        tauxTaxeItemCloned.setTauxRetard(tauxRetardItemOriginal.getTauxRetard());
        tauxTaxeItemCloned.setCategorieTerrain(tauxRetardItemOriginal.getCategorieTerrain());
                

    }

    public TauxRetardItem clone(TauxRetardItem tauxRetardItem) {
        TauxRetardItem cloned = new TauxRetardItem();
        clone(tauxRetardItem, cloned);
        return cloned;
    }
}
