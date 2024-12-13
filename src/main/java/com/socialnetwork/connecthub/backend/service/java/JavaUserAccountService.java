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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JavaUserAccountService implements UserAccountService {
    private static JavaUserAccountService instance;

    private JavaUserAccountService() {
        // Private constructor to prevent instantiation
    }

    public static synchronized JavaUserAccountService getInstance() {
        if (instance == null) {
            instance = new JavaUserAccountService();
        }
        return instance;
    }

    @Override
    public void signup(SignUpDTO signUpDTO) throws InvalidSignupException {
        // Validate username
        if (signUpDTO.getUsername() == null || signUpDTO.getUsername().isEmpty()) {
            throw new InvalidSignupException("Username is required.");
        }

        // Validate Email
        if (signUpDTO.getEmail() == null || signUpDTO.getEmail().isEmpty()) {
            throw new InvalidSignupException("Email is required.");
        } else if (!signUpDTO.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$")) {
            throw new InvalidSignupException("Write Valid Email Address.");
        } else if (JsonUserRepository.getInstance().findByEmail(signUpDTO.getEmail()).isPresent()) {
            throw new InvalidSignupException("Email is already in use.");
        }

        // Validate password
        if (signUpDTO.getPassword() == null || signUpDTO.getPassword().isEmpty()) {
            throw new InvalidSignupException("Password is required.");
        } else if (!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())) {
            throw new InvalidSignupException("Passwords do not match.");
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

        newUser.setFriends(new ArrayList<>());
        newUser.setPosts(new ArrayList<>());
        newUser.setStories(new ArrayList<>());
        newUser.setGroups(new ArrayList<>());

        newUser.setProfilePhotoPath("src/main/java/com/socialnetwork/connecthub/resources/pics/default-profile.jpg");
        newUser.setBio("");

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

        return new UserDTO(user).setOnlineStatus(true); // Update user status to online using builder
    }

    @Override
    public void logout(String userId) {
        // Update user status to offline
        updateUserStatus(userId, false);
        JsonUserRepository.getInstance().save(JsonUserRepository.getInstance().findById(userId).orElseThrow());
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
        return new UserDTO(user);
    }

    @Override
    public List<UserDTO> getUsersByUsername(String userId, String username) {
        List<User> users = JsonUserRepository.getInstance().findByUsername(username);
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            if (!user.getUserId().equals(userId))
                userDTOs.add(new UserDTO(user));
        }

        return userDTOs;
    }
}
