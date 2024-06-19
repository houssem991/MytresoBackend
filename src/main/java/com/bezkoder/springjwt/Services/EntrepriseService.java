package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.Entreprise;
import com.bezkoder.springjwt.models.User;
import com.bezkoder.springjwt.payload.request.EnterpriseRequest;
import com.bezkoder.springjwt.payload.response.EntrepriseResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.payload.response.UserResponse;
import com.bezkoder.springjwt.repository.AccessRepository;
import com.bezkoder.springjwt.repository.EnterpriseRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
class EntrepriseService implements IEntrepriseService {
    @Autowired

    EnterpriseRepository enterpriseRepository;
    @Autowired

    UserRepository userRepository;
    @Autowired

    IUserService userService;
    @Autowired

    FileStorageService fileStorageService;
    @Override
    public List<EntrepriseResponse> findall() {
        List<Entreprise> entreprises= enterpriseRepository.findAll();
        List<EntrepriseResponse> entreprises1= new ArrayList<>();
        entreprises.forEach(val->{
            EntrepriseResponse entrepriseResponse= new EntrepriseResponse();
            entrepriseResponse.setId(val.getId());
            entrepriseResponse.setName(val.getName());
            entrepriseResponse.setUsers(userService.findallUserByEntreprise(val.getId()));
        entreprises1.add(entrepriseResponse);
        }
);
        return entreprises1;
    }

    @Override
    public Entreprise findbyId(Integer id) {
        return enterpriseRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Entreprise not found! "));
    }

    @Override
    public ResponseEntity<?> AddEnreprise(EnterpriseRequest enterpriseRequest) {
        if (enterpriseRepository.existsByMatriculefiscale(enterpriseRequest.getMatriculefiscale())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: L'entreprise existe déja !"));
        }
        Entreprise entreprise = new Entreprise();
        entreprise.setName(enterpriseRequest.getName());
        entreprise.setMatriculefiscale(enterpriseRequest.getMatriculefiscale());
        entreprise.setAdresse(enterpriseRequest.getAdresse());
        User user = userRepository.findById(enterpriseRequest.getIduser())
                .orElseThrow(() -> new RuntimeException("Erreur: User not found! "));
        user.setEntreprise(entreprise);
        enterpriseRepository.save(entreprise);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse(entreprise.getId().toString()));
    }

    @Override
    public ResponseEntity<?> UpdateEnreprise(EnterpriseRequest enterpriseRequest, int id) {
        Entreprise entreprise = enterpriseRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Entreprise not found! "));
        entreprise.setName(enterpriseRequest.getName());
        enterpriseRepository.save(entreprise);
        return ResponseEntity.ok(new MessageResponse("Entreprise edited successfully"));
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        Entreprise entreprise = enterpriseRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Entreprise not found! "));
        enterpriseRepository.delete(entreprise);
        return ResponseEntity.ok(new MessageResponse("Entreprise deleted successfully"));
    }
    public String uploadImage(int id, MultipartFile file) {
        String fileName = fileStorageService.storeLogoEntreprise(id, file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return "le fichier a téléchargé avec succès";
    }
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
