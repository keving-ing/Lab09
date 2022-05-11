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
	
	
	
	public Model() {
		dao = new BordersDAO();
	}
	
	public void creaGrafo(int anno)
	{
		grafo = new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
		country = dao.loadAllCountries();
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
	
	public int getComponenteConnessa() {
		
		ConnectivityInspector<Country,DefaultEdge> connesse = new ConnectivityInspector<Country,DefaultEdge>(this.grafo);
//		Set<Country> visitati = new HashSet<>();
//		DepthFirstIterator<Country, DefaultEdge> it = new DepthFirstIterator<Country, DefaultEdge>(this.grafo);
//		 while(it.hasNext())
//		 {
//			 visitati.add(it.next());
//		 }
		
		return connesse.connectedSets().size();
	}

}
