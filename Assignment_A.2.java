import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class SinglePassClustering {
    @SuppressWarnings("rawtypes")
    static Hashtable<String, Float>[] documents = new Hashtable[5];
    @SuppressWarnings("rawtypes")
    static Hashtable<String, Float>[] cluster = new Hashtable[5];
    static int[][] clusters = new int[5][5];
    static int noOfClusters = 1;

    public static void main(String[] args) throws IOException {
        int loop;
        String[] doc = new String[6];
        BufferedReader fin;
        String line = "";

        for (loop = 0; loop < 5; loop++) {
            documents[loop] = new Hashtable<String, Float>();
            cluster[loop] = new Hashtable<String, Float>();
        }

        @SuppressWarnings("rawtypes")
        Enumeration<?> temp;
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the number of Documents:");
        int noOfDocuments = Integer.parseInt(stdInput.readLine());
        System.out.println("Enter the threshold:");
        float threshold = Float.parseFloat(stdInput.readLine());

        for (loop = 0; loop < noOfDocuments; loop++) {
            System.out.println("Enter the Document Name:");
            doc[loop] = stdInput.readLine();
        }

        for (loop = 0; loop < noOfDocuments; loop++) {
            fin = new BufferedReader(new FileReader("F:\\" + doc[loop] + ".txt"));
            while ((line = fin.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line);
                while (st.hasMoreElements()) {
                    String str = st.nextToken();
                    String ptr = st.nextToken();
                    float i = Float.parseFloat(ptr);
                    documents[loop].put(str, i);
                }
            }
        }

        singlePassAlgorithm(noOfDocuments, threshold);
        search();
    }

    @SuppressWarnings("unchecked")
    private static void search() {
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));
        String ch = null;
        String query = null;
        System.out.println("\n");
        System.out.println("Do you want to enter any query? (yes/no)");

        try {
            ch = stdInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Enumeration<String> temp;

        if (ch.equals("yes")) {
            System.out.println("Enter the query:");
            try {
                query = stdInput.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int loop = 0; loop < noOfClusters; loop++) {
                temp = cluster[loop].keys();
                while (temp.hasMoreElements()) {
                    String str = temp.nextElement();
                    if (str.equals(query)) {
                        System.out.println("Query found in cluster " + (loop + 1));
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void singlePassAlgorithm(int noOfDocuments, float threshold) {
        cluster[0] = (Hashtable<String, Float>) documents[0].clone();
        clusters[0][0] = 1;
        clusters[0][1] = 0;

        for (int i = 1; i < noOfDocuments; ++i) {
            float max = -1;
            int clusterId = -1;

            for (int j = 0; j < noOfClusters; ++j) {
                float similarity = calculateSimilarity(documents[i], cluster[j]);
                if (similarity > threshold) {
                    if (similarity > max) {
                        max = similarity;
                        clusterId = j;
                    }
                }
            }

            if (max == -1) {
                clusters[noOfClusters][0] = 1;
                clusters[noOfClusters][1] = i;
                noOfClusters++;
                addToCluster(documents[i], noOfClusters);
            } else {
                clusters[clusterId][0] += 1;
                int index = clusters[clusterId][0];
                clusters[clusterId][index] = i;
                calculateClusterRepresentative(documents[i], cluster[clusterId]);
            }
        }

        Enumeration<String> temp;

        for (int loop = 0; loop < noOfClusters; loop++) {
            System.out.println("Cluster no" + loop);
            temp = cluster[loop].keys();
            while (temp.hasMoreElements()) {
                String str = temp.nextElement();
                System.out.println(str + "            " + cluster[loop].get(str));
            }
        }

        for (int i = 0; i < noOfClusters; ++i) {
            System.out.print("\n" + i + "\t");
            for (int j = 1; j <= clusters[i][0]; ++j) {
                System.out.print(" " + clusters[i][j]);
            }
        }
    }

    private static void calculateClusterRepresentative(Hashtable<String, Float> doc, Hashtable<String, Float> clust) {
        int flag = 0;
        float freq1, freq2 = 0;

        Enumeration<String> temp1, temp2;
        temp1 = doc.keys();
        temp2 = clust.keys();

        while (temp1.hasMoreElements()) {
            flag = 0;
            String str = temp1.nextElement();
            freq1 = doc.get(str);
            temp2 = clust.keys();

            while (temp2.hasMoreElements()) {
                String str1 = temp2.nextElement();
                freq2 = clust.get(str1);

                if (str.equals(str1)) {
                    flag = 1;
                }
            }

            if (flag == 0) {
                clust.put(str, freq1);
            } else if (flag == 1) {
                freq1 = (freq1 + freq2) / 2;
                clust.put(str, freq1);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void addToCluster(Hashtable<String, Float> doc, int noOfClusters) {
        cluster[noOfClusters - 1] = (Hashtable<String, Float>) doc.clone();
    }

    private static float calculateSimilarity(Hashtable<String, Float> doc, Hashtable<String, Float> clust) {
        double answer = 0;

        Enumeration<String> temp1, temp2;
        temp1 = doc.keys();
        temp2 = clust.keys();

        while (temp1.hasMoreElements()) {
            String str = temp1.nextElement();
            temp2 = clust.keys();

            while (temp2.hasMoreElements()) {
                String str1 = temp2.nextElement();

                if (str.equals(str1)) {
                    answer++;
                }
            }
        }

        answer = 2 * answer;
        return (float) answer;
    }
}
