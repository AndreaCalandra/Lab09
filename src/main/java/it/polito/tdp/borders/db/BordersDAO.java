package it.polito.tdp.borders.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		//List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				idMap.put(rs.getInt("ccode"), new Country(rs.getString("StateAbb"), rs.getInt("ccode"),  rs.getString("StateNme")));
			}
			
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer, Country> idMap) {
		
		String sql = "SELECT state1no, state2no, year "
				+ "FROM contiguity "
				+ "WHERE YEAR <= ? AND state1no < state2no AND conttype = 1";
		
		List<Border> lista = new ArrayList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country n1 = idMap.get(rs.getInt("state1no"));
				Country n2 = idMap.get(rs.getInt("state2no"));
				lista.add(new Border(n1, n2, rs.getInt("year")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		return lista;
	}
	
	public List<Country> getVertici(int anno, Map<Integer, Country> idMap) {
		
		String sql = "SELECT DISTINCT state1no "
				+ "FROM contiguity "
				+ "WHERE YEAR <= ? AND state1no < state2no AND conttype = 1";
		
		List<Country> listaCountry = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country n1 = idMap.get(rs.getInt("state1no"));
				listaCountry.add(n1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		return listaCountry;
	}
	
	
	public Map<Country, Integer> getNumConfini(int anno, Map<Integer, Country> idMap) {
		
		String sql = "SELECT state1no, COUNT(*) AS confini "
				+ "FROM contiguity "
				+ "WHERE YEAR <= ? AND conttype = 1 "
				+ "GROUP BY state1no";

		Map<Country, Integer> mappa = new HashMap<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Country n1 = idMap.get(rs.getInt("state1no"));
				mappa.put(n1, rs.getInt("confini"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
		return mappa;
		
	}
}
