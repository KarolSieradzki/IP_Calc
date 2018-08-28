package com.example.karol.kalkulator_ip.EditTexts.Values;

import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.Enums.Status;
import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;
import com.example.karol.kalkulator_ip.R;

public class Selected extends ValueDigit {
    public Selected(EditText editText, TextView err, ObjectManager objectManager) {
        super(editText, err, objectManager);
    }

    boolean checkValue() {
        String val = editText.getText().toString();

        if (val.length() < 1) {
            status = Status.EMPTY;
            return false;
        }

        if (objectManager.subnetsQ.status != Status.EMPTY) {
            Long subQ = Long.parseLong(objectManager.subnetsQ.value);
            Long sel = Long.parseLong(val);

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
}
