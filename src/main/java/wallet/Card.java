package main.java.wallet;

import org.hibernate.validator.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Card{

	@Id
int card_id;

@NotEmpty
@Length(min=1,message="card name invalid")
String card_name;

@Length(min=1,message="card number invalid")
String card_number;

String expiration_date;
public Card(){}
public Card(int card_id,String card_name,String card_number, String expiration_date){
super();
this.card_id = card_id;
this.card_name = card_name;
this.card_number = card_number;
this.expiration_date = expiration_date;
}
public int getCard_id(){return card_id;}
public String getCard_name(){return card_name;}
public String getCard_number(){return card_number;}
public String getExpiration_date(){return expiration_date;}
public void setCard_id(int card_id){this.card_id = card_id;}
public void setCard_name(String card_name){this.card_name = card_name;}
public void setCard_number(String card_number){this.card_number = card_number;}
public void setExpiration_date(String expiration_date){this.expiration_date = expiration_date;}
@Override
public String toString() {
return new StringBuffer(" id : ").append(this.card_id)
.append(" name : ").append(this.card_name)
.append(" number : ").append(this.card_number).append(" date : ")
.append(this.expiration_date).toString();
}
}




