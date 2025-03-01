package main.database;

public class UserPreference {
    private static String Profile;
    private static int ProfileID;
    private static int Interval;

    static {
        Profile = "None";
        ProfileID = 0;
        Interval = 5;
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

}
