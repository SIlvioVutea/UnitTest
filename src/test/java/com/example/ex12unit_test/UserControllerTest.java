package com.example.ex12unit_test;

import com.example.ex12unit_test.users.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserController userController;
    @Autowired
    private MockMvc mockMvc;

    private void insertUser() throws Exception {

        this.mockMvc.perform(post("/v1/users").content("""
                        {
                        "name": "Gabriel",
                        "email": "hey@itsme"
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testCreateUser() throws Exception {


        this.mockMvc.perform(post("/v1/users").content("""
                        {
                        "name": "Gabriel",
                        "email": "hey@itsme"
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                        {
                        "id": 1,
                        "name": "Gabriel",
                        "email": "hey@itsme"
                        }
                        """)).andReturn();

    }

    @Test
    void readUserList_test() throws Exception {
        insertUser();
        insertUser();

        this.mockMvc.perform(get("/v1/users"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andReturn();
    }

    @Test
    void deleteUser_test() throws Exception {
        insertUser();
        insertUser();
        this.mockMvc.perform(delete("/v1/users/1"))
                .andDo(print());

        this.mockMvc.perform(get("/v1/users"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(status().isFound());
    }

    @Test
    void readStudent_test() throws Exception {
        insertUser();
        this.mockMvc.perform(get("/v1/users/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json("""
                        {"id": 1,
                        "name": "Gabriel",
                        "email": "hey@itsme"
                        }
                        """))
                .andReturn();
    }

}
