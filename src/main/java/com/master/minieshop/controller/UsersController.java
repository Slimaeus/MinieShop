package com.master.minieshop.controller;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.model.EmailDetails;
import com.master.minieshop.service.CustomUserDetailsService;
import com.master.minieshop.service.EmailService;
import com.master.minieshop.service.UserService;
import com.master.minieshop.utility.Utility;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class UsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String login(Model model, AppUser user) {
        model.addAttribute("userD",user);
        return "users/login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new AppUser());
        return "users/register";
    }
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") AppUser user,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            return "users/register";
        }
        user.setRole(Role.LoyalCustomer);
        user.setPassword(new
                BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/user-details/{id}")
    public String userDetail( @PathVariable("id") Long id,Model model, HttpSession session){
        AppUser user = userService.getById(id);
        System.out.println(user);
            model.addAttribute("userD",user);
            return "users/user-details";

    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "users/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "users/forgot_password_form";
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String subject = "Here's the link to reset your password";
        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        emailDetails.setRecipient(recipientEmail);
        emailDetails.setSubject(subject);
        emailDetails.setContent(content);

        emailService.sendHtmlEmail(emailDetails);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        AppUser user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "users/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        AppUser customer = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(customer, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "redirect:/home";
    }
}
