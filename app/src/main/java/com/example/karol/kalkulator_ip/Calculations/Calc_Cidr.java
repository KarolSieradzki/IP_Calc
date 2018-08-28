package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;

public class Calc_Cidr {
    public String by_mask(Octet[] mask) {
        int cidr = 0;

        for (int i = 0; i < 4; i++) {
            if (Integer.parseInt(mask[i].getDec()) == 255)
                cidr += 8;
            else {
                for (int j = 0; j < mask[i].getBin().length(); j++) {
                    if (mask[i].getBin().charAt(j) == '1')
                        cidr++;
                }
                break;
            }
        }

        return Integer.toString(cidr);
    }
}
