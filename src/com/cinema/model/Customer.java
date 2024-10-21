package com.cinema.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int id;
	private String name;
	
	private String email;
	private String address;
	private List<Ticket> tickets = new ArrayList<Ticket>();
	

	public Customer() {
		
	}
	
	public Customer(int id, String name, String email, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;

	}
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	public String getAddress() {
		return address;
	}
	
	public List<Ticket> getTickets() {
		return tickets;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	public String[] toArray() {
		String[] customerData = new String[8];
		customerData[0] = this.id+"";
		customerData[1] = this.name;
		customerData[2] = this.email;
		customerData[3] = this.address;
	
		return customerData;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + "]";
	}

}
