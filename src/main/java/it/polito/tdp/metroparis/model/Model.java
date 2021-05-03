package it.polito.tdp.metroparis.model;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	Graph<Fermata,DefaultEdge> grafo ;
	
	
	public void CreaGrafo() {
		this.grafo=new SimpleGraph<>(DefaultEdge.class);
		
		MetroDAO dao = new MetroDAO();
		List<Fermata> fermate = dao.getAllFermate();
		
		//for(Fermata f : fermate ) {
		//	this.grafo.addVertex(f);
		//}
		Graphs.addAllVertices(this.grafo, fermate);
		
		//aggiungiamo gli archi 
		/*for(Fermata f1 : this.grafo.vertexSet()) {
			for(Fermata f2 : this.grafo.vertexSet()) {
				if(!f1.equals(f2) && dao.fermatecollegate(f1,f2)) {
					this.grafo.addEdge(f1, f2);
				}
			}
		}*/
		List<Connessione> connessioni = dao.getAllConnessione(fermate);
		for(Connessione c : connessioni) {
			this.grafo.addEdge(c.getStazP(), c.getStazA());
		}
		
		
		System.out.println(this.grafo);
		
		//per trovare gli archi adiacenti
		//this.grafo.incomingEdgesOf(null) //archi entranti
		//this.grafo.outgoingEdgesOf(null) //archi uscenti
		//this.grafo.edgesOf(null)//nel caso di archi non orientati
		
		/*Fermata f ;
		Set<DefaultEdge> archi = this.grafo.edgesOf(f); //archi adiacenti
		for(DefaultEdge b : archi) {
			Fermata f1 = this.grafo.getEdgeSource(b); //stazione di partenza
			//oppure
			Fermata f2 = grafo.getEdgeTarget(b); //stazione di arrivo
			//nel caso di archi non orientati dipende da come è stato memorizzato il grafo 
			//uno dei due coincide sicuramente con f
			if(f1.equals(f)) {
				//f2 è quella che mi serve
			}
		}*/
		
		//Graphs.getOppositeVertex(grafo, b, f); //b un arco adiacente, f da dove si parte e restituisce il vertice opposto
		
		//stesso lavoro del for precedente
		//List<Fermata> adiacenti = Graphs.successorListOf(grafo, f);//ritorna una lista di vertici successori di un determinato vertice
		//Graphs.predecessorListOf(grafo, f) //predecessori di un vertice
		//grafo non orientato i metodi si equivalgono
		//direttamente i vertici adiacenti raggiungibili non quelli raggiungibili
		
		
		//tutti i vertici raggiungibili a partire da uno
		}
	
	public List<Fermata> fermateRaggiungibili(Fermata partenza){
		//visita in ampiezza oggetto algoritmo in ampiezza
		BreadthFirstIterator<Fermata, DefaultEdge> bfv = new BreadthFirstIterator<>(this.grafo,partenza);
		//DepthFirstIterator<Fermata, DefaultEdge> dfv = new DepthFirstIterator<>(grafo,partenza);
		List<Fermata> result = new ArrayList<Fermata>();
		
		while(bfv.hasNext()) {
			Fermata f = bfv.next();
			result.add(f);
		}
		return result;
	}
	
	public Fermata provaFermata(String nome) {
		for(Fermata f : this.grafo.vertexSet()) {
			if(f.getNome().equals(nome)) {
				return f;
			}
		}
		return null;
	}

}
