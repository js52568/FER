package dz1;

import java.util.Arrays;

public class DomainElement {

    private int[] values;

    public DomainElement(int[] values) {
        this.values = values;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        //radi li ovo
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainElement that = (DomainElement) o;
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        String str = "";
        if (values.length == 1) {
            str = Integer.toString(values[0]);
        } else {
            str = "(";
            for (int i=0; i<values.length;i++) {
                if (i==values.length-1){
                    str += Integer.toString(values[i]) + ")";
                } else {
                    str += Integer.toString(values[i]) + ",";
                }
            }
        }
        //provjeriti radi li, treba namjestiti za vise values
        return str;
        //return Arrays.toString(values);
    }

    public int getNumberOfComponents() {
        return this.values.length;
    }

    public int getComponentValue(int index) {
        return this.values[index];
    }

    public static DomainElement of(int... values){
        DomainElement de = new DomainElement(values);
        return de;
    }
}
