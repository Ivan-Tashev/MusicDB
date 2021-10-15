package com.example.musicdb.web;

import com.example.musicdb.model.binding.UserRegBindModel;
import com.example.musicdb.model.service.UserServiceModel;
import com.example.musicdb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login-error")
    public ModelAndView failedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username) {

        ModelAndView modelAndView = new ModelAndView()
                .addObject("bad_credentials", true)
                .addObject("username", username);
        modelAndView.setViewName("login");

        return modelAndView;
    }

    @ModelAttribute
    public void setUserRegBindModel(Model model) {
        if (!model.containsAttribute("userRegBindModel")) {
            model.addAttribute("userRegBindModel", new UserRegBindModel());
        }
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register") //  <form th:action="@{/users/register}" method="POST">
    public String registerAndLogin(@Valid UserRegBindModel userRegBindModel,
                                   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegBindModel", userRegBindModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegBindModel", bindingResult);
            return "register";
        }
        if (userService.usernameExists(userRegBindModel.getUsername())) {
            redirectAttributes.addFlashAttribute("userRegBindModel", userRegBindModel)
                    .addFlashAttribute("usernameExists", true);
            return "redirect:/users/register";
        }

        UserServiceModel userServiceModel =
                modelMapper.map(userRegBindModel, UserServiceModel.class);
        userService.registerAndLogin(userServiceModel);
        return "redirect:/home";
    }
}
