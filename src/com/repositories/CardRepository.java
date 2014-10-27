package com.repositories;

import java. util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;
import main.java.wallet.Card;

public class CardRepository implements Repository<Card>{
	MongoTemplate mongoTemplate;
	
	public void setMongoTemplate(MongoTemplate mongoTemplate){
		this.mongoTemplate = mongoTemplate;
	}


public List<Card> getAllObjects(){
	return mongoTemplate.findAll(Card.class);
}

public void saveObject(Card card){
	mongoTemplate.insert(card);
}

public Card getObject(String id){
	return mongoTemplate.findOne(new Query(Criteria.where("card_id").is(id)), Card.class);
}

public WriteResult updateCardName(String id, String name){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("card_id").is(id)),
			Update.update("card_name", name), Card.class);
}

public WriteResult updateCardNumber(String id, String number){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("card_id").is(id)),
			Update.update("card_number", number), Card.class);
}

public WriteResult updateCardExpirationDate(String id, String expiration_date){
	return mongoTemplate.updateFirst(
			new Query(Criteria.where("card_id").is(id)),
			Update.update("expiration_date", expiration_date), Card.class);
}

public void deleteObject(String id){
	mongoTemplate.remove(new Query(Criteria.where("card_id").is(id)), Card.class);
}

public void createCollection(){
	if(!mongoTemplate.collectionExists(Card.class)){
		mongoTemplate.createCollection(Card.class);
	}
}

public void dropCollection(){
	if(mongoTemplate.collectionExists(Card.class)){
		mongoTemplate.dropCollection(Card.class);
	}
}
}