package main.exporter;
//import main.NetworkTools.DeviceDisplay;
//import main.NetworkTools.PortDisplay;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;
public class Print_info implements Printable {
    private final JPanel print_able;
    public Print_info(JPanel print_able){
        this.print_able = print_able;
    }
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0){
            return Printable.NO_SUCH_PAGE;// ถ้ามีหน้าที่เกินไป ให้หยุดการพิมพ์
        }
        Graphics2D g2d = (Graphics2D) graphics; // แปลงอ็อบเจ็กต์ Graphics เป็น Graphics2D เพื่อให้สามารถใช้ฟังก์ชันเพิ่มเติมได้
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY()); //ปรับแต่งหน้ากระดาษตามแนวแกน x , y
        double pageWidth = pageFormat.getImageableWidth();
        double pageHeight = pageFormat.getImageableHeight(); //ขนาดหน้ากระดาษ
        double panelWidth = print_able.getWidth(); //setขนาดpanelแต่ล่ะอันที่รับเข้า
        double panelHeight = print_able.getHeight();
        double scale_width = pageWidth / panelWidth;
        double scale_height = pageHeight / panelHeight;
        double scale = Math.min(scale_width, scale_height); //กำหนดขนาดให้ขั้นต่ำอยู่xกับy
        g2d.scale(scale, scale);
        print_able.paint(g2d);
        return Printable.PAGE_EXISTS;

    }
    public static void print_panel(JPanel print_able){
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Print_info(print_able));
        if (printerJob.printDialog()) {
            try{
                printerJob.print();
            }catch (PrinterException e){
                e.printStackTrace();
            }
        }

    }
}
