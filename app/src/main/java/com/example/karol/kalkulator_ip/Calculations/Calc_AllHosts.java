package com.example.karol.kalkulator_ip.Calculations;

public class Calc_AllHosts {
    public String by_cidr_subnetQ(int cidr, long subnetQ) {
        long allHosts = 0;

        if (cidr <= 30) {
            allHosts = 1;
            for (int i = 0; i < 32 - cidr; i++)
                allHosts *= 2;
            allHosts -= 2;
        }

        if (subnetQ > 1)
            allHosts *= subnetQ;

        return Long.toString(allHosts);
    }
}
