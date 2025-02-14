package com.example.travel_in_time;

import java.util.List;

public class OnThisDayResponse {

    private List<WikiDataModel> events;
    private List<WikiDataModel> births;
    private List<WikiDataModel> deaths;
    private List<WikiDataModel> holidays;

    public List<WikiDataModel> getEvents() {
        return events;
    }

    public void setEvents(List<WikiDataModel> events) {
        this.events = events;
    }

    public List<WikiDataModel> getBirths() {
        return births;
    }

    public void setBirths(List<WikiDataModel> births) {
        this.births = births;
    }

    public List<WikiDataModel> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<WikiDataModel> deaths) {
        this.deaths = deaths;
    }

    public List<WikiDataModel> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<WikiDataModel> holidays) {
        this.holidays = holidays;
    }

}
