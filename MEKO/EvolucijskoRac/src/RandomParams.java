import java.util.Random;

public class RandomParams {

    double[] params;

    public RandomParams() {
        this.params = getParams();
    }

    public static double[] getParams(){
        double[] params = new double[5];
        for (int i=0;i<5;i++) {
            Random r = new Random();
            double randomValue = -4 + (4 + 4) * r.nextDouble();
            params[i] = randomValue;
        }
        return params;
    }


}
