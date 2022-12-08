package dz1;

public class MutableFuzzySet implements IFuzzySet{

    private double[] memberships;
    private IDomain iDomain;

    public MutableFuzzySet(IDomain iDomain) {
        this.iDomain = iDomain;
        int n = iDomain.getCardinality();
        this.memberships = new double[n];
        for (int i=0;i<memberships.length;i++) {
            this.memberships[i] = 0.0000000;
        }
    }

    //postavljanje vrijednosti funkcije pripadnosti
    public MutableFuzzySet set(DomainElement de,double x) {
        int index = this.iDomain.indexOfElement(de);
        double[] temp = this.memberships;
        temp[index] = x;
        this.setMemberships(temp);
        return this;
    }

    public double[] getMemberships() {
        return memberships;
    }

    public void setMemberships(double[] memberships) {
        this.memberships = memberships;
    }

    @Override
    public IDomain getDomain() {
        return this.iDomain;
    }

    @Override
    public double getValueAt(DomainElement de) {
        int index = this.iDomain.indexOfElement(de);
        if (index == -1) {
            return -1;
        }
        else {
            return this.memberships[index];
        }
    }
}
