package com.example.karol.kalkulator_ip.EditTexts.Values;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.Enums.Status;
import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;
import com.example.karol.kalkulator_ip.R;

public class GlobalSelected extends ValueDigit {
    private ValueDigit[] selected = new ValueDigit[4];
    public String actualSubnet = "";

    public GlobalSelected(EditText editText, TextView err, ObjectManager objectManager,
                          ValueDigit... val) {
        super(editText, err, objectManager);
        setTextWatcher();

        for (int i = 0; i < val.length; i++)
            selected[i] = val[i];
    }

    private void setTextWatcher() {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkValue()) {
                    setValue();
                    status = Status.GIVEN;
                    objectManager.calculate();
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    boolean checkValue() {
        String val = editText.getText().toString();

        if (val.length() < 1) {
            status = Status.EMPTY;
            return false;
        }

        if (objectManager.subnetsQ.status != Status.EMPTY) {
            long subQ = Long.parseLong(objectManager.subnetsQ.value);
            long sel = Long.parseLong(val);

            if (sel > subQ || sel < 1) {
                err.setText(R.string.subnetNotExist);
                return false;
            }
        } else {
            err.setText(R.string.subnetNotExist);
            return false;
        }

        err.setText("");
        return true;
    }

    private void setValue() {
        String val = editText.getText().toString();
        value = val;
        status = Status.GIVEN;

        for (int i = 0; i < selected.length; i++) {
            selected[i].value = val;
            selected[i].editText.setText(val);
            selected[i].status = Status.S_CALCULATED;
        }
    }
}
