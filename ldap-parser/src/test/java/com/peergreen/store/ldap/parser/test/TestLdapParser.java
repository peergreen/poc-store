//package com.peergreen.store.ldap.parser.test;
//
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Test;
//
//import com.peergreen.store.ldap.parser.exception.InvalidLdapFormatException;
//import com.peergreen.store.ldap.parser.impl.DefaultLdapParser;
//import com.peergreen.tree.Node;
//
//public class TestLdapParser {
//    private DefaultLdapParser parser; 
//    private String filter ; 
//   @BeforeMethod
//   public void setUp(){
//      parser = new DefaultLdapParser();
//   }
//   
//  @Test
//  public void f() {
//      
//      filter = "(&(name=jpa)";
//      try {
//      Node<String> root =   parser.parse(filter);
//    } catch (InvalidLdapFormatException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
//    
//  }
//  
//  @Test
//  public void f1(){
//      filter =  "(&(name=jpa))";
//      try {
//          Node<String> root =   parser.parse(filter);
//        } catch (InvalidLdapFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//  }
//  
//  @Test
//  public void f2(){
//
//     filter = "(&(name=jpa)(version=2.1)(namespace=BD))";
//     try {
//         Node<String> root =   parser.parse(filter);
//       } catch (InvalidLdapFormatException e) {
//           // TODO Auto-generated catch block
//           e.printStackTrace();
//       }
//  }
//  
//  @Test
//  public void f3(){
//
//      filter = "(&(name=jpa)(|(version=2.1)(version <= 3.4)(namespace=BD))";
//      try {
//          Node<String> root =   parser.parse(filter);
//        } catch (InvalidLdapFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//     
//  }
//  
//  @Test
//  public void f4(){
//
//      filter = "(&(name=jpa)(!(version=2.1))(namespace=BD))";
//      try {
//          Node<String> root =   parser.parse(filter);
//        } catch (InvalidLdapFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//     
//  }
//  
//  @Test
//  public void f5(){
//      filter = "(!(&(a=b)(c!=d)))";
//      try {
//          Node<String> root =   parser.parse(filter);
//        } catch (InvalidLdapFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//  }
//  
//  @Test
//  public void f6(){
//      filter = "(f(name^jpa)(version=2.1)(namespace=BD))";
//      try {
//          Node<String> root =   parser.parse(filter);
//        } catch (InvalidLdapFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//  }
//  @Test
//  public void f7(){
//      filter = "(&(name^jpa)(version x 2.1)(namespace & BD))";
//      try {
//          Node<String> root =   parser.parse(filter);
//        } catch (InvalidLdapFormatException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//  }
//}
