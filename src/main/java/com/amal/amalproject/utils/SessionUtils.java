package com.amal.amalproject.utils;

import com.amal.amalproject.entities.Compte;
import com.amal.amalproject.entities.User;
import com.amal.amalproject.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class SessionUtils {
    private static Compte compte = null;
    private static String photo = null;

    private static String role = null;

    public static void addCurrentUser(Compte compte) {
        SessionUtils.compte = compte;
        if (compte != null) {
            SessionUtils.role = compte.getRole();
        }
    }

    public static void addCurrentUserPhoto(String photo) {
        SessionUtils.photo = photo;
    }

    public static void clearSession() {
        SessionUtils.compte = null;
        SessionUtils.photo = null;
        SessionUtils.role = null;
    }

    public static Compte getCurrentUser() {
        return SessionUtils.compte;
    }

    public static String getCurrentUserPhoto() {
        return SessionUtils.photo;
    }

    public static String getCurrentUserRole() {
        return SessionUtils.role;
    }
}
