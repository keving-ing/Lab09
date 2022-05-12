package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;


import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	
	private BordersDAO dao;
	private Graph<Country,DefaultEdge> grafo;
	private Map<Integer, Country> country;
	private Set<Country> raggiungibili = new HashSet<Country>();
	
	
	
	public Model() {
		dao = new BordersDAO();
	}
	
	
	
	public Map<Integer, Country> getCountry() {
		country = dao.loadAllCountries();
		return country;
	}



	public void creaGrafo(int anno)
	{
		grafo = new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		List<Country> vertici = dao.loadVertex(anno);
		
		Graphs.addAllVertices(grafo, vertici);
		
		for(Border b: this.dao.getBorder())
		{
			Graphs.addEdgeWithVertices(this.grafo, b.getC1(), b.getC2());
		}
		
		
		System.out.println("Vertici: " + grafo.vertexSet().size());
		System.out.println("Archi: " + grafo.edgeSet().size());
		
	}
	
	public Map<Country, Integer> numeroConfini()
	{
		return dao.nStatiConfinanti();
	}
	
	public int nV()
	{
		return this.grafo.vertexSet().size();
	}
	
	public int nA()
	{
		return this.grafo.edgeSet().size();
	}
	
	public int getComponenteConnessa(Country c) {
		
		//per stamapre il numeroo di componenti connesse senza vertice di partenza
		ConnectivityInspector<Country,DefaultEdge> connesse = new ConnectivityInspector<Country,DefaultEdge>(this.grafo);
		
		DepthFirstIterator<Country, DefaultEdge> it = new DepthFirstIterator<Country, DefaultEdge>(this.grafo, c);
		 while(it.hasNext())
		 {
			 raggiungibili.add(it.next());
		 }
		 
		 return connesse.connectedSets().size();
		
	}



	public Set<Country> getRaggiungibili() {
		return raggiungibili;
	}
	
	
	
	

}
