package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;

public class Calc_Class {
    public String by_ip(String octet) {
        int o = Integer.parseInt(octet);

        if (o >= 0 && o <= 127)
            return "A";
        else if (o >= 128 && o <= 191)
            return "B";
        else if (o >= 192 && o <= 223)
            return "C";
        else if (o >= 224 && o <= 239)
            return "D";
        else if (o >= 240 && o <= 255)
            return "E";

        return null;
    }

    public String by_mask(Octet[] mask) {
        String[] clss = new String[3];
        clss[0] = "A";
        clss[1] = "B";
        clss[2] = "C";

        String ipClass = clss[0];

        for (int i = 2; i >= 0; i--) {
            if (Integer.parseInt(mask[i].getDec()) == 255) {
                ipClass = clss[i];
                break;
            }
        }

        return ipClass;
    }
}
