import java.util.ArrayList;
import java.util.List;

public class PrecisionRecallCalculator {
    public static void main(String[] args) {
    
        List<String> answerSetA = new ArrayList<>();
        answerSetA.add("1");
        answerSetA.add("2");
        answerSetA.add("3");
        answerSetA.add("4");
        answerSetA.add("5");


        List<String> relevantDocumentsRq1 = new ArrayList<>();
        relevantDocumentsRq1.add("1");
        relevantDocumentsRq1.add("2");
        relevantDocumentsRq1.add("4");

    
        double precision = calculatePrecision(answerSetA, relevantDocumentsRq1);
        double recall = calculateRecall(answerSetA, relevantDocumentsRq1);

     
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
    }


    public static double calculatePrecision(List<String> answerSet, List<String> relevantDocuments) {
        int truePositives = 0;
        int falsePositives = 0;

        for (String document : answerSet) {
            if (relevantDocuments.contains(document)) {
                ++truePositives;
            } else {
                ++falsePositives;
            }
        }

        return (double) truePositives / (truePositives + falsePositives);
    }

    public static double calculateRecall(List<String> answerSet, List<String> relevantDocuments) {
        int truePositives = 0;
        int falseNegatives = 0;

        for (String document : relevantDocuments) {
            if (answerSet.contains(document)) {
                truePositives++;
            } else {
                falseNegatives++;
            }
        }

        return (double) truePositives / (truePositives + falseNegatives);
    }
}
