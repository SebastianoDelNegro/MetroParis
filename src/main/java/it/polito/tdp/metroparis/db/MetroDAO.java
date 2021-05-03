package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> getAllLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

	public boolean fermatecollegate(Fermata f1, Fermata f2) {
		
		String sql = "SELECT COUNT(*) AS cnt "
				+ "FROM connessione "
				+ "WHERE id_stazA =? "
				+ "AND id_stazP = ?";
		
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, f1.getIdFermata());
			st.setInt(2, f2.getIdFermata());
			ResultSet rs = st.executeQuery();
		
			rs.first();
			int conteggio = rs.getInt("cnt");
			
			
			
			
			conn.close();
			return (conteggio>0);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
				
				
	}
	
	
	public List<Connessione> getAllConnessione(List<Fermata> fermate){
		
		List<Connessione> connessioni = new ArrayList<Connessione>();
		String sql = "SELECT * "
				+ "FROM connessione "
				+ "WHERE id_stazP>id_stazA";
		try {
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				int id_partenza = rs.getInt("id_stazP");
				Fermata partenza = null;
				for(Fermata f : fermate) {
					if(f.getIdFermata()==id_partenza) {
						partenza = f;
					}
				}
				int id_arrivo = rs.getInt("id_stazA");
				Fermata arrivo = null;
				for(Fermata f1 : fermate) {
					if(f1.getIdFermata()==id_arrivo) {
						arrivo = f1;
					}
				}
				
				
				Connessione c = new Connessione(rs.getInt("id_connessione"),null,partenza,arrivo);
				connessioni.add(c);
			}

			conn.close();
			return connessioni;

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


}
