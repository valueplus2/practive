package wsc;

import com.sforce.soap.SuperClass.SoapConnection;
import com.sforce.soap.SuperClass.Connector;
import com.sforce.soap.SuperClass.RequestClass;
import com.sforce.soap.SuperClass.ResponseClass;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
//import com.sforce.soap.enterprise.*;

import com.sforce.soap.partner.sobject.SObject;

import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;

public class CallWS {


  static final String USERNAME = "fuzhaolong@qq.com";
  static final String PASSWORD = "valueplus1212";

  static SoapConnection MyWebserviceWSconnection;
  //static EnterpriseConnection enterpriseConnection;
  static PartnerConnection partnerConnection;

  public static void main(String[] args) {

    ConnectorConfig config = new ConnectorConfig();
    config.setUsername(USERNAME);
    config.setPassword(PASSWORD);


    try {

      //create a connection to Enterprise API -- authentication occurs
      //enterpriseConnection = com.sforce.soap.enterprise.Connector.newConnection(config);  
      partnerConnection = com.sforce.soap.partner.Connector.newConnection(config);
      // display some current settings
      System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
      System.out.println("Service EndPoint: "+config.getServiceEndpoint());
      System.out.println("Username: "+config.getUsername());
      System.out.println("SessionId: "+config.getSessionId());


      //create new connection to exportData webservice -- no authentication information is included
      MyWebserviceWSconnection = Connector.newConnection("","");
      //include session Id (obtained from enterprise api) in exportData webservice
      MyWebserviceWSconnection.setSessionHeader(config.getSessionId());
      
      RequestClass[] reqClass = new RequestClass[100];
      for(int i=0;i<reqClass.length;i++){
    	  RequestClass Req = new RequestClass();
    	  Req.setAccountName("Account Created By Java Program"+i); 
    	  reqClass[i]=Req;
      }
      
      ResponseClass[] resClass = new ResponseClass[100];
//      ResponseClass resClass = new ResponseClass();
      resClass = MyWebserviceWSconnection.behaviourOfWebService(reqClass);
     
      for(ResponseClass a:resClass){
	  System.out.println("Record ID ---"+a.getResponseResultID());	  
      System.out.println("Record Name ---"+a.getResponseResultName());
      System.out.println("Record Record Type ---"+a.getResponseResultRecordType());
      }
      //String result = MyWebserviceWSconnection.receiveData("test");
      //System.out.println("Result: "+result);
      try {
    	  QueryResult queryResults1 = partnerConnection.query("select id from Case ORDER BY CreatedDate DESC LIMIT 5");
    	  if (queryResults1.getSize() > 0) {
	          for (SObject s: queryResults1.getRecords()) {
	            System.out.println("Id: " + s.getId() + " " + s.getField("Name"));
	          }
    	  }
	        
    	  QueryResult queryResults = partnerConnection.query("SELECT Id, FirstName, LastName, Account.Name " +
	              "FROM Contact WHERE AccountId != NULL ORDER BY CreatedDate DESC LIMIT 5");
	      if (queryResults.getSize() > 0) {
	          for (SObject s: queryResults.getRecords()) {
	            System.out.println("Id: " + s.getId() + " " + s.getField("FirstName"));
	            System.out.println("小白");
	            System.out.println("小hong");
	            System.out.println("小");
	          }
	        }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

    } catch (ConnectionException e1) {
        e1.printStackTrace();
    }  
  }
  /*private static void queryContacts() {
	     
	    System.out.println("Querying for the 5 newest Contacts...");
	     
	    try {
	        
	      QueryResult queryResults = enterpriseConnection.query("SELECT Id, FirstName, LastName, Account.Name " +
	              "FROM Contact WHERE AccountId != NULL ORDER BY CreatedDate DESC LIMIT 5");
	      if (queryResults.getSize() > 0) {
	          for (SObject s: queryResults.getRecords()) {
	            //System.out.println("Id: " + s.getId() + " " + s.getField("FirstName") + " " + 
	            //    s.getField("LastName") + " - " + s.getChild("Account").getField("Name"));
	          }
	        }
	       
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
  }*/
}