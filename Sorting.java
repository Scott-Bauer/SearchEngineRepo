import java.util.ArrayList;

import java.util.HashMap;

import java.util.Map.Entry;

// You may need it to implement fastSort

public class Sorting {

    /*
     * This method takes as input an HashMap with values that are Comparable.
     * It returns an ArrayList containing all the keys from the map, ordered
     * in descending order based on the values they mapped to.
     *
     * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number
     * of pairs in the map.
     */
    public static <K, V extends Comparable> ArrayList<K> slowSort(HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());    //Start with unsorted list of urls

        int N = sortedUrls.size();
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N - i - 1; j++) {
                if (results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j + 1))) < 0) {
                    K temp = sortedUrls.get(j);
                    sortedUrls.set(j, sortedUrls.get(j + 1));
                    sortedUrls.set(j + 1, temp);
                }
            }
        }
        return sortedUrls;
    }


    /*
     * This method takes as input an HashMap with values that are Comparable.
     * It returns an ArrayList containing all the keys from the map, ordered
     * in descending order based on the values they mapped to.
     *
     * The time complexity for this method is O(n*log(n)), where n is the number
     * of pairs in the map.
     */

    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {

        quickSort test = new quickSort<>(results);
        // ArraList<K> f = test.toret.subList(0, test.toSort.length);

        //System.out.println((test.toSort));
        return test.toret;

    }


    // ADD YOUR CODE HERE


    public static class quickSort<K, V extends Comparable> {

        HashMap<K, V> results;
        K[] toSort;

        ArrayList<K> toret;
        Object[] vals;


        public quickSort(HashMap<K, V> input) {
            this.results = input;
            this.toSort = (K[]) results.keySet().toArray();
            this.toret = new ArrayList<>(results.keySet());

            this.vals = results.values().toArray();
             sort(0, toSort.length - 1);

        }

        public void sort(int start, int end) {
            if (end <= start ) {

                return;
            }
            int index = start + (end - start) / 2;
            sort(start, index);
            sort(index + 1, end);
            merge(start, index, end);
        }

        public void merge(int first, int hindex, int last) {
            K[] sorted = (K[]) new Object[last - first + 1];
            Object[] v2 = new Object[last - first + 1];
            int i = first;
            int j = hindex + 1;
            int x = 0;
            while (i <= hindex && j <= last) {

                if (((V) vals[i]).compareTo((V) vals[j]) >= 0) {
                    sorted[x] = toSort[i];
                    v2[x] = vals[i];

                    i++;
                } else {
                    sorted[x] = toSort[j];
                    v2[x] = vals[j];
                    j++;
                }
                x++;
            }

            while (i <= hindex) {
                sorted[x] = toSort[i];
                v2[x] = vals[i];
                x++;
                i++;
            }
            while (j <= last) {
                sorted[x] = toSort[j];
                v2[x] = vals[j];
                x++;
                j++;

            }
            for (i = first; i <= last; i++) {
                //toret.set(i,sorted[i-first]);
                toSort[i] = sorted[i - first];
                toret.set(i, toSort[i]);
                vals[i] = v2[i - first];

            }

        }

    }
}