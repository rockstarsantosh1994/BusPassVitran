package com.rockstar.buspassvitran.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Isaac Newton on 27-01-2017.
 */
public class MyValidator1 {
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String REQUIRED_MSG = "Field required";

    // validating email id
    public static boolean isValidEmail(EditText editText) {
        Log.d("tag","validate method call");
        String email = editText.getText().toString().trim();
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            editText.setError("Enter valid Email");
            return false;
        }
        editText.setError(null);
        return true;
    }

    // validating password
    public static boolean isValidPassword(EditText editText) {
        String pass = editText.getText().toString().trim();
        if (pass != null && pass.length() > 3) {
            editText.setError(null);
            return true;
        }
        editText.setError("Enter valid Password");
        return false;
    }

    public static boolean isValidField(EditText editText) {
        String txtValue = editText.getText().toString().trim();
        if (txtValue.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }
        editText.setError(null);
        return true;
    }

    public static boolean isValidSpinner(Spinner spinner) {
        View view = spinner.getSelectedView();
        TextView textView = (TextView) view;
        if (spinner.getSelectedItemPosition() == 0) {
            textView.setError("None selected");
            return false;
        }
        textView.setError(null);
        return true;
    }

    public static boolean isValidMobile(EditText editText) {
        String mob = editText.getText().toString().trim();
        if (mob != null && mob.length() == 10) {
            editText.setError(null);
            return true;
        }
        editText.setError(REQUIRED_MSG + " Enter 10 digits");
        return false;
    }


}
