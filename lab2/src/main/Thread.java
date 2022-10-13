package main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Thread {
    final static SimpleDateFormat format =
            new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(String str) {
        String[] splited = str.split("\\s+");
        String date = splited[0];
        String time = splited[2];

        try {
            this.date = format.parse(date + " " + time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
