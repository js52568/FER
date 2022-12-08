package dz1;

import java.util.Iterator;

public class SimpleDomain extends Domain{

    private int first;
    private int second;

    public SimpleDomain(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public int getCardinality(){
        int card = this.second - this.first;
        return card;
    }

    @Override
    public IDomain getComponent(int index){
        return this;
    }

    @Override
    public int getNumberOfComponents(){
        return 1;
    }

    @Override
    public Iterator<DomainElement> iterator() {
        Iterator<DomainElement> it = new Iterator<DomainElement>() {

            private int currentIndex = first;

            @Override
            public boolean hasNext() {
                return currentIndex < second;
            }

            @Override
            public DomainElement next() {
                int value[] = {currentIndex};
               DomainElement de = new DomainElement(value) ;
               currentIndex++;
               return de;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }
}
