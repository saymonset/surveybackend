package com.auth0.samples.authapi.springbootauthupdated.task;

import java.util.ArrayList;
import java.util.List;

import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.MyService;

@RestController
@RequestMapping("/taskstest")
public class TaskTestController {
	 
    private TaskTestRepository tasktestRepository;

    public TaskTestController(TaskTestRepository taskRepository) {
        this.tasktestRepository = taskRepository;
    }

    @PostMapping
    public void addTask(@RequestBody TaskTest task) {
        tasktestRepository.save(task);
    }

    @GetMapping
    public List<TaskTest> getTasks() {
        return tasktestRepository.findAll();
    }
    
  
    
    
 
 
    @PutMapping("/{id}")
    public void editTask(@PathVariable long id, @RequestBody TaskTest task) {
        TaskTest existingTask = tasktestRepository.findById(id).get();
        Assert.notNull(existingTask, "Task not found");
        existingTask.setDescription(task.getDescription());
        tasktestRepository.save(existingTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable long id) {
        TaskTest taskToDel = tasktestRepository.findById(id).get();
        tasktestRepository.delete(taskToDel);
    }
    
   
}