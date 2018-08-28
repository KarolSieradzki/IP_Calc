package com.example.karol.kalkulator_ip.EditTexts.Values;

import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;
import com.example.karol.kalkulator_ip.R;
import com.example.karol.kalkulator_ip.Enums.Status;

public class ValueCidr extends Value {
    public ValueCidr(EditText editText, TextView err, int widthPixels,
                     ObjectManager objectManager) {
        this.editText = editText;
        this.err = err;
        this.objectManager = objectManager;

        setTextWatcher();
        setSize(widthPixels);
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

    private boolean checkValue() {
        String val = editText.getText().toString();

        if (val.indexOf('/') < 0)
            addSlash();

        if (val.length() < 2) {
            status = Status.EMPTY;
            return false;
        } else {
            StringBuilder txt = new StringBuilder(val);

            int i = txt.indexOf("/");
            while (i >= 0) {
                txt.deleteCharAt(i);
                i = txt.indexOf("/");
            }

            int val_int = Integer.parseInt(txt.toString());

            if (val_int >= 0 && val_int <= 32)
                return true;

            else {
                err.setText(R.string.incorrect_cidr);
                return false;
            }
        }
    }

    private void addSlash() {
        editText.removeTextChangedListener(textWatcher);

        String str = editText.getText().toString();
        StringBuilder txt = new StringBuilder(str);
        txt.insert(0, '/');

        while (txt.length() > 3)
            txt.deleteCharAt(txt.length() - 1);

        editText.setText(txt.toString());
        editText.setSelection(editText.getText().length());

        editText.addTextChangedListener(textWatcher);
    }

    private void setSize(int widthPixels) {
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) editText.getLayoutParams();

        double calc_width = widthPixels / 5.5;

        params.width = (int) calc_width;

        editText.setLayoutParams(params);
    }

    private void setValue() {
        StringBuilder txt = new StringBuilder(editText.getText().toString());

        int i = txt.indexOf("/");
        while (i >= 0) {
            txt.deleteCharAt(i);
            i = txt.indexOf("/");
        }

        value = txt.toString();
        status = Status.GIVEN;
    }
}