package com.example.karol.kalkulator_ip.EditTexts;

import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.EditTexts.Addresses.Octet;
import com.example.karol.kalkulator_ip.Enums.Id;
import com.example.karol.kalkulator_ip.Enums.NumSys;
import com.example.karol.kalkulator_ip.Enums.Status;

public class TextBox {
    public Status status = Status.EMPTY;
    public NumSys numSys = NumSys.DECIMAL;

    public Octet[] oct = new Octet[4];
    public String value = "";

    public Id id = null;

    public TextView err;
    public EditText editText;

    public void initializeOct() {
        for (int i = 0; i < 4; i++)
            oct[i] = new Octet();
    }
}
