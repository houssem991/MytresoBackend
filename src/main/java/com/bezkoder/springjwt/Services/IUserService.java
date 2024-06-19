package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.SignupRequest;
import com.bezkoder.springjwt.payload.response.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {

    List<UserResponse> findall();
    List<UserResponse> findallUserByEntreprise(int identreprise);
    UserResponse findbyIdd(Long id);
    public ResponseEntity<?> UpdateUser( SignupRequest signUpRequest, long id);
    void delete (Long id );
}
