package main.exporter;

import main.database.graph.*;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExportCSV {
    private static List<?> values;
    private static List<LocalDateTime> labels;
    private static List<LocalDateTime> filteredLabels;
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
        formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        if ("Latency/Time".equals(type)) {
            LatencyFetch latencyFetch = LatencyFetch.getInstance();
            latencyFetch.fetch(profile_id);
            labels = latencyFetch.getDateTimes();
            values = latencyFetch.getAvg();
        } else if ("Device/Time".equals(type)) {
            DeviceFetch deviceFetch = DeviceFetch.getInstance();
            deviceFetch.fetch(profile_id);
            labels = deviceFetch.getDateTime();
            values = deviceFetch.getDevices();
        } else if ("Ports/Time".equals(type)) {
            PortFetch portFetch = PortFetch.getInstance();
            portFetch.fetch(profile_id);
            labels = portFetch.getDateTime();
            values = portFetch.getCount();
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
                if ("Latency/Time".equals(type)) {
                    writer.append("Time,Average Latency\n");
                } else if ("Device/Time".equals(type)) {
                    writer.append("Time,Device Count\n");
                } else if ("Ports/Time".equals(type)) {
                    writer.append("Time,Port Count\n");
                }

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
