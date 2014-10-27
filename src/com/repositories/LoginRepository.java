package com.repositories;

import java. util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;


import main.java.wallet.Login;

public class LoginRepository implements Repository<Login>{
	MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}


public List<Login> getAllObjects(){
	return mongoTemplate.findAll(Login.class);
}

public void saveObject(Login login){
	mongoTemplate.insert(login);
}

public Login getObject(String id){
	return mongoTemplate.findOne(new Query(Criteria.where("login_id").is(id)), Login.class);
}

public WriteResult updateLoginUrl(String id, String url){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("login_id").is(id)),
			Update.update("url", url), Login.class);
}

public WriteResult updateLogin(String id, String login){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("login_id").is(id)),
			Update.update("login", login), Login.class);
}

public WriteResult updateLoginPassword(String id, String password){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("login_id").is(id)),
			Update.update("password", password), Login.class);
}

public void deleteObject(String id){
	mongoTemplate.remove(new Query(Criteria.where("login_id").is(id)), Login.class);
}

public void createCollection(){
	if(!mongoTemplate.collectionExists(Login.class)){
		mongoTemplate.createCollection(Login.class);
	}
}

public void dropCollection(){
	if(mongoTemplate.collectionExists(Login.class)){
		mongoTemplate.dropCollection(Login.class);
	}
}
}