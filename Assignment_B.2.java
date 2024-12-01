import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class PrecisionRecallCalculator1 {
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
        double fmeasure = calculateFMeasure(precision, recall);
        double emeasure = calculateEMeasure(precision, recall);


        System.out.println("F-Measure: " + fmeasure);
        System.out.println("E-Measure: " + emeasure);
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

    public static double calculateFMeasure(double precision, double recall) {
        return (2 * precision * recall) / (precision + recall);
    }

    public static double calculateEMeasure(double precision, double recall) {
        return Math.sqrt(
                Math.pow(1 - precision, 2)
                + Math.pow(1 - recall, 2)
               );
    }
}
