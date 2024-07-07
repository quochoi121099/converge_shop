package com.studyprogramming.service;

import com.studyprogramming.entity.User;
import com.studyprogramming.payload.UserCreationPayload;
import com.studyprogramming.repository.UserRepository;
import com.studyprogramming.service.impl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.studyprogramming.constant.ConstantImage.LINK_IMAGES_USER;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * Thêm một User mới vào danh sách công việc cần làm
     *
     * @return Trả về đối tượng User sau khi lưu vào DB, trả về null nếu không thành công
     */
    public User createUser(UserCreationPayload user) throws IOException {
        User existingUser = userRepository.findByEmail(user.getEmail());
        // BCryptPasswordEncoder
        String salt = BCrypt.gensalt();
        String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
        // image
        String[] fileName = fileStorageService.storeUserFiles(user.getImages()).toArray(new String[0]);
        // Save
        if (existingUser == null) {
            User userEntity = new User();
            userEntity.setImageNames(fileName);


            userEntity.setUserName(user.getUserName());
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            userEntity.setRole(user.getRole());
            userEntity.setPhoneNumber(user.getPhoneNumber());
            userEntity.setEmail(user.getEmail());
            userEntity.setAddress(user.getAddress());
            userEntity.setPassword(hashedPassword);
            userEntity.setGender(user.getGender());
            userEntity.setDateOfBirth(user.getDateOfBirth());
            return userRepository.save(userEntity);
        }else{

            existingUser.setImageNames(fileName);

            existingUser.setUserName(user.getUserName());
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setRole(user.getRole());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            existingUser.setEmail(user.getEmail());
            existingUser.setAddress(user.getAddress());
            existingUser.setPassword(hashedPassword);
            existingUser.setGender(user.getGender());
            existingUser.setDateOfBirth(user.getDateOfBirth());
            return userRepository.save(existingUser);
        }
    }



    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getById(UUID uuid) {
        if (uuid == null) return null;
        return userRepository.getById(uuid);
    }

    public List<User> getAllUser(){
        List<User> list = userRepository.findAll();
        return list;
    }

    public List<User> getImageLink(List<User> users) {
        return users.stream().map(this::addImageUrl).collect(Collectors.toList());
    }

    private User addImageUrl(User user) {
        String[] updatedImageNames = Arrays.stream(user.getImageNames())
                .map(imageName -> LINK_IMAGES_USER + imageName)
                .toArray(String[]::new);
        user.setImageNames(updatedImageNames);
        return user;
    }
}
