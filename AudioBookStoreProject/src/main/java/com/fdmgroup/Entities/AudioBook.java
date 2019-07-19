package com.fdmgroup.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AudioBook {
	
	@Id
	private String title;
	private String author;
	private String narrator;
	private double price;
	
	public AudioBook(String title, String author, String narrator, double price) {
		super();
		this.title = title;
		this.author = author;
		this.narrator = narrator;
		this.price = price;
	}



	public AudioBook(){
		
	}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNarrator() {
		return narrator;
	}

	public void setNarrator(String narrator) {
		this.narrator = narrator;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	



	@Override
	public String toString() {
		return "AudioBook [title=" + title + ", author=" + author + ", narrator=" + narrator
				+ ", price=" + price + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		AudioBook other = (AudioBook) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}



	

}
