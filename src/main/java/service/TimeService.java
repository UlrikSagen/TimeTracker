package service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.Contract;
import model.TimeEntry;


public class TimeService {
    
    //method for adding or editing entry
    public List<TimeEntry> addOrEdit(List<TimeEntry> entries, LocalDate date, LocalTime start, LocalTime end){

        List<TimeEntry> out = new ArrayList<>();
        boolean replaced = false;

        for (TimeEntry entry : entries) {
            if (entry.getDate().equals(date)){
                out.add(new TimeEntry(date, start, end));
                replaced = true;
            } else {
                out.add(entry);
            }
        }

        if (!replaced) {
            out.add(new TimeEntry(date, start, end));
        }
        return out;
    }

    //method for deleting entry by date
    public List<TimeEntry> deleteEntry(List<TimeEntry> entries, LocalDate date){
        List<TimeEntry> out = new ArrayList<>();

        for (TimeEntry entry : entries) {
            if (!entry.getDate().equals(date)){
                out.add(entry);
            }
        }
        return out;
    }

    //method for calculating total minutes from list of entries
    public int getTotalMinutes(List<TimeEntry> entries){
        return TimeCalculator.getTotalMinutes(entries);
    }

    //method for filtering entries by month and year
    public List<TimeEntry> filterByMonth(List<TimeEntry> entries, int month, int year){
        List<TimeEntry> filtered = new ArrayList<>();

        for (TimeEntry entry : entries) {
            if (entry.getDate().getMonthValue() == month && entry.getDate().getYear() == year) {
                filtered.add(entry);
            }
        }

        return filtered;
    }

    //method for converting list of entries to list of strings
    public List<String> getListOfStrings(List<TimeEntry> entries){
        List<String> listOfStrings =  new ArrayList<>();
        String entryToString;
        for (TimeEntry entry : entries){
            entryToString = entryToString(entry);
            listOfStrings.add(entryToString);
        }
        return listOfStrings;
    }

    //method for formatting single entry to string
    public String entryToString(TimeEntry entry){
        String entryToString = entry.getDate() + ":     " + entry.getStart() + "   -   " + entry.getEnd();

        return entryToString;
    }

    //method for formatting list of entries to printable string
    public String printableString(List<String> entries) {
        return String.join("\n", entries);
    }

    //method for formatting hours worked string
    public String hoursWorkedString(int totalMinutes){
        return(totalMinutes/60 + " hours and " + totalMinutes % 60 + " minutes.");
    }

    //method for calculating total salary
    public float calculateSalary(List<TimeEntry> entries, Contract contract){
        Duration overTime = Duration.ofMinutes(0);
        for (TimeEntry entry : entries){
            overTime = overTime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        float totalSalary = TimeCalculator.calculateSalary(entries, contract, overTime);

        return totalSalary;
    }

    //method for calculating overtime salary
    public float calculateOverTimeSalary(List<TimeEntry> entries, Contract contract){
        Duration totalOvertime = Duration.ofMinutes(0);
        for (TimeEntry entry : entries){
            totalOvertime = totalOvertime.plus(TimeCalculator.calculateOvertimeHours(Duration.between(entry.getStart(), entry.getEnd())));
        }
        float overTimeSalary = TimeCalculator.calculateOvertimeSalary(contract, totalOvertime);
        
        return overTimeSalary;
    }
}
