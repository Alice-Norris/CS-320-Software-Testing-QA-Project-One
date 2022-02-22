package com.alicek.appointmentServiceTest;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import com.alicek.appointmentService.ApptService;
import com.alicek.appointmentService.Appt;

@TestInstance(Lifecycle.PER_CLASS)
public class ApptServiceTest {
    //the service to be used as a test fixture
    private ApptService service;

    //test values
    Date apptDate;
    String apptDesc;

    //setup service at the beginning
    @BeforeAll
    
    void setupService(){
        service = new ApptService();
    }

    //reset parameters to valid input before each test
    @BeforeEach
    void setUpParameters() {
        apptDate = new Date(System.currentTimeMillis() + 1000);
        apptDesc = "Appointment for initial consultation of client";
    }
    
    //since the service only takes input, no output tests will be written
    //grouping valid tests
    @Nested
    @Tag("validTest")
    class validInputTests{
        Appt testAppt;

        //test of addAppt() method with valid input
        @Test
        void testAddApptValid() {
            Appt newAppt = service.addAppt(apptDate, apptDesc);
            Assertions.assertAll(() -> Assertions.assertEquals(apptDate, newAppt.getApptDate()),
                                 () -> Assertions.assertEquals(apptDesc, newAppt.getApptDesc())
                                );
        }

        //test of deleteAppt() method with valid input
        @Test
        void testDeleteApptValid() {
            testAppt = service.addAppt(apptDate, apptDesc);
            String apptId = testAppt.getApptId();
            Assertions.assertTrue(service.deleteAppt(apptId));
        }

    @Nested
    @Tag("nonvalidTest")
    class nonvalidInputTests{
        
        //tests for the addAppt method
        @Nested
        @Tag("addTests")
        class addApptTests{
            //null and past appointment Date test for addAppt() method
            @Test
            void testaddApptNonValidApptDate(){
                Exception illegalArgumentApptDateNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppt(null, apptDesc));
                Exception illegalArgumentApptDatePast = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppt(new Date(System.currentTimeMillis() - 100), apptDesc));
                String message = "Appointment Date must not be null or in the past!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptDateNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentApptDatePast.getMessage())
                                    );    
            }

            //invalid and null appt description test for addAppt() method
            @Test
            void testAddApptNonValidApptDesc(){
                Exception illegalArgumentApptDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppt(apptDate, null));
                Exception illegalArgumentApptDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addAppt(apptDate, apptDesc + apptDesc));
                String message = "Invalid Appointment Description! Must be a string of 50 characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptDescNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentApptDescTooLong.getMessage())
                                    );
                
            }

            //test for deleteAppt() method with nonexistent appt ID
            @Tag("deleteTests")
            @Test void nonvalidDeleteAppt(){
                Exception nonexistentApptId = Assertions.assertThrows(IllegalArgumentException.class, () -> service.deleteAppt("0"));
                Assertions.assertEquals("Appt ID not found in memory!", nonexistentApptId.getMessage());
            }
        }
    }   
}
}

