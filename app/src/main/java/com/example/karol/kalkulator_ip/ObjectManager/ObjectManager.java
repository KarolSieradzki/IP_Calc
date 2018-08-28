package com.example.karol.kalkulator_ip.ObjectManager;

import com.example.karol.kalkulator_ip.Calculations.Calc;
import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;
import com.example.karol.kalkulator_ip.Enums.Id;
import com.example.karol.kalkulator_ip.Enums.NumSys;
import com.example.karol.kalkulator_ip.Enums.Status;

import java.util.Vector;

public class ObjectManager extends Objects {
    private Calc calc = new Calc();
    private Vector<Id> knownValues = new Vector<>();

    public void calculate() {
        updateIds();
        clear();

        for (int i = 0; i < 3; i++) {
            try_calc_mask();
            try_calc_cidr();
            try_calc_class();
            try_calc_subnetQ();
            try_calc_hostsInSubnet();
            try_calc_allHosts();
            try_calc_subnetAddr();
            try_calc_broadcast();
            try_calc_first_host();
            try_calc_last_host();
            try_calc_globalSel();
        }

        checkStatus();
    }

    private void try_calc_globalSel() {

        if (find(Id.IP) && find(Id.CLASS) && find(Id.CIDR)) {
            String val = calc.global_selected.by_ip_class_cidr
                    (ip.oct, ipClass.value, Integer.parseInt(cidr.value));

            if (!val.equals("")) {
                if (!globalSelSubnet.actualSubnet.equals(val) &&
                        !val.equals(globalSelSubnet.value)) {
                    globalSelSubnet.editText.setText(val);
                    globalSelSubnet.status = Status.S_CALCULATED;
                    knownValues.add(globalSelSubnet.id);
                    globalSelSubnet.actualSubnet = val;
                }
            }
        }
    }

    private void try_calc_mask() {
        if (mask.status == Status.EMPTY) {
            Octet[] oct = new Octet[4];
            for (int i = 0; i < 4; i++)
                oct[i] = new Octet();

            if (find(Id.CIDR))
                oct = calc.mask.by_cidr(Integer.parseInt(cidr.value), mask.numSys);

            else if (find(Id.IP) && find(Id.SUBNET))
                oct = calc.mask.by_ip_netAddr(ip.oct, subnet.oct, mask.numSys);

            else if (find(Id.CLASS) && find(Id.SUBNET_Q))
                oct = calc.mask.by_class_subnetsQ
                        (ipClass.value, Long.parseLong(subnetsQ.value), mask.numSys);

            else if (find(Id.CLASS))
                oct = calc.mask.by_class(ipClass.value, mask.numSys);


            if (checkOctet(oct)) {
                if (mask.numSys == NumSys.BINARY) {
                    for (int i = 0; i < 4; i++)
                        mask.oct[i].set(oct[i].getBin(), Octet.NumerationSystem.BINARY);
                } else {
                    for (int i = 0; i < 4; i++)
                        mask.oct[i].set(oct[i].getDec(), Octet.NumerationSystem.DECIMAL);
                }

                mask.status = Status.CALCULATED;
                mask.display();
                knownValues.add(mask.id);
            }
        }
    }

    private void try_calc_cidr() {
        if (cidr.status == Status.EMPTY) {
            if (find(Id.MASK)) {
                cidr.value = calc.cidr.by_mask(mask.oct);
                cidr.editText.setText(cidr.value);
                cidr.status = Status.CALCULATED;
                knownValues.add(cidr.id);
            }
        }
    }

    private void try_calc_class() {
        if (ipClass.status == Status.EMPTY) {
            String val = "";

            if (find(Id.IP))
                val = calc.ipclass.by_ip(ip.oct[0].getDec());

            else if (find(Id.MASK))
                val = calc.ipclass.by_mask(mask.oct);


            if (!val.equals("")) {
                ipClass.value = val;
                ipClass.editText.setText(val);
                ipClass.status = Status.CALCULATED;
                knownValues.add(ipClass.id);
            }
        }
    }

