package ru.job4j.cinema.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void whenLoginPageThenSuccess() {
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.loginPage(model, false);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("loginPage");
    }

    @Test
    void whenLoginThenSuccess() {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        when(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        when(httpServletRequest.getSession()).thenReturn(session);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/mainPage");
    }

    @Test
    void whenLoginThenFail() {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        when(userService.findUserByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());
        when(httpServletRequest.getSession()).thenReturn(session);
        String page = userController.login(user, httpServletRequest);
        assertThat(page).isEqualTo("redirect:/loginPage?fail=true");
    }

    @Test
    void whenLogoutThenSuccess() {
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.logout(session);
        assertThat(page).isEqualTo("redirect:/mainPage");
    }

    @Test
    void whenAddUserThenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.addUser(model, session);
        assertThat(page).isEqualTo("addUser");
    }

    @Test
    void whenRegistrationThenSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        when(userService.add(user)).thenReturn(Optional.of(user));
        String page = userController.registration(user);
        assertThat(page).isEqualTo("redirect:/success");
    }

    @Test
    void whenRegistrationThenFail() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User(1, "Denis", "denfort50@yandex.ru", "+79999999999", "password");
        when(userService.add(user)).thenReturn(Optional.empty());
        String page = userController.registration(user);
        assertThat(page).isEqualTo("redirect:/fail");
    }

    @Test
    void whenFail() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.fail(model, session);
        assertThat(page).isEqualTo("fail");
    }

    @Test
    void whenSuccess() {
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.success(model, session);
        assertThat(page).isEqualTo("success");
    }
}
