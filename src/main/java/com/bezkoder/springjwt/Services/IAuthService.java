package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.*;
import com.bezkoder.springjwt.payload.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IAuthService {

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    public ResponseEntity<?> registerUser( SignupRequest signUpRequest);
    public ResponseEntity<?> registerCollaborator( SignupCollaboratorRequest signUpRequest);
    public ResponseEntity<?> InviteCollaborateur( InviteRequest inviteRequest) throws Exception;
    public ResponseEntity<?> ValidateUser( long iduser);

    public ResponseEntity<?> passwordoubli(PasswordoublierRequest loginRequest) throws Exception;
    public ResponseEntity<?> restpass(RestRequest restRequest) throws Exception;
    public String uploadImage(long id, MultipartFile file);
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
