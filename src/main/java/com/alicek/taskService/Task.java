package com.alicek.taskService;

public class Task {
    //private fields for tasks
    private String taskId = "";
    private String taskName = "";
    private String taskDesc = "";
    
    //Constructor for task objects. Takes two strings from user input (taskName and taskDesc),
    //and the task ID from service's generateTaskId()
    public Task(String taskName, String taskDesc, String taskId) {

        //using setter validation in constructor
        this.setTaskName(taskName);
        this.setTaskDesc(taskDesc);
        
        //no setter, testing conditions here, taskId can't be null, longer than 10 characters, or negative
        if (taskId == null || taskId.length() > 10 || Long.parseLong(taskId, 36) < 0) {
            throw new IllegalArgumentException("Invalid Task ID! Must be a positive ID string of 10 characters or less.");
        }
        this.taskId = taskId;
    }

    //Getters and setters follow past this point
    //All setters check for valid input, to ensure that a task isn't updated with invalid data.
    public String getTaskName() {
        return this.taskName;
    }
    
    public void setTaskName(String taskName) {
        if (taskName == null || taskName.length() > 20) {
            throw new IllegalArgumentException("Invalid Task name! Must be a string of 20 characters or less!");
        }
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return this.taskDesc;
    }
    
    public void setTaskDesc(String taskDesc) {
        if (taskDesc == null || taskDesc.length() > 50) {
            throw new IllegalArgumentException("Invalid Task Description! Must be a string of 50 characters or less!");
        }
        this.taskDesc = taskDesc;
    }
    
    public String getTaskId() {
        return this.taskId;
    }
}