    private void try_calc_subnetQ() {
        if (subnetsQ.status == Status.EMPTY) {
            if (find(Id.MASK)) {
                subnetsQ.value = calc.subnetQ.by_mask_class
                        (Integer.parseInt(cidr.value), ipClass.value);

                subnetsQ.editText.setText(subnetsQ.value);
                subnetsQ.status = Status.CALCULATED;
                knownValues.add(subnetsQ.id);
            }
        }
    }

    private void try_calc_hostsInSubnet() {
        if (hostsInSubnet.status == Status.EMPTY) {
            if (find(Id.CIDR)) {
                hostsInSubnet.value = calc.hostsInSubnet.by_cidr(Integer.parseInt(cidr.value));
                hostsInSubnet.editText.setText(hostsInSubnet.value);
                hostsInSubnet.status = Status.CALCULATED;
                knownValues.add(hostsInSubnet.id);
            }
        }
    }

    private void try_calc_allHosts() {
        if (allHosts.status == Status.EMPTY) {
            String val = "";

            if (find(Id.CIDR) && find(Id.SUBNET_Q))
                val = calc.allHosts.by_cidr_subnetQ
                        (Integer.parseInt(cidr.value), Long.parseLong(subnetsQ.value));


            if (!val.equals("")) {
                allHosts.value = val;
                allHosts.editText.setText(val);
                allHosts.status = Status.CALCULATED;
                knownValues.add(allHosts.id);
            }
        }
    }

    private void try_calc_subnetAddr() {
        if (subnet.status == Status.EMPTY) {
            Octet[] oct = new Octet[4];
            for (int i = 0; i < 4; i++)
                oct[i] = new Octet();

            if (find(Id.MASK) && find(Id.S_SUBNET) && find(Id.CLASS)) {
                oct = calc.subnetAddr.by_mask_ip_class
                        (Integer.parseInt(cidr.value), ip.oct, ipClass.value,
                                Integer.parseInt(selSubnet.value), subnet.numSys);
            }

            if (checkOctet(oct)) {
                if (subnet.numSys == NumSys.BINARY) {
                    for (int i = 0; i < 4; i++)
                        subnet.oct[i].set(oct[i].getBin(), Octet.NumerationSystem.BINARY);
                } else {
                    for (int i = 0; i < 4; i++)
                        subnet.oct[i].set(oct[i].getDec(), Octet.NumerationSystem.DECIMAL);
                }

                subnet.status = Status.CALCULATED;
                subnet.display();
                knownValues.add(subnet.id);
            }
        }
    }

    private void try_calc_broadcast() {
        if (broadcast.status == Status.EMPTY) {
            Octet[] oct = new Octet[4];
            for (int i = 0; i < 4; i++)
                oct[i] = new Octet();

            if (find(Id.SUBNET) && find(Id.CIDR) && find(Id.S_BROADCAST)
                    && find(Id.CLASS) && find(Id.MASK)) {
                Octet[] sub = new Octet[4];
                for (int i = 0; i < 4; i++)
                    sub[i] = new Octet();

                sub = calc.subnetAddr.by_mask_ip_class
                        (Integer.parseInt(cidr.value), ip.oct, ipClass.value,
                                Integer.parseInt(selBroadcast.value), subnet.numSys);

                if (checkOctet(sub)) {
                    oct = calc.broadcast.by_subnetAdrForSelected_cidr
                            (Integer.parseInt(cidr.value), sub);
                }
            }

            if (checkOctet(oct)) {
                if (broadcast.numSys == NumSys.BINARY) {
                    for (int i = 0; i < 4; i++)
                        broadcast.oct[i].set(oct[i].getBin(), Octet.NumerationSystem.BINARY);
                } else {
                    for (int i = 0; i < 4; i++)
                        broadcast.oct[i].set(oct[i].getDec(), Octet.NumerationSystem.DECIMAL);
                }

                broadcast.status = Status.CALCULATED;
                broadcast.display();
                knownValues.add(broadcast.id);
            }
        }
    }

