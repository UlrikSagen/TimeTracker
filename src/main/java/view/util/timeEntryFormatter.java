package view.util;

import model.TimeEntry;

public class timeEntryFormatter {
    public static String entryToString(TimeEntry entry){
        return entry.getDate() + ":   " + entry.getStart() + "  -   " + entry.getEnd();
    }

    public static String hoursWorked(int totalMinutes){
        return(totalMinutes/60 + " hours and " + totalMinutes % 60 + " minutes.");

    }
}
