package dz2;

import dz1.*;

import java.util.Arrays;

public class Relations {

    public Relations() {
    }

    public static boolean isUtimesURelation(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        boolean ret = true;
        IDomain d1 = domain.getComponent(0);
        IDomain d2 = domain.getComponent(1);
        IDomain testDomain = Domain.combine(d1,d2);
        for (int i=0;i<domain.getCardinality();i++) {
            if (!domain.elementForIndex(i).equals(testDomain.elementForIndex(i))) ret = false;
        }
        return ret;
    }

    public static boolean isSymmetric(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        boolean ret = true;
        int d1 = domain.getComponent(0).getNumberOfComponents();
        //int d2 = domain.getComponent(0).getNumberOfComponents();
        for (int i=0;i< domain.getCardinality();i++){
            int[] v1 = Arrays.copyOfRange(domain.elementForIndex(i).getValues(), 0, d1);
            int[] v2 = Arrays.copyOfRange(domain.elementForIndex(i).getValues(), d1, domain.elementForIndex(i).getValues().length);
            double value = relation.getValueAt(domain.elementForIndex(i));
            int[] result = Arrays.copyOf(v2, v1.length + v2.length);
            System.arraycopy(v1, 0, result, v1.length, v2.length);
            DomainElement other = DomainElement.of(result);
            double otherValue = relation.getValueAt(other);
            if (value != otherValue) ret = false;
        }
        return ret;
    }

    public static boolean isReflexive(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        boolean ret = true;
        int d1 = domain.getComponent(0).getNumberOfComponents();
        //int d2 = domain.getComponent(0).getNumberOfComponents();
        for (int i=0;i< domain.getCardinality();i++){
            int[] v1 = Arrays.copyOfRange(domain.elementForIndex(i).getValues(), 0, d1);
            int[] v2 = Arrays.copyOfRange(domain.elementForIndex(i).getValues(), d1, domain.elementForIndex(i).getValues().length);
            double value = relation.getValueAt(domain.elementForIndex(i));
            if (Arrays.equals(v1,v2) && value != 1) ret = false;
        }
        return ret;
    }

    public static boolean isMaxMinTransitive(IFuzzySet relation) {
        IDomain domain = relation.getDomain();
        for (DomainElement x:domain.getComponent(0)) {
            for (DomainElement z:domain.getComponent(0)) {
                int index = 0;
                double[] mins = new double[domain.getComponent(0).getCardinality()];
                double max = 0;
                for (DomainElement y: domain.getComponent(0)) {
                    int[] xy = Arrays.copyOf(x.getValues(), x.getValues().length + y.getValues().length);
                    System.arraycopy(y.getValues(), 0, xy, x.getValues().length, y.getValues().length);
                    int[] yz = Arrays.copyOf(y.getValues(), y.getValues().length + z.getValues().length);
                    System.arraycopy(z.getValues(), 0, yz, y.getValues().length, z.getValues().length);
                    double xyV = relation.getValueAt(DomainElement.of(xy));
                    double yzV = relation.getValueAt(DomainElement.of(yz));
                    mins[index] = Math.min(xyV,yzV);
                    if (Math.min(xyV,yzV) > max) max = Math.min(xyV,yzV);
                    index++;
                }
                int[] xz = Arrays.copyOf(x.getValues(), x.getValues().length + z.getValues().length);
                System.arraycopy(z.getValues(), 0, xz, x.getValues().length, z.getValues().length);
                double xzV = relation.getValueAt(DomainElement.of(xz));
                if (xzV < max) return false;
            }
        }

        return true;
    }

    public static IFuzzySet compositionOfBinaryRelations(IFuzzySet r1, IFuzzySet r2) {
        IDomain domainA = r1.getDomain();
        IDomain domainB = r2.getDomain();
        MutableFuzzySet r3 = new MutableFuzzySet(Domain.combine(domainA.getComponent(0),domainB.getComponent(1)));
        for (DomainElement x:domainA.getComponent(0)) {
            for (DomainElement z: domainB.getComponent(1)) {
                int index = 0;
                double[] mins = new double[domainA.getComponent(0).getCardinality()];
                double max = 0;
                for (DomainElement y: domainA.getComponent(0)) {
                    int[] xy = Arrays.copyOf(x.getValues(), x.getValues().length + y.getValues().length);
                    System.arraycopy(y.getValues(), 0, xy, x.getValues().length, y.getValues().length);
                    int[] yz = Arrays.copyOf(y.getValues(), y.getValues().length + z.getValues().length);
                    System.arraycopy(z.getValues(), 0, yz, y.getValues().length, z.getValues().length);
                    double xyV = 0;
                    if (r1.getValueAt(DomainElement.of(xy)) != -1) xyV = r1.getValueAt(DomainElement.of(xy));
                    double yzV = 0;
                    if (r2.getValueAt(DomainElement.of(yz)) != -1) yzV = r2.getValueAt(DomainElement.of(yz));
                    mins[index] = Math.min(xyV,yzV);
                    if (Math.min(xyV,yzV) > max) max = Math.min(xyV,yzV);
                    index++;
                }
                int[] xz = Arrays.copyOf(x.getValues(), x.getValues().length + z.getValues().length);
                System.arraycopy(z.getValues(), 0, xz, x.getValues().length, z.getValues().length);
                int pos = r3.getDomain().indexOfElement(DomainElement.of(xz));
                double[] helper = r3.getMemberships();
                helper[pos] = max;
                r3.setMemberships(helper);
            }
        }
        return (IFuzzySet) r3;
    }

    public static boolean isFuzzyEquivalence(IFuzzySet r2) {
        if (isSymmetric(r2)==true && isReflexive(r2)==true && isMaxMinTransitive(r2)==true) {
            return true;
        } else {
            return false;
        }
    }
}
