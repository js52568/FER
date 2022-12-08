package dz1;

import java.util.Iterator;

public class Domain implements IDomain{

    public Domain() {
    }

    public static IDomain intRange(int n1, int n2){
        IDomain id = new SimpleDomain(n1,n2);
        return id;
    }

    public static Domain combine(IDomain d1,IDomain d2){
        //krivo broji, mora biti broj simple a ne kardinaliteti
        int size = d1.getNumberOfComponents() + d2.getNumberOfComponents();
        SimpleDomain components[] = new SimpleDomain[size];
        for (int i = 0; i < d1.getNumberOfComponents(); i++) {
            components[i] = (SimpleDomain) d1.getComponent(i);
        }
        for (int i = d1.getNumberOfComponents(); i < d1.getNumberOfComponents() + d2.getNumberOfComponents(); i++) {
            components[i] = (SimpleDomain) d2.getComponent(i);
        }
        Domain d = new CompositeDomain(components);
        return d;
    }

    @Override
    public int getCardinality() {
        return 0;
    }

    @Override
    public DomainElement elementForIndex(int n) {
        int curr = 0;
        for (DomainElement e: this) {
            if (n == curr) {
                return e;
            }
            curr++;
        }
        return null;
    }

    @Override
    public int indexOfElement(DomainElement de) {
        int curr = 0;
        int index = -1;
        for (DomainElement e: this) {
            if (de.equals(e)) {
                index = curr;
            }
            curr++;
        }
        //what
        return index;
    }

    @Override
    public IDomain getComponent(int index) {
        return null;
    }

    @Override
    public int getNumberOfComponents() {
        return 0;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        return null;
    }
}
