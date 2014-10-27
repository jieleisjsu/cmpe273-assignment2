package com.repositories;

import java. util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;


import main.java.wallet.User;

public class UserRepository implements Repository<User>{
	MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}


public List<User> getAllObjects(){
	return mongoTemplate.findAll(User.class);
}

public void saveObject(User user){
	mongoTemplate.insert(user);
}

public User getObject(String id){
	return mongoTemplate.findOne(new Query(Criteria.where("user_id").is(id)), User.class);
}

public WriteResult updateUserEmail(String id, String email){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("user_id").is(id)),
			Update.update("email", email), User.class);
}

public WriteResult updateUserName(String id, String name){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("user_id").is(id)),
			Update.update("name", name), User.class);
}

public WriteResult updateUserPassword(String id, String password){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("user_id").is(id)),
			Update.update("password", password), User.class);
}

public void deleteObject(String id){
	mongoTemplate.remove(new Query(Criteria.where("user_id").is(id)), User.class);
}

public void createCollection(){
	if(!mongoTemplate.collectionExists(User.class)){
		mongoTemplate.createCollection(User.class);
	}
}

public void dropCollection(){
	if(mongoTemplate.collectionExists(User.class)){
		mongoTemplate.dropCollection(User.class);
	}
}
}