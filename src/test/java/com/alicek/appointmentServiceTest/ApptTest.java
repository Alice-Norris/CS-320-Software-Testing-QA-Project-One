package com.alicek.appointmentServiceTest;
import java.util.Date;
import com.alicek.appointmentService.Appt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;

public class ApptTest {
    //valid parameters for appointment testing
    Date apptDate = new Date(System.currentTimeMillis() + 100); //Date set slightly in the future so the date doesn't get older than now before use
    String apptDesc = "Appointment for initial consultation of client";
    String apptId = "abc123";

    @Nested
    @Tag("inputTest")
    @Tag("validTest")
    class ValidInputTests{
            //only one test needed for all valid input
            //testing constructor with valid input
            @Tag("constructorTest")
            @Test
            void testApptConstructorValidInput() {
                Appt testAppt = new Appt(apptDate, apptDesc, apptId);
                Assertions.assertAll(() -> Assertions.assertEquals(apptDate, testAppt.getApptDate()),
                                     () -> Assertions.assertEquals(apptDesc, testAppt.getApptDesc()),
                                     () -> Assertions.assertEquals(apptId, testAppt.getApptId())
                                    );
            }

            //grouping valid input tests on setters
            @Nested
            @Tag("inputTest")
            @Tag("validTest")
            @Tag("setterTests")
            class ValidSetterTests{
                Appt testAppt;
                
                //appt created with valid parameters to test changes before each test
                @BeforeEach
                void createNewAppt() {
                    testAppt = new Appt(apptDate, apptDesc, apptId);
                }
                //testing that updated Appointment Date is saved and retrieved properly
                @Test
                void testSetApptDate() {
                    Date updatedDate = new Date(System.currentTimeMillis() + 1000);
                    testAppt.setApptDate(updatedDate);
                    Assertions.assertTrue(testAppt.getApptDate().equals(updatedDate));
            
                }

                //testing that updated Appointment Description is saved and retrieved properly
                @Test
                void testSetApptDesc() {
                    testAppt.setApptDesc("Appt for third consultation of client");
                    Assertions.assertTrue(testAppt.getApptDesc().equals("Appt for third consultation of client"));
                }
        }

        //grouping tests of invalid input
        @Nested
        @Tag("inputTest")
        @Tag("nonvalidTest")
        class NonValidInputTests{
            //constructor each parameter to be invalid
            @Tag("constructorTest")
            @Nested
            //each test in this group will attempt to create an appointment using a nonvalid and a null value for appointment name, appointment desc, and appt ID
            class apptConstructorNonvalidInputTests{
                //String to hold error messages    
                String message;
                    @Test
                    //Tests for Appt dates in the past and null
                    void testApptConstructorExceptionApptDate() {
                        Exception illegalArgumentApptDatePast = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(new Date(System.currentTimeMillis() - 1000), apptDesc, apptId));
                        Exception illegalArgumentApptDateNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(null, apptDesc, apptId));
                        message = "Appointment Date must not be null or in the past!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptDatePast.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentApptDateNull.getMessage())
                                            );
                    }
                    //Tests for null and too long descriptions
                    @Test
                    void testApptConstructorExceptionApptDesc() {
                        Exception illegalArgumentApptDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(apptDate, apptDesc + apptDesc, apptId));
                        Exception illegalArgumentApptDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(apptDate, null, apptId));
                        message = "Invalid Appointment Description! Must be a string of 50 characters or less!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptDescTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentApptDescNull.getMessage())
                                            );
                    }

                    //Tests for appt ID negative, too long, or null
                    @Test
                    void testApptConstructorExceptionApptId() {
                        Exception illegalArgumentApptIdNegative = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(apptDate, apptDesc, "-34567"));
                        Exception illegalArgumentApptIdTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(apptDate, apptDesc, "12345678901"));
                        Exception illegalArgumentApptIdNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Appt(apptDate, apptDesc, null));
                        message = "Invalid Appointment ID! Must be a positive ID string of 10 characters or less.";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptIdNegative.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentApptIdTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentApptIdNull.getMessage())
                                             );
                    }
            //tests for setters with nonvalid and null inputs for setters.
            @Nested
            class nonvalidSetterTests{

                Appt testAppt = new Appt(apptDate, apptDesc, apptId);
                //tests for Appointment Dates in the past and null
                @Test
                void testsetApptDateNonvalid() {
                    Exception illegalArgumentApptDatePast = Assertions.assertThrows(IllegalArgumentException.class, () -> testAppt.setApptDate(new Date(-1000)));
                    Exception illegalArgumentApptDateNull = Assertions.assertThrows(IllegalArgumentException.class, () -> testAppt.setApptDate(null));
                    String message = "Appointment Date must not be null or in the past!";
                    Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptDatePast.getMessage()),
                                            () -> Assertions.assertEquals(message, illegalArgumentApptDateNull.getMessage())
                                        );
                }
                //tests for Appoint Description too long and null
                @Test
                void testSetApptDescNonValid() {
                    Exception illegalArgumentApptDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> testAppt.setApptDesc(apptDesc + apptDesc));
                    Exception illegalArgumentApptDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> testAppt.setApptDesc(null));
                    String message = "Invalid Appointment Description! Must be a string of 50 characters or less!";
                    Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentApptDescTooLong.getMessage()),
                                            () -> Assertions.assertEquals(message, illegalArgumentApptDescNull.getMessage())
                                        );
                }
            }
        }
        
            //grouping output tests
            //each simply ensures that the proper values are returned from the testing appointment created inside the class.
            @Nested
            @Tag("outputTest")
            @TestInstance(Lifecycle.PER_CLASS)
            class OutputTests{
                Appt testAppt;

                @BeforeAll 
                void createTestAppt(){
                    testAppt = new Appt(apptDate, apptDesc, apptId);
                }

                @Tag("outputTest")
                @Test
                void testGetApptDate() {
                    Assertions.assertTrue(testAppt.getApptDate().equals(apptDate));
                }

                @Tag("outputTest")
                @Test
                void testGetApptDesc(){
                    Assertions.assertTrue(testAppt.getApptDesc().equals(apptDesc));
                }
                @Tag("outputTest")
                @Test
                void testGetApptId() {
                    Assertions.assertTrue(testAppt.getApptId() instanceof String && testAppt.getApptId().length() <= 10);
                }
            }    
        }
    }
}

