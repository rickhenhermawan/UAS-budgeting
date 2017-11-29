package com.example.joseph.mobileproject;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Joseph on 11/17/17.
 */

public class Util {

    Locale locale = null;
    NumberFormat rupiahFormat = null;

    public String rupiah(String rp)
    {
        Locale locale = new Locale("id", "ID");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        return (fmt.format(Integer.parseInt(rp)));
    }

}