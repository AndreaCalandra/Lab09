package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;



public class Model {

	private SimpleGraph<Country, DefaultEdge> grafo;	// grafo pesato
	private BordersDAO dao;	
	private Map<Integer, Country> idMap;
	private Map<Country, Integer> grado;
	private Map<Country, Country> predecessore;
	
	public Model() {
		dao = new BordersDAO();
		idMap = new HashMap<>();
		dao.loadAllCountries(idMap);		
		grado = new HashMap<>();
		
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);	// creo il grafo
		
		Graphs.addAllVertices(grafo, dao.getVertici(anno, idMap));
	
		
		for (Border b: dao.getCountryPairs(anno, idMap)) {	
			if (this.grafo.containsVertex(b.getS1()) && this.grafo.containsVertex(b.getS2())) {
				DefaultEdge e = this.grafo.getEdge(b.getS1(), b.getS2());
				if (e == null) {
					Graphs.addEdgeWithVertices(grafo, b.getS1(), b.getS2());
				}
			}
		}
		
		//System.out.println("# vertici = " + grafo.vertexSet().size());
		//System.out.println("# archi = " + grafo.edgeSet().size());		
		
		grado = dao.getNumConfini(anno, idMap);
		//for (Map.Entry<Country, Integer> entry : grado.entrySet()) {
	      //  System.out.println(entry.getKey() + " = " + entry.getValue().toString());	
	    //}		
		
	}
	
	public int numVertici() {
		return grafo.vertexSet().size();
	}

	public int numArchi() {
		return grafo.edgeSet().size();
	}

	public String rotte(int anno) {
		String s = "";
		grado = dao.getNumConfini(anno, idMap);
		for (Map.Entry<Country, Integer> entry : grado.entrySet()) {
	      	s = s + entry.getKey().toString() + " = " + entry.getValue().toString() + "\n";	
	    }
		return s;
	}
	
	public List<Country> getVertici(int anno) {
		List<Country> lista = new ArrayList<>();
		for (Map.Entry<Country, Integer> entry : grado.entrySet()) {
	      	lista.add(entry.getKey());	
	    }
		return lista;
	}
	
	public List<Country> statiRaggiungibili(Country c) {
		List<Country> risposta = new ArrayList<>();
		BreadthFirstIterator <Country, DefaultEdge> bfv = new BreadthFirstIterator<> (grafo, c);
		predecessore = new HashMap<>();
		this.predecessore.put(c, null);
		
		bfv.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {
			

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				// TODO Auto-generated method stub
				DefaultEdge arco = e.getEdge() ;
				Country a = grafo.getEdgeSource(arco);
				Country b = grafo.getEdgeTarget(arco);
				// ho scoperto 'a' arrivando da 'b' (se 'b' lo conoscevo) b->a
				if(predecessore.containsKey(b) && !predecessore.containsKey(a)) {
					predecessore.put(a, b) ;
				} else if(predecessore.containsKey(a) && !predecessore.containsKey(b)) {
					// di sicuro conoscevo 'a' e quindi ho scoperto 'b'
					predecessore.put(b, a) ;
				}
				
			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		while(bfv.hasNext()) {
			Country f = bfv.next() ;
			risposta.add(f) ;
		}
		
		return risposta;
	}


}
