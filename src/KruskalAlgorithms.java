import java.util.*;

/**
 * KruskalAlgorithms provides methods based on Kruskal's minimum spanning tree algorithm.
 * The algorithms operate on a graph that implements the interface Graph<V,E>
 * 
 * @author Lucia Moura created startup code
 *
 * @param <V> 
 * @param <E>
 */
public class KruskalAlgorithms<V,E extends Comparable<E>> {
	
	
	/**
	 * minimumSpaningTree uses the more general auxiliary method kruskalClusters
	 * this is already implemented, but its correctness relies on the correctness of kruskalClusters.
	 * @param g an undirected graph
	 * @return edges of a minimum spanning tree of g, if g is connected
	 * edges of a minimum spanning forest of g formed of trees of each connected component, if g is not connected.
	 * 
	 */
	public  LinkedList<Edge<E>> minimumSpanningTree(Graph<V,E> g) {  //no change to be done here
		return kruskalClusters(g,1, new Partition<Vertex<V>>());
	}
	
	// kruskalClusters has two inputs: a graph g and the desired number of clusters k
	// its outputs are Partition<Vertex<V>> clusters storing the k clusters
	// and a collection of edges that are part of the spanning forest used to create the clusters
	/**
	 * kruskalClusters runs steps of the Kruskal's algorithm until k clusters are found.
	 * It does not crash if the graph has more than k connected components; in this case
	 * it computes the connected components. 
	 * @param g is a graph where the edge compareTo is based on the distance/weight of the edge
	 * @param k is the number of desired clusters
	 * @param clusters is output that contains the partition storing the clusters
	 * @return the edges of minimum spanning trees of the clusters
	 */
	public LinkedList <Edge<E>>  kruskalClusters (Graph<V,E> g, int k, Partition<Vertex<V>> clusters) {
		
		// Initialization steps 
		
		PriorityQueue<Edge<E>> pq = new PriorityQueue<Edge<E>>(g.numEdges()); // priority queue
		HashMap<Vertex<V>,Partition<Vertex<V>>.Node> hash=new HashMap<Vertex<V>,Partition<Vertex<V>>.Node>(2*g.numVertices()); 
		// Hash Map to store the Node where each vertex is stored in the Partition
		LinkedList <Edge<E>> forest =new LinkedList <Edge<E>>(); // creates an empty list of edges
		
		// adding each edge to the priority queue
		for (Edge<E> e:g.edges()) {
			pq.add(e);
		}
		
		// adding each vertex to a cluster and saving the pair (vertex,Node) into the hash map
		for (Vertex<V> v: g.vertices()) {
			Partition<Vertex<V>>.Node node=clusters.makeCluster(v);
			hash.put(v,node);
		}
		
		// **** TODO main kruskalClusters loop to be implemented by students ******
		//
		//System.out.println(">>>>>>>>> kruskalClusters: main loop not implemented <<<<<<< "); // remove this
		//
		
		//author: Junhan Liu
		//Student#: 7228243
		//Course: CSI2110_A
		//Assignment: 4	
		
		/*
		 * 测试方法内最开始有37个vertex，37个cluster，666个edge，
		 * 将所有edge都放进PQueue中，每一次Que出来一个key值最小的edge
		 * 如果不在一个cluster中，那么将两个edge的两个vertex所在的cluster union
		 * 因为有37个独立的cluster，所以最后可以决定到剩余多少个cluster停止
		 */
		while(forest.size() < (g.numVertices()-k)){
			/*
			 * when remove, the edge has the smallest number of edge
			 * will be removed from PQ
			 * when the edge first added
			 * the PQ sort the edge according to their #edges
			 */
			Edge<E> edge = pq.remove();
			Vertex<V>[] vertex = g.endVertices(edge);
			Vertex<V> u = vertex[0];
			Vertex<V> v = vertex[1];
			
		  Partition<Vertex<V>>.Dnode node = clusters.find(hash.get(u));
		  Partition<Vertex<V>>.Dnode node2 = clusters.find(hash.get(v));
		  
		  if( node != node2){
			  forest.add(edge);
			  clusters.union(hash.get(u), hash.get(v));
		  }	
		}
		
		
		//
		// *** main loop not implemented 
      
		return forest; // currently returns null
	}
	
}
