package ru.pashabezborod.vigenereCoderServer;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import ru.pashabezborod.vigenereCoderServer.controller.UserController;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.ResponseStatus;
import ru.pashabezborod.vigenereCoderServer.model.StandardResponse.StandardResponse;
import ru.pashabezborod.vigenereCoderServer.model.User.User;
import ru.pashabezborod.vigenereCoderServer.model.User.UserRequest;
import ru.pashabezborod.vigenereCoderServer.service.AuthService;
import ru.pashabezborod.vigenereCoderServer.service.UserService;

import java.util.Optional;

@SpringBootTest()
@ActiveProfiles(profiles = {"develop"})
@DisplayName(value = "MAIN TEST")
public class IntegrationTest {
    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;
    private final String name = "USERNAME1342452", password = "PaSsWoRd131452", address = "testIpAddress";
    private String cookie;

    @BeforeEach
    @DisplayName(value = "User creation and getting cookie")
    public void createTestUser() {
        StandardResponse userResponse = userController.addUser(new UserRequest(name, password));
        StandardResponse cookieResponse = authService.login(new UserRequest(name, password), address);
        cookie = cookieResponse.getResponse();
        Assertions.assertSame(ResponseStatus.OK, userResponse.getStatus(), "User creation failed " + userResponse.getStatus());
        Assertions.assertSame(ResponseStatus.OK, cookieResponse.getStatus(), "Getting cookie failed "+ cookieResponse.getStatus());
        Assertions.assertNotNull(cookie, "Cookie is null");
    }

    @Test
    @DisplayName(value = "User editing")
    public void editUser() {
        Optional<User> user = userService.getUser(User.get(new UserRequest(name, password)));
        Assertions.assertTrue(user.isPresent(), "Can't get exists user");
        StandardResponse response = userService.editUser(cookie, "newStupidPasswordForTest", address);
        Assertions.assertSame(ResponseStatus.OK, response.getStatus(), "Failed when updated password " + response.getStatus());
    }

    @AfterEach
    @DisplayName(value = "Delete test user")
    public void deleteTestUser() {
        StandardResponse response = userService.deleteUser(cookie, address);
        Assertions.assertSame(ResponseStatus.OK, response.getStatus(), "User delete failed " + response.getStatus());
    }
}
