package com.alicek.taskService;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.LongStream;

//Task service meant to provide creation, update, and deletion of tasks
public class TaskService {
    
    //in-memory task storage
    HashMap<String, Task> tasks = new HashMap<String, Task>();

    //create LongStream of random Longs within the bounds. The upper bound is the max decimal number
    //that can be represented by an alphanumeric (base 36) number.
    LongStream apptIdLongGenerator = new Random().longs(0L, 3656158440062975L);

    private String generateTaskId() {
        while(true){
            //create LongStream of random Longs within the bounds. The upper bound is the max decimal number
            //that can be represented by an alphanumeric (base 36) number.
            LongStream apptIdLongGenerator = new Random().longs(0L, 3656158440062975L);

            //get the first stable result of the generator as a Long type.
            Long taskIdLong = apptIdLongGenerator.findFirst().getAsLong();
            String taskId = Long.toString(taskIdLong, 36);
            if (!tasks.containsKey(taskId)){
                return taskId;
            }
        }
    }

    //method for adding a Task. Takes three strings, only two of which are supplied by user input
    public Task addTask(String taskName, String taskDesc) {
        String taskId = this.generateTaskId();
        Task addedTask = new Task(taskName, taskDesc, taskId);
        tasks.put(taskId, addedTask);
        return addedTask;
    }

    //Method used to test tasks ids and ensure they both are valid and exist in the task hashmap
    private void testTaskId(String taskId){

        //throw an exception if the taskId isn't found in memory.
        if (!tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("Task ID not found in memory!");
        }

    }

    //Method used to delete a task from in-memory storage (hashmap)
    //tests task ID first, so exception is thrown if necessary.
    //returns "true" if task was deleted and "false" if not.
    public Boolean deleteTask(String taskId) {
        Boolean deleted = false;
        testTaskId(taskId);  
        tasks.remove(taskId);

        if (tasks.containsKey(taskId) == false){
            deleted = true;
        }
        return deleted;
    
    }

    //Method used to update tasks. Throws an error if a supplied action isn't supported.
    public String updateTask(String taskId, String parameter, String newValue){
        testTaskId(taskId);
        Task task = tasks.get(taskId);
        if (parameter == null){
            throw new IllegalArgumentException("Null parameter given. Expected 'name' or 'desc'");
        }
        switch (parameter){
            case "name":
                task.setTaskName(newValue);
                return task.getTaskName();
            case "desc":
                task.setTaskDesc(newValue);
                return task.getTaskDesc();
            default:
                throw new IllegalArgumentException(String.format(
                    "Invalid parameter given to modify. Expected 'task' or 'desc', got %s", parameter));
        }

    }

}
    
