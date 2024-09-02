package CurveFitting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class FunctionFittingTest {
    double[] xData ;
    double[] yData ;
    Logit5pFitResult logit5pFitResult ;
    Logit4pFitResult logit4pFitResult ;
    FunctionFitting function ;
    FunctionFitting function2 ;
    FitResult result;
    @BeforeEach
    void setUp() {
        xData = new double[]{1, 2, 3, 4, 5};
        yData = new double[]{1, 1.2, 3, 7, 7};
        logit5pFitResult = new Logit5pFitResult();
        logit4pFitResult = new Logit4pFitResult();
        function = new FunctionFitting(logit4pFitResult);
        function2 = new FunctionFitting(logit5pFitResult);

    }

    @AfterEach
    void tearDown() throws InvalidDataException {
    }

    @Test
    void fit() {
        try {
            FitResult result = function.fit(xData, yData);
            FitResult result2 = function2.fit(xData, yData);
            System.out.println(Arrays.toString(result.getParams()));
            System.out.println(Arrays.toString(result2.getParams()));
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
    }

    @Test
    void evaluateFit() throws InvalidDataException {
        result = function.fit(xData, yData);
        FitEvaluationResult evaluationResult = function.evaluateFit(xData,yData,result);
        System.out.println(evaluationResult.getR2());
    }

    @Test
    void predict() throws InvalidDataException {
        result = function.fit(xData, yData);
        double [] y = function.predict(result,xData);
        System.out.println(Arrays.toString(y));
    }
}