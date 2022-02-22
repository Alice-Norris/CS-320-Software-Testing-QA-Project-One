package com.alicek.contactServiceTest;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.alicek.contactService.ContactService;
import com.alicek.contactService.Contact;

public class ContactServiceTest {
    //the service to be used as a test fixture
    private static ContactService service;

    //array of strings to hold test values
    static String firstName;
    static String lastName;
    static String phoneNum;
    static String address;

    //setup service at the beginning
    @BeforeAll
    static void setupService(){
        service = new ContactService();
    }

    //reset parameters to valid input before each test
    @BeforeEach
    void setUpParameters() {
        firstName = "Jane";
        lastName = "Doe";
        phoneNum = "5558675309";
        address = "123 District Street";
    }
    //since the service only takes input, no output tests will be written
    //grouping valid tests
    @Nested
    @Tag("validTest")
    class validInputTests{
        Contact testContact;

        //test of addContact() method with valid input
        @Test
        void testAddContactValid() {
            Contact newContact = service.addContact(firstName, lastName, phoneNum, address);
            Assertions.assertAll(() -> Assertions.assertEquals("Jane", newContact.getFirstName()),
                                 () -> Assertions.assertEquals("Doe", newContact.getLastName()),
                                 () -> Assertions.assertEquals("5558675309", newContact.getPhoneNum()),
                                 () -> Assertions.assertEquals("123 District Street", newContact.getAddress())
                                );
        }

        //test of deleteContact() method with valid input
        @Test
        void testDeleteContactValid() {
            testContact = service.addContact(firstName, lastName, phoneNum, address);
            String contactId = testContact.getContactId();
            Assertions.assertTrue(service.deleteContact(contactId));
        }

        //tests of different choices in the updateContact() method with valid inputs
        @Nested
        @Tag("updateTests")
        class validUpdateTests{
            //contact to compare to data retrieved from service.
            Contact testContact;
            //contactId for each input to use
            String contactId;
            //response from update method
            String response;

            //before each test, add a new contact to the service
            @BeforeEach
            void addContactToService(){
                testContact = service.addContact(firstName, lastName, phoneNum, address);
                contactId = testContact.getContactId();
            }

            //after each test, delete the added contact.
            @AfterEach
            void deleteContact(){
                service.deleteContact(contactId);
            }
            
            //test to ensure updated contact first name is saved and retrieved properly
            @Test 
            void testUpdateContactFirstName() {
                response = service.updateContact(contactId, "first", "Janey");
                Assertions.assertTrue(response.equals("Janey"));
            }

            //test to ensure updated contact last name is saved and retrieved properly
            @Test
            void testUpdateContactLastName() {
                response = service.updateContact(contactId, "last", "Doeson");
                Assertions.assertTrue(response.equals("Doeson"));
            }

            //test to ensure updated phone number is saved and retrieved properly
            @Test
            void testUpdateContactPhoneNum() {
                response = service.updateContact(contactId, "phone", "8881234567");
                Assertions.assertTrue(response.equals("8881234567"));
            }

            //test to ensure updated contact address is saved and retrieved properly
            @Test
            void testUpdateContactAddress() {
                response = service.updateContact(contactId, "address", "123 District Road");
                Assertions.assertTrue(response.equals("123 District Road"));
            }
        
        }
    }

    @Nested
    @Tag("nonvalidTest")
    class nonvalidInputTests{
        
