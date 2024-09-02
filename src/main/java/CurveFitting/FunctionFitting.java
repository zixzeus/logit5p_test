package CurveFitting;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

class FitResult {
    protected ParametricUnivariateFunction fitFunction;
    protected int numOfParams;
    protected double [] params;

    public FitResult(ParametricUnivariateFunction fitFunction,int numOfParams) {
        this.fitFunction = fitFunction;
        this.numOfParams = numOfParams;
    }

    public ParametricUnivariateFunction getFitFunction() {
        return fitFunction;
    }

    public void setFitFunction(ParametricUnivariateFunction fitFunction) {
        this.fitFunction = fitFunction;
    }

    public double[] getParams() {
        return params;
    }

    public void setParams(double[] params) {
        this.params = params;
    }
    public int getNumOfParams() {
        return numOfParams;
    }

}

class Logit5pFitResult extends FitResult {
    public Logit5pFitResult() {
        super(new Logit5pFunction(),5);
    }
}

class Logit4pFitResult extends FitResult {
    public Logit4pFitResult() {
        super(new Logit4pFunction(),4);
    }
}

class FitEvaluationResult {
    private final FitResult fitResult;
    private final double[] xData;
    private final double[] yData;

    public FitEvaluationResult(double[] xData, double[] yData, FitResult fitResult) {
        this.xData = xData;
        this.yData = yData;
        this.fitResult = fitResult;
    }

    public double getR2(){
        double sum_y = 0;
        for (int i = 0; i < xData.length; i++) {
            sum_y += yData[i];
        }
        //y的平均值
        double avg_y = sum_y / yData.length;

        //检测值（y[i]）与y的平均值（avg_y）的 差值平方和
        double tss = 0;
        for (double yDatum : yData) {
            tss += Math.pow(yDatum - avg_y, 2);
        }
        //检测值（y[i]）与拟合的y值（parametricUnivariateFunction.value(xy[i], params)）的差值平方和
        double rss = 0;
        for (int i = 0; i < yData.length; i++) {
            rss += Math.pow(yData[i] - fitResult.getFitFunction().value(xData[i],fitResult.getParams()), 2);
        }
        //R^2 计算
        return 1 - rss / tss;
    }

}

class FunctionFitting {
    private final FitResult fitResult;

    FunctionFitting(FitResult fitResult) {
        this.fitResult = fitResult;
    }

    FitResult fit(double[] xData, double[] yData) throws InvalidDataException{
        WeightedObservedPoints obs = new WeightedObservedPoints();
        for (int i = 0; i < xData.length; i++) {
            obs.add(xData[i], yData[i]);
        }
        FuncFitter fitter = new FuncFitter();
        fitter.FitFunction(fitResult.getFitFunction(),fitResult.getNumOfParams());
        double coeffs[] = fitter.fit(obs.toList());
        fitResult.setParams(coeffs);
        return fitResult;
    }

    FitEvaluationResult evaluateFit(double[] xData, double[] yData, FitResult fitResult) throws InvalidDataException {
        return new FitEvaluationResult(xData,yData, fitResult);
    }

    double[] predict(FitResult fitResult, double[] xData) throws InvalidDataException {
        double[] yData = new double[xData.length];
        for (int i = 0; i < xData.length; i++) {
            yData[i]= fitResult.getFitFunction().value(xData[i],fitResult.getParams());
        }
        return yData;
    }
}



