package main.java.wallet;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.text.SimpleDateFormat;
import java.text.Format;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import main.java.wallet.User;
import main.java.wallet.Card;
import main.java.wallet.Login;
import main.java.wallet.Bank;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
   Map<Integer, User> userData = new HashMap<Integer, User>();

    
    int userId = 0;
    int cardId = 0;
    int webId = 0;
    int baId = 0;
  
    @RequestMapping(value = "/v1/users", method = RequestMethod.POST)
    public User CreateUser(@RequestBody User user) {
    	    	logger.info("Start create user.");
		userId++;
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		
		//String formattedDate = dateFormat.format(date);
		Format f = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String created_at = f.format(date);
		String updated_at = f.format(date);
		User new_user = new User(userId, user.getEmail(), user.getPassword(), created_at, updated_at);
		
		DB db = Application.db;
		DBCollection table = db.getCollection("users");

		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("id", userId);
		document.put("email", new_user.getEmail());
		document.put("password", new_user.getPassword());
		document.put("created_at", new_user.getCreated_at());
		document.put("updated_at", new_user.getUpdated_at());
		table.insert(document);
		
		return new_user;
    }

    private User userfromdb(int userId) {
    	DB db = Application.db;
		DBCollection table = db.getCollection("users");

    	/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		
		searchQuery.put("id", userId);
        
		DBCursor cursor = table.find(searchQuery);
		JSONObject json;
		User new_user = null;
		try {
		 json = new JSONObject(cursor.next().toString());
		 
		 new_user = new User(json.getInt("id"),
				 			json.getString("email"),
				 			json.getString("password"),
				 			json.getString("created_at"),
				 			json.getString("updated_at"));
		}
		catch (JSONException e)
			{
			e.printStackTrace();
		}
    	return new_user;
    }
    
    @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.GET)
    public User ViewUser(@PathVariable("id") int userId) {
    	logger.info("Start view.");
    	
		return userfromdb(userId);
    }
    
    @RequestMapping(value = "/v1/users/{id}", method = RequestMethod.PUT)
    public User UpdateUser(@PathVariable("id") int userId, @RequestBody User user) {
    	logger.info("Start update.");

    	DB db = Application.db;
		DBCollection table = db.getCollection("users");

    	BasicDBObject query = new BasicDBObject();
		query.put("id", userId);

		BasicDBObject newDocument = new BasicDBObject();
		newDocument.put("email", user.getEmail());
		newDocument.put("password", user.getPassword());
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);
		newDocument.put("updated_at", formattedDate);

		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newDocument);

		table.update(query, updateObj);

    	return userfromdb(userId);
    }

    @RequestMapping(value = "/v1/users/{id}/idcards", method = RequestMethod.POST)
    public Card CreateID(@PathVariable("id") int userId, @RequestBody Card idcard) {
    	logger.info("Start create card.");
		cardId++;
		Card new_id = new Card(cardId, idcard.getCard_name(), idcard.getCard_number(), idcard.getExpiration_date());
		
		DB db = Application.db;
		DBCollection table = db.getCollection("idcards");

		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("id", userId);
		document.put("card_id", cardId);
		document.put("card_name",idcard.getCard_name() );
		document.put("card_number", idcard.getCard_number());
		document.put("created_at", idcard.getExpiration_date());
		document.put("updated_at", idcard.getExpiration_date());
		table.insert(document);
		
		
		return new_id;
    }
    @RequestMapping(value = "/v1/users/{id}/idcards", method = RequestMethod.GET)
    public String ListID(@PathVariable("id") int userId) {
      	logger.info("Start get card.");
    	DB db = Application.db;
		DBCollection table = db.getCollection("idcards");

    	/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		
		searchQuery.put("id", userId);
        		DBCursor cursor = table.find(searchQuery);
		JSONObject json;
		Card new_card = null;
		String cardString = null;

		while (cursor.hasNext()) {
		try {
		 json = new JSONObject(cursor.next().toString());
		 new_card = new Card(json.getInt("card_id"),
				 				json.getString("card_name"),
				 				json.getString("card_number"),
				 				json.getString("created_at"));
		}
		catch (JSONException e)
			{
			e.printStackTrace();
		}
		cardString += new_card.toString();
		}
    	return cardString;
    }
  	
    @RequestMapping(value = "/v1/users/{id}/idcards/{cid}", method = RequestMethod.DELETE)
    public String deleteID(@PathVariable("id") int userId, @PathVariable("cid") int cardId) {
      	logger.info("Start delete card.");

      	
    	DB db = Application.db;
		DBCollection table = db.getCollection("idcards");

    	/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		
		searchQuery.put("id", userId);
		searchQuery.put("card_id", cardId);
        
		DBCursor cursor = table.find(searchQuery);
		JSONObject json;
		Card new_card = null;
		String cardString = null;

		while (cursor.hasNext()) {
			try {
				json = new JSONObject(cursor.next().toString());
				new_card = new Card(json.getInt("card_id"),
						json.getString("card_name"),
						json.getString("card_number"),
						json.getString("created_at"));
				}	
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			cardString += new_card.toString();
			table.findAndRemove(cursor.getQuery());
		}
      	      	return cardString;
    }
    @RequestMapping(value = "/v1/users/{id}/weblogins", method = RequestMethod.POST)
    public Login CreateWeblogin(@PathVariable("id") int userId, @RequestBody Login weblogin) {
    	logger.info("Start create web login.");
		webId++;
		Login new_weblogin = new Login(webId, weblogin.getUrl(), weblogin.getLogin(), weblogin.getPassword());
	
		
		DB db = Application.db;
		DBCollection table = db.getCollection("weblogins");

		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("id", userId);
		document.put("login_id", webId);
		document.put("url",weblogin.getUrl());
		document.put("login", weblogin.getLogin());
		document.put("password", weblogin.getPassword());
		table.insert(document);
		
		
		return new_weblogin;
    }
    @RequestMapping(value = "/v1/users/{id}/weblogins", method = RequestMethod.GET)
    public String ListWeblogin(@PathVariable("id") int userId) {
      	logger.info("Start get web login.");
      	DB db = Application.db;
		DBCollection table = db.getCollection("weblogins");

    	/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		
		searchQuery.put("id", userId);
        
		DBCursor cursor = table.find(searchQuery);
		JSONObject json;
		Login new_weblogin = null;
		String webloginString = null;

		while (cursor.hasNext()) {
		try {
		 json = new JSONObject(cursor.next().toString());
		 new_weblogin = new Login(json.getInt("login_id"),
				 				json.getString("url"),
				 				json.getString("login"),
				 				json.getString("password"));
		}
		catch (JSONException e)
			{
			e.printStackTrace();
		}
		webloginString += new_weblogin.toString();
		}
    	return webloginString;
      	      	
    	    }
    @RequestMapping(value = "/v1/users/{id}/weblogins/{login_id}", method = RequestMethod.DELETE)
    public String deleteWeblogin(@PathVariable("id") int userId, @PathVariable("login_id") int loginID) {
      	logger.info("Start delete web login.");
     
 
        
        	DB db = Application.db;
    		DBCollection table = db.getCollection("weblogins");

        	/**** Find and display ****/
    		BasicDBObject searchQuery = new BasicDBObject();
    		
    		searchQuery.put("id", userId);
    		searchQuery.put("login_id", loginID);
        
    		DBCursor cursor = table.find(searchQuery);
    		JSONObject json;
    		Login new_weblogin = null;
    		String webloginString = null;

    		while (cursor.hasNext()) {
    			try {
    				json = new JSONObject(cursor.next().toString());
    				new_weblogin = new Login(json.getInt("login_id"),
    						json.getString("url"),
    						json.getString("login"),
    						json.getString("password"));
    				}	
    				catch (JSONException e)
    				{
    					e.printStackTrace();
    				}
    			webloginString += new_weblogin.toString();
    			table.findAndRemove(cursor.getQuery());
    		}
        
          	return webloginString;

 }
    
    @RequestMapping(value = "/v1/users/{id}/bankaccounts", method = RequestMethod.POST)
    public Bank CreateBankAccount(@PathVariable("id") int userId, @RequestBody Bank bankacc) {
    	logger.info("Start create Bank Account.");
		baId++;
		Bank new_bankaccount = new Bank(baId, bankacc.getAccount_name(), bankacc.getRouting_number(), bankacc.getAccount_number());

		
		DB db = Application.db;
		DBCollection table = db.getCollection("bankaccount");

		int responseCode = 404;
		String acc_name =  bankacc.getAccount_name();
		String url = "https://www.routingnumbers.info/api/data.json?rn="+bankacc.getRouting_number();
		logger.info(url);
		try {
			URL obj = new URL(url);
			HttpURLConnection con = null;
			con  = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JSONObject json = new JSONObject(response.toString());
		
			logger.info(response.toString());
			responseCode = json.getInt("code");
			if (responseCode == 200) {
				acc_name = json.getString("customer_name");
			} else {
			}
		}
		catch (Exception e)
		{
			
		}
		
		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("id", userId);
		document.put("bank_id", baId);
		document.put("account_name", acc_name);
		document.put("routing_number", bankacc.getRouting_number());
		document.put("account_number",  bankacc.getAccount_number());
		table.insert(document);

		
		return new_bankaccount;
    }
    
    @RequestMapping(value = "/v1/users/{id}/bankaccounts", method = RequestMethod.GET)
    public String Listbankaccount(@PathVariable("id") int userId) {
      	logger.info("Start get bank account.");
      	DB db = Application.db;
		DBCollection table = db.getCollection("bankaccount");

    	/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		
		searchQuery.put("id", userId);
        //MongoTemplate template;
		DBCursor cursor = table.find(searchQuery);
		JSONObject json;
		Bank new_bankaccount = null;
		String bankaccountString = null;

		while (cursor.hasNext()) {
		try {
		 json = new JSONObject(cursor.next().toString());
		 new_bankaccount = new Bank(json.getInt("bank_id"),
				 				json.getString("account_name"),
				 				json.getString("routing_number"),
				 				json.getString("account_number"));
	      

		}
		catch (JSONException e)
			{
			e.printStackTrace();
		}
		bankaccountString += new_bankaccount.toString();
		}
    	return bankaccountString;
      	      	
    	
    }
    
    @RequestMapping(value = "/v1/users/{id}/bankaccounts/{bankacc_id}", method = RequestMethod.DELETE)
    public String deletebankaccount(@PathVariable("id") int userId, @PathVariable("bankacc_id") int bankacID) {
      	logger.info("Start delete bank account.");
           	DB db = Application.db;
     		DBCollection table = db.getCollection("bankaccounts");

         	/**** Find and display ****/
     		BasicDBObject searchQuery = new BasicDBObject();
     		
     		searchQuery.put("id", userId);
     		searchQuery.put("bank_id", bankacID);
             //MongoTemplate template;
     		DBCursor cursor = table.find(searchQuery);
     		JSONObject json;
     		Bank new_bankaccount = null;
     		String bankaccountString = null;

     		while (cursor.hasNext()) {
     			try {
     				json = new JSONObject(cursor.next().toString());
     				new_bankaccount = new Bank(json.getInt("bank_id"),
     						json.getString("account_name"),
     						json.getString("routing_number"),
     						json.getString("account_number"));
     				}	
     				catch (JSONException e)
     				{
     					e.printStackTrace();
     				}
     			bankaccountString += new_bankaccount.toString();
     			table.findAndRemove(cursor.getQuery());
     		}
        
       return bankaccountString;

     
    }
}
