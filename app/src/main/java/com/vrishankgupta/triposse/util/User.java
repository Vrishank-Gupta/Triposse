package com.vrishankgupta.triposse.util;

public class User{
	private String country;
	private String password;
	private String address;
	private Dob dob;
	private String passportNumber;
	private String email;
	private String contactNumber;
	private String username;

	public User(String country, String password, String address, Dob dob, String passportNumber, String email, String contactNumber, String username)
    {
		this.country = country;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.passportNumber = passportNumber;
		this.email = email;
		this.contactNumber = contactNumber;
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
		this.passportNumber = passportNumber;
	}

	public String getPassportNumber(){
		return passportNumber;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}

	public String getContactNumber(){
		return contactNumber;
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
			",passport_number = '" + passportNumber + '\'' + 
			",email = '" + email + '\'' + 
			",contact_number = '" + contactNumber + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}
