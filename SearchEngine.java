import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
    public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)
    public MyWebGraph internet;
    public XmlParser parser;

    public SearchEngine(String filename) throws Exception{
        this.wordIndex = new HashMap<String, ArrayList<String>>();
        this.internet = new MyWebGraph();
        this.parser = new XmlParser(filename);
    }

    /*
     * This does a graph traversal of the web, starting at the given url.
     * For each new page seen, it updates the wordIndex, the web graph,
     * and the set of visited vertices.
     *
     * 	This method will fit in about 30-50 lines (or less)
     */
    public void crawlAndIndex(String url) throws Exception {
    if(internet.getVisited(url)){
        return;
    }
        internet.addVertex(url);

        wordIndex.put(url, parser.getContent(url));

        internet.setVisited(url, true);
        ArrayList<String> adj = parser.getLinks(url);
        if (adj == null) {
            return;
        }

        for (String s : adj) {

                internet.addVertex(s);
                internet.addEdge(url, s);
                crawlAndIndex(s);
            }
        }

        // TODO : Add code here




    /*
     * This computes the pageRanks for every vertex in the web graph.
     * It will only be called after the graph has been constructed using
     * crawlAndIndex().
     * To implement this method, refer to the algorithm described in the
     * assignment pdf.
     *
     * This method will probably fit in about 30 lines.
     */
    public void assignPageRanks(double epsilon) {
        // TODO : Add code here
            ArrayList<String> vertices = internet.getVertices();

        for(int i = 0; i<vertices.size(); i++){
            internet.setPageRank(vertices.get(i), 1.0);
        }

        int count =0;


        ArrayList<Double> iter = new ArrayList<>();
        ArrayList<Double> ranks= computeRanks(vertices);
        ArrayList<Double> next = computeRanks(vertices);
        while(count>= 0) {
    int ind = 0;
    for (int k = 0; k < vertices.size(); k++) {
        if (Math.abs(ranks.get(k) - next.get(k)) < epsilon) {
            ind += 1;
        }

    }
    if (ind == vertices.size()) {
        break;
    }
    else{

        for (int g = 0; g < next.size(); g++) {
            ranks.set(g, next.get(g));
        }

        next = computeRanks(vertices);

    }





            /*    iteration = 0;
            for (int i = 0; i < ranks.size(); i++) {
                iteration += ranks.get(i);
            }*/








           /* nextiteration=0;
            for (int i = 0; i < next.size(); i++) {
                nextiteration += next.get(i);
            }*/




                count++;

    }









    }

    /*
     * The method takes as input an ArrayList<String> representing the urls in the web graph
     * and returns an ArrayList<double> representing the newly computed ranks for those urls.
     * Note that the double in the output list is matched to the url in the input list using
     * their position in the list.
     */
    public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
        // TODO : Add code here


        double d = 0.5;
        ArrayList<Double> ranks = new ArrayList<Double>();
        for(int i =0; i<vertices.size(); i++){
            double rank = 0;
          String v = vertices.get(i);
          ArrayList<String> w = internet.getEdgesInto(v);
          double total = 0;
          double parenth = 0;
          for(int j = 0; j<w.size(); j++){

                  parenth = internet.getPageRank(w.get(j)) / internet.getOutDegree(w.get(j));


              total += parenth;

          }

            //System.out.println("Total for " +v+ " is " + total);

            rank = d+(total * d);
            ranks.add(i, rank);
        }
        for (int p =0; p<vertices.size(); p++) {
            internet.setPageRank(vertices.get(p), ranks.get(p));

        }


            return ranks;
        }
        //return null;



    /* Returns a list of urls containing the query, ordered by rank
     * Returns an empty list if no web site contains the query.
     *
     * This method should take about 25 lines of code.
     */
    public ArrayList<String> getResults(String query) {
        // TODO: Add code here
        ArrayList<String> urls = new ArrayList<>();
    for(HashMap.Entry<String, ArrayList<String>> entry: wordIndex.entrySet()) {
        ArrayList<String> list = (ArrayList) entry.getValue();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(query)) {
                urls.add(entry.getKey());
            }
        }
    }
   HashMap<String, Double> res = new HashMap<>();
        for (int i = 0; i<urls.size(); i++){
            res.put(urls.get(i), internet.getPageRank(urls.get(i)));
        }
        for (int i = 0; i<urls.size(); i++){
            res.put(urls.get(i), internet.getPageRank(urls.get(i)));
        }
    return Sorting.fastSort(res);


    }
}
