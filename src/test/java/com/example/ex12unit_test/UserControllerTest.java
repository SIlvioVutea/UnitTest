package com.example.ex12unit_test;

import com.example.ex12unit_test.users.controllers.UserController;
import com.example.ex12unit_test.users.models.User;
import com.example.ex12unit_test.users.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    private static final User DEAFAULT_USER_INSERTED = new User(0, "j", "k@email.it");
    private static final User DEAFAULT_USER_SAVED = new User(1, "j", "k@email.it");

    @Test
    void create_userIsCreated() {
        when(userService.create(DEAFAULT_USER_INSERTED))
                .thenReturn(DEAFAULT_USER_SAVED);
        User result = userController.create(DEAFAULT_USER_INSERTED);
        assertEquals(DEAFAULT_USER_SAVED.getId(), result.getId());
    }

    @Test
    void loadBy_retrieveUser_whenRightIdIsGiven() {
        long id = 1;
        when(userService.getBy(id))
                .thenReturn(Optional.of(DEAFAULT_USER_SAVED));
        User result = userController.loadBy(id).get();

        assertEquals(DEAFAULT_USER_SAVED.getEmail(), result.getEmail());
    }

    @Test
    void loadAll_retrieveOneUser() {
        List<User> users = new ArrayList<>();
        users.add(DEAFAULT_USER_SAVED);
        when(userService.getAllUsers())
                .thenReturn(users);

        Collection<User> result = userController.loadAll();

        assertEquals(users.size(), result.size());
    }

    @Test
    void delete_noUserIsRetrieved_afterCorrectDeleteById() {
        when(userService.create(DEAFAULT_USER_INSERTED))
                .thenReturn(DEAFAULT_USER_SAVED);
        userController.deleteBy(1);
        List<User> users = new ArrayList<>();
        when(userService.getAllUsers())
                .thenReturn(users);
        Collection<User> result = userController.loadAll();
        assertEquals(users.size(), result.size());

    }

}
