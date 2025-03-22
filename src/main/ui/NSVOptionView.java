package main.ui;

import main.database.JDBCProfileEdit;
import raven.datetime.DatePicker;
import raven.datetime.DateSelectionAble;

import javax.swing.*;
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
    public NSVOptionView(JComponent j){
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
        int choice = JOptionPane.showConfirmDialog(j,frame,"Range & Profile",JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
        if (choice == 0) {
            LocalDate[] dates = datePicker.getSelectedDateRange();
            if (dates != null) {
                LocalDateTime[] dt = {dates[0].atTime(0, 0, 0),dates[1].atTime(0, 0, 0)};
                System.out.println(dt[0]);
                System.out.println(dt[1]);
            }
        }
    }
}
