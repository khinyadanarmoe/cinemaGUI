package com.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cinema.database.PgSqlConnectionFactory;
import com.cinema.model.Movie;
import com.cinema.model.Schedule;
import com.cinema.model.Theatre;

public class ScheduleDao extends AbstractDao<Schedule>{
	
	private PgSqlConnectionFactory connectionFactory;
	private AbstractDao<Movie> movieDao;
	private AbstractDao<Theatre> theatreDao;
	
	public ScheduleDao() {
		this.connectionFactory = new PgSqlConnectionFactory();
		this.movieDao = new MovieDao();
		this.theatreDao = new TheatreDaoImp();
	}


	@Override
	public String getTableName() {
		return "schedule";
	}

	@Override
	public Schedule convertToObject(ResultSet resultSet) throws SQLException {
	
			Schedule schedule = new Schedule();
			schedule.setId(resultSet.getInt("id"));
			int movieId = resultSet.getInt("movie_id");
			Movie movie = this.movieDao.findbyId(movieId);
			schedule.setMovie(movie);
			int theatreId = resultSet.getInt("theatre_id");
			Theatre theatre = this.theatreDao.findbyId(theatreId);
			schedule.setThreatre(theatre);
			schedule.setStartTime(resultSet.getString("start_time"));
			schedule.setEndTime(resultSet.getString("end_time"));
			schedule.setPublicDate(resultSet.getString("public_date"));
			return schedule;
	}

	@Override
	public String getInsertValues() {
		return "(movie_id, theatre_id, start_time, end_time, public_date) VALUES (?, ?, ?, ?, ?)";
	}

	@Override
	public void setParameters(PreparedStatement preparedStatement, Schedule entity) throws SQLException {
		preparedStatement.setInt(1, entity.getMovie().getId());
		preparedStatement.setInt(2, entity.getThreatre().getId());
		preparedStatement.setString(3, entity.getStartTime());
		preparedStatement.setString(4, entity.getEndTime());
		preparedStatement.setString(5, entity.getPublicDate());
	}


	@Override
	public String getUpdateQuery() {
		return "UPDATE schedule SET movie_id = ?, theatre_id = ?, start_time = ?, end_time = ?, public_date = ? WHERE id = ?;";
	}


	@Override
	public void setUpdateParameters(PreparedStatement preparedStatement, Schedule entity) {
		
		try {
			preparedStatement.setInt(1, entity.getMovie().getId());
			preparedStatement.setInt(2, entity.getThreatre().getId());
			preparedStatement.setString(3, entity.getStartTime());
			preparedStatement.setString(4, entity.getEndTime());
			preparedStatement.setString(5, entity.getPublicDate());
			preparedStatement.setInt(6, entity.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
