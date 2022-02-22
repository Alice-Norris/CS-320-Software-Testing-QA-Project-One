package com.alicek.taskServiceTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import com.alicek.taskService.TaskService;
import com.alicek.taskService.Task;

@TestInstance(Lifecycle.PER_CLASS)
public class TaskServiceTest {
    //the service to be used as a test fixture
    private TaskService service;

    //array of strings to hold test values
    String taskName;
    String taskDesc;

    //setup service at the beginning
    @BeforeAll
    
    void setupService(){
        service = new TaskService();
    }

    //reset parameters to valid input before each test
    @BeforeEach
    void setUpParameters() {
        taskName = "Clean-Up Aisle 1";
        taskDesc = "Need to clean up concrete spill on aisle one";
    }
    
    //since the service only takes input, no output tests will be written
    //grouping valid tests
    @Nested
    @Tag("validTest")
    class validInputTests{
        Task testTask;

        //test of addTask() method with valid input
        @Test
        void testAddTaskValid() {
            Task newTask = service.addTask(taskName, taskDesc);
            Assertions.assertAll(() -> Assertions.assertEquals(taskName, newTask.getTaskName()),
                                 () -> Assertions.assertEquals(taskDesc, newTask.getTaskDesc())
                                );
        }

        //test of deleteTask() method with valid input
        @Test
        void testDeleteTaskValid() {
            testTask = service.addTask(taskName, taskDesc);
            String taskId = testTask.getTaskId();
            Assertions.assertTrue(service.deleteTask(taskId));
        }

        //tests of different choices in the updateTask() method with valid inputs
        @Nested
        @Tag("updateTests")
        class validUpdateTests{
            //task to compare to data retrieved from service.
            Task testTask;
            //taskId for each input to use
            String taskId;
            //response from update method
            String response;

            //before each test, add a new task to the service
            @BeforeEach
            void addTaskToService(){
                testTask = service.addTask(taskName, taskDesc);
                taskId = testTask.getTaskId();
            }

            //after each test, delete the added task.
            @AfterEach
            void deleteTask(){
                service.deleteTask(taskId);
            }
            
            @Test 
            void testUpdateTaskTaskName() {
                response = service.updateTask(taskId, "name", "Clean-Up Aisle 2");
                Assertions.assertTrue(response.equals("Clean-Up Aisle 2"));
            }

            @Test
            void testUpdateTaskTaskDesc() {
                response = service.updateTask(taskId, "desc", "Need to clean up concrete spill on aisle two");
                Assertions.assertTrue(response.equals("Need to clean up concrete spill on aisle two"));
            }
        }
    }

    @Nested
    @Tag("nonvalidTest")
    class nonvalidInputTests{
        
        //tests for the addTask method
        @Nested
        @Tag("addTests")
        class addTaskTests{
            //invalid task name test for addTask() method
            @Test
            void testaddTaskTaskName(){
                Exception illegalArgumentTaskNameNull= Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(null, taskDesc));
                Exception illegalArgumentTaskNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(taskName + taskName, taskDesc));
                String message = "Invalid Task name! Must be a string of 20 characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskNameNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentTaskNameTooLong.getMessage())
                                    );    
            }

            //invalid task description test for addTask() method
            @Test
            void testAddTaskNullTaskDesc(){
                Exception illegalArgumentTaskDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(taskName, null));
                Exception illegalArgumentTaskDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.addTask(taskName, taskDesc + taskDesc));
                String message = "Invalid Task Description! Must be a string of 50 characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskDescNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentTaskDescTooLong.getMessage())
                                    );
                
            }

        //test for deleteTask() method, with a nonexistent task ID
        @Tag("deleteTests")
        @Test void nonvalidDeleteTask(){
            Exception nonexistentTaskId = Assertions.assertThrows(IllegalArgumentException.class, () -> service.deleteTask("0"));
            Assertions.assertAll(() -> Assertions.assertEquals("Task ID not found in memory!", nonexistentTaskId.getMessage()));
        }

        //tests for nonvalid input in the updateTask() method
        @Nested
        @Tag("updateTests")
        @TestInstance(Lifecycle.PER_CLASS)
        class nonvalidUpdateTests{
            String taskId;
            Task testTask;

            //Before all tests, create and add task for testing, get taskId for update methods to use
            @BeforeAll
            void setUp(){
                testTask = service.addTask(taskName, taskDesc);
                taskId = testTask.getTaskId();
            }

            //testing null and invalid inputs for task name, and checking error messages.
            @Test
            void testUpdateTaskTaskName(){
                Exception illegalArgumentTaskNameNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateTask(taskId, "name", null));
                Exception illegalArgumentTaskNameTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateTask(taskId, "name", taskName + taskName));
                String message = "Invalid Task name! Must be a string of 20 characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskNameNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentTaskNameTooLong.getMessage())
                                    );    
            }

            //testing null and invalid inputs for task description, and checking error messages.
            @Test
            void testUpdateTaskTaskDesc(){
                Exception illegalArgumentTaskDescNull = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateTask(taskId, "desc", null));
                Exception illegalArgumentTaskDescTooLong = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateTask(taskId, "desc", taskDesc + taskDesc));
                String message = "Invalid Task Description! Must be a string of 50 characters or less!";
                Assertions.assertAll(() -> Assertions.assertEquals(message, illegalArgumentTaskDescNull.getMessage()),
                                     () -> Assertions.assertEquals(message, illegalArgumentTaskDescTooLong.getMessage())
                                    );                
            }
            //testing nonvalid choice for updateContact (not "first", "last", "phone", or "address") and null
            @Test
            void testUpdateTaskNonValidInput(){
                Exception illegalArgumentNonvalidParameter = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateTask(taskId, "payment", "1234567890123456"));
                Exception illegalArgumentNullParameter = Assertions.assertThrows(IllegalArgumentException.class, () -> service.updateTask(taskId, null, "1234567890123456"));
                Assertions.assertAll(() -> Assertions.assertEquals("Invalid parameter given to modify. Expected 'task' or 'desc', got payment",
                                                                   illegalArgumentNonvalidParameter.getMessage()),

                                     () -> Assertions.assertEquals("Null parameter given. Expected 'name' or 'desc'",
                                                                   illegalArgumentNullParameter.getMessage())
                                    );
            }
        }
    }
}


}