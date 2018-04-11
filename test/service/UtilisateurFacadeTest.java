/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Utilisateur;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aniela
 */
public class UtilisateurFacadeTest {
    
    public UtilisateurFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class UtilisateurFacade.
     * @throws java.lang.Exception
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");
        Utilisateur entity = new Utilisateur("EE567", "aniconda","ahle touate","chaima");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
        instance.create(entity);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
       
    }

//    /**
//     * Test of edit method, of class UtilisateurFacade.
//     */
//    @Test
//    public void testEdit() throws Exception {
//        System.out.println("edit");
//        Utilisateur entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
//        instance.edit(entity);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of remove method, of class UtilisateurFacade.
//     */
//    @Test
//    public void testRemove() throws Exception {
//        System.out.println("remove");
//        Utilisateur entity = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
//        instance.remove(entity);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of find method, of class UtilisateurFacade.
//     */
//    @Test
//    public void testFind() throws Exception {
//        System.out.println("find");
//        Object id = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
//        Utilisateur expResult = null;
//        Utilisateur result = instance.find(id);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findAll method, of class UtilisateurFacade.
//     */
//    @Test
//    public void testFindAll() throws Exception {
//        System.out.println("findAll");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
//        List<Utilisateur> expResult = null;
//        List<Utilisateur> result = instance.findAll();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findRange method, of class UtilisateurFacade.
//     */
//    @Test
//    public void testFindRange() throws Exception {
//        System.out.println("findRange");
//        int[] range = null;
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
//        List<Utilisateur> expResult = null;
//        List<Utilisateur> result = instance.findRange(range);
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of count method, of class UtilisateurFacade.
//     */
//    @Test
//    public void testCount() throws Exception {
//        System.out.println("count");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        UtilisateurFacade instance = (UtilisateurFacade)container.getContext().lookup("java:global/classes/UtilisateurFacade");
//        int expResult = 0;
//        int result = instance.count();
//        assertEquals(expResult, result);
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
