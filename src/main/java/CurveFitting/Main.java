package CurveFitting;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        double[] xData = {1, 2, 3, 4, 5};
        double[] yData = {1, 1.2, 3, 7, 7};
        Logit5pFitResult logit5pFitResult = new Logit5pFitResult();
        FunctionFitting function = new FunctionFitting(logit5pFitResult);

        try {
            FitResult result = function.fit(xData, yData);

            FitEvaluationResult evaluationResult = function.evaluateFit(xData,yData,result);
            System.out.println(evaluationResult.getR2());
            System.out.println(Arrays.toString(result.getParams()));
            double [] y = function.predict(result,xData);
            System.out.println(Arrays.toString(y));
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }
}