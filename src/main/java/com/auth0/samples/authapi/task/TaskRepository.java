package com.auth0.samples.authapi.task;

import com.auth0.samples.authapi.user.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByOwner(ApplicationUser applicationUser);
}
