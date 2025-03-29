package main.exporter;

import main.database.graph.*;
import org.json.*;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportJSON {
    private static List<?> values;
    private static List<LocalDateTime> labels;
    private static List<LocalDateTime> filteredLabels;
    private static List<Object> filteredValues;
    private static Frame frame;
    private static FileDialog fileDialog;
    private static DateTimeFormatter formatter;

    public static void exportData(int profile_id, String type) {

        if (profile_id == 0) {
            System.out.println("Profile not found!");
            return;
        }

        values = new ArrayList<>();
        labels = new ArrayList<>();
        filteredLabels = new ArrayList<>();
        filteredValues = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        if ("Latency/Time".equalsIgnoreCase(type)) {
            LatencyFetch latencyFetch = LatencyFetch.getInstance();
            latencyFetch.fetch(profile_id);
            labels = latencyFetch.getDateTimes();
            values = latencyFetch.getAvg();
        } else if ("Device/Time".equalsIgnoreCase(type)) {
            DeviceFetch deviceFetch = DeviceFetch.getInstance();
            deviceFetch.fetch(profile_id);
            labels = deviceFetch.getDateTime();
            values = deviceFetch.getDevices();
        } else {
            System.out.println("Invalid type: " + type);
            return;
        }

        filteredLabels.addAll(labels);
        filteredValues.addAll(values);

        saveToJson(type);
    }

    private static void saveToJson(String type) {
        frame = new Frame();
        fileDialog = new FileDialog(frame, "Save JSON File", FileDialog.SAVE);
        fileDialog.setFile("data.json");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (directory != null && filename != null) {
            String filePath = directory + filename;
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < filteredLabels.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("time", filteredLabels.get(i).format(formatter));
                jsonObject.put(type.equals("Latency/Time") ? "Average_latency" : "Device_count", filteredValues.get(i));
                jsonArray.put(jsonObject);
            }

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(jsonArray.toString(4));
                System.out.println("JSON saved at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File save canceled");
        }
    }
}
