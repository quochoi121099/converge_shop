//package com.studyprogramming.entity.validator;
//
//import com.studyprogramming.payload.UserCreationPayload;
//import org.thymeleaf.util.StringUtils;
//
//import java.util.Optional;
//
//public class UserValidator implements Validator<UserCreationPayload>{
//    /**
//     * Kiểm tra một object Todo có hợp lệ không
//     * @param user
//     * @return
//     */
//    @Override
//    public boolean isValid(UserCreationPayload user) {
//        return Optional.ofNullable(user)
//                .filter(t -> !StringUtils.isEmpty(t.getName()))
//                .filter(t -> !StringUtils.isEmpty(String.valueOf(t.getPhoneNumber())))
//                .filter(t -> !StringUtils.isEmpty(t.getAddress()))
//                .filter(t -> !StringUtils.isEmpty(t.getEmail()))
//                .filter(t -> !StringUtils.isEmpty(t.getPassword()))
//                .filter(t -> !StringUtils.isEmpty(String.valueOf(t.getGender())))
//                .isPresent();// Kiểm tra khác rỗng
//    }
//
//
//}
