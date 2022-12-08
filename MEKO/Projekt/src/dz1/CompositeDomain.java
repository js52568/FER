package dz1;

import java.util.Iterator;

public class CompositeDomain extends Domain{

    private SimpleDomain[] simpleDomains;

    public CompositeDomain(SimpleDomain[] simpleDomains) {
        this.simpleDomains = simpleDomains;
    }

    @Override
    public int getCardinality() {
        int card = 1;
        for (SimpleDomain sd: simpleDomains) {
            card = card * sd.getCardinality();
        }
        return card;
    }

    @Override
    public IDomain getComponent(int index) {
        IDomain idom = null;
        for (int i = 0; i < simpleDomains.length; i++) {
            if (i == index){
                idom = simpleDomains[index];
            }
        }
        return idom;
    }

    @Override
    public int getNumberOfComponents() {
        return simpleDomains.length;
    }

    public DomainElement[] combineAll() {
        //inicijalizirati velicinu
        int size = 1;
        for (int i=0; i< simpleDomains.length;i++) {
            size = size*simpleDomains[i].getCardinality();
        }
        DomainElement[] newElements = new DomainElement[size];
        int index = 0;
        for (int i=simpleDomains[0].getFirst(); i<simpleDomains[0].getSecond();i++) {
            newElements[index] = new DomainElement(new int[]{i});
            index++;
        }
        int start = 0;
        //stavio sam end na 5 i sad ih samo 5 popuni a treba bit 15
        int end = index*simpleDomains[1].getCardinality();
        for (int i=1;i<simpleDomains.length;i++) {
            newElements = combineTwo(newElements, simpleDomains[i],start,index);
            start = end+1;
            if (i+1< simpleDomains.length) end *= simpleDomains[i+1].getCardinality();
        }
        return newElements;
    }

    public DomainElement[] combineSimple(SimpleDomain d1,SimpleDomain d2) {
        DomainElement[] newElements = {};
        int index = 0;
        for (int i=d1.getFirst(); i<d1.getSecond();i++){
            for (int j=d2.getFirst(); j<d2.getSecond();j++){
              DomainElement element = new DomainElement(new int[]{i, j});
              newElements[index] = element;
              index++;
            }
        }
        return newElements;
    }

    public DomainElement[] combineTwo(DomainElement[] d1, SimpleDomain sd,int start,int index) {
        //size
        int counter = 0;
        DomainElement[] newElements = new DomainElement[d1.length+sd.getCardinality()];
        for (int i=start; i<index;i++) {
            for (int j=sd.getFirst(); j<sd.getSecond();j++) {
                int pos = d1[i].getValues().length;
                int[] newValues = new int[pos+1];
                for (int k=0;k<d1[i].getValues().length;k++) {
                    newValues[k] = d1[i].getValues()[k];
                }
                //newValues = d1[i].getValues();
                //problem
                newValues[pos] = j;
                DomainElement de = new DomainElement(newValues);
                newElements[counter] = de;
                counter++;
            }
        }
        return newElements;
    }

    @Override
    public Iterator<DomainElement> iterator() {

        DomainElement[] allElements = combineAll();

        Iterator<DomainElement> it = new Iterator<DomainElement>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < allElements.length && allElements[currentIndex]!=null;
            }

            @Override
            public DomainElement next() {
                DomainElement de = allElements[currentIndex];
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
}
