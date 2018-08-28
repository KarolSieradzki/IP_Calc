package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;
import com.example.karol.kalkulator_ip.Enums.NumSys;

public class Calc_Mask {
    private Octet[] oct = new Octet[4];

    private void initializeOct() {
        for (int i = 0; i < 4; i++)
            oct[i] = new Octet();
    }

    public Octet[] by_cidr(int cidr, NumSys numSys) {
        initializeOct();

        StringBuilder ss = new StringBuilder("00000000000000000000000000000000");
        for (int i = 0; i < cidr; i++)
            ss.setCharAt(i, '1');

        String s = ss.toString();

        oct[0].set(s.substring(0, 8), Octet.NumerationSystem.BINARY);
        oct[1].set(s.substring(8, 16), Octet.NumerationSystem.BINARY);
        oct[2].set(s.substring(16, 24), Octet.NumerationSystem.BINARY);
        oct[3].set(s.substring(24), Octet.NumerationSystem.BINARY);

        if (numSys == NumSys.DECIMAL) {
            for (int i = 0; i < 4; i++)
                oct[i].set(oct[i].getDec(), Octet.NumerationSystem.DECIMAL);

            return oct;
        }

        return oct;
    }

    public Octet[] by_ip_netAddr(Octet[] ipAddr, Octet[] netAddr, NumSys numSys) {
        int differentOct = 0;

        if (ipAddr.length == netAddr.length) {
            for (int i = 0; i < ipAddr.length; i++) {
                if (!ipAddr[i].getValue().equals(netAddr[i].getValue())) {
                    differentOct = i;
                    break;
                }
            }
        }

        String ipAddrbin = ipAddr[differentOct].getBin();
        int networkNum = Integer.parseInt(netAddr[differentOct].getDec());
        int sum = 0;
        int cidr = 0;

        for (int i = 128, j = 0; j < 8; i /= 2, j++) {
            if (sum == networkNum) {
                cidr = (differentOct * 8) + (j + 1);
                break;
            } else if (ipAddrbin.charAt(j) == '1') {
                sum += i;

                if (sum == networkNum) {
                    cidr = (differentOct * 8) + (j + 1);
                    break;
                }
            }
        }

        return by_cidr(cidr, numSys);
    }

    public Octet[] by_class_subnetsQ(String clss, long subQ, NumSys numSys) {
        int cidr = 0;

        switch (clss) {
            case "A":
                cidr = 8;
                break;
            case "B":
                cidr = 16;
                break;
            case "C":
                cidr = 24;
                break;
        }

        for (int i = 1; i <= 8; i++) {
            if (Math.pow(2, i) == subQ) {
                cidr += i;
                break;
            }
        }

        return by_cidr(cidr, numSys);
    }

    public Octet[] by_class(String clss, NumSys numSys) {
        int cidr = 0;
        switch (clss) {
            case "A":
                cidr = 8;
                break;
            case "B":
                cidr = 16;
                break;
            case "C":
                cidr = 24;
                break;
        }
        return by_cidr(cidr, numSys);
    }
}