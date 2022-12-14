package com.projectvibewave.vibewaveapp.controller;

import com.projectvibewave.vibewaveapp.dto.EmailConfirmationDto;
import com.projectvibewave.vibewaveapp.dto.UserSignUpDto;
import com.projectvibewave.vibewaveapp.service.ConfirmationTokenService;
import com.projectvibewave.vibewaveapp.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String logIn(Model model) {
        logger.info("Accessed Log In Page");
        return "auth/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/signup")
    public String signUp(Model model) {
        logger.info("Accessed Sign Up Page");
        var user = userService.getUserSignUpDto();
        model.addAttribute("user", user);
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute("user") UserSignUpDto user, BindingResult bindingResult, Model model) {
        logger.info("trying to sign up a user....");
        var isSuccessful = userService.trySignUp(user, bindingResult);

        if (!isSuccessful) {
            return "auth/signup";
        }

        return "redirect:/?signup";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/confirm")
    public String confirmEmail(@Param("token") String token, Model model) {
        logger.info("Accessed Confirm Page");
        var status = userService.tryConfirmEmail(token);
        model.addAttribute("message", status.getMessage());
        return "auth/confirm";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("/confirm/resend")
    public String resendConfirmationToken(Model model) {
        logger.info("Accessed Resend Confirmation Token Page");
        var emailConfirmation = new EmailConfirmationDto();
        model.addAttribute("emailConfirmation", emailConfirmation);
        return "auth/resend-confirmation";
    }

    @PostMapping("/confirm/resend")
    public String resendConfirmationToken(@Valid @ModelAttribute("emailConfirmation") EmailConfirmationDto emailConfirmationDto, BindingResult bindingResult, Model model) {
        logger.info("trying to resend email confirmation....");
        var isSuccessful = userService.resendConfirmationToken(emailConfirmationDto, bindingResult);

        if (!isSuccessful) {
            return "auth/resend-confirmation";
        }

        return "redirect:/?resend";
    }
}
