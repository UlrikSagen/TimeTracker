package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import model.TimeEntry;
import service.TimeService;



public class Controller {
    
    private final TimeService service;

    public Controller(TimeService service) {
        this.service = service;
    }

    public void addOrEditEntry(LocalDate date, LocalTime start, LocalTime end) {
        service.addOrEdit(date, start.withSecond(0).withNano(0), end.withSecond(0).withNano(0));
    }

    public List<TimeEntry> getEntriesByMonth(int month, int year) {
        return service.filterByMonth(month, year);
    }

    public int getMinutesByMonth(int month, int year) {
        return service.getTotalMinutes(month, year);
    }

    public int getTotalMinutes() {
        return service.getTotalMinutes();
    }

    public int getMinutesByEntry(TimeEntry entry){
        int minutes = 0;
        minutes += service.getMinutesByEntry(entry);
        return minutes;
    }


    public void deleteEntry(LocalDate date) {
        service.deleteEntry(date);
    }

    public String getDate(){
        return LocalDate.now().toString();
    }

    public float getSalaryByMonth(int month, int year){
        return service.calculateSalary(month, year);
    }

    public float getSalary(){
        return service.calculateSalary();
    }

    public float getSalary(int month, int year){
        return service.calculateSalary(month, year);
    }

    public float getOvertimeSalaryByMonth(int month, int year){
        return service.calculateOverTimeSalary(month, year);
    }

    public float getOvertimeSalary(){
        return service.calculateOverTimeSalary();
    }

    //public Timer timer(){
    //    Timer timer = new Timer();
    //    return timer;
    //}

}

