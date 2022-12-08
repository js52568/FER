package dz1;

public class Debug {
    public static void print(IDomain domain, String headingText) {
        if(headingText!=null) {
            System.out.println(headingText);
        }
        for(DomainElement e : domain) {
            System.out.println("Element domene: " + e);
        }
        System.out.println("Kardinalitet domene je: " + domain.getCardinality());
        System.out.println();
    }

    //to do
    public static void print(IFuzzySet set, String headingText) {
        if(headingText!=null) {
            System.out.println(headingText);
        }
        if (set instanceof MutableFuzzySet) {
            MutableFuzzySet set1 = (MutableFuzzySet) set;

            IDomain domain = set1.getDomain();
            double[] mems = set1.getMemberships();
            for (int i = 0; i < set1.getMemberships().length; i++) {
                String str = "d(";
                DomainElement el = domain.elementForIndex(i);
                str += el + ")=";
                double mem = mems[i];
                str += String.format("%.6f", mem);
                System.out.println(str);
            }
        } else if (set instanceof CalculatedFuzzySet) {
            CalculatedFuzzySet set1 = (CalculatedFuzzySet) set;
            IDomain domain = set1.getDomain();
            for (DomainElement e: domain) {
                double value = set1.getValueAt(e);
                String str = "d(" + e + ")=" + value;
                System.out.println(str);
            }

        } else {
            System.out.println("Error");
        }
        System.out.println();

    }

}
