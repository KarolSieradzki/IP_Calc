package com.example.karol.kalkulator_ip.EditTexts.Addresses;

import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.karol.kalkulator_ip.EditTexts.TextBox;
import com.example.karol.kalkulator_ip.Enums.*;
import com.example.karol.kalkulator_ip.ObjectManager.ObjectManager;
import com.example.karol.kalkulator_ip.R;

public class Address extends TextBox {
    private TextWatcher textWatcher = null;
    private ObjectManager objectManager;

    public Address(EditText editText, TextView err, Button btn, int widthPixels,
                   ObjectManager objectManager) {
        for (int i = 0; i < 4; i++)
            oct[i] = new Octet();

        this.err = err;
        this.editText = editText;
        this.objectManager = objectManager;

        setSize(widthPixels);
        setTextWatcher();
        setOnClickBtn(btn);

        display();
    }

    private void setOnClickBtn(final Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numSys == NumSys.DECIMAL) {
                    btn.setText(R.string.btn_bin);
                    numSys = NumSys.BINARY;
                    display();
                    checkAddress();
                } else {
                    btn.setText(R.string.btn_dec);
                    numSys = NumSys.DECIMAL;
                    display();
                    checkAddress();
                }
            }
        });
    }

    private void setTextWatcher() {
        textWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkAddress()) {
                    setAddress();
                    status = Status.GIVEN;
                    objectManager.calculate();
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }

    private boolean checkAddress() {
        String text = editText.getText().toString();

        if (isEmpty(text)) {
            clearAddress();
            err.setText("");
            status = Status.EMPTY;
            return false;
        }

        if (isIncorrectNumberOfDots(text)) {
            err.setText(R.string.incorrect_address);
            return false;
        }

        if (isTooLongNumber(text, numSys)) {
            err.setText(R.string.incorrect_address);
            return false;
        }

        if (isIncorrectInBinary(text, numSys)) {
            err.setText(R.string.incorrect_address);
            return false;
        }

        Octet[] addr = StringToOctet(text, numSys);

        if (isOctetEmpty(addr)) {
            err.setText(R.string.incorrect_address);
            return false;
        }

        if (isIncorrectNumberInOctet(addr, numSys)) {
            err.setText(R.string.incorrect_address);
            return false;
        }

        if (!checkMask(addr)) {
            err.setText(R.string.incorrect_mask);
            return false;
        }

        err.setText("");
        return true;
    }

    private boolean isEmpty(String str) {
        return str.equals("");
    }

    private boolean isIncorrectNumberOfDots(String str) {
        int numOfDots = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '.')
                numOfDots++;
        }
        return numOfDots != 3;
    }

    private boolean isTooLongNumber(String str, NumSys numSys) {
        if (numSys == NumSys.DECIMAL)
            if (str.length() > 15) return true;
            else if (str.length() > 35) return true;

        int numOfDigitsInOct = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '.') {
                numOfDigitsInOct++;
                if (numSys == NumSys.BINARY) {
                    if (numOfDigitsInOct > 8)
                        return true;
                } else {
                    if (numOfDigitsInOct > 3)
                        return true;
                }
            } else
                numOfDigitsInOct = 0;
        }

        return false;
    }

    private Octet[] StringToOctet(String value, NumSys numSys) {
        Octet[] oct = new Octet[4];
        for (int i = 0; i < 4; i++) oct[i] = new Octet();

        Octet.NumerationSystem numerationSystem = Octet.NumerationSystem.DECIMAL;
        if (numSys == NumSys.BINARY) numerationSystem = Octet.NumerationSystem.BINARY;

        value = value + "."; //additional dot at the end to know where to end
        StringBuilder buffer = new StringBuilder("");
        int numOfDots = 0;

        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == '.') {
                oct[numOfDots].set(buffer.toString(), numerationSystem);
                buffer = new StringBuilder("");
                numOfDots++;
            } else buffer.append(value.charAt(i));
        }
        return oct;
    }

    private boolean isOctetEmpty(Octet[] address) {
        for (int i = 0; i < 4; i++) {
            if (address[i].value.equals(""))
                return true;
        }
        return false;
    }

    private boolean isIncorrectNumberInOctet(Octet[] address, NumSys numSys) {
        int oct_i = -1;
        for (int i = 0; i < 4; i++) {
            if (numSys == NumSys.DECIMAL)
                oct_i = Integer.parseInt(address[i].value);
            else
                oct_i = Integer.parseInt(address[i].value, 2);

            if (oct_i > 255 || oct_i < 0) return true;
        }

        return false;
    }

    private boolean isIncorrectInBinary(String addr, NumSys numSys) {
        if (numSys == NumSys.BINARY) {
            for (int i = 0; i < addr.length(); i++) {
                if (addr.charAt(i) != '1' &&
                        addr.charAt(i) != '0' &&
                        addr.charAt(i) != '.')
                    return true;
            }
        }
        return false;
    }

    private boolean checkMask(Octet[] mask) {
        if (id == Id.MASK) {
            boolean zeroOccurred = false;

            for (int i = 0; i < 4; i++) {
                if (mask[i].getBin().length() != 8) return false;

                for (int j = 0; j < 8; j++) {
                    if (mask[i].getBin().charAt(j) == '0')
                        zeroOccurred = true;
                    else if (mask[i].getBin().charAt(j) == '1' && zeroOccurred)
                        return false;
                }
            }
            return true;
        }
        return true;
    }

    private void setAddress() {
        String adr = editText.getText().toString();
        adr += "."; //additional dot at the end
        String buffer = "";
        int dot = 0;

        for (int j = 0; j < adr.length(); j++) {
            if (adr.charAt(j) != '.') {
                buffer += adr.charAt(j);
            } else {
                if (numSys == NumSys.DECIMAL)
                    oct[dot].set(buffer, Octet.NumerationSystem.DECIMAL);
                else
                    oct[dot].set(buffer, Octet.NumerationSystem.BINARY);

                buffer = "";
                dot++;
            }
        }
    }

    public void display() {
        editText.removeTextChangedListener(textWatcher);

        String text;

        if (status != Status.EMPTY) {
            if (numSys == NumSys.DECIMAL)
                text = oct[0].getDec() + "." + oct[1].getDec() + "." + oct[2].getDec() + "." + oct[3].getDec();
            else
                text = oct[0].getBin() + "." + oct[1].getBin() + "." + oct[2].getBin() + "." + oct[3].getBin();

            editText.setText(text);
        } else
            editText.setText("");

        editText.addTextChangedListener(textWatcher);
    }

    private void clearAddress() {
        for (int i = 0; i < 4; i++)
            oct[i].clear();
    }

    private void setSize(int widthPixels) {
        ConstraintLayout.LayoutParams params_ip;
        params_ip = (ConstraintLayout.LayoutParams) editText.getLayoutParams();

        double calc_width = widthPixels / 1.7;

        params_ip.width = (int) calc_width;

        editText.setLayoutParams(params_ip);
    }
}
