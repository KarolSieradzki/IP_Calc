package com.example.karol.kalkulator_ip.EditTexts.Addresses;

import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;

public class AddressForSubnet extends Address {
    public AddressForSubnet(EditText editText, TextView err, Button btn, int widthPixels,
                            ObjectManager objectManager) {
        super(editText, err, btn, widthPixels, objectManager);
        setSize(widthPixels);
    }

    private void setSize(int widthPixels) {
        ConstraintLayout.LayoutParams params_ip =
                (ConstraintLayout.LayoutParams) editText.getLayoutParams();

        double calc_width = widthPixels / 1.5;

        params_ip.width = (int) calc_width;

        editText.setLayoutParams(params_ip);
    }
}
