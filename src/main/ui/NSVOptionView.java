package main.ui;

import main.database.JDBCProfileEdit;
import main.database.graph.DataFetcher;
import raven.datetime.DatePicker;

import javax.swing.*;
import java.sql.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class NSVOptionView {
    JPanel frame;
    JComboBox<String> cb;
    ArrayList<String> arr;
    DatePicker datePicker;
    JFormattedTextField editor;
    DataFetcher fetcher;
    public NSVOptionView(NetworkStatisticsViews Owner, DataFetcher fetcher){
        this.fetcher = fetcher;
        frame = new JPanel();
        arr = new ArrayList<>();
        cb = new JComboBox<>();

        editor = new JFormattedTextField();
        datePicker = new DatePicker();
        datePicker.setEditor(editor);
        datePicker.setDateSelectionMode(DatePicker.DateSelectionMode.BETWEEN_DATE_SELECTED);
        datePicker.setDateSelectionAble(localDate -> !localDate.isAfter(LocalDate.now()));
        frame.add(editor);
        JDBCProfileEdit.refresh(arr);
        Iterator<String> it = arr.iterator();
        while (it.hasNext()) {
            cb.addItem( it.next() );
        }
        frame.add(cb);
        int choice = JOptionPane.showConfirmDialog(Owner,frame,"Range & Profile",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (choice == 0) {
            LocalDate[] dates = datePicker.getSelectedDateRange();
            String[] name = cb.getSelectedItem().toString().split("\\s");
            int id = JDBCProfileEdit.getID(name[0]);
            LocalDateTime[] dt;
            if (dates != null) {
                dt = new LocalDateTime[]{dates[0].atTime(0, 0, 0), dates[1].atTime(23, 59, 59)};
            } else {
                dt = new LocalDateTime[]{LocalDateTime.now().minusDays(3), LocalDateTime.now()};
            }
            fetcher.fetchRange(id,dt[0],dt[1]);
            Owner.updateGraph(id);
        }
    }
}
