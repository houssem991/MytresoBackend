package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Initilal;
import com.bezkoder.springjwt.models.RetenueSource;
import com.bezkoder.springjwt.payload.request.InitialRequest;
import com.bezkoder.springjwt.payload.request.RentenueRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.InitialRepository;
import com.bezkoder.springjwt.repository.RentenuRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class InitialService implements IInitialService {
    @Autowired

    InitialRepository initialRepository;
    @Autowired

    UserRepository userRepository;

    @Override
    public List<Initilal> findall() {
        List<Initilal> initilals= initialRepository.findAll();
        return initilals;
    }

    @Override
    public Initilal findbyId(long id) {
        return initialRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Initial not found! "));
    }

    @Override
    public ResponseEntity<?> AddInitial(InitialRequest initialRequest) {
        if(initialRepository.existsByElement(initialRequest.getElement())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Cet element est deja initialise!"));
        }
        Initilal initilal = new Initilal();
        initilal.setElement(initialRequest.getElement());
        initilal.setInitial(initialRequest.getInitial());
        initilal.setUser(userRepository.findById(initialRequest.getIduser()).get());
        initialRepository.save(initilal);
        return ResponseEntity.ok(new MessageResponse("Initial ajouté avec succés"));
    }


    @Override
    public ResponseEntity<?> UpdateInitial(InitialRequest initialRequest, long id) {
        if (!initialRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Retenue n'existe pas !"));
        }
        Initilal initilal= initialRepository.findById(id).get();
        initilal.setElement(initialRequest.getElement());
        initilal.setInitial(initialRequest.getInitial());
        initilal.setUser(userRepository.findById(initialRequest.getIduser()).get());
        initialRepository.save(initilal);
        return ResponseEntity.ok(new MessageResponse("Initial modifié avec succés"));    }

    @Override
    public ResponseEntity<?> deleteInitial(long id) {
        if (!initialRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Initial n'existe pas !"));
        }
        Initilal initilal = initialRepository.findById(id).get();
        initialRepository.delete(initilal);
        return ResponseEntity.ok(new MessageResponse("Initial supprimé avec succés"));
    }




}
