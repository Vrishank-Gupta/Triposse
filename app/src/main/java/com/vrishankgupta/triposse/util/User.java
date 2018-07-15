package com.vrishankgupta.triposse.util;

public class User{
	private String country;
	private String password;
	private String address;
	private Dob dob;
	private String passport_number;
	private String email;
	private String contact_number;
	private String username;

	
	public User(String country, String password, String address, Dob dob, String passportNumber, String email, String contactNumber, String username)
	{
		this.country = country;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.passport_number = passportNumber;
		this.email = email;
		this.contact_number = contactNumber;
		this.username = username;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public String getCountry(){
		return country;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDob(Dob dob){
		this.dob = dob;
	}

	public Dob getDob(){
		return dob;
	}

	public void setPassportNumber(String passportNumber){
		this.passport_number = passportNumber;
	}

	public String getPassportNumber(){
		return passport_number;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setContactNumber(String contactNumber){
		this.contact_number = contactNumber;
	}

	public String getContactNumber(){
		return contact_number;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"country = '" + country + '\'' + 
			",password = '" + password + '\'' + 
			",address = '" + address + '\'' + 
			",dob = '" + dob + '\'' + 
			",passport_number = '" + passport_number + '\'' + 
			",email = '" + email + '\'' + 
			",contact_number = '" + contact_number + '\'' +
			",username = '" + username + '\'' + 
			"}";
		}
}
