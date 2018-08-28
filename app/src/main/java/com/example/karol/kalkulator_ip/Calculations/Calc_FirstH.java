package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;

public class Calc_FirstH {
    public Octet[] by_subnetAdrForSelected(Octet[] subnet) {
        int oct = Integer.parseInt(subnet[3].getDec());
        oct++;

        subnet[3].set(Integer.toString(oct), Octet.NumerationSystem.DECIMAL);

        return subnet;
    }
}
