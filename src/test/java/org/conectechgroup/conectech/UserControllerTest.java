package org.conectechgroup.conectech;

import org.conectechgroup.conectech.DTO.PostDTO;
import org.conectechgroup.conectech.DTO.UserDTO;
import org.conectechgroup.conectech.controller.UserController;
import org.conectechgroup.conectech.model.Post;
import org.conectechgroup.conectech.model.User;
import org.conectechgroup.conectech.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
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
        // Create a mock for the UserDTO object
        UserDTO userMock = Mockito.mock(UserDTO.class);

        // Configure the behavior of the mock UserDTO
        when(userMock.getId()).thenReturn("1");
        when(userMock.getName()).thenReturn("User1");
        when(userMock.getEmail()).thenReturn("user1@example.com");



        // Create a list of mock UserDTOs
        List<UserDTO> users = Collections.singletonList(userMock);

        // Configure the behavior of the userService
        when(userService.findAll()).thenReturn(users);

        // Perform the GET request
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("User1"))
                .andExpect(jsonPath("$[0].email").value("user1@example.com"));
        verify(userService, times(1)).findAll();
    }


    @Test
    public void testFindById() throws Exception {
        User user = Mockito.mock(User.class);
        when(user.getId()).thenReturn("1");
        when(user.getName()).thenReturn("User1");

        when(userService.findById("1")).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("User1"));
        verify(userService, times(1)).findById("1");
    }
    @Test
    public void testFindByName() throws Exception {
        User user = new User();
        user.setId("1");
        user.setName("User1");

        UserDTO userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setName("User1");

        when(userService.findByName("User1")).thenReturn(user);
        when(userService.convertToDTO(user)).thenReturn(userDTO);

        mockMvc.perform(get("/users/name/User1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("User1"));

        verify(userService, times(1)).findByName("User1");
        verify(userService, times(1)).convertToDTO(user);
    }


    @Test
    public void testInsert() throws Exception {
        // Create a mock for the User object
        User userMock = Mockito.mock(User.class);

        // Configure the behavior of the user service
        when(userService.insert(any(User.class))).thenReturn(userMock);

        // Set up the mock User object to return expected values
        when(userMock.getId()).thenReturn("1");
        when(userMock.getName()).thenReturn("User1");

        // Perform the POST request
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"User1\",\"email\":\"user1@example.com\",\"dateOfBirth\":\"2000-01-01\",\"cpfcnpj\":\"12345678901\",\"password\":\"password\",\"gender\":\"Male\",\"bio\":\"Bio\",\"username\":\"username1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/users/1"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("User1"));
        verify(userService, times(1)).insert(any(User.class));
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
        verify(userService, times(1)).getUserImage("1");
    }
    @Test
    public void testUpdate() throws Exception {
        // Create a mock for the User object
        User userMock = Mockito.mock(User.class);
        // Configure the behavior of the user service
        when(userService.updateProfile(any(User.class))).thenReturn(userMock);
        // Perform the PUT request
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"name\":\"User1\",\"email\":\"userEmail@test.com\",\"dateOfBirth\":\"2000-01-01\",\"cpfcnpj\":\"123456789\",\"password\":\"password\",\"gender\":\"male\",\"bio\":\"bio\",\"username\":\"username1\"}"))
                .andExpect(status().isNoContent()); // Expect status 204
        // Verify if the updateProfile method is called on the userService
        verify(userService, times(1)).updateProfile(any(User.class));
    }

    @Test
    public void testDelete() throws Exception {
        // Mock the User object
        User userMock = Mockito.mock(User.class);
        when(userMock.getId()).thenReturn("1");

        // Configure the userService to return the mock User when findById is called
        when(userService.findById("1")).thenReturn(userMock);

        // Perform the DELETE request
        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isNoContent()); // Expect 204 status

        // Verify that the findById method was called once with the correct parameter
        verify(userService, times(1)).findById("1");
        // Verify that the delete method was called once with the correct parameter
        verify(userService, times(1)).delete("1");
    }

    @Test
    public void testGetUserPosts() throws Exception {
        // Mock the PostDTO object
        PostDTO postMock = Mockito.mock(PostDTO.class);
        List<PostDTO> posts = Collections.singletonList(postMock);

        // Configure the userService to return the mock list of posts when getPosts is called
        when(userService.getPosts("1")).thenReturn(posts);

        // Perform the GET request
        mockMvc.perform(get("/users/1/posts"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());

        // Verify that the getPosts method was called once with the correct parameter
        verify(userService, times(1)).getPosts("1");
    }

    @Test
    public void testAddPostToUser() throws Exception {
        // Arrange
        User user = new User("1", "User1", "user1@example.com", new Date(), "12345678901", "password", "Male", "Bio", "username1");
        Post post = new Post("1", user, "Post Title", 0, "Post Content", new Date(), Collections.emptyList(), Collections.emptyList());

        when(userService.findById(anyString())).thenReturn(user);
        when(postService.insert(any(Post.class))).thenReturn(post);

        // Act and Assert
        mockMvc.perform(post("/users/1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\",\"title\":\"Post Title\",\"content\":\"Post Content\",\"date\":\"2021-01-01\",\"userId\":\"1\"}"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).findById(anyString());
        verify(postService, times(1)).insert(any(Post.class));
        verify(userService, times(1)).update(any(User.class));
        verify(userService, times(1)).addPostToUser(anyString(), any(Post.class));
    }

    // Outros testes podem ser adicionados da mesma forma para as demais funcionalidades do controller
}
