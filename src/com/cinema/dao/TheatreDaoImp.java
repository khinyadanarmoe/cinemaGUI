package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Cinema;
import com.cinema.model.Seat;
import com.cinema.model.Theatre;

public class TheatreDaoImp extends TheatreDao{

	private PgSqlConnectionFactory connectionFactory;
	private AbstractDao<Cinema> cinemaDao;
	private List<Theatre> theatres;
	private Theatre theatre;

	public TheatreDaoImp() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.cinemaDao = new CinemaDao();
	}

	@Override
	public String getTableName() {
		return "theatres";
	}

	@Override
	public Theatre convertToObject(ResultSet resultSet) throws SQLException {

		Theatre threatre = new Theatre();
		threatre.setId(resultSet.getInt("id"));
		threatre.setName(resultSet.getString("name"));
		int cinema_id = resultSet.getInt("cinema_id");
		threatre.setCinema(this.cinemaDao.findbyId(cinema_id));
		return threatre;
	}

	@Override
	public String getInsertValues() {
		return "(name, cinema_id) values (?, ?)";
	}

	@Override
	public void setParameters(PreparedStatement preparedStatement, Theatre entity) throws SQLException {
		preparedStatement.setString(1, entity.getName());
		preparedStatement.setInt(2, entity.getCinema().getId());
	}

	@Override
	public String getUpdateQuery() {
		return "update schedule set name = ? , cinema_id = ? where id = ?";
	}

	@Override
	public void setUpdateParameters(PreparedStatement preparedStatement, Theatre entity) {
		
		try {
			
			preparedStatement.setString(1, entity.getName());
			preparedStatement.setInt(2, entity.getCinema().getId());
			preparedStatement.setInt(3, entity.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public List<Theatre> getTheatreByCinemaId(int cinmeaId) {
		
		List<Theatre> theatres = new ArrayList<>();
		String query = "select * from " + getTableName() + " where cinema_id = ?";

		try {
			Connection connection = this.connectionFactory.createConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, cinmeaId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				this.theatre = this.convertToObject(resultSet);
				theatres.add(theatre);
				System.out.println(theatre);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			this.connectionFactory.closeConnection();
		}
		
		return theatres;
	}
}
