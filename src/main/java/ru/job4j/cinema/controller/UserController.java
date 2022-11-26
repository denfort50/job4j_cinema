package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static ru.job4j.cinema.util.UserAttributeTool.addAttributeUser;

/**
 * Класс представляет собой контроллер для обработки действий с пользователями
 * @author Denis Kalchenko
 * @version 1.0
 */
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDB = userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userDB.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDB.get());
        return "redirect:/mainPage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/mainPage";
    }

    @GetMapping("/addUser")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("newUser", new User(0, "Имя", "Почта", "Телефон", "Пароль"));
        addAttributeUser(model, session);
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "fail";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        addAttributeUser(model, session);
        return "success";
    }
}
