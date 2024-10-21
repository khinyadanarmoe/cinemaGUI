package com.example.view;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cinema.dao.AbstractDao;
import com.cinema.dao.CinemaDao;
import com.cinema.model.Cinema;

public class CinemaListingPage extends JFrame implements ActionListener {

	private AbstractDao<Cinema> cinemaDao;
	private ScrollPane scrollPane;
	private DefaultTableModel tableModel;
	private String[][] cinemaDataTable;
	private JButton selectBtn;
	private JTable cinemaTable;
	private String[] columns = {"id" , "Name", "Address"};
	private JFrame parentPage;
	private String sourcePage;

	public CinemaListingPage(JFrame parentPage, String sourcePage) {
		
		this.parentPage = parentPage;
		this.sourcePage = sourcePage;
		this.cinemaDao = new CinemaDao();
		this.setTitle("Cinema Listing");
		this.setLocation(200,150);
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setSize(500,400);
		this.setLayout(new BorderLayout());
		initializeComponents();
		initializeTableComponent();
		this.setVisible(true);

	}

	private void initializeTableComponent() {
		
		this.scrollPane = new ScrollPane();
		this.tableModel = new DefaultTableModel(null, this.columns);
		this.cinemaTable = new JTable(this.tableModel);
		this.scrollPane.add(this.cinemaTable);
		this.add(this.scrollPane, BorderLayout.CENTER);
		this.loadCinemaDataTable(); 
		this.prepareCinemaDataModel();
	}

	private void prepareCinemaDataModel() {
		
		for (String[] cinemaDataRow: this.cinemaDataTable) {
			this.tableModel.addRow(cinemaDataRow);
			
		}
		
	}

	private void initializeComponents() {
		
		this.tableModel = new DefaultTableModel();
		this.selectBtn = new JButton("Select");
		this.selectBtn.addActionListener(this);
		this.add(selectBtn, BorderLayout.SOUTH);
	}

	public void loadCinemaDataTable() {
		List<Cinema> cinemas = this.cinemaDao.getAll();
		this.cinemaDataTable = new String[cinemas.size()][this.columns.length];
		int rowCount = 0;
		for (Cinema cinema: cinemas) {
			this.cinemaDataTable[rowCount] = cinema.toArray();
			rowCount++;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.selectBtn) {
			int cinemaId = this.getCinemaId();
			if (this.sourcePage == "createPage") {
				CreateMovieSchedulePage page = (CreateMovieSchedulePage) this.parentPage;
				page.refreshSelectedCinema(cinemaId);
			}
			
			else if (this.sourcePage == "editPage") {
				EditMovieSchedulePage page = (EditMovieSchedulePage) this.parentPage;
				page.refreshSelectedCinema(cinemaId);
			}
			
			this.dispose();
		}	

	}
	
	
	public int getSelectedRow() {
		int selectedRow = this.cinemaTable.getSelectedRow();
		
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select one");
			return -1;
		}
		return selectedRow;
		
	}

	public int getCinemaId() {
		int id = Integer.parseInt(this.cinemaDataTable[getSelectedRow()][0]);
		System.out.println("id" + id);
		return id;
	}
}