        //tests for the addContact method
        @Nested
        @Tag("addTests")
        class addContactTests{
            //invalid first name test for addContact() method
            @Test
            void testaddContactFirstName(){
                Exception illegalArgumentFirstNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(null, lastName, phoneNum, address));
                Exception illegalArgumentFirstNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact("JaneJaneJane", lastName, phoneNum, address));
                String message = "First name invalid, must be a String of ten characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentFirstNameNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentFirstNameTooLong.getMessage())
                                    );    
            }

            //invalid last name test for addContact() method
            @Test
            void testAddContactLastName(){
                Exception illegalArgumentLastNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, null, phoneNum, address));
                Exception illegalArgumentLastNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, "DoeDoeDoeDoe", phoneNum, address));
                String message = "Last name invalid, must be a String of ten characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentLastNameNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentLastNameTooLong.getMessage())
                                    );
                
            }

            //invalid phone number for addContact() method
            @Test
            void testAddContactPhoneNum(){
                Exception illegalArgumentPhoneNumNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, lastName, null, address));
                Exception illegalArgumentPhoneNumTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, lastName, "12345678901", address));
                Exception IllegalArgumentPhoneNumTooShort = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, lastName, "123456789", address));
                String message = "Phone number invalid, must be exactly ten characters!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentPhoneNumNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentPhoneNumTooLong.getMessage()),
                                     () -> Assertions.assertEquals(message, IllegalArgumentPhoneNumTooShort.getMessage())
                );
            }

            //invalid address test for the addContact() method
            @Test 
            void testAddContactAddress(){
                Exception illegalArgumentAddressNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, lastName, phoneNum, null));
                Exception illegalArgumentAddressTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addContact(firstName, lastName, phoneNum, address.repeat(2)));
                String message = "Address invalid, must be a String of thirty characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentAddressNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentAddressTooLong.getMessage())
                );

            }
        }

        //test for deleteContact() method, with nonexistent contactId
        @Tag("deleteTests")
        @Test void nonvalidDeleteContact(){
            Exception nonexistentContactId = Assertions.assertThrows(IllegalArgumentException.class, () -> service.deleteContact("0"));
            Assertions.assertEquals("Contact ID not found in memory!", nonexistentContactId.getMessage());
        }

        //tests for nonvalid input in the updateContact() method
        @Nested
        @Tag("updateTests")
        @TestInstance(Lifecycle.PER_CLASS)
        class nonvalidUpdateTests{
            String contactId;
            Contact testContact;

            //Before all tests, create and add contact for testing, get contactId for update methods to use
            @BeforeAll
            
            void setUp(){
                testContact = service.addContact(firstName, lastName, phoneNum, address);
                contactId = testContact.getContactId();
            }

            //testing null and invalid inputs for first name, and checking error messages.
            @Test
            void testUpdateContactFirstName(){
                Exception IllegalArgumentFirstNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "first", null));
                Exception IllegalArgumentFirstNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "first", "JaneJaneJane"));
                String message = "First name invalid, must be a String of ten characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, IllegalArgumentFirstNameNull.getMessage()),
                                     () -> Assertions.assertEquals(message, IllegalArgumentFirstNameTooLong.getMessage())
                                    );    
            }

            //testing null and invalid inputs for last name, and checking error messages.
            @Test
            void testUpdateContactLastName(){
                Exception illegalArgumentLastNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "last", null));
                Exception illegalArgumentLastNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "last", "DoeDoeDoeDoe"));
                String message = "Last name invalid, must be a String of ten characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentLastNameNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentLastNameTooLong.getMessage())
                                    );
                
            }

            //testing null and invalid inputs for phone number, and checking error messages.
            @Test
            void testUpdateContactPhoneNum(){
                Exception illegalArgumentPhoneNumNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "phone", null));
                Exception illegalArgumentPhoneNumTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "phone", "12345678901"));
                Exception IllegalArgumentPhoneNumTooShort = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "phone", "123456789"));
                String message = "Phone number invalid, must be exactly ten characters!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentPhoneNumNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentPhoneNumTooLong.getMessage()),
                                     () -> Assertions.assertEquals(message, IllegalArgumentPhoneNumTooShort.getMessage())
                                    );
            }

            //testing null and invalid inputs for address, and checking error messages.
            @Test 
            void testUpdateContactAddress(){
                Exception illegalArgumentAddressNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "address", null));
                Exception IllegalArgumentAddressTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "address", "123 District Street 123 District Street"));
                String message = "Address invalid, must be a String of thirty characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentAddressNull.getMessage()),
                                     () -> Assertions.assertEquals(message, IllegalArgumentAddressTooLong.getMessage())
                                    );
            }

            //testing nonvalid choice for updateContact (not "first", "last", "phone", or "address") and null
            @Test
            void testUpdateContactNonValidInput(){
                Exception illegalArgumentNonvalidParameter = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, "payment", "1234567890123456"));
                Exception illegalArgumentNullParameter = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateContact(contactId, null, "1234567890123456"));
                Assertions.assertAll(() -> Assertions.assertEquals("Invalid parameter given to modify. Expected 'first', 'last', 'phone', or 'address', got payment",
                                                                   illegalArgumentNonvalidParameter.getMessage()),

                                     () -> Assertions.assertEquals("Null parameter given. Expected 'first', 'last', 'phone', or 'address'",
                                                                   illegalArgumentNullParameter.getMessage())
                                    );
            }
        }
    }
}