    private void try_calc_first_host() {
        if (firstHost.status == Status.EMPTY) {
            Octet[] oct = new Octet[4];
            for (int i = 0; i < 4; i++)
                oct[i] = new Octet();

            if (find(Id.SUBNET) && find(Id.CIDR) && find(Id.S_FIRST_H)
                    && find(Id.CLASS) && find(Id.MASK)) {
                Octet[] sub = new Octet[4];

                for (int i = 0; i < 4; i++)
                    sub[i] = new Octet();

                sub = calc.subnetAddr.by_mask_ip_class
                        (Integer.parseInt(cidr.value), ip.oct, ipClass.value,
                                Long.parseLong(selFirstH.value), subnet.numSys);

                if (checkOctet(sub))
                    oct = calc.firstH.by_subnetAdrForSelected(sub);
            }

            if (checkOctet(oct)) {
                if (firstHost.numSys == NumSys.BINARY) {
                    for (int i = 0; i < 4; i++)
                        firstHost.oct[i].set(oct[i].getBin(), Octet.NumerationSystem.BINARY);
                } else {
                    for (int i = 0; i < 4; i++)
                        firstHost.oct[i].set(oct[i].getDec(), Octet.NumerationSystem.DECIMAL);
                }

                firstHost.status = Status.CALCULATED;
                firstHost.display();
                knownValues.add(firstHost.id);
            }
        }
    }

    private void try_calc_last_host() {
        if (lastHost.status == Status.EMPTY) {
            Octet[] oct = new Octet[4];

            for (int i = 0; i < 4; i++)
                oct[i] = new Octet();

            if (find(Id.SUBNET) && find(Id.CIDR) && find(Id.S_LAST_H)
                    && find(Id.CLASS) && find(Id.MASK)) {
                Octet[] sub = new Octet[4];
                Octet[] brdcst = new Octet[4];

                for (int i = 0; i < 4; i++) {
                    sub[i] = new Octet();
                    brdcst[i] = new Octet();
                }

                sub = calc.subnetAddr.by_mask_ip_class
                        (Integer.parseInt(cidr.value), ip.oct, ipClass.value,
                                Long.parseLong(selLastH.value), subnet.numSys);

                brdcst = calc.broadcast.by_subnetAdrForSelected_cidr
                        (Integer.parseInt(cidr.value), sub);

                if (checkOctet(brdcst))
                    oct = calc.lastH.by_BroadcastAdrForSelected(brdcst);
            }

            if (checkOctet(oct)) {
                if (lastHost.numSys == NumSys.BINARY) {
                    for (int i = 0; i < 4; i++)
                        lastHost.oct[i].set(oct[i].getBin(), Octet.NumerationSystem.BINARY);
                } else {
                    for (int i = 0; i < 4; i++)
                        lastHost.oct[i].set(oct[i].getDec(), Octet.NumerationSystem.DECIMAL);
                }

                lastHost.status = Status.CALCULATED;
                lastHost.display();
                knownValues.add(lastHost.id);
            }
        }
    }

//   ////  //   ////  //   ////  //   ////  //   ////  //   ////  //   ////  //   ////  //   ////  //

    private void updateIds() {
        knownValues.clear();

        for (int i = 0; i < 16; i++) {
            if (obj[i].status == Status.GIVEN || obj[i].status == Status.S_CALCULATED)
                knownValues.add(obj[i].id);
        }
    }

    private void clear() {
        for (int i = 0; i < 16; i++) {
            if (obj[i].status == Status.CALCULATED) {
                obj[i].oct = new Octet[4];
                obj[i].initializeOct();
                obj[i].value = "";
                obj[i].editText.setText("");
                obj[i].status = Status.EMPTY;
            }
        }
    }

    public void clearAllValues() {
        for (int i = 0; i < 16; i++) {
            obj[i].oct = new Octet[4];
            obj[i].initializeOct();
            obj[i].value = "";
            obj[i].editText.setText("");
            obj[i].status = Status.EMPTY;
        }
        checkStatus();
    }

    private boolean find(Id id) {
        for (int i = 0; i < knownValues.size(); i++) {
            if (knownValues.elementAt(i) == id)
                return true;
        }
        return false;
    }

    private boolean checkOctet(Octet[] oct) {
        return !oct[0].getValue().equals("") && !oct[1].getValue().equals("") &&
                !oct[2].getValue().equals("") && !oct[3].getValue().equals("");
    }

    private void checkStatus() {
        for (int i = 0; i < 16; i++) {
            if (obj[i].status == Status.GIVEN)
                obj[i].err.setText("*");
            else
                obj[i].err.setText("");
        }
    }
}
