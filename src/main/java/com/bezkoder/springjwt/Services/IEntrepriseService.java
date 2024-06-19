package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.payload.request.EnterpriseRequest;
import com.bezkoder.springjwt.payload.request.RoleRequest;
import com.bezkoder.springjwt.payload.response.EntrepriseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEntrepriseService {

    List<EntrepriseResponse> findall();

    Entreprise findbyId(Integer id);
    ResponseEntity<?> AddEnreprise(EnterpriseRequest enterpriseRequest);
    public String uploadImage(int id, MultipartFile file);
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
     ResponseEntity<?> UpdateEnreprise(EnterpriseRequest enterpriseRequest, int id);
     ResponseEntity<?> delete (Integer id );
}
