package com.jwtproject.springboot_security.service;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jwtproject.springboot_security.entity.User;
import com.jwtproject.springboot_security.repository.UserRepository;

@Service
public class UserService {

	UserRepository userRepo;
	PasswordEncoder encoder;

	public UserService(UserRepository userRepo, PasswordEncoder encoder) {
		super();
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	public User addUser(User user, MultipartFile aadhaarImage, MultipartFile panImage) {
		
		User dbUserByUserName = userRepo.findByUsername(user.getUsername());
		User dbUserByMail = userRepo.findByEmail(user.getEmail());
		User savedUser = new User();
		savedUser.setPassword(encoder.encode(user.getPassword()));
		savedUser.setName(user.getName());
		savedUser.setEmail(user.getEmail());
		savedUser.setUsername(user.getUsername());
		savedUser.setRoles(user.getRoles());
		try {
			savedUser.setAadhaarImage(aadhaarImage.getBytes());
			savedUser.setPanImage(panImage.getBytes());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != dbUserByMail && null != dbUserByUserName) {
			updateUser(user, user.getUsername());
		} else {
			savedUser = userRepo.save(savedUser);
		}
		return savedUser;
	}

	public User addUser(User user) {
		User dbUserByUserName = userRepo.findByUsername(user.getUsername());
		User dbUserByMail = userRepo.findByEmail(user.getEmail());
		User savedUser = new User();
		savedUser.setPassword(encoder.encode(user.getPassword()));
		savedUser.setName(user.getName());
		savedUser.setEmail(user.getEmail());
		savedUser.setUsername(user.getUsername());
		savedUser.setRoles(user.getRoles());
		savedUser.setAadhaarImage(user.getAadhaarImage());
		savedUser.setPanImage(user.getPanImage());

		if (null != dbUserByMail && null != dbUserByUserName) {
			updateUser(user, user.getUsername());
		} else {
			savedUser = userRepo.save(savedUser);
		}
		return savedUser;
	}

	public User findUser(String userName) {
		User dbUser = userRepo.findByUsername(userName);
		System.out.println("Get User: "+ dbUser);
		if (dbUser != null)
			return dbUser;
		else
			throw new UsernameNotFoundException("User does not exists");
	}

	public void updateUser(User user, String userName) {
		User updateUser = findUser(userName);
		System.out.println("User to update: " + updateUser);
		if (updateUser != null) {
			updateUser.setName(user.getName());
			updateUser.setPassword(encoder.encode(user.getPassword()));
			updateUser.setRoles(user.getRoles());
			userRepo.save(updateUser);
			System.out.println("Updated User: "+ updateUser);
		} else {
			throw new UsernameNotFoundException("User does not Exists");
		}

	}

	public void deleteUser(String userName) {
		User dbUser = findUser(userName);
		if (dbUser != null) {
			userRepo.delete(dbUser);
		} else {
			throw new UsernameNotFoundException("User does not Exists");
		}
	}
	
	/**
	 * This method is used to decode the image from Base64 string and save it to the
	 * specified output file path.
	 * 
	 * @param encodeToString Base64 encoded string of the image
	 * @param outputFile     Path where the decoded image will be saved
	 * @return Path of the saved image file
	 */
	public Path imagedecode(String encodeToString, String outputFile) {
		try {
			byte[] decodedBytes = java.util.Base64.getDecoder().decode(encodeToString);
			return java.nio.file.Files.write(java.nio.file.Paths.get(outputFile), decodedBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


}
