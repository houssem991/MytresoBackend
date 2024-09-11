package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.RetenueSource;
import com.bezkoder.springjwt.payload.request.RentenueRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.RentenuRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class RetenueService implements IRetenueService {
    @Autowired

    RentenuRepository RetenueRepository;
    @Autowired

    UserRepository userRepository;

    @Override
    public List<RetenueSource> findall() {
        List<RetenueSource> Retenues= RetenueRepository.findAll();
        return Retenues;
    }

    @Override
    public RetenueSource findbyId(long id) {
        return RetenueRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Retenue not found! "));
    }

    @Override
    public ResponseEntity<?> AddRentenue(RentenueRequest rentenueRequest) {

        RetenueSource Retenue = new RetenueSource();
        Retenue.setPourcentage(rentenueRequest.getPourcentage());
        Retenue.setUser(userRepository.findById(rentenueRequest.getIduser()).get());
        RetenueRepository.save(Retenue);
        return ResponseEntity.ok(new MessageResponse("Retenue ajouté avec succés"));
    }


    @Override
    public ResponseEntity<?> UpdateRetenue(RentenueRequest RetenueRequest, long id) {
        if (!RetenueRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Retenue n'existe pas !"));
        }
        RetenueSource Retenue = RetenueRepository.findById(id).get();
        Retenue.setPourcentage(RetenueRequest.getPourcentage());
        Retenue.setUser(userRepository.findById(RetenueRequest.getIduser()).get());
        RetenueRepository.save(Retenue);
        return ResponseEntity.ok(new MessageResponse("Retenue modifié avec succés"));    }

    @Override
    public ResponseEntity<?> deleteRetenue(long id) {
        if (!RetenueRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Retenue n'existe pas !"));
        }
        RetenueSource Retenue = RetenueRepository.findById(id).get();
        RetenueRepository.delete(Retenue);
        return ResponseEntity.ok(new MessageResponse("Retenue supprimé avec succés"));
    }




}
