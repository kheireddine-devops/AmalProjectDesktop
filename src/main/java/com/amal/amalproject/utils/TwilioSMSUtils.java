package com.amal.amalproject.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMSUtils {
    private static final String ACCOUNT_SID = "ACe1e29ee475ceeeb623ddb29521a89818";
    private static final String AUTH_TOKEN = "af09e1dff48bbf00441b47c6b2b37d48";
    private static final String TWILIO_NUMBER = "+14793091738";
    private static boolean init = false;

    private TwilioSMSUtils() {}

    private static void init() {
        if(init == false) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            init = true;
        }
    }

    public static boolean sendMessage(String to,String content) {
        try {
            init();
            Message message = Message.creator(new PhoneNumber(to),new PhoneNumber(TWILIO_NUMBER), content)
                    .create();
            System.out.println("DONE : " + message.getSid());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
