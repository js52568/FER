import java.lang.Math;
public class Example {
    double x;
    double y;
    double value;
    double beta0;
    double beta1;
    double beta2;
    double beta3;
    double beta4;

    public Example(double x, double y, double value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public double prijenosnaKarakteristika(double x,double y,double beta0,double beta1,double beta2,double beta3,double beta4) {
        double value = Math.sin(beta0+beta1*x) + beta2*Math.cos(x*(beta3+y))*(1/ (1 +Math.exp(Math.pow(x-beta4,2))));
        return value;
    }



}
