package com.alicek.appointmentService;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.LongStream;

//Appt service meant to provide creation, update, and deletion of appts
public class ApptService {
    
    //in-memory appt storage
    HashMap<String, Appt> appts = new HashMap<String, Appt>();

   
    //apptId generated here for new appointments as an alphanumeric string, ensuring apptId isn't
    //already used, repeating until an unused ID is found
    private String generateApptId() {
        while(true){
             //create LongStream of random Longs within the bounds. The upper bound is the max decimal number
            //that can be represented by an alphanumeric (base 36) number.
            LongStream apptIdLongGenerator = new Random().longs(0L, 3656158440062975L);
            //get the first stable result of the generator as a Long type.
            Long apptIdLong = apptIdLongGenerator.findFirst().getAsLong();
            String apptId = Long.toString(apptIdLong, 36);
            if (!appts.containsKey(apptId)){
                return apptId;
            }
        }
    }

    //method for adding an instance of appointment. Takes a string, apptDesc, which is supplied by the user, and a Date.
    public Appt addAppt(Date apptDate, String apptDesc) {
        String apptId = this.generateApptId();
        Appt addedAppt = new Appt(apptDate, apptDesc, apptId);
        appts.put(apptId, addedAppt);
        return addedAppt;
    }

    //Method used to test appointment ids and ensure they both are valid and exist in the appt hashmap
    //kept separate from deleteAppt for future use
    private void testApptId(String apptId){

        //throw an exception if the apptId isn't found in memory.
        if (!appts.containsKey(apptId)) {
            throw new IllegalArgumentException("Appt ID not found in memory!");
        }

    }

    //Method used to delete an appointment from in-memory storage (hashmap)
    //tests appt ID first, so exception is thrown if necessary.
    //returns "true" if appt was deleted and "false" if not.
    public Boolean deleteAppt(String apptId) {
        Boolean deleted = false;
        testApptId(apptId);  
        appts.remove(apptId);

        if (appts.containsKey(apptId) == false){
            deleted = true;
        }
        return deleted;
    
    }
}
    
