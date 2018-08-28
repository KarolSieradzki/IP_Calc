package com.example.karol.kalkulator_ip.EditTexts.Values;

import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.Enums.Status;
import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;

public class ValueClass extends Value {
    public ValueClass(EditText editText, TextView err, int widthPixels,
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

        if (val.length() < 1) {
            status = Status.EMPTY;
            return false;
        }
        return true;
    }

    private void setValue() {
        value = editText.getText().toString().toUpperCase();
        status = Status.GIVEN;
    }

    private void setSize(int widthPixels) {
        ConstraintLayout.LayoutParams params =
                (ConstraintLayout.LayoutParams) editText.getLayoutParams();

        double calc_width = widthPixels / 5.5;

        params.width = (int) calc_width;

        editText.setLayoutParams(params);
    }
}
