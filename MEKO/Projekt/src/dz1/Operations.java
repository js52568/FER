package dz1;

public class Operations {

    public Operations() {
    }

    //to do
    public static IFuzzySet unaryOperation(IFuzzySet fs,IUnaryFunction unaryFunction) {
        IDomain domain = fs.getDomain();
        double[] newMems = new double[domain.getCardinality()];
        MutableFuzzySet newSet = new MutableFuzzySet(domain);
        for (int i=0;i<domain.getCardinality();i++) {
            newMems[i] = unaryFunction.valueAt(fs.getValueAt(domain.elementForIndex(i)));
        }
        newSet.setMemberships(newMems);
        return (IFuzzySet) newSet;
    }

    //to do
    public static IFuzzySet binaryOperation(IFuzzySet fs1,IFuzzySet fs2,IBinaryFunction binaryFunction) {
        IDomain d1 = fs1.getDomain();
        IDomain d2 = fs2.getDomain();
        if (d1.getCardinality() != d2.getCardinality()) throw new UnsupportedOperationException("Domains do not have the same cardinality!");
        double[] newMems = new double[d1.getCardinality()];
        MutableFuzzySet newSet = new MutableFuzzySet(d1);
        for (int i=0;i<d1.getCardinality();i++) {
            newMems[i] = binaryFunction.valueAt(fs1.getValueAt(d1.elementForIndex(i)),fs2.getValueAt(d2.elementForIndex(i)));
        }
        newSet.setMemberships(newMems);
        return (IFuzzySet) newSet;
    }

    //to do
    public static IUnaryFunction zadehNot() {
        IUnaryFunction uf = x -> 1-x;
        return uf;
    }

    //to do
    public static IBinaryFunction zadehAnd() {
        IBinaryFunction bf = (x, y) -> Math.min(x,y);
        return bf;
    }

    //to do
    public static IBinaryFunction zadehOr() {
        IBinaryFunction bf = (x, y) -> Math.max(x,y);
        return bf;
    }

    //to do
    public static IBinaryFunction hamacherTNorm(double x) {
        IBinaryFunction bf = (a, b) -> ((double)(a*b))/((double)(x+(1-x)*(a+b-a*b)));
        return bf;
    }

    //to do
    public static IBinaryFunction hamacherSNorm(double x) {
        IBinaryFunction bf = (a, b) -> ((double)(a+b-(2-x)*a*b))/((double)(1-(1-x)*a*b));
        return bf;
    }
}
