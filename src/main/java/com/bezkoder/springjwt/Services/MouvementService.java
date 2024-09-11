package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.MouvementCaisse;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.CaisseRepository;
import com.bezkoder.springjwt.repository.MouvementRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class MouvementService implements IMouvementService {
    @Autowired

    CaisseRepository caisseRepository;
    @Autowired

    MouvementRepository mouvementRepository;


    @Override
    public List<MouvementCaisse> findAllByCaisse(long idCaisse) {
        Caisse c = caisseRepository.findById(idCaisse).get();
        return mouvementRepository.findAllByCaisse(c);
    }

    @Override
    public ResponseEntity<?> deleteCaisse(long id) {
        MouvementCaisse m = mouvementRepository.findById(id).get();
        mouvementRepository.delete(m);
        return ResponseEntity.ok(new MessageResponse("Mouvement caisse supprimé avec success"));
    }
}
