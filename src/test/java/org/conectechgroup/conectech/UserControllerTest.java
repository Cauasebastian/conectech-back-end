package org.conectechgroup.conectech;

import org.conectechgroup.conectech.DTO.UserDTO;
import org.conectechgroup.conectech.controller.UserController;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @Mock
    private EventService eventService;

    @Mock
    private InterestService interestService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        List<UserDTO> users = Collections.singletonList(new UserDTO("1", "User1", "user1@example.com", new Date(),"Male", Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("User1"));
    }

    @Test
    public void testFindById() throws Exception {
        User user = new User("1", "User1", "user1@example.com", new Date(), "12345678901", "password", "Male", "Bio", "username1");
        when(userService.findById("1")).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("User1"));
    }

    @Test
    public void testInsert() throws Exception {
        User user = new User("1", "User1", "user1@example.com", new Date(), "12345678901", "password", "Male", "Bio", "username1");
        when(userService.insert(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"User1\",\"email\":\"user1@example.com\",\"dateOfBirth\":\"2000-01-01\",\"cpfcnpj\":\"12345678901\",\"password\":\"password\",\"gender\":\"Male\",\"bio\":\"Bio\",\"username\":\"username1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/users/1"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("User1"));
    }

    @Test
    public void testUploadImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "image.jpg", MediaType.IMAGE_JPEG_VALUE, "test image content".getBytes());

        mockMvc.perform(multipart("/users/1/uploadImage")
                        .file(image))
                .andExpect(status().isOk())
                .andExpect(content().string("Image uploaded successfully"));

        // Verify that saveUserWithImage was called
        verify(userService, times(1)).saveUserWithImage(eq("1"), any(MultipartFile.class));
    }

    @Test
    public void testGetUserImage() throws Exception {
        byte[] imageBytes = "test image content".getBytes();
        when(userService.getUserImage("1")).thenReturn(imageBytes);

        mockMvc.perform(get("/users/1/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))
                .andExpect(content().bytes(imageBytes));
    }
    @Test
    public void testUpdate() throws Exception {
        User user = new User("1", "User1", "userEmail@test.com", new Date(), "123456789", "password", "male" , "bio", "username1");
        when(userService.update(any(User.class))).thenReturn(user);
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"User1\",\"email\":\"userEmail@test.com\",\"dateOfBirth\":\"2000-01-01\",\"cpfcnpj\":\"123456789\",\"password\":\"password\",\"gender\":\"male\",\"bio\":\"bio\",\"username\":\"username1\"}"))
                .andExpect(status().isNoContent()); // Expect 204 status
    }

    // Outros testes podem ser adicionados da mesma forma para as demais funcionalidades do controller
}
