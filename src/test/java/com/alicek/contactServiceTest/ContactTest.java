package com.alicek.contactServiceTest;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import com.alicek.contactService.Contact;

public class ContactTest {
    //valid parameters to be used for tests of valid input.
    static String firstName = "Jane";
    static String lastName = "Doe";
    static String phoneNum = "5558675309";
    static String address = "123 District Street";
    static String contactId = "1234567890";

    @Nested
    @Tag("inputTest")
    @Tag("validTest")
    class ValidInputTests{
            //only one test needed for all valid input, all should be as given in validParameters
            //testing constructor with valid input
            @Tag("constructorTest")
            @Test
            void testContactConstructorValidInput() {
                Contact testContact = new Contact(firstName, lastName, phoneNum, address, contactId);
                Assertions.assertAll(() -> Assertions.assertEquals("Jane", testContact.getFirstName()),
                                     () -> Assertions.assertEquals("Doe", testContact.getLastName()),
                                     () -> Assertions.assertEquals("5558675309", testContact.getPhoneNum()),
                                     () -> Assertions.assertEquals("123 District Street", testContact.getAddress())
                                    );
            }

            //grouping valid input tests on setters
            @Nested
            @Tag("inputTest")
            @Tag("validTest")
            @Tag("setterTests")
            class ValidSetterTests{
                Contact testContact;
                
                //contact created with valid parameters to test changes before each test
                @BeforeEach
                void createNewContact() {
                    testContact = new Contact(firstName, lastName, phoneNum, address, contactId);
                }
                @Test
                void testSetFirstName() {
                    testContact.setFirstName("Janey");
                    Assertions.assertTrue(testContact.getFirstName().equals("Janey"));
            
                }

                @Test
                void testSetLastName() {
                    testContact.setLastName("Doeson");
                    Assertions.assertTrue(testContact.getLastName().equals("Doeson"));
                }

                @Test
                void testSetPhoneNum() {
                    testContact.setPhoneNum("5551234567");
                    Assertions.assertTrue(testContact.getPhoneNum().equals("5551234567"));

                }

                @Test
                void testSetAddress() {
                    testContact.setAddress("12345 District Court");
                    Assertions.assertTrue(testContact.getAddress().equals("12345 District Court"));
                }
            }

        }

