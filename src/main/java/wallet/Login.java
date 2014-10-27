package main.java.wallet;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Login{

	@Id
int login_id;

@NotEmpty
String url;

@NotEmpty
String login;

@NotEmpty
String password;

public Login(){}
public Login(int login_id, String url, String login,String password){
super();
this.login_id = login_id;
this.url = url;
this.login = login;
this.password = password;
}
public int getLogin_id(){return login_id;}
public String getUrl(){return url;}
public String getLogin(){return login;}
public String getPassword(){return password;}
public void setLogin_id(int login_id){this.login_id = login_id;}
public void setUrl(String url){this.url = url;}
public void setLogin(String login){this.login = login;}
public void setPassword(String password){this.password = password;}
@Override
public String toString() {
return new StringBuffer(" id : ").append(this.login_id)
.append(" url : ").append(this.url)
.append(" login : ").append(this.login)
.append(" password : ").append(this.password).toString();
}
}
