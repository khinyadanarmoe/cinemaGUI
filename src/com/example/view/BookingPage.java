package com.example.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.cinema.dao.AbstractDao;
import com.cinema.model.Customer;
import com.cinema.model.Schedule;
import com.cinema.dao.ScheduleDao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BookingPage extends JFrame {

	private AbstractDao<Schedule> scheduleDao;
	private JTable moviesTable;
	private JScrollPane scrollPane;
	private JButton bookingBtn, createScheduleBtn, editScheduleBtn, deleteScheduleBtn;
	private String[] columns = { "id", "Movie Title", "Cinema Name", "Theatre Name", "Start Time", "End Time",
			"Public Date", "Duration" };
	private String[][] moviesScheduleDataTable;
	private DefaultTableModel tableModel;

	public BookingPage() {
		System.out.println("calling constructor !!!!");
		this.scheduleDao = new ScheduleDao();
		this.initializeComponents();

	}

	private void initializeComponents() {

		this.setTitle("Movie Schedules");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		this.tableModel = new DefaultTableModel(null, this.columns);
		this.moviesTable = new JTable(this.tableModel);

		this.scrollPane = new JScrollPane(this.moviesTable);

		this.add(this.scrollPane, BorderLayout.CENTER);

		this.getScheudleDataTable();
		this.prepareMovieDataTable();

		this.bookingBtn = new JButton("Select Movie & Book Seat");
		this.createScheduleBtn = new JButton("Create");
		this.editScheduleBtn = new JButton("Edit");
		this.deleteScheduleBtn = new JButton("Delete");
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1, 4));
		btnPanel.add(bookingBtn);
		btnPanel.add(createScheduleBtn);
		btnPanel.add(editScheduleBtn);
		btnPanel.add(deleteScheduleBtn);

		this.add(btnPanel, BorderLayout.SOUTH);
		selectMovieForBookingAction();
		createBtnAction();
		editSchedultBtnAction();
		deleteScheuduleBtnAction();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void deleteScheuduleBtnAction() {
		
		this.deleteScheduleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteAction();
				
			}

			
			
		});
		
		
		
	}
	
	private void deleteAction() {
		int selectedRow = moviesTable.getSelectedRow();
		if (selectedRow != -1) {
			int scheduleId = Integer.parseInt(this.moviesScheduleDataTable[selectedRow][0]);
			scheduleDao.delete(scheduleId);
			JOptionPane.showMessageDialog(this, "Succuessfully deleted.");
			this.refreshBookingPage();
		} else {
			JOptionPane.showMessageDialog(this, "Please select one to delete.");
		} 
		
	}

	private void editSchedultBtnAction() {
		
		this.editScheduleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				editAction();
				
			}
			
		});
		
	}

	private void editAction() {
		
		openEditMovieSchedulePage();
		
	}

	private void openEditMovieSchedulePage() {
		
		int selectedRow = this.moviesTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select one to edit.");
		}
		int scheduleId = Integer.parseInt(this.moviesScheduleDataTable[selectedRow][0]);
		new EditMovieSchedulePage(this, scheduleId);
		
	}

	private void createBtnAction() {

		this.createScheduleBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				openMovieSchedulePage();

			}

		});

	}

	public void openMovieSchedulePage() {
		new CreateMovieSchedulePage(this);
	}

	private void getScheudleDataTable() {

		List<Schedule> schedules = this.scheduleDao.getAll();
		this.moviesScheduleDataTable = new String[schedules.size()][columns.length];
		int rowCount = 0;
		for (Schedule schedule : schedules) {

			moviesScheduleDataTable[rowCount] = schedule.toArray();
			rowCount++;

		}

	}

	private void prepareMovieDataTable() {
		for (String[] scheduleDataRow : this.moviesScheduleDataTable) {
			this.tableModel.addRow(scheduleDataRow);

		}
	}

	private void selectMovieForBookingAction() {
		this.bookingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bookingBtnAction();
			}
		});
	}

	private void bookingBtnAction() {
		int selectedRow = moviesTable.getSelectedRow();
		if (selectedRow != -1) {
			int scheduleId = Integer.parseInt(this.moviesScheduleDataTable[selectedRow][0]);
			try {
				SeatView seatView = new SeatView(scheduleId);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select a movie");
		}

	}

	public void refreshBookingPage() {
		this.tableModel.setRowCount(0);
		
		this.getScheudleDataTable();
		this.prepareMovieDataTable();
		

	};

}
