package com.example.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.cinema.dao.ScheduleDao;
import com.cinema.dao.CustomerDao;
import com.cinema.dao.TicketDao;
import com.cinema.dao.AbstractDao;
import com.cinema.dao.SeatDaoImpl;
import com.cinema.model.Customer;
import com.cinema.model.Movie;
import com.cinema.model.Schedule;
import com.cinema.model.Seat;
import com.cinema.model.Ticket;

public class TicketInfoPage {

	private AbstractDao<Customer> customerDao;
	private SeatDaoImpl seatDao;
	private AbstractDao<Ticket> ticketDao;
	private AbstractDao<Schedule> scheduleDao;
	private JFrame frame;
	private JLabel cinemaLabel;
	private JLabel cinemaValueLabel;
	private JLabel movieLabel;
	private JLabel movieValueLabel;
	private JLabel theatreLabel;
	private JLabel theatreValueLabel;
	private JLabel priceLabel;
	private JLabel priceValueLabel;
	private JLabel publicDateLabel;
	private JLabel publicDateValueLabel;
	private JLabel startTimeLabel;
	private JLabel startTimeValueLabel;
	private JLabel seatLabel;
	private JLabel seatValueLabel;
	private JLabel customerIdLabel;
	private JTextField customerIdTextFile;
	private JButton saveBtn;
	private JButton cancelBtn;
	private JPanel detailsPanel;
	private Schedule schedule;
	private Seat seat;

	public TicketInfoPage(int seatId, int scheduleId) {
		
		this.ticketDao = new TicketDao();
		this.customerDao = new CustomerDao();
		this.seatDao = new SeatDaoImpl();
		this.scheduleDao = new ScheduleDao();
		this.seat = this.seatDao.findbyId(seatId);
		this.schedule = this.scheduleDao.findbyId(scheduleId);
		initializeComponent();
		addToPanel();
	
		this.frame.setLocation(300, 300);
		this.frame.setVisible(true);
	}

	private void initializeComponent() {

		this.frame = new JFrame("Ticket Information");
		this.frame.setSize(400, 300);
		this.frame.setLayout(new BorderLayout());
		this.frame.setLocation(300, 300);
		this.frame.setVisible(true);

		this.detailsPanel = new JPanel();
		this.detailsPanel.setLayout(new GridLayout(9, 2));

		this.cinemaLabel = new JLabel("Cinema Name: ");
		this.cinemaValueLabel = new JLabel(this.schedule.getThreatre().getCinema().getName());

		this.theatreLabel = new JLabel("Theatre Name:");
		this.theatreValueLabel = new JLabel(this.schedule.getThreatre().getName());

		this.movieLabel = new JLabel("Movie Title:");
		Movie movie = this.schedule.getMovie();
		this.movieValueLabel = new JLabel(movie.getTitle() + "->" + movie.getDuration());

		this.seatLabel = new JLabel("Seat Name:");
		this.seatValueLabel = new JLabel(this.seat.getTitle() + "");

		this.priceLabel = new JLabel("Price:");
		this.priceValueLabel = new JLabel(priceCalculator() + "");

		this.publicDateLabel = new JLabel("Public Date: ");
		this.publicDateValueLabel = new JLabel(this.schedule.getPublicDate().toString());

		this.startTimeLabel = new JLabel("StartTime:");
		this.startTimeValueLabel = new JLabel(this.schedule.getStartTime().toString());

		this.customerIdLabel = new JLabel("Customer ID:");
		this.customerIdTextFile = new JTextField(15);

		this.saveBtn = new JButton("Save");
		this.cancelBtn = new JButton("Cancel");

	}

	private void addToPanel() {

		this.detailsPanel.add(this.cinemaLabel);
		this.detailsPanel.add(this.cinemaValueLabel);

		this.detailsPanel.add(this.movieLabel);
		this.detailsPanel.add(this.movieValueLabel);

		this.detailsPanel.add(this.theatreLabel);
		this.detailsPanel.add(this.theatreValueLabel);

		this.detailsPanel.add(this.seatLabel);
		this.detailsPanel.add(this.seatValueLabel);

		this.detailsPanel.add(this.priceLabel);
		this.detailsPanel.add(this.priceValueLabel);

		this.detailsPanel.add(this.publicDateLabel);
		this.detailsPanel.add(this.publicDateValueLabel);

		this.detailsPanel.add(this.startTimeLabel);
		this.detailsPanel.add(this.startTimeValueLabel);

		this.detailsPanel.add(this.customerIdLabel);
		this.detailsPanel.add(this.customerIdTextFile);

		this.detailsPanel.add(cancelBtn);
		this.detailsPanel.add(saveBtn);
		saveBtnAction();
		
		this.frame.add(this.detailsPanel, BorderLayout.NORTH);

	}

	private double priceCalculator() {

		if (this.seat.isNormal()) {
			return 300;
		} else if (this.seat.isVIP()) {
			return 500;
		} else {
			return 200;
		}
		
	}
		
	private void saveBtnAction() {
		this.saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int customerId = Integer.parseInt(customerIdTextFile.getText());
				Customer customer = customerDao.findbyId(customerId);
				if (customer != null) {
					Ticket ticket = new Ticket();
					ticket.setCustomer(customer);
					ticket.setSeat(seat);
					ticket.setSchedule(schedule);
					ticket.setPrice(priceCalculator());
				
					ticketDao.create(ticket);
					JOptionPane.showMessageDialog(frame, "Successfully Created!!");
					
				}else {
					JOptionPane.showMessageDialog(frame, "Customer id " + customerId + " not found.");
				}
				
			}
			
		});
	}

}
