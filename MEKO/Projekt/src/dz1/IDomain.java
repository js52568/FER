package dz1;

public interface IDomain extends Iterable<DomainElement>{

    public int getCardinality();

    public DomainElement elementForIndex(int n);

    public int indexOfElement(DomainElement de);

    public IDomain getComponent(int index);

    public int getNumberOfComponents();
}
