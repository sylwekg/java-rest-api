package com.auth0.samples.authapi.task;

import com.auth0.samples.authapi.user.ApplicationUser;
import com.auth0.samples.authapi.user.ApplicationUserRepository;
import com.auth0.samples.authapi.user.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private TaskRepository taskRepository;

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@PostMapping
	public Response addTask(@RequestBody Task task, Principal principal) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser applicationUser = applicationUserRepository.findByEmail(auth.getName());
		task.setOwner(applicationUser);
		Task result = taskRepository.save(task);
		if(result != null) {
			return new Response("Item created", false, result);
		} else {
			return new Response("Database error - Item not saved", true);
		}
	}

	@GetMapping
	public List<Task> getTasks() {
		return taskRepository.findAll();
	}

	@PutMapping("/{id}")
	public void editTask(@PathVariable long id, @RequestBody Task task) {
		Task existingTask = taskRepository.findOne(id);
		Assert.notNull(existingTask, "Task not found");
		existingTask.setDescription(task.getDescription());
		taskRepository.save(existingTask);
	}

	@DeleteMapping("/{id}")
	public void deleteTask(@PathVariable long id) {
		taskRepository.delete(id);
	}
}
