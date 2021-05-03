package it.polito.tdp.metroparis.model;

import java.util.List;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Model m = new Model();
		m.CreaGrafo();
		
		
		
		Fermata p = m.provaFermata("La Fourche");
		List<Fermata> raggiungibili = m.fermateRaggiungibili(p);
		System.out.println(raggiungibili);

	}

}
