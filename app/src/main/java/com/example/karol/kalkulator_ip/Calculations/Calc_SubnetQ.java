package com.example.karol.kalkulator_ip.Calculations;

public class Calc_SubnetQ {
    public String by_mask_class(int cidr, String ipClass) {
        long subnetQ = 0;

        switch (ipClass) {
            case "A": {
                int bitsForSubnet = cidr - 8;
                if (bitsForSubnet > 16) {
                    return "0";
                }
                subnetQ = (long) Math.pow(2, bitsForSubnet);
            }
            break;
            case "B": {
                int bitsForSubnet = cidr - 16;
                if (bitsForSubnet > 8) {
                    return "0";
                }
                subnetQ = (long) Math.pow(2, bitsForSubnet);
            }
            break;
            case "C": {
                int bitsForSubnet = cidr - 24;
                if (bitsForSubnet > 8) {
                    return "0";
                }
                subnetQ = (long) Math.pow(2, bitsForSubnet);
            }
            break;
        }

        return Long.toString(subnetQ);
    }
}
