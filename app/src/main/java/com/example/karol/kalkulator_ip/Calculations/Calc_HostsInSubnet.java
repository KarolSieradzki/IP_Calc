package com.example.karol.kalkulator_ip.Calculations;

public class Calc_HostsInSubnet {
    public String by_cidr(int cidr) {
        int hostsQ = 0;

        if (cidr <= 30) {
            hostsQ = 1;

            for (int i = 0; i < 32 - cidr; i++)
                hostsQ *= 2;

            hostsQ -= 2;
        }

        return Integer.toString(hostsQ);
    }
}
