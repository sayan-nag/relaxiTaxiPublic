package com.relaxiTaxi.onboarding.utils;

import java.util.regex.Pattern;

public class Utilities {

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    public static boolean isValidEmail(String email) {
        Pattern pattern = Utilities.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    //0nboard@123
    //onboardrelaxidrivers@gmail.com
}
