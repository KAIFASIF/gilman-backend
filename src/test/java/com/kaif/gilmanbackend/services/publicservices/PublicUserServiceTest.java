package com.kaif.gilmanbackend.services.publicservices;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.kaif.gilmanbackend.entities.Token;
import com.kaif.gilmanbackend.entities.User;
import com.kaif.gilmanbackend.repos.TokenRepo;
import com.kaif.gilmanbackend.services.publicServices.PublicUserService;

public class PublicUserServiceTest {
    @Autowired
    public PublicUserService publicUserService;

    @MockBean
    public TokenRepo tRepo;

    @Test
    public void saveUserTokenTest() {
        // create dummy user token 
        String jwt = "asdfasdfjkasdfjkl;adfs.adsfhkladfsjkladfs.asdhjkladfhjklshjk";
        User user = new User();
        publicUserService.saveUserToken(jwt, user);
        verify(tRepo, times(1)).save(any(Token.class));
        
    }
}