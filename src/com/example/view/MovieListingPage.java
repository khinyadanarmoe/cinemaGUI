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
import com.cinema.dao.MovieDao;
import com.cinema.model.Movie;

public class MovieListingPage extends JFrame implements ActionListener{
	
	private AbstractDao<Movie> movieDao;
	private JTable movieTable;
	private JButton selectMovieBtn;
	private DefaultTableModel tableModel;
	private String[] columns = {"Id", "Title", "Duration"};
	private String[][] movieDataTable;
	private JFrame parentPage;
	private String sourcePage;
	
	
	public MovieListingPage(JFrame parentPage, String sourcePage) {
		
		this.parentPage = parentPage;
		this.sourcePage = sourcePage;
		this.movieDao = new MovieDao();
		initializeComponents();
		addUIComponents();
		this.setVisible(true);
		
	}
	
	private void initializeTableComponent() {
		this.tableModel = new DefaultTableModel(this.columns, 0);
		this.movieTable = new JTable(this.tableModel);
		this.prepareMovieDataTable();
		loadMovieData();
		
	}
	
	private void loadMovieData() {
		for (String[] movieDataRow: this.movieDataTable) {
			this.tableModel.addRow(movieDataRow);
		}
	}
	
	private void prepareMovieDataTable() {
		List<Movie> movies = this.movieDao.getAll();
		this.movieDataTable = new String[movies.size()][this.columns.length];
		int rowCount = 0;
		for (Movie movie: movies) {
			this.movieDataTable[rowCount] = movie.toArray();
			rowCount++;
		}
	}


	
	private void addUIComponents() {
		
		this.add(this.movieTable, BorderLayout.CENTER);
		this.add(this.selectMovieBtn, BorderLayout.SOUTH);
		
		this.selectMovieBtn.addActionListener(this);

		
	}


	private void selectBtnAction() {
		
		int movieId = getSelectedMovieId();
		
		if (this.sourcePage == "createPage") {
			CreateMovieSchedulePage page = (CreateMovieSchedulePage) this.parentPage;
			page.refreshSelectedMovie(movieId);
		}
		
		else if (this.sourcePage == "editPage") {
			EditMovieSchedulePage page = (EditMovieSchedulePage) this.parentPage;
			page.refreshSelectedMovie(movieId);
		}
		
		this.dispose();
		
	}

	private void addSelectBtnAction() {
		
		this.selectMovieBtn.addActionListener(e -> selectBtnAction());
		
		
	}
	
	
	private int getSelectedMovieId() {
		return Integer.parseInt(this.movieDataTable[this.getSelectedIndex()][0]);
	}
	
	private int getSelectedIndex() {
		int selectedRow =  this.movieTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a movie");
		}else {
			return selectedRow;
		}
		
		return -1;
	}

	private void initializeComponents() {
		
		this.setTitle("Movie Listing Page");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		this.setSize(600,400);
		this.setLocation(100, 100);
		
		initializeTableComponent();
		this.selectMovieBtn = new JButton("Select");

		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == this.selectMovieBtn) {
			this.selectBtnAction();
		}
		
	}

}
