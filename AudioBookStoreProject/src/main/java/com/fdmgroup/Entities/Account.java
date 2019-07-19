package com.fdmgroup.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	private String email;
	private String password;
	private String firstLineAddress;
	private String postcode;
	private String cardName;
	private long cardNumber;
	private String securityCode;
	private String expiryDate;


	// constructor
	public Account(String email, String password, String firstLineAddress, String postcode, String cardName,
			long cardNumber, String securityCode, String expiryDate) {
		super();
		this.email = email;
		this.password = password;
		this.firstLineAddress = firstLineAddress;
		this.postcode = postcode;
		this.cardName = cardName;
		this.cardNumber = cardNumber;
		this.securityCode = securityCode;
		this.expiryDate = expiryDate;
	}
	
	public Account(){
		
	}
	
	// getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstLineAddress() {
		return firstLineAddress;
	}

	public void setFirstLineAddress(String firstLineAddress) {
		this.firstLineAddress = firstLineAddress;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}



	@Override
	public String toString() {
		return "Account [email=" + email + ", password=" + password + ", confirmPassword=" + ", firstLineAddress=" + firstLineAddress + ", postcode=" + postcode + ", cardName=" + cardName
				+ ", cardNumber=" + cardNumber + ", securityCode=" + securityCode + ", expiryDate=" + expiryDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
