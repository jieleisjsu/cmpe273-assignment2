package com.repositories;

import java. util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import main.java.wallet.Bank;


public class BankRepository implements Repository<Bank>{
	MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}


public List<Bank> getAllObjects(){
	return mongoTemplate.findAll(Bank.class);
}

public void saveObject(Bank bank){
	mongoTemplate.insert(bank);
}

public Bank getObject(String id){
	return mongoTemplate.findOne(new Query(Criteria.where("bank_id").is(id)), Bank.class);
}

public WriteResult updateBankAccountName(String id, String account_name){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("bank_id").is(id)),
			Update.update("account_name", account_name), Bank.class);
}

public WriteResult updateBankRoutingNumber(String id, String routing_number){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("bank_id").is(id)),
			Update.update("routing_number", routing_number), Bank.class);
}

public WriteResult updateBankAccountNumber(String id, String account_number){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("bank_id").is(id)),
			Update.update("account_number", account_number), Bank.class);
}

public void deleteObject(String id){
	mongoTemplate.remove(new Query(Criteria.where("bank_id").is(id)), Bank.class);
}

public void createCollection(){
	if(!mongoTemplate.collectionExists(Bank.class)){
		mongoTemplate.createCollection(Bank.class);
	}
}

public void dropCollection(){
	if(mongoTemplate.collectionExists(Bank.class)){
		mongoTemplate.dropCollection(Bank.class);
	}
}
}