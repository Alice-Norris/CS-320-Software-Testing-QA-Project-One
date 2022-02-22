package com.alicek.appointmentService;
import java.util.Date;

public class Appt {
    //private fields for appts
    private String apptId;
    private Date apptDate;
    private String apptDesc;
    
    //Constructor for appt objects. Takes a string from user input (apptDesc),
    //an appt ID from the service's generateApptId(), and a Date object
    public Appt(Date apptDate, String apptDesc, String apptId) {

        //using setter validation in constructor
        this.setApptDate(apptDate);
        this.setApptDesc(apptDesc);
        
        //no setter, apptId must not be null, less than 10 characters, and its Long value positive
        if (apptId == null || apptId.length() > 10 || Long.parseLong(apptId, 36) < 0) {
            throw new IllegalArgumentException("Invalid Appointment ID! Must be a positive ID string of 10 characters or less.");
        }
        this.apptId = apptId;
    }

    //Getters and setters follow past this point
    //All setters check for valid input, to ensure that an appointment isn't updated with invalid data.
    
    public Date getApptDate() {
        return this.apptDate;
    }
    
    public void setApptDate(Date apptDate) {
        if (apptDate == null || apptDate.before(new Date())){
            throw new IllegalArgumentException("Appointment Date must not be null or in the past!");
        }
        this.apptDate = apptDate;
    }
    public String getApptDesc() {
        return this.apptDesc;
    }
    
    public void setApptDesc(String apptDesc) {
        if (apptDesc == null || apptDesc.length() > 50) {
            throw new IllegalArgumentException("Invalid Appointment Description! Must be a string of 50 characters or less!");
        }
        this.apptDesc = apptDesc;
    }
    
    public String getApptId() {
        return this.apptId;
    }
}
