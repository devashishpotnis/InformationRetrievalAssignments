import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Conflation {
    public static void main(String[] args) {
        InputStreamReader st = new InputStreamReader(System.in);
        BufferedReader buff = new BufferedReader(st);
        String fname = "";
        System.out.println("Enter File Name :");
        try {
            fname = buff.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        conflation(fname);
    }

    private static void conflation(String fname) {
        BufferedReader buff;
        try {
            buff = new BufferedReader(new FileReader(fname));
            String line = "", data = "";
            line = buff.readLine();
            while (line != null) {
                data += line;
                System.out.println(line);
                line = buff.readLine();
            }
            Pattern pattern = Pattern.compile("(ed|ing)|\\b(this|is|a|and|are|an|the)\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(data);
            String clean = matcher.replaceAll("");
            StringTokenizer st = new StringTokenizer(clean);
            String currentToken = "";
            ArrayList<String> token = new ArrayList<>();
            ArrayList<Integer> count = new ArrayList<>();
            while (st.hasMoreTokens()) {
                currentToken = st.nextToken();
                int index = token.indexOf(currentToken);
                if (index != -1) {
                    count.set(index, count.get(index) + 1);
                } else {
                    token.add(currentToken);
                    count.add(1);
                }
            }
            System.out.println("OUTPUT IS:\nTOKENS\tNO OF OCCURRENCES");
            for (int i = 0; i < token.size(); i++) {
                System.out.println(token.get(i) + "\t" + count.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