        //grouping tests of invalid input
        @Nested
        @Tag("inputTest")
        @Tag("nonvalidTest")
        class NonValidInputTests{
            //constructor tests for each arraylist member being invalid
            @Tag("constructorTest")
            @Nested
            //each test in this group will attempt to create a contact using a nonvalid and a null value for each expected member
            class contactConstructorNonvalidInputTests{
                //String to hold error messages    
                String message;
                    @Test
                    void testContactConstructorExceptionFirstName() {
                        Exception illegalArgumentFirstNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> new Contact(firstName.repeat(3), lastName, phoneNum, address, contactId));
                        Exception illegalArgumentFirstNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact(null, lastName, phoneNum, address, contactId));
                        message = "First name invalid, must be a String of ten characters or less!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentFirstNameTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentFirstNameNull.getMessage())
                                            );
                    }
        
                    @Test
                    void testContactConstructorExceptionLastName() {
                        Exception illegalArgumentLastNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> new Contact(firstName, lastName.repeat(4), phoneNum, address, contactId));
                        Exception illegalArgumentLastNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, null, phoneNum, address, contactId));
                        message = "Last name invalid, must be a String of ten characters or less!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentLastNameTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentLastNameNull.getMessage())
                                            );
                    }
        
                    @Test
                    void testContactConstructorExceptionPhone() {
                        Exception illegalArgumentPhoneNumTooShort = Assertions.assertThrows(IllegalArgumentException.class, ()-> new Contact(firstName, lastName, "555867530", address, contactId));
                        Exception illegalArgumentPhoneNumTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> new Contact(firstName, lastName, "55586753091", address, contactId));
                        Exception illegalArgumentPhoneNumNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, lastName, null, address, contactId));
                        message = "Phone number invalid, must be exactly ten characters!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentPhoneNumTooShort.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentPhoneNumTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentPhoneNumNull.getMessage())
                                            );
                    }

                    @Test
                    void testContactConstructorExceptionAddress() {
                        Exception illegalArgumentAddressTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> new Contact(firstName, lastName, phoneNum, address.repeat(2), contactId));
                        Exception illegalArgumentAddressNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, lastName, phoneNum, null, contactId));
                        message = "Address invalid, must be a String of thirty characters or less!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentAddressTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentAddressNull.getMessage())
                                            );
                    }

                    @Test
                    void testContactConstructorExceptionContactId(){
                        Exception illegalArgumentTaskIdNegative = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, lastName, phoneNum, address, "-34567"));
                        Exception illegalArgumentTaskIdTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact(firstName, lastName, phoneNum, address, "12345678901"));
                        Exception illegalArgumentTaskIdNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Contact (firstName, lastName, phoneNum, address, null));
                        message = "Invalid Task ID! Must be a string of 10 characters or less.";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskIdNegative.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentTaskIdTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentTaskIdNull.getMessage())
                                            );
                    }
                }
            
            //tests for setters with nonvalid and null inputs for setters.
            @Nested
            class nonvalidSetterTests{

                        Contact testContact = new Contact(firstName, lastName, phoneNum, address, contactId);
                @Test
                void testSetAddressNonvalid(){
                    Exception illegalArgumentAddressTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> testContact.setAddress(address.repeat(3)));
                    Exception illegalArgumentAddressNull = Assertions.assertThrows(IllegalArgumentException.class, ()-> testContact.setAddress(null));
                    String message = "Address invalid, must be a String of thirty characters or less!";
                    Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentAddressTooLong.getMessage()),
                                            () -> Assertions.assertEquals(message, illegalArgumentAddressNull.getMessage())
                                        );
                }
            
                @Test
                void testSetFirstNameNonvalid() {
                    Exception illegalArgumentFirstNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> testContact.setFirstName(firstName.repeat(3)));
                    Exception illegalArgumentFirstNameNull = Assertions.assertThrows(IllegalArgumentException.class, ()-> testContact.setFirstName(null));
                    String message = "First name invalid, must be a String of ten characters or less!";
                    Assertions.assertAll(()->Assertions.assertEquals(message, illegalArgumentFirstNameTooLong.getMessage()),
                                            ()->Assertions.assertEquals(message, illegalArgumentFirstNameNull.getMessage())
                                        );
                }
            
                @Test
                void testSetLastNameNonValid() {
                    Exception illegalArgumentLastNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()-> testContact.setLastName(lastName.repeat(4)));
                    Exception illegalArgumentLastNameNull = Assertions.assertThrows(IllegalArgumentException.class, ()-> testContact.setLastName(null));
                    String message = "Last name invalid, must be a String of ten characters or less!";
                    Assertions.assertAll(()->Assertions.assertEquals(message, illegalArgumentLastNameTooLong.getMessage()),
                                            ()->Assertions.assertEquals(message, illegalArgumentLastNameNull.getMessage())
                                        );
                }
            
                @Test
                void testSetPhoneNumNonValid() {
                    Exception illegalArgumentPhoneNumTooLong = Assertions.assertThrows(IllegalArgumentException.class, ()->testContact.setPhoneNum("12345678901"));
                    Exception illegalArgumentPhoneNumTooShort = Assertions.assertThrows(IllegalArgumentException.class, ()->testContact.setPhoneNum("123456789"));
                    Exception illegalArgumentPhoneNumNull = Assertions.assertThrows(IllegalArgumentException.class, ()->testContact.setPhoneNum(null));
                    String message = "Phone number invalid, must be exactly ten characters!";
                    Assertions.assertAll(()-> Assertions.assertEquals(message, illegalArgumentPhoneNumTooLong.getMessage()),
                                            ()-> Assertions.assertEquals(message, illegalArgumentPhoneNumTooShort.getMessage()),
                                            ()-> Assertions.assertEquals(message, illegalArgumentPhoneNumNull.getMessage())
                                        );              
                }
            }
        }
        
        //grouping output tests
        //each simply ensures that the proper values are returned from the testing contact created inside the class.
        @Nested
        @Tag("outputTest")
        @TestInstance(Lifecycle.PER_CLASS)
        class OutputTests{
            Contact testContact;

            @BeforeAll 
            void createTestContact(){
                testContact = new Contact(firstName, lastName, phoneNum, address, contactId);
            }

            @Tag("outputTest")
            @Test
            void testGetAddress() {
                Assertions.assertTrue(testContact.getAddress().equals(address));
            }

            @Tag("outputTest")
            @Test
            void testGetContactId() {
                Assertions.assertTrue(testContact.getContactId() instanceof String && testContact.getContactId().length() <= 10);
            }

            @Tag("outputTest")
            @Test
            void testGetFirstName() {
                Assertions.assertTrue(testContact.getFirstName().equals(firstName));
            }

            @Tag("outputTest")
            @Test
            void testGetLastName() {
                Assertions.assertTrue(testContact.getLastName().equals(lastName));
            }

            @Tag("outputTest")
            @Test
            void testGetPhoneNum() {
                Assertions.assertTrue(testContact.getPhoneNum().equals(phoneNum));

            }
        }    
}

