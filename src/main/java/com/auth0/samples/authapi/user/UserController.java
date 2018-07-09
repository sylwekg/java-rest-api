package com.auth0.samples.authapi.user;

import com.auth0.samples.authapi.util.Response;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	private ApplicationUserRepository applicationUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(ApplicationUserRepository applicationUserRepository,
						  BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.applicationUserRepository = applicationUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@PostMapping("/sign-up")
	public Response signUp(@RequestBody @Valid ApplicationUser user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new Response(bindingResult);
		}
		ApplicationUser existingUser = applicationUserRepository.findByEmail(user.getEmail());
		if( existingUser == null){
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			ApplicationUser result = applicationUserRepository.save(user);
			return new Response("User created",false, result);
		} else {
			return new Response("User e-mail already exist.",true);
		}
	}
}
