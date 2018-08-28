package com.example.karol.kalkulator_ip.ObjectManager;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Address;
import com.example.karol.kalkulator_ip.EditTexts.Addresses.AddressForSubnet;
import com.example.karol.kalkulator_ip.EditTexts.TextBox;
import com.example.karol.kalkulator_ip.EditTexts.Values.GlobalSelected;
import com.example.karol.kalkulator_ip.EditTexts.Values.ValueCidr;
import com.example.karol.kalkulator_ip.EditTexts.Values.ValueClass;
import com.example.karol.kalkulator_ip.EditTexts.Values.ValueDigit;

public class Objects
{
    public Address ip = null;
    public Address mask = null;

    public AddressForSubnet subnet = null;
    public AddressForSubnet broadcast = null;
    public AddressForSubnet firstHost = null;
    public AddressForSubnet lastHost = null;

    public ValueCidr cidr = null;
    public ValueClass ipClass = null;

    public TextBox subnetsQ = null;
    public ValueDigit allHosts = null;
    public ValueDigit hostsInSubnet = null;

    public GlobalSelected globalSelSubnet = null;
    public ValueDigit selSubnet = null;
    public ValueDigit selBroadcast = null;
    public ValueDigit selFirstH = null;
    public ValueDigit selLastH = null;

    public TextBox[] obj = new TextBox[16];
}
