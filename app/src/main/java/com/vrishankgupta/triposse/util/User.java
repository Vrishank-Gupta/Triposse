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
	private String imei;
	public User(String country, String password, String address, Dob dob, String passportNumber, String email, String contactNumber, String username, String imei)
	{
		this.country = country;
		this.password = password;
		this.address = address;
		this.dob = dob;
		this.passport_number = passportNumber;
		this.email = email;
		this.contact_number = contactNumber;
		this.username = username;
		this.imei = imei;

	}

    @Override
    public String toString() {
        return "User{" +
                "country='" + country + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", dob=" + dob +
                ", passport_number='" + passport_number + '\'' +
                ", email='" + email + '\'' +
                ", contact_number='" + contact_number + '\'' +
                ", username='" + username + '\'' +
                ", imei='" + imei + '\'' +
                '}';
    }



    public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Dob getDob() {
		return dob;
	}

	public void setDob(Dob dob) {
		this.dob = dob;
	}

	public String getPassport_number() {
		return passport_number;
	}

	public void setPassport_number(String passport_number) {
		this.passport_number = passport_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
}
