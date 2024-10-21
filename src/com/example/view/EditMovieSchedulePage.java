package com.example.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;


import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;

import javax.swing.*;

import org.jdatepicker.JDatePicker;
import com.cinema.dao.MovieDao;
import com.cinema.dao.ScheduleDao;
import com.cinema.dao.TheatreDaoImp;
import com.cinema.dao.CinemaDao;
import com.cinema.dao.AbstractDao;
import com.cinema.model.Cinema;
import com.cinema.model.Movie;
import com.cinema.model.Schedule;
import com.cinema.model.Theatre;

public class EditMovieSchedulePage extends JFrame implements ActionListener {

	private AbstractDao<Movie> movieDao;
	private AbstractDao<Cinema> cinemaDao;
	private JLabel movieLabel, cinemaLabel, theatreLabel, startTimeLabel, endTimeLabel, publicDateLabel;
	private JLabel movieLink, cinemaLink, theatreLink;
	private JTextField startTimeField, endTimeField, publicDateField;
	private JButton updateBtn, resetBtn;
	private Movie movie;
	private Cinema cinema;
	private Theatre theatre;
	private AbstractDao<Theatre> theatreDaoImp;
	private AbstractDao<Schedule> scheduleDao;
	private JFrame parentPage;
	private Schedule schedule;

	public EditMovieSchedulePage(JFrame parentPage, int scheduleId) {

		this.parentPage = parentPage;
		this.scheduleDao = new ScheduleDao();
		this.schedule = scheduleDao.findbyId(scheduleId);
		this.theatreDaoImp = new TheatreDaoImp();
		this.cinemaDao = new CinemaDao();
		this.movieDao = new MovieDao();
		initializeComponents();
		addUIComponents();
		this.setVisible(true);
	}

	private void addUIComponents() {

		this.add(this.movieLabel);
		this.add(this.movieLink);

		this.add(this.cinemaLabel);
		this.add(this.cinemaLink);

		this.add(this.theatreLabel);
		this.add(this.theatreLink);

		this.add(this.startTimeLabel);
		this.add(this.startTimeField);

		this.add(this.endTimeLabel);
		this.add(this.endTimeField);

		this.add(this.publicDateLabel);
		this.add(this.publicDateField);

		this.add(this.updateBtn);
		this.add(this.resetBtn);

	}

	private void initializeComponents() {

		this.setTitle("Edit Movie Schedule");
		this.setSize(500, 400);
		this.setLocation(100, 100);
		this.setLayout(new GridLayout(7, 2, 10, 30));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		prepareMovieLabel();
		prepareCinemaLabel();
		prepareTheatreLabel();

		this.startTimeLabel = new JLabel("Start Time");
		this.endTimeLabel = new JLabel("End Time");
		this.publicDateLabel = new JLabel("Public Date");

		this.startTimeField = new JTextField(schedule.getStartTime());
		this.endTimeField = new JTextField(schedule.getEndTime());
		this.publicDateField = new JTextField(schedule.getPublicDate());

		this.updateBtn = new JButton("Update");
		this.resetBtn = new JButton("Reset");

		this.updateBtn.addActionListener(this);
		this.resetBtn.addActionListener(this);

	}

	private void prepareTheatreLabel() {
		this.theatreLabel = new JLabel("Theatre");
		this.theatreLink = new JLabel("<html><a href = ''>" + this.schedule.getThreatre() +"</html>");
		this.theatreLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.theatreLink.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked select theatre");
				openTheatreListingPage();
			}

		});

	}

	public void openTheatreListingPage() {
		if (this.cinema == null) {
			JOptionPane.showMessageDialog(this, "Please select a cinema first.");
		} else {
			new TheatreListingPage(this, this.cinema.getId(), "eidtPage");
		}

	}

	private void prepareCinemaLabel() {
		this.cinemaLabel = new JLabel("Cinema");
		this.cinemaLink = new JLabel("<html><a href = ''>" + this.schedule.getThreatre().getCinema() +"</html>");
		this.cinemaLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.cinemaLink.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				openCinemaListingPage();
			}

		});

	}

	public void openCinemaListingPage() {
		new CinemaListingPage(this, "editPage");
	}

	private void prepareMovieLabel() {
		this.movieLabel = new JLabel("Movie");
		this.movieLink = new JLabel("<html><a href = ''>" + this.schedule.getMovie() +"</html>");
		this.movieLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.movieLink.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked select movie");
				openMovieListingPage();
			}

		});

	}

	public void openMovieListingPage() {
		new MovieListingPage(this, "editPage");

	}

	public String getSelectedMovieLabel() {
		if (this.movie == null) {
			return "<html><a href = ''>Select Movie:</html>";
		} else {
			return "<html><a href = ''>" + this.movie.toString() + ":</html>";
		}
	}

	public void refreshSelectedMovie(int movieId) {
		this.movie = this.movieDao.findbyId(movieId);
		this.movieLink.setText(this.getSelectedMovieLabel());

	}

	public void refreshSelectedCinema(int cinemaId) {
		this.cinema = this.cinemaDao.findbyId(cinemaId);
		this.cinemaLink.setText(this.getSelectedCinemaLabel());
	}

	public void refreshSelectedTheatre(int theatreId) {
		this.theatre = this.theatreDaoImp.findbyId(theatreId);
		this.theatreLink.setText(this.getSelectedTheatreLabel());

	}
	
	private String getSelectedCinemaLabel() {
		if (this.cinema == null) {
			return "<html><a href = ''>Select Cinema:</html>";
		} else {
			return "<html><a href = ''>" + this.cinema + ":</html>";
		}
	}



	private String getSelectedTheatreLabel() {
		if (this.theatre == null) {
			return "<html><a href = ''>Select Theatre:</html>";
		} else {
			return "<html><a href = ''>" + this.theatre + ":</html>";
		}
	}

	private void addUpdateBtnAction() {

		this.updateBtn.addActionListener(e -> updateBtnAction());

	}

	private void updateBtnAction() {

//		(Movie movie, Theater threader, Time startTime, Time endTime, Date publicDate)

		String startTime = startTimeField.getText();
		String endTime = endTimeField.getText();
		String publicDate = publicDateField.getText();

		Schedule schedule = new Schedule(this.movie, this.theatre, startTime, endTime, publicDate);

		scheduleDao.update(schedule);
		System.out.println(schedule);
		JOptionPane.showMessageDialog(this, "Successfully Created!!");

		BookingPage page = (BookingPage) this.parentPage;
		page.refreshBookingPage();
		this.dispose();

	}

	private void addResetBtnAction() {
		this.resetBtn.addActionListener(e -> resetBtnAction());
	}

	private void resetBtnAction() {

		movieLink.setText("<html><a href = ''>Select Movie:</html>");
		cinemaLink.setText("<html><a href = ''>Select Cinema:</html>");
		theatreLink.setText("<html><a href = ''>Select Theatre:</html>");

		startTimeField.setText("");
		endTimeField.setText("");
		publicDateField.setText("");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.updateBtn) {
			this.updateBtnAction();

		}

		if (e.getSource() == this.resetBtn) {
			this.resetBtnAction();
		}
	}

	private boolean checkNull() {
		return movieLink.getText().isEmpty() || cinemaLink.getText().isEmpty() || theatreLink.getText().isEmpty()
				|| startTimeLabel.getText().isEmpty() || endTimeLabel.getText().isEmpty()
				|| publicDateLabel.getText().isEmpty();
	}

}
