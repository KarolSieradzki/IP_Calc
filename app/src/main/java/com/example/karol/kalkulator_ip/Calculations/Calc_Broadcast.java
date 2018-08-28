package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;

public class Calc_Broadcast {
    public Octet[] by_subnetAdrForSelected_cidr(int cidr, Octet[] subnetAdr) {
        Octet[] broadcast = new Octet[4];
        for (int i = 0; i < 4; i++)
            broadcast[i] = new Octet();

        StringBuilder[] br = new StringBuilder[4];
        for (int i = 0; i < 4; i++)
            br[i] = new StringBuilder(subnetAdr[i].getBin());

        int bitNum = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                bitNum++;
                if (bitNum > cidr)
                    br[i].setCharAt(j, '1');
            }
        }

        for (int i = 0; i < 4; i++)
            broadcast[i].set(br[i].toString(), Octet.NumerationSystem.BINARY);

        return broadcast;
    }
}
