package dz1;

public class CalculatedFuzzySet implements IFuzzySet{

    private IDomain iDomain;
    private  IIntUnaryFunction iIntUnaryFunction;

    public CalculatedFuzzySet(IDomain iDomain, IIntUnaryFunction iIntUnaryFunction) {
        this.iDomain = iDomain;
        this.iIntUnaryFunction = iIntUnaryFunction;
    }

    @Override
    public IDomain getDomain() {
        return this.iDomain;
    }

    @Override
    public double getValueAt(DomainElement de) {
        int index = iDomain.indexOfElement(de);
        double value = iIntUnaryFunction.valueAt(index);
        return value;
    }
}
