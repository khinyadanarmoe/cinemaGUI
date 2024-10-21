package com.example.view;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.cinema.dao.TheatreDaoImp;
import com.cinema.model.Theatre;

public class TheatreListingPage extends JFrame implements ActionListener {

	private JButton selectBtn;
	private JTable theatreTable;
	private DefaultTableModel tabelModel;
	private ScrollPane scrollPane;
	private String[] columns = { "id", "name" };
	private TheatreDaoImp theatreDaoImp;
	private String[][] theatreDataTable;
	private JFrame parentPage;
	private CinemaListingPage cinemaListingPage;
	private int cinemaId;
	private String sourcePage;

	public TheatreListingPage(JFrame parentPage, int cinemaId, String sourcePage) {

		this.cinemaId = cinemaId;
		this.parentPage = parentPage;
		this.sourcePage = sourcePage;
		this.theatreDaoImp = new TheatreDaoImp();

		initializeComponents();
		this.setVisible(true);
	}

	private void initializeComponents() {

		this.setTitle("TheatreListingPage");
		this.setSize(500,400);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);

		initailizeTableComponent();

		this.selectBtn = new JButton("Select");
		this.selectBtn.addActionListener(this);
		this.add(selectBtn, BorderLayout.SOUTH);

	}

	private void initailizeTableComponent() {

		this.tabelModel = new DefaultTableModel(null, this.columns);
		this.theatreTable = new JTable(this.tabelModel);
		this.scrollPane = new ScrollPane();
		this.scrollPane.add(this.theatreTable);
		this.add(scrollPane, BorderLayout.CENTER);

		this.loadTheatreData();
		this.prepareTheatreData();

	}

	private void prepareTheatreData() {

		for (String[] theatreData : this.theatreDataTable) {
			this.tabelModel.addRow(theatreData);
		}

	}

	private void loadTheatreData() {

		System.out.println(cinemaId);
		List<Theatre> theatres = this.theatreDaoImp.getTheatreByCinemaId(this.cinemaId);
		this.theatreDataTable = new String[theatres.size()][this.columns.length];
		int rowCount = 0;
		for (Theatre theatre : theatres) {
			this.theatreDataTable[rowCount] = theatre.toArray();
			rowCount++;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.selectBtn) {
			
			if (this.sourcePage == "createPage") {
				CreateMovieSchedulePage page = (CreateMovieSchedulePage) this.parentPage;
				page.refreshSelectedTheatre(getTheareId());
				System.out.println(getTheareId());
			}
			
			else if (this.sourcePage == "editPage") {
				EditMovieSchedulePage page = (EditMovieSchedulePage) this.parentPage;
				page.refreshSelectedTheatre(getTheareId());
				System.out.println(getTheareId());
			}
			this.dispose();
		}

	}

	private int getTheareId() {

		int selectedRow = this.theatreTable.getSelectedRow();

		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select one");
			return -1;
		}
		else {
			System.out.println(selectedRow);
			return Integer.parseInt(this.theatreDataTable[selectedRow][0]);
		}
		
		
		
	}


}
