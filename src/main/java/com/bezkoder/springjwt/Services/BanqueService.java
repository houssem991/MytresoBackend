package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.BanqueRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.BanqueRepository;
import com.bezkoder.springjwt.repository.CaisseRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BanqueService implements IBanqueService {
    @Autowired

    BanqueRepository banqueRepository;
    @Autowired

    UserRepository userRepository;

    @Override
    public List<Banque> findall() {
        List<Banque> banques= banqueRepository.findAll();
        return banques;
    }

    @Override
    public Banque findbyId(long id) {
        return banqueRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Banque not found! "));
    }

    @Override
    public ResponseEntity<?> AddBanque(BanqueRequest banqueRequest) {

        Banque banque = new Banque();
        banque.setNom(banqueRequest.getNom());
        banque.setAdresse(banqueRequest.getAdresse());
        banque.setTelephone(banqueRequest.getTelephone());
        banque.setSolde(banqueRequest.getSolde());
        banque.setUser(userRepository.findById(banqueRequest.getIduser()).get());
        banqueRepository.save(banque);
        return ResponseEntity.ok(new MessageResponse("Banque ajouté avec succés"));
    }


    @Override
    public ResponseEntity<?> UpdateBanque(BanqueRequest banqueRequest, long id) {
        if (!banqueRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Banque n'existe pas !"));
        }
        Banque banque = banqueRepository.findById(id).get();
        banque.setNom(banqueRequest.getNom());
        banque.setAdresse(banqueRequest.getAdresse());
        banque.setTelephone(banqueRequest.getTelephone());
        banque.setSolde(banqueRequest.getSolde());
        banque.setUser(userRepository.findById(banqueRequest.getIduser()).get());
        banqueRepository.save(banque);
        return ResponseEntity.ok(new MessageResponse("Banque modifié avec succés"));    }

    @Override
    public ResponseEntity<?> deleteBanque(long id) {
        if (!banqueRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La banque n'existe pas !"));
        }
        Banque banque = banqueRepository.findById(id).get();
        banqueRepository.delete(banque);
        return ResponseEntity.ok(new MessageResponse("Banque supprimé avec succés"));
    }




}
