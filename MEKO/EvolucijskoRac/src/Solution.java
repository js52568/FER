import java.util.Arrays;

public class Solution {
    double[] params;
    double mse;

    public Solution(double[] params, double mse) {
        this.params = params;
        this.mse = mse;
    }

    public Solution() {
    }

    public double[] getParams() {
        return params;
    }

    public void setParams(double[] params) {
        this.params = params;
    }

    public double getMse() {
        return mse;
    }

    public void setMse(double mse) {
        this.mse = mse;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "params=" + Arrays.toString(params) +
                ", mse=" + mse +
                '}';
    }
}
