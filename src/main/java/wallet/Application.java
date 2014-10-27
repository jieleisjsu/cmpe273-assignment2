package main.java.wallet;

import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

@Configuration
@ComponentScan
@EnableAutoConfiguration


public class Application {
	
	static public DB db;
	
public static void main(String[] args) {
	try{
		String textUri = "mongodb://root:root@ds047950.mongolab.com:47950/digitalwalletdb";
		MongoClientURI uri = new MongoClientURI(textUri);
		MongoClient mongo = new MongoClient(uri);
		db = mongo.getDB("digitalwalletdb");
	} catch (UnknownHostException e){
		e.printStackTrace();
	} catch (MongoException e){
		e.printStackTrace();
	}
	
SpringApplication.run(Application.class, args);
}}

