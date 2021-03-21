package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.Route;
import domain.Airport;


public class RouteDAO extends BaseDAO<Route> {
	public RouteDAO(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	public void updateRoute(Route route) throws ClassNotFoundException, SQLException {
		save("UPDATE route SET origin_id = ?, destination_id = ?,  WHERE id = ?",
				new Object[] {route.getOriginID(), route.getDestinationID(), route.getId()});
	}

	public void deleteRoute(Route route) throws ClassNotFoundException, SQLException {
		save("DELETE FROM route WHERE id = ?", 
				new Object[] {route.getId()});
	}
	
	public void addRoute(Route route) throws ClassNotFoundException, SQLException {
		save("INSERT INTO route VALUES (?, ?, ?)", 
				new Object[] {route.getId(), route.getOriginID().getCity(), route.getDestinationID().getCity()});
	}
	
	public List<Route> readAllRoutes() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route", null);
	}
	
	public List<Route> readRouteByID(Route route) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route WHERE id = ?", new Object[] {route.getId()});
	}
	
	public List<Route> readRouteByOrigin(Airport airport) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route WHERE origin_id = ?", new Object[] {airport.getAirportCode() });
	}
	
	public List<Route> readRouteByPlaneCodes(Airport origin, Airport dest) throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM route WHERE origin_id = ? AND destination_id = ?", new Object[] {origin.getAirportCode(), dest.getAirportCode() });
	}

	
	public List<Route> extractData(ResultSet rs) throws ClassNotFoundException, SQLException {
		List<Route> routes = new ArrayList<>();
		while (rs.next()) {
			Route r = new Route();
			r.setId(rs.getInt("id"));
			Airport origin = new Airport();
			Airport dest = new Airport();
			origin.setAirportCode(rs.getString("origin_id"));
			dest.setAirportCode(rs.getString("destination_id"));
			r.setOriginID(origin);
			r.setDestinationID(dest);
			routes.add(r);
		}
		return routes;
		
	}
}
