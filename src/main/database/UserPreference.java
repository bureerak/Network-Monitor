package main.database;

import main.NetworkScanner.InitializeScan;
import main.ui.HomeViews;

public class UserPreference {
    private static String Profile;
    private static int ProfileID;
    private static int Interval;
    private static int Status;
    private static final InitializeScan initializeScan;

    public static final int IDLE = 0;
    public static final int READY = 1;
    public static final int SAVING = 2;

    static {
        Profile = "None";
        ProfileID = 1;
        Interval = 5;
        Status = 0;
        initializeScan = new InitializeScan();
    }

    public static String getProfile() {
        return Profile;
    }
    public static void setProfile(String Profile) {
        UserPreference.Profile = Profile;
    }

    public static int getProfileID() {
        return ProfileID;
    }
    public static void setProfileID(int ProfileID) {
        UserPreference.ProfileID = ProfileID;
    }

    public static int getInterval() {
        return Interval;
    }
    public static void setInterval(int Interval) {
        UserPreference.Interval = Interval;
    }

    public static int getStatus() {
        return Status;
    }
    public static void setStatus(int status) {
        Status = status;
        initializeScan.notifyStatus(getStatus());
        if (status == 0) {
            HomeViews.setStatus("Status: IDLE");
        } else if (status == 1) {
            HomeViews.setStatus("Status: Ready");
        } else {
            HomeViews.setStatus("Status: Monitoring");
        }
    }

}
