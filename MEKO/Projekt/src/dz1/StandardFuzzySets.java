package dz1;

public class StandardFuzzySets {

    public StandardFuzzySets() {
    }

    //to do
    public static IIntUnaryFunction lFunction(int alfa,int beta) {
        IIntUnaryFunction f = x -> {
            double ret = -1;
            if (x < alfa) {
                ret = 1;
            } else if (x >= alfa && x < beta) {
                ret = ((double) (beta-x)) / ((double) (beta - alfa));
            } else if (x>=beta) {
                ret = 0;
            }
            return ret;
        };
        return f;
    }
    //to do
    public static IIntUnaryFunction gammaFunction(int alfa,int beta) {
        IIntUnaryFunction f = x -> {
            double ret = -1;
            if (x < alfa) {
                ret = 0;
            } else if (x >= alfa && x < beta) {
                ret = ((double) (x-alfa)) / ((double) (beta - alfa));
            } else if (x>=beta) {
                ret = 1;
            }
            return ret;
        };
        return f;
    }
    //to do
    public static IIntUnaryFunction lambdaFunction(int alfa,int beta,int gama) {
        IIntUnaryFunction f = x -> {
            double ret = -1;
            if (x < alfa) {
                ret = 0;
            } else if (x >= alfa && x < beta) {
                ret = ((double)(x-alfa))/((double)(beta-alfa));
            } else if (x >= beta && x < gama) {
                ret = ((double)(gama - x))/((double)(gama-beta));
            } else if (x>=gama) {
                ret = 0;
            }
            return ret;
        };
        return f;
    }
}
