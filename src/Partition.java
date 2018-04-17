/**
 * 
 * Disjoint Partition of a set providing an union-find data structure
 * where clusters are implemented as linked lists of elements of type T 
 * Each cluster is represented by a Dnode of a doubly linked list of clusters
 * Each cluster/Dnode, points to a singly linked list of Node each containing an element in the cluster.
 * For efficient implementation of method find, each Node points to the Dnode of the cluster it belongs to.
 * 
 * @author Lucia Moura
 *
 * @param <T>
 */
public class Partition <T> {
 
 // inner class specifying a node in the singly linked lists
 public class Node {
  private Node next;
  private T element;
  private Dnode cluster=null;
  public Node (T element, Node next, Dnode cluster) {
   this.element=element;
   this.next=next;
   this.cluster=cluster;
  }
  
  //self method
  public T getElement(){
   return this.element;
  }
 }
 
 // inner class specifying a node in the doubly linked list of clusters
 public class Dnode {
  private Node first;
  private Dnode next, prev;
  private int size;
  
  Dnode(Node first, Dnode prev, Dnode next) {
   this.first=first;
   this.prev=prev;
   this.next=next;
   this.size=0;
  }
  
  //self method
  public Node first(){
   return this.first;
  }
  
 }
  
 private Dnode headCluster, tailCluster; // references to the dummy head and tail of the doubly linked list
 private int countClusters; // size of doubly linked list (not counting the dummies)
 
 public Partition() { 
  // creates an empty doubly linked list of clusters with dummy head and tail
  headCluster=new Dnode(null,null,null);
  tailCluster=new Dnode(null,headCluster,null);
  headCluster.next=tailCluster;
  countClusters=0;
 }
 
 public int numClusters() {
  return countClusters;
 }
 /**
  * makeCluster creates a new cluster formed by the given element and inserts at the end of the list of clusters
  * @param element
  * whenever an element is inserted
  * this method creats a new cluster (Dnode) for the element
  * then wait to be unioned with other cluster
  */
 public Node makeCluster(T element) {  // nothing needs to be changed here
  Node newNode=new Node(element,null,null);
  Dnode newCluster=new Dnode(newNode,tailCluster.prev,tailCluster);
  tailCluster.prev.next=newCluster;
  tailCluster.prev=newCluster;
  newCluster.first.cluster=newCluster;
  newCluster.size++;
  countClusters++;
  return newNode;
 }

 /****** for students to implement ***
  * find returns the Dnode corresponding to the cluster where the node belongs to
  * 
  */
 
 //author: Junhan Liu
 //Student#: 7228243
 //Course: CSI2110_A
 //Assignment: 4 
 public Dnode find(Node node) { // this is very short
  // **************** TODO: to be implemented by students 
  //System.out.print("\n>>>>>>>> Partition<T>: 'find' method needs to be implemented <<<<<<<<\n"); // to be removed
  
  Dnode result = this.headCluster.next;
  
  while(result != this.tailCluster){
   Node walker = result.first;
   while(walker != null){
    if(walker.element == node.element){
     return walker.cluster;
    }
    walker = walker.next;
   }
   result = result.next;
  }
  
  return null; // dummy return value must be corrected
  
 }
 
 /******** for students to implement ****
  *  union returns merges the cluster where node p belongs to with the one node q belongs to.
  *  This method must run in O(min(n_q,n_p)) time, where n_p is the size of the cluster of node p
  *  and n_q is the size of the cluster of node q.
  *  Note: You must do appropriate updates on the linked list and double linked list and its corresponding
  *  nodes and sizes to correctly reflect the fact that the clusters have been merged.
  *  */
 //author: Junhan Liu
 //Student#: 7228243
 //Course: CSI2110_A
 //Assignment: 4 
 
 /*
  * the union method is created first before take the running time into account
  * it does not run in O(min(p,q))
  * but it gives the preferred order (5,1,6,9,1,4)
  * the second union() operation is O(min(p,q))
  */
// public void union (Node p, Node q) {
//  
//  // **************** TODO: to be implemented by students 
//  //System.out.print("\n>>>>>>>>Partition<T>: 'union' method needs to be implemented <<<<<<<<<\n"); // to be removed later
//  
//  //if q cluster is after p cluster
//  //merge q cluster into p cluster
  //merge the later one into the front one
//  Dnode origin;
//  Dnode toMerge;
//  
//  /*
//   at most 26 iterations since there are at most 26 clusters in this case
//   therefore O(N) for the operation isAfter();
//   */
//  if( isAfter(p, q) ){   // O(N)
//   
//   origin = find(p);
//   toMerge = find(q);
//  }
//  else{
//   origin = find(q);
//   toMerge = find(p);
//  }
//  
//  Dnode newNext = toMerge.next; //O(1)
//  
////  //
////  System.out.println(origin.first.getElement());
////  System.out.println(toMerge.first.getElement());
////  //
//  Node walker = origin.first; //O(1)
//  while(walker.next != null){  //O(p) or O(q)
//   //reaches the last node of the origin list
//   walker = walker.next; 
//  }
//  
//  Node walker2 = toMerge.first; //O(1)
//  while(walker2 != null){   //O(q) or O(p)
//   Node newNode = new Node(walker2.element, null, origin);
//   walker.next = newNode;
//   walker2 = walker2.next;
//   walker = newNode;
//   origin.size++;
//  }
//  
//  //update the list
//  Dnode before = toMerge.prev;
//  before.next = toMerge.next;
//  before.next.prev = before;
//  
//  
// }
 
  
 //author: Junhan Liu
 //Student#: 7228243
 //Course: CSI2110_A
 //Assignment: 4 
 /*
  * This union method is operating in O(min(p,q)) time
  * but the order is not same as the given
  * with the content of each cluster being the same though
  */
 public void union (Node p, Node q) {
  
  Dnode origin = this.find(p);
  Dnode toMerge = this.find(q);
  if(origin.size < toMerge.size){
   origin = find(q);
   toMerge = find(p);
  }
  
  Node walker = toMerge.first;
  //walk through the shorter cluster in O(p),where p now is less then q
  while(walker != null){
   Node newNode = new Node(walker.element,null,origin);
   Node temp = origin.first;
   origin.first = newNode;
   newNode.next = temp;
   origin.size++;
   walker = walker.next;
  }
  
  Dnode before = toMerge.prev;
  before.next = toMerge.next;
  before.next.prev = before; 
 }
 
 //author: Junhan Liu
 //Student#: 7228243
 //Course: CSI2110_A
 //Assignment: 4 
 
 /*
  at most 26 iterations since there are at most 26 clusters in this case
  therefore O(N) for the operation
  */
 private boolean isAfter(Dnode p, Dnode q){
  //return true if the cluster q is after the cluster p

  while( p != this.tailCluster){
   if(p.next == q){
    return true;
   }
   p = p.next;
  }
  return false;
 }
 
 @Override
 public String toString() {
  // prints all clusters information and elements (nothing to be changed here)
  StringBuilder s = new StringBuilder(); int num=0;
  for (Dnode d=headCluster.next; d!=tailCluster; d=d.next) {
   s.append("Cluster ");
      s.append(++num); s.append(" (size="); s.append(d.size); s.append("): ");
   for (Node n=d.first; n!=null; n=n.next) {
    s.append(n.element.toString());
    s.append(',');
   }
   s.append("\n");
  }
  return s.toString();
 }
 
}
