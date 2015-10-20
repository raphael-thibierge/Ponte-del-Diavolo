package IA;

/**
 * Created by raph on 20/10/15.
 */
public class MutableInteger {
    Integer value;

    public MutableInteger(int integer){
        this.value = integer;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
