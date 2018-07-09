package com.auth0.samples.authapi.task;

import com.auth0.samples.authapi.user.ApplicationUser;
import com.auth0.samples.authapi.user.ApplicationUserRepository;
import com.auth0.samples.authapi.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

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
	public Response addTask(@RequestBody Task task) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser applicationUser = applicationUserRepository.findByEmail(auth.getName());
		task.setOwner(applicationUser);
		if(applicationUser != null) {
			Task result = taskRepository.save(task);
			if(result != null) {
				return new Response("Item created", false, result);
			} else {
				return new Response("Database error - Item not saved", true);
			}
		} else {
			return new Response("Please login", true);
		}
	}

	@GetMapping
	public List<Task> getTasks() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser applicationUser = applicationUserRepository.findByEmail(auth.getName());
		return taskRepository.findByOwner(applicationUser);
	}

	@PutMapping("/{id}")
	public Response editTask(@PathVariable long id, @RequestBody Task task) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser applicationUser = applicationUserRepository.findByEmail(auth.getName());
		Task existingTask = taskRepository.findById(id);
		Assert.notNull(existingTask, "Task not found");

		if(existingTask.getOwner().getId()==applicationUser.getId()) {
			existingTask.setDescription(task.getDescription());
			taskRepository.save(existingTask);
			return new Response("Task updated",false, existingTask);
		} else {
			return new Response("You are not the owner of the task", true);
		}
	}

	@DeleteMapping("/{id}")
	public Response deleteTask(@PathVariable long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ApplicationUser applicationUser = applicationUserRepository.findByEmail(auth.getName());
		Task existingTask = taskRepository.findById(id);
		Assert.notNull(existingTask, "Task not found");

		if(existingTask.getOwner().getId()==applicationUser.getId()) {
			taskRepository.delete(existingTask);
			return new Response("Task deleted",false);
		} else {
			return new Response("You are not the owner of the task", true);
		}
	}
}
