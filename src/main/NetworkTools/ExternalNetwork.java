package main.NetworkTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import org.json.JSONObject;

public class ExternalNetwork {
    private String infoIP = null;
    private String infoISP = null;
    private String infoCountry = null;
    private String infoCity = null;
    private boolean isInfoLoaded = false;

    /**
     * Prepare a user's external network information such as IP, ISP, COUNTRY, CITY.
     */
    public ExternalNetwork() {
        try {
            URL infoURL = new URL("https://ipinfo.io/?token=e6fc17858e851b");
            BufferedReader infoReader = new BufferedReader(new InputStreamReader(infoURL.openStream(), "UTF-8"));
            String info = "";

            for (String line; (line = infoReader.readLine()) != null;) {
                info += line + "\n";
            }

            infoReader.close();

            JSONObject infoJSON = new JSONObject(info);
            infoIP = infoJSON.getString("ip");
            infoISP = infoJSON.getString("org");
            infoCountry = infoJSON.getString("country");
            infoCity = infoJSON.getString("city");

            isInfoLoaded = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Return an external IP Address.
     * @return IP Address as string if information is loaded, otherwise null.
     */
    public String getIP() {
        return isInfoLoaded ? infoIP : null;
    }

    /**
     * Return an external ISP Name.
     * @return ISP as string if information is loaded, otherwise null.
     */
    public String getISP() {
        return isInfoLoaded ? infoISP : null;
    }

    /**
     * Return an external country name.
     * @return Country as string if information is loaded, otherwise null.
     */
    public String getCountry() {
        return isInfoLoaded ? infoCountry : null;
    }

    /**
     * Return an external city name.
     * @return City as string if information is loaded, otherwise null.
     */
    public String getCity() {
        return isInfoLoaded ? infoCity : null;
    }

    /*public static void main(String[] args) {
        ExternalNetwork network = new ExternalNetwork();
        System.out.println("IP: " + network.getIP());
        System.out.println("ISP: " + network.getISP());
        System.out.println("Country: " + network.getCountry());
        System.out.println("City: " + network.getCity());
    }*/
}
