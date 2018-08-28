package com.example.karol.kalkulator_ip.Calculations;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;

public class Calc_LastH
{
    public Octet[] by_BroadcastAdrForSelected(Octet[] broadcast)
    {
        int oct = Integer.parseInt(broadcast[3].getDec());
        oct--;

        broadcast[3].set(Integer.toString(oct), Octet.NumerationSystem.DECIMAL);

        return broadcast;
    }
}
