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

public abstract class TheatreDao extends AbstractDao<Theatre> {

	public abstract List<Theatre> getTheatreByCinemaId(int cinmeaId);
}
