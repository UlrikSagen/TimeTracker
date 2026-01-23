package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import model.Contract;
import model.TimeEntry;
import service.TimeService;
import storage.TimeRepository;



public class Controller {
    
    private final TimeService service;
    private List<TimeEntry> entries;
    private final Contract contract;


    public Controller(TimeService service) {
        this.service = service;
        this.entries = TimeRepository.loadEntries();
        this.contract = TimeRepository.loadContract();
    }

    public void addOrEditEntry(LocalDate date, LocalTime start, LocalTime end) {
        this.entries = service.addOrEdit(this.entries, date, start.withSecond(0).withNano(0), end.withSecond(0).withNano(0));
        saveEntries();
    }

    public List<TimeEntry> getAll() {
        return this.entries;
    }

    public List<TimeEntry> getEntriesByMonth(int month, int year) {
        return service.filterByMonth(this.entries, month, year);
    }

    public int getMinutesByMonth(int month, int year) {
        List<TimeEntry> monthlyEntries = getEntriesByMonth(month, year);
        return service.getTotalMinutes(monthlyEntries);
    }

    public int getTotalMinutes() {
        return service.getTotalMinutes(this.entries);
    }

    public void reloadEntries() {
        this.entries = TimeRepository.loadEntries();
    }

    public void saveEntries() {
        TimeRepository.saveEntries(this.entries);
    }

    public void deleteEntry(LocalDate date) {
        this.entries = service.deleteEntry(this.entries, date);
        saveEntries();
    }

    public String getDate(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    public String entriesToString(int month, int year){
        List<TimeEntry> entriesByMonth = getEntriesByMonth(month, year);
        List<String> entriesByMonthList = service.getListOfStrings(entriesByMonth);
        String printableString = service.printableString(entriesByMonthList);
        return printableString;
    }

    public String hoursWorkedString(int month, int year){
        return service.hoursWorkedString(getMinutesByMonth(month, year));
    }

    public float getSalaryByMonth(int month, int year){
        return service.calculateSalary(getEntriesByMonth(month, year), this.contract);
    }

    public float getSalary(){
        return service.calculateSalary(this.entries, this.contract);
    }

    public float getOvertimeSalaryByMonth(int month, int year){
        return service.calculateOverTimeSalary(getEntriesByMonth(month, year), this.contract);
    }

    public float getOvertimeSalary(){
        return service.calculateOverTimeSalary(this.entries, this.contract);
    }

    //public Timer timer(){
    //    Timer timer = new Timer();
    //    return timer;
    //}

}

