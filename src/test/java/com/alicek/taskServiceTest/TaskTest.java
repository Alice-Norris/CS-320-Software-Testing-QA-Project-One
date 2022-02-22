package com.alicek.taskServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInstance;
import com.alicek.taskService.Task;

public class TaskTest {
    //valid parameters for task testing
    String taskName = "Clean-Up Aisle 1";
    String taskDesc = "Need to clean up concrete spill on aisle one";
    String taskId = "abc123";

    @Nested
    @Tag("inputTest")
    @Tag("validTest")
    class ValidInputTests{
            //only one test needed for all valid input
            //testing constructor with valid input
            @Tag("constructorTest")
            @Test
            void testTaskConstructorValidInput() {
                Task testTask = new Task(taskName, taskDesc, taskId);
                Assertions.assertAll(() -> Assertions.assertEquals(taskName, testTask.getTaskName()),
                                     () -> Assertions.assertEquals(taskDesc, testTask.getTaskDesc())
                                    );
            }

            //grouping valid input tests on setters
            @Nested
            @Tag("inputTest")
            @Tag("validTest")
            @Tag("setterTests")
            class ValidSetterTests{
                Task testTask;
                
                //task created with valid parameters to test changes before each test
                @BeforeEach
                void createNewTask() {
                    testTask = new Task(taskName, taskDesc, taskId);
                }

                @Test
                void testsetTaskName() {
                    testTask.setTaskName("Clean-Up Aisle 2");
                    Assertions.assertTrue(testTask.getTaskName().equals("Clean-Up Aisle 2"));
            
                }

                @Test
                void testsetTaskDesc() {
                    testTask.setTaskDesc("Need to clean up concrete spill on aisle two");
                    Assertions.assertTrue(testTask.getTaskDesc().equals("Need to clean up concrete spill on aisle two"));
                }
        }

        //grouping tests of invalid input
        @Nested
        @Tag("inputTest")
        @Tag("nonvalidTest")
        class NonValidInputTests{
            //constructor test, each parameter to be invalid
            @Tag("constructorTest")
            @Nested
            //each test in this group will attempt to create a task using a nonvalid and a null value for task name and task desc
            class taskConstructorNonvalidInputTests{
                //String to hold error messages    
                String message;
                    @Test
                    void testTaskConstructorExceptionTaskName() {
                        Exception illegalArgumentTaskNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(taskName + taskName, taskDesc, taskId));
                        Exception illegalArgumentTaskNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(null, taskDesc, taskId));
                        message = "Invalid Task name! Must be a string of 20 characters or less!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskNameTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentTaskNameNull.getMessage())
                                            );
                    }
        
                    @Test
                    void testTaskConstructorExceptionTaskDesc() {
                        Exception illegalArgumentTaskDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(taskName, taskDesc + taskDesc, taskId));
                        Exception illegalArgumentTaskDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(taskName, null, taskId));
                        message = "Invalid Task Description! Must be a string of 50 characters or less!";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskDescTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentTaskDescNull.getMessage())
                                            );
                    }

                    @Test void testTaskConstructorExceptionTaskId() {
                        Exception illegalArgumentTaskIdNegative = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(taskName, taskDesc, "-34567"));
                        Exception illegalArgumentTaskIdTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(taskName, taskDesc, "12345678901"));
                        Exception nullTaskId = Assertions.assertThrows(IllegalArgumentException.class, () -> new Task(taskName, taskDesc, null));
                        message = "Invalid Task ID! Must be a positive ID string of 10 characters or less.";
                        Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskIdNegative.getMessage()),
                                             () -> Assertions.assertEquals(message, illegalArgumentTaskIdTooLong.getMessage()),
                                             () -> Assertions.assertEquals(message, nullTaskId.getMessage())
                                            );
                    }
            //tests for setters with nonvalid and null inputs for setters.
            @Nested
            class nonvalidSetterTests{

                Task testTask = new Task(taskName, taskDesc, taskId);
                
                @Test
                void testsetTaskNameNonvalid() {
                    Exception illegalArgumentTaskNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> testTask.setTaskName(taskName + taskName));
                    Exception illegalArgumentTaskNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> testTask.setTaskName(null));
                    String message = "Invalid Task name! Must be a string of 20 characters or less!";
                    Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskNameTooLong.getMessage()),
                                            () -> Assertions.assertEquals(message, illegalArgumentTaskNameNull.getMessage())
                                        );
                }
            
                @Test
                void testsetTaskDescNonValid() {
                    Exception illegalArgumentTaskDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> testTask.setTaskDesc(taskDesc + taskDesc));
                    Exception illegalArgumentTaskDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> testTask.setTaskDesc(null));
                    String message = "Invalid Task Description! Must be a string of 50 characters or less!";
                    Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskDescTooLong.getMessage()),
                                            () -> Assertions.assertEquals(message, illegalArgumentTaskDescNull.getMessage())
                                        );
                }
            }
        }
        
        //grouping output tests
        //each simply ensures that the proper values are returned from the testing task created inside the class.
        @Nested
        @Tag("outputTest")
        @TestInstance(Lifecycle.PER_CLASS)
        class OutputTests{
            Task testTask;

            @BeforeAll 
            void createTestTask(){
                testTask = new Task(taskName, taskDesc, taskId);
            }

            @Tag("outputTest")
            @Test
            void testGetTaskName() {
                Assertions.assertTrue(testTask.getTaskName().equals(taskName));
            }

            @Tag("outputTest")
            @Test
            void testGetTaskDesc(){
                Assertions.assertTrue(testTask.getTaskDesc().equals(taskDesc));
            }
            @Tag("outputTest")
            @Test
            void testGetTaskId() {
                Assertions.assertTrue(testTask.getTaskId() instanceof String && testTask.getTaskId().length() <= 10);
            }
        }    
    }}
}

