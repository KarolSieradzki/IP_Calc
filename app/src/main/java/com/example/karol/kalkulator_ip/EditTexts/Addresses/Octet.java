package com.example.karol.kalkulator_ip.EditTexts.Addresses;

public class Octet {
    public String value;

    public enum NumerationSystem {BINARY, DECIMAL}

    public NumerationSystem numSys;

    public Octet() {
        numSys = NumerationSystem.DECIMAL;
        this.value = "";
    }

    public void set(String o, NumerationSystem n) {
        value = o;
        numSys = n;
    }

    public void clear() {
        numSys = NumerationSystem.DECIMAL;
        value = "";
    }

    public String getValue() {
        return value;
    }

    public String getDec() {
        if (numSys == NumerationSystem.DECIMAL)
            return value;
        else
            return toDecimal(value);
    }

    public String getBin() {
        if (numSys == NumerationSystem.BINARY)
            return value;
        else
            return toBinary(value);
    }

    private String toBinary(String v) {
        int dec = Integer.parseInt(v);

        StringBuilder bin = new StringBuilder(Integer.toBinaryString(dec));

        bin = saveAt8bits(bin);

        return bin.toString();
    }

    private StringBuilder saveAt8bits(StringBuilder v){
        while (v.length() < 8)
            v.insert(0, "0");

        return v;
    }

    private String toDecimal(String v) {
        int dec = Integer.parseInt(v, 2);

        return Integer.toString(dec);
    }
}
