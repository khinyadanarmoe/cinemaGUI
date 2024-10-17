package com.example.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
	
	private JFrame homeFrame;
	private JMenuBar menuBar;
	private JMenu bookingsItem;
	private JMenu moviesItem;
	private JMenu cinemasItem;
	private JMenuItem ticketBookingItem;
	private JMenuItem cancleBookingItem;
	private JMenuItem customersItem;
	
	

	public HomePage() {
		initializeComponents();
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setLocation(0, 0);
		homeFrame.setSize(700, 500);
		homeFrame.setVisible(true);
		
	}
	
	
	private void initializeComponents() {
		this.homeFrame = new JFrame();
		this.menuBar = new JMenuBar();
		this.bookingsItem = new JMenu("Bookings");
		this.ticketBookingItem = new JMenuItem("Ticket Booking");
		this.cancleBookingItem = new JMenuItem("Cancle Booking");
		this.moviesItem = new JMenu("Movies");
		this.cinemasItem = new JMenu("Cineams");
		this.customersItem = new JMenuItem("Customers");
		this.bookingsItem.add(ticketBookingItem);
		this.bookingsItem.add(cancleBookingItem);
		this.menuBar.add(bookingsItem);
		this.ticketBookingAction();
		this.menuBar.add(moviesItem);
		this.menuBar.add(cinemasItem);
		this.menuBar.add(customersItem);
		this.customersAction();
		
		
		this.homeFrame.setJMenuBar(this.menuBar);
	}
	
	
	private void customersAction() {
		this.customersItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerOverviewPage customerPage = new CustomerOverviewPage();
				
			}
			
		});
		
	}


	private void ticketBookingAction() {
		this.ticketBookingItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				BookingPage bookingPage = new BookingPage();
			}
			
		});
	}
	

	

}
