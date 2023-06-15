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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Console;
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


    @GetMapping("/user-details/{username}")
    public String userDetail( @PathVariable("username") String username, Model model){
        AppUser user = userService.findByUsername(username);
        model.addAttribute("userD", user);
        return "users/user-details";
    }


    @GetMapping("/edit-user/{username}")
    public String editUser( @PathVariable("username") String username,HttpSession session, Model model){
//        AppUser user = (AppUser)session.getAttribute("userD");
        model.addAttribute("user", userService.findByUsername(username));
        return "users/edit-user";
    }

    @PostMapping("/edit-user/{username}")
    public String editUser(@PathVariable("username") String username, @ModelAttribute("user") AppUser user
            , HttpSession session, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            model.addAttribute("user", userService.findByUsername(username));
            return "users/edit-user";
        }
        else{
            user.setPassword(new
                    BCryptPasswordEncoder().encode(user.getPassword()));
            userService.save(user);

            return "redirect:/login";
        }
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
            model.addAttribute("message", "Chúng tôi đã gửi đường dẫn khôi phục mật khẩu đến email của bạn. Vui lòng kiểm tra hộp thư.");

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
        }

        return "users/forgot_password_form";
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException {
        EmailDetails emailDetails = new EmailDetails();
        String subject = "Đây là đường dẫn để khôi phục mật khẩu";
        String content = "<p>Xinh chào,</p>"
                + "<p>Bạn vừa yêu cầu đổi mật khẩu.</p>"
                + "<p>Nhấn đường dẫn dưới đây để đổi mật khẩu:</p>"
                + "<p><a href=\"" + link + "\">Đổi mật khẩu</a></p>"
                + "<br>"
                + "<p>Hãy bỏ qua email nếu bạn nhớ mật khẩu, "
                + "hoặc không phải bạn gửi yêu cầu!</p>";

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

            model.addAttribute("error" +
                    "Không tìm thấy token!");
        }

        return "users/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        AppUser customer = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Khôi phục mật khẩu");

        if (customer == null) {
            model.addAttribute("message", "Token không hợp lệ!");
            return "users/reset_password_form";
        }

        userService.updatePassword(customer, password);

        model.addAttribute("message", "Bạn đã đổi mật khẩu thành công!");

        return "redirect:/home";
    }
}
