package dz1;

public class Primjer2 {
    public static void main(String[] args) {
        IDomain d = Domain.intRange(0, 11);
        IFuzzySet set1 = new MutableFuzzySet(d)
                .set(DomainElement.of(0), 1.0)
                .set(DomainElement.of(1), 0.8)
                .set(DomainElement.of(2), 0.6)
                .set(DomainElement.of(3), 0.4)
                .set(DomainElement.of(4), 0.2);
        Debug.print(set1, "Set1:");
        IFuzzySet notSet1 = Operations.unaryOperation(
                set1, Operations.zadehNot());
        Debug.print(notSet1, "notSet1:");
        IFuzzySet union = Operations.binaryOperation(
                set1, notSet1, Operations.zadehOr());
        Debug.print(union, "Set1 union notSet1:");
        IFuzzySet hinters = Operations.binaryOperation(
                set1, notSet1, Operations.hamacherTNorm(1.0));
        Debug.print(hinters, "Set1 intersection with notSet1 using parameterised Hamacher T norm with parameter 1.0:");
        IDomain dd = Domain.intRange(-3,3);
        IFuzzySet set5 = new MutableFuzzySet(dd)
                .set(DomainElement.of(-1), 0.5)
                .set(DomainElement.of(0), 0.7)
                .set(DomainElement.of(1), 0.6);
        IFuzzySet notSet5 = Operations.unaryOperation(
                set5, Operations.zadehNot());
        IFuzzySet newSET = Operations.binaryOperation(set5,notSet5,Operations.zadehAnd());
        Debug.print(newSET,"");

    }
}
