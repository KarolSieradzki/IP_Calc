package com.example.karol.kalkulator_ip;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.Enums.Id;
import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;
import com.example.karol.kalkulator_ip.EditTexts.Addresses.Address;
import com.example.karol.kalkulator_ip.EditTexts.Addresses.AddressForSubnet;
import com.example.karol.kalkulator_ip.EditTexts.Values.*;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int widthPixels = getResources().getDisplayMetrics().widthPixels;

        final ObjectManager objectManager = new ObjectManager();

        //ip address
        final Address ip_adr = new Address(
                (EditText) findViewById(R.id.ip_editText),
                (TextView) findViewById(R.id.ip_err),
                (Button) findViewById(R.id.ip_sys),
                widthPixels, objectManager);
        ip_adr.id = Id.IP;

        //mask
        final Address mask_adr = new Address(
                (EditText) findViewById(R.id.mask_editText),
                (TextView) findViewById(R.id.mask_err),
                (Button) findViewById(R.id.mask_btn),
                widthPixels, objectManager);
        mask_adr.id = Id.MASK;

        //class
        final ValueClass ip_class = new ValueClass(
                (EditText) findViewById(R.id.class_editText),
                (TextView) findViewById(R.id.class_err), widthPixels, objectManager);
        ip_class.id = Id.CLASS;

        //cidr
        final ValueCidr cidr = new ValueCidr(
                (EditText) findViewById(R.id.cidr_editText),
                (TextView) findViewById(R.id.cidr_err), widthPixels, objectManager);
        cidr.id = Id.CIDR;

        //subnets quantity
        final ValueDigit subnets_q = new ValueDigit(
                (EditText) findViewById(R.id.subnets_quantity_editText),
                (TextView) findViewById(R.id.subnet_q_err),
                objectManager);
        subnets_q.id = Id.SUBNET_Q;

        //all hosts in network
        final ValueDigit all_hosts = new ValueDigit(
                (EditText) findViewById(R.id.all_hosts_editText),
                (TextView) findViewById(R.id.all_hosts_err),
                objectManager);
        all_hosts.id = Id.ALL_HOSTS;

        //hosts quantity in every subnet
        final ValueDigit hosts_q = new ValueDigit(
                (EditText) findViewById(R.id.hosts_in_subnet_editText),
                (TextView) findViewById(R.id.hosts_in_subn_err),
                objectManager);
        hosts_q.id = Id.HOSTS_IN_SUBNET;

        //subnet
        final AddressForSubnet subnet_adr = new AddressForSubnet(
                (EditText) findViewById(R.id.subnet_adress_editText),
                (TextView) findViewById(R.id.sub_ip_err),
                (Button) findViewById(R.id.subnet_adr_btn),
                widthPixels, objectManager);
        subnet_adr.id = Id.SUBNET;

        //broadcast
        final AddressForSubnet broadcast_adr = new AddressForSubnet(
                (EditText) findViewById(R.id.subnet_broadcast_editText),
                (TextView) findViewById(R.id.broadcast_ip_err),
                (Button) findViewById(R.id.broadcast_adr_btn),
                widthPixels, objectManager);
        broadcast_adr.id = Id.BROADCAST;

        //first host address
        final AddressForSubnet firstH_adr = new AddressForSubnet(
                (EditText) findViewById(R.id.first_host_adr_editText),
                (TextView) findViewById(R.id.first_ip_err),
                (Button) findViewById(R.id.first_adr_btn),
                widthPixels, objectManager);
        firstH_adr.id = Id.FIRST_HOST;

        //last host address
        final AddressForSubnet lastH_adr = new AddressForSubnet(
                (EditText) findViewById(R.id.last_host_adr_editText),
                (TextView) findViewById(R.id.last_ip_err),
                (Button) findViewById(R.id.last_adr_btn),
                widthPixels, objectManager);
        lastH_adr.id = Id.LAST_HOST;

        //selected subnet
        final Selected selSubnet = new Selected(
                (EditText) findViewById(R.id.sel_subnet_adr_editText),
                (TextView) findViewById(R.id.sub_adr_err), objectManager);
        selSubnet.id = Id.S_SUBNET;

        //selected broadcast
        final Selected selBroadcast = new Selected(
                (EditText) findViewById(R.id.sel_subnet_br_editText),
                (TextView) findViewById(R.id.broadcast_err), objectManager);
        selBroadcast.id = Id.S_BROADCAST;

        //selected First host address
        final Selected selFirstH = new Selected(
                (EditText) findViewById(R.id.sel_first_editText),
                (TextView) findViewById(R.id.first_err), objectManager);
        selFirstH.id = Id.S_FIRST_H;

        //selected last host address
        final Selected selLastH = new Selected(
                (EditText) findViewById(R.id.sel_last_editText),
                (TextView) findViewById(R.id.last_err), objectManager);
        selLastH.id = Id.S_LAST_H;

        //global subnet number
        final GlobalSelected global_subnet_number = new GlobalSelected(
                (EditText) findViewById(R.id.sel_subnet_editText),
                (TextView) findViewById(R.id.sel_sub_err),
                objectManager,
                selSubnet, selBroadcast, selFirstH, selLastH);
        global_subnet_number.id = Id.GLOBAL_S_SUBNET;

        Button Clear_Btn = findViewById(R.id.clear_all_btn);
        Clear_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectManager.clearAllValues();
            }
        });

        final ConstraintLayout background = findViewById(R.id.background);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objectManager.calculate();
            }
        });

        //**//--//++//**//--//++//**//--//++//**//--//++//**//--//++//**//--//++//**//--//++//**//--

        objectManager.ip = ip_adr;
        objectManager.mask = mask_adr;

        objectManager.ipClass = ip_class;
        objectManager.cidr = cidr;

        objectManager.subnet = subnet_adr;
        objectManager.broadcast = broadcast_adr;
        objectManager.firstHost = firstH_adr;
        objectManager.lastHost = lastH_adr;

        objectManager.subnetsQ = subnets_q;
        objectManager.allHosts = all_hosts;
        objectManager.hostsInSubnet = hosts_q;

        objectManager.globalSelSubnet = global_subnet_number;
        objectManager.selSubnet = selSubnet;
        objectManager.selBroadcast = selBroadcast;
        objectManager.selFirstH = selFirstH;
        objectManager.selLastH = selLastH;

        //**//--//++//**//--//++//**//--//++//**//--//++//**//--//++//**//--//++//**//--//++//**//--

        objectManager.obj[0] = objectManager.ip;
        objectManager.obj[1] = objectManager.mask;
        objectManager.obj[2] = objectManager.ipClass;
        objectManager.obj[3] = objectManager.cidr;
        objectManager.obj[4] = objectManager.subnet;
        objectManager.obj[5] = objectManager.broadcast;
        objectManager.obj[6] = objectManager.firstHost;
        objectManager.obj[7] = objectManager.lastHost;
        objectManager.obj[8] = objectManager.subnetsQ;
        objectManager.obj[9] = objectManager.allHosts;
        objectManager.obj[10] = objectManager.hostsInSubnet;
        objectManager.obj[11] = objectManager.globalSelSubnet;
        objectManager.obj[12] = objectManager.selSubnet;
        objectManager.obj[13] = objectManager.selBroadcast;
        objectManager.obj[14] = objectManager.selFirstH;
        objectManager.obj[15] = objectManager.selLastH;

    }
}
