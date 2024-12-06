package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.UserAccountService;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.shared.dto.LoginDTO;
import com.socialnetwork.connecthub.shared.dto.SignUpDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.shared.exceptions.InvalidLoginException;
import com.socialnetwork.connecthub.shared.exceptions.InvalidSignupException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;
import java.util.Optional;

public class JavaUserAccountService implements UserAccountService {

    @Override
    public void signup(SignUpDTO signUpDTO) throws InvalidSignupException {
        // Validate Email
        if (signUpDTO.getEmail() == null || signUpDTO.getEmail().isEmpty()) {
            throw new InvalidSignupException("Email is required.");
        } else if (!signUpDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")) {
            throw new InvalidSignupException("Write Valid Email Address.");
        }

        // Validate username
        if (signUpDTO.getUsername() == null || signUpDTO.getUsername().isEmpty()) {
            throw new InvalidSignupException("Username is required.");
        }

        // Validate password
        if (signUpDTO.getPassword() == null || signUpDTO.getPassword().isEmpty()) {
            throw new InvalidSignupException("Password is required.");
        }

        // Validate DOB
        if (signUpDTO.getDateOfBirth() == null) {
            throw new InvalidSignupException("Date of birth is required.");
        }

        // Check if the user already exists by email
        Optional<User> existingUser = JsonUserRepository.getInstance().findByEmail(signUpDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new InvalidSignupException("Email already in use.");
        }

        // Create and save new user
        User newUser = new User();
        newUser.setEmail(signUpDTO.getEmail());
        newUser.setUsername(signUpDTO.getUsername());
        newUser.setHashedPassword(hashPassword(signUpDTO.getPassword()));
        newUser.setDateOfBirth(signUpDTO.getDateOfBirth());
        newUser.setOnlineStatus(false); // Default to offline

        JsonUserRepository.getInstance().save(newUser);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws InvalidLoginException {
        // Validate Email
        if (loginDTO.getEmail() == null || loginDTO.getEmail().isEmpty()) {
            throw new InvalidLoginException("Email is required.");
        } else if (!loginDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")) {
            throw new InvalidLoginException("Write Valid Email Address.");
        }

        // Validate password
        if (loginDTO.getPassword() == null || loginDTO.getPassword().isEmpty()) {
            throw new InvalidLoginException("Password is required.");
        }

        // Find the user by email
        Optional<User> userOpt = JsonUserRepository.getInstance().findByEmail(loginDTO.getEmail());
        if (userOpt.isEmpty()) {
            throw new InvalidLoginException("Email not found.");
        }

        User user = userOpt.get();

        // Verify the password
        if (!BCrypt.checkpw(loginDTO.getPassword(), user.getHashedPassword())) {
            throw new InvalidLoginException("Wrong password.");
        }

        // Update user status to online
        updateUserStatus(user.getUserId(), true);

        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getProfilePhotoPath(),
                user.getCoverPhotoPath(),
                user.getBio(),
                true); // Update user status to online
    }

    @Override
    public void logout(String userId) {
        // Update user status to offline
        updateUserStatus(userId, false);
    }

    @Override
    public String hashPassword(String password) {
        // Hash the password using Bcrypt
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public void updateUserStatus(String userId, boolean onlineStatus) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.setOnlineStatus(onlineStatus);
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public UserDTO getUserById(String userId) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        return new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getProfilePhotoPath(),
                user.getCoverPhotoPath(),
                user.getBio(),
                user.isOnlineStatus());
    }
}
