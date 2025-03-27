package main.exporter;

import main.database.graph.*;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportCSV {
    private static List<?> values;
    private static List<LocalTime> labels;
    private static List<LocalTime> filteredLabels;
    private static List<Object> filteredValues;
    private static Frame frame;
    private static FileDialog fileDialog;
    private static DateTimeFormatter formatter;
    private static String filePath;

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

        if ("Latency/Time".equals(type)) {
            LatencyFetch latencyFetch = LatencyFetch.getInstance();
            latencyFetch.fetch(profile_id);
            labels = latencyFetch.getDateTimes();
            values = latencyFetch.getAvg();
        } else if ("Device/Time".equals(type)) {
            DeviceFetch deviceFetch = DeviceFetch.getInstance();
            deviceFetch.fetch(profile_id);
            labels = deviceFetch.getTime();
            values = deviceFetch.getDevices();
        } else {
            System.out.println("Invalid type: " + type);
            return;
        }

        filteredLabels.addAll(labels);
        filteredValues.addAll(values);

        saveToCSV(type);
    }

    private static void saveToCSV(String type) {
        frame = new Frame();
        fileDialog = new FileDialog(frame, "Save CSV File", FileDialog.SAVE);
        fileDialog.setFile("data.csv");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();

        if (directory != null && filename != null) {
            filePath = directory + filename;

            try (FileWriter writer = new FileWriter(filePath)) {
                writer.append(type.equals("Latency/Time") ? "Time,Average Latency\n" : "Time,Device Count\n");

                for (int i = 0; i < filteredLabels.size(); i++) {
                    writer.append(filteredLabels.get(i).format(formatter)).append(",")
                            .append(filteredValues.get(i).toString()).append("\n");
                }
                System.out.println("CSV saved at: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File save canceled");
        }
    }
}
