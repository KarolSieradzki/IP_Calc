package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;
import com.example.karol.kalkulator_ip.Enums.NumSys;

public class Calc_SubnetAddr {
    public Octet[] by_mask_ip_class(int cidr, Octet[] ip, String ipClass, long selected, NumSys numSys) {
        Octet[] subnet = new Octet[4];
        for (int i = 0; i < 4; i++)
            subnet[i] = new Octet();

        switch (ipClass) {
            case "A": {
                subnet[0] = ip[0];
                subnet[3].set("0", Octet.NumerationSystem.DECIMAL);

                if (cidr >= 8 && cidr <= 24) {
                    int bitsForSubnet = cidr - 8;
                    StringBuilder subAdr = new StringBuilder("0000000000000000");//16bit for subnet
                    String selBin = Long.toBinaryString(selected - 1);

                    if (bitsForSubnet > 0) {
                        while (selBin.length() < bitsForSubnet)
                            selBin = "0" + selBin;

                        subAdr.delete(0, bitsForSubnet);
                        subAdr.insert(0, selBin);
                    }
                    subnet[1].set(subAdr.substring(0, 8), Octet.NumerationSystem.BINARY);
                    subnet[2].set(subAdr.substring(8), Octet.NumerationSystem.BINARY);
                }
            }
            break;
            case "B": {
                subnet[0] = ip[0];
                subnet[1] = ip[1];
                subnet[3].set("0", Octet.NumerationSystem.DECIMAL);

                if (cidr >= 16 && cidr <= 24) {
                    int bitsForSubnet = cidr - 16;

                    StringBuilder subAdr = new StringBuilder("00000000");//8bit for subnet
                    String selBin = Long.toBinaryString(selected - 1);

                    if (bitsForSubnet > 0) {
                        while (selBin.length() < bitsForSubnet)
                            selBin = "0" + selBin;

                        subAdr.delete(0, bitsForSubnet);
                        subAdr.insert(0, selBin);
                    }
                    subnet[2].set(subAdr.toString(), Octet.NumerationSystem.BINARY);
                }
            }
            break;
            case "C": {
                subnet[0] = ip[0];
                subnet[1] = ip[1];
                subnet[2] = ip[2];

                if (cidr >= 24 && cidr <= 32) {
                    int bitsForSubnet = cidr - 24;

                    StringBuilder subAdr = new StringBuilder("00000000");//8bit for subnet
                    String selBin = Long.toBinaryString(selected - 1);

                    if (bitsForSubnet > 0) {
                        while (selBin.length() < bitsForSubnet)
                            selBin = "0" + selBin;

                        subAdr.delete(0, bitsForSubnet);
                        subAdr.insert(0, selBin);
                    }
                    subnet[3].set(subAdr.toString(), Octet.NumerationSystem.BINARY);
                }
            }
            break;
        }

        return subnet;
    }
}
