package CurveFitting;

import org.apache.commons.math3.analysis.ParametricUnivariateFunction;

class Logit5pFunction implements ParametricUnivariateFunction {
    @Override
    public double value(double x, double... parameters) {
        double a = parameters[0];
        double b = parameters[1];
        double c = parameters[2];
        double d = parameters[3];
        double m = parameters[4];
        return d + (a - d) / Math.pow(1 + Math.pow(x / c, b), m);
    }

    @Override
    public double[] gradient(double x, double... parameters) {
        // Gradient implementation
        double [] gradients = new double[parameters.length];
        double a = parameters[0];
        double b = parameters[1];
        double c = parameters[2];
        double d = parameters[3];
        double m = parameters[4];


        double u = Math.pow(x / c, b) + 1;
        double v = Math.pow(u, m);

        gradients[0]=1/v;
        gradients[1]=-m*Math.pow(x/c,b)*(a-d)*Math.log(x/c)/(u *v);
        gradients[2]=b*m*Math.pow(x/c,b)*(a-d)/(c* u *v);
        gradients[3]=1-1/v;
        gradients[4]=-(a-d)*Math.log(u)/v;

        return gradients;
    }

}

class Logit4pFunction implements ParametricUnivariateFunction {
    @Override
    public double value(double x, double... parameters) {
        double a = parameters[0];
        double b = parameters[1];
        double c = parameters[2];
        double d = parameters[3];
        return d + (a - d) / (1 + Math.pow(x / c, b));
    }

    @Override
    public double[] gradient(double x, double... parameters) {
        // Gradient implementation
        double [] gradients = new double[parameters.length];
        double a = parameters[0];
        double b = parameters[1];
        double c = parameters[2];
        double d = parameters[3];

        double den = 1 + Math.pow(x / c, b);

        gradients[0] = 1 / den; // 对 a 求导
        gradients[1] = -((a - d) * Math.pow(x / c, b) * Math.log(x / c)) / (den * den); // 对 b 求导
        gradients[2] = (b * Math.pow(x / c, b - 1) * (x / (c * c)) * (a - d)) / (den * den); // 对 c 求导
        gradients[3] = 1 - (1 / den);

        return gradients;
    }
}






