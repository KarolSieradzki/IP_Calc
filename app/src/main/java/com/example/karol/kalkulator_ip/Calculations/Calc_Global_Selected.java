package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;

public class Calc_Global_Selected {
    public String by_ip_class_cidr(Octet[] ip, String ipClass, int cidr) {
        String actSubnet = "";

        switch (ipClass) {
            case "A": {
                if (cidr > 8) {
                    String subnetOctets;
                    subnetOctets = ip[1].getBin();
                    subnetOctets = subnetOctets + ip[2].getBin();

                    int bitsForSubnetNmbr = cidr - 8;

                    actSubnet = subnetOctets.substring(0, bitsForSubnetNmbr);
                    int as = Integer.parseInt(actSubnet, 2);
                    as++;
                    actSubnet = Integer.toBinaryString(as);
                } else
                    actSubnet = "01";
            }
            break;
            case "B": {
                if (cidr > 16) {
                    int bitsForSubnetNmbr = cidr - 16;

                    actSubnet = ip[2].getBin().substring(0, bitsForSubnetNmbr);
                    int as = Integer.parseInt(actSubnet, 2);
                    as++;
                    actSubnet = Integer.toBinaryString(as);
                } else
                    actSubnet = "01";
            }
            break;
            case "C": {
                if (cidr > 24) {
                    int bitsForSubnetNmbr = cidr - 24;

                    actSubnet = ip[3].getBin().substring(0, bitsForSubnetNmbr);
                    int as = Integer.parseInt(actSubnet, 2);
                    as++;
                    actSubnet = Integer.toBinaryString(as);
                } else
                    actSubnet = "01";
            }
            break;
        }

        if (!actSubnet.equals(""))
            return Integer.toString(Integer.parseInt(actSubnet, 2));
        else return "";
    }
}