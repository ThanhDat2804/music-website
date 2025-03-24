package com.example.music.controller;

import com.example.music.common.R;
import com.example.music.model.domain.Consumer;
import com.example.music.model.domain.ResetPasswordRequest;
import com.example.music.model.request.ConsumerRequest;
import com.example.music.service.ConsumerService;
import com.example.music.service.impl.ConsumerServiceImpl;
import com.example.music.service.impl.SimpleOrderManager;
import com.example.music.utils.RandomUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    ConsumerServiceImpl consumerServiceImpl;

    @Autowired
    private SimpleOrderManager simpleOrderManager;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * TODO: Called from the frontend
     * User registration
     */
    @PostMapping("/user/add")
    public R addUser(@RequestBody ConsumerRequest registryRequest) {
        return consumerService.addUser(registryRequest);
    }

    /**
     * TODO: Called from the frontend
     * Login validation
     */
    @PostMapping("/user/login/status")
    public R loginStatus(@RequestBody ConsumerRequest loginRequest, HttpSession session) {
        return consumerService.loginStatus(loginRequest, session);
    }

    /**
     * Email login
     */
    @PostMapping("/user/email/status")
    public R loginEmailStatus(@RequestBody ConsumerRequest loginRequest, HttpSession session) {
        return consumerService.loginEmailStatus(loginRequest, session);
    }

    /**
     * Password recovery (Forgot password)
     */
    @PostMapping("/user/resetPassword")
    public R resetPassword(@RequestBody ResetPasswordRequest passwordRequest) {
        Consumer user = consumerService.findByEmail(passwordRequest.getEmail());
        String code = stringRedisTemplate.opsForValue().get("code");

        if (user == null) {
            return R.fatal("User does not exist");
        } else if (!code.equals(passwordRequest.getCode())) {
            return R.fatal("Verification code is invalid or has expired");
        }

        ConsumerRequest consumerRequest = new ConsumerRequest();
        BeanUtils.copyProperties(user, consumerRequest);
        System.out.println(user);
        System.out.println(consumerRequest);

        consumerRequest.setPassword(passwordRequest.getPassword());
        consumerServiceImpl.updatePassword01(consumerRequest);

        return R.success("Password successfully updated");
    }

    /**
     * Send verification code feature
     */
    @GetMapping("/user/sendVerificationCode")
    public R sendCode(@RequestParam String email) {
        Consumer user = consumerService.findByEmail(email);
        if (user == null) {
            return R.fatal("User does not exist");
        }

        String code = RandomUtils.code();
        simpleOrderManager.sendCode(code, email);

        // Store in Redis
        stringRedisTemplate.opsForValue().set("code", code, 5, TimeUnit.MINUTES);
        return R.success("Verification code sent successfully");
    }

    /**
     * TODO: Called from the admin panel
     * Return all users
     */
    @GetMapping("/user")
    public R allUser() {
        return consumerService.allUser();
    }

    /**
     * TODO: Called from the user interface
     * Return user details by ID
     */
    @GetMapping("/user/detail")
    public R userOfId(@RequestParam int id) {
        return consumerService.userOfId(id);
    }

    /**
     * TODO: Called from the admin panel
     * Delete user
     */
    @GetMapping("/user/delete")
    public R deleteUser(@RequestParam int id) {
        return consumerService.deleteUser(id);
    }

    /**
     * TODO: Called from both frontend and backend
     * Update user information
     */
    @PostMapping("/user/update")
    public R updateUserMsg(@RequestBody ConsumerRequest updateRequest) {
        return consumerService.updateUserMsg(updateRequest);
    }

    /**
     * TODO: Called from both frontend and backend
     * Update user password
     */
    @PostMapping("/user/updatePassword")
    public R updatePassword(@RequestBody ConsumerRequest updatePasswordRequest) {
        return consumerService.updatePassword(updatePasswordRequest);
    }

    /**
     * Update user avatar
     */
    @PostMapping("/user/avatar/update")
    public R updateUserPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") int id) {
        return consumerService.updateUserAvatar(avatarFile, id);
    }
}
