package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.models.Banque;
import com.bezkoder.springjwt.models.Caisse;
import com.bezkoder.springjwt.models.MouvementCaisse;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.CaisseRequest;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.BanqueRepository;
import com.bezkoder.springjwt.repository.CaisseRepository;
import com.bezkoder.springjwt.repository.MouvementRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
class CaisseService implements ICaisseService {
    @Autowired

    CaisseRepository caisseRepository;
    @Autowired

    BanqueRepository banqueRepository;
    @Autowired
    MouvementRepository mouvementRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Caisse> findall() {
        List<Caisse> caisse= caisseRepository.findAll();
        return caisse;
    }

    @Override
    public List<Caisse> findallCaisseWithoutCurrentCaisse(long id, long iduser) {
        return caisseRepository.findAllCaisseWithoutCurrentCaisse(iduser,id);
    }

    @Override
    public Caisse findbyId(long id) {
        return caisseRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Caisse not found! "));
    }

    @Override
    public ResponseEntity<?> AddCaisse(CaisseRequest caisseRequest) {

        Caisse caisse = new Caisse();
        caisse.setLibelle(caisseRequest.getLibelle());
        caisse.setSolde(caisseRequest.getSolde());
        caisse.setUser(userRepository.findById(caisseRequest.getIduser()).get());
        caisseRepository.save(caisse);
        return ResponseEntity.ok(new MessageResponse("Caisse ajouté avec succés"));
    }

    @Override
    public ResponseEntity<?> AlimenterCaisse(AlimentationRequest alimentationRequest, long id) {

        if (!caisseRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Caisse n'existe pas !"));
        }

        Caisse caisse = caisseRepository.findById(id).get();
        caisse.setSolde(BigDecimal.valueOf(caisse.getSolde()).add(BigDecimal.valueOf(alimentationRequest.getMontant())).doubleValue());
        caisseRepository.save(caisse);

        MouvementCaisse mc =new MouvementCaisse();
        if(alimentationRequest.getIdcaisse()!=0){
            MouvementCaisse mc1 =new MouvementCaisse();
            Caisse c =caisseRepository.findById(alimentationRequest.getIdcaisse()).get();
            c.setSolde(BigDecimal.valueOf(c.getSolde()).subtract(BigDecimal.valueOf(alimentationRequest.getMontant())).doubleValue());
            mc.setLibelle("Alimentation de la "+c.getLibelle());
            mc1.setLibelle("Alimentation vers la "+caisse.getLibelle());
            mc1.setDate(LocalDate.now());
            mc1.setCaisse(c);
            mc1.setMouvement("-"+alimentationRequest.getMontant()+" DT");
            mouvementRepository.save(mc1);
            caisseRepository.save(c);
        }
        if(alimentationRequest.getIdbanque()!=0){
            Banque b =banqueRepository.findById(alimentationRequest.getIdbanque()).get();
            b.setSolde(BigDecimal.valueOf(b.getSolde()).subtract(BigDecimal.valueOf(alimentationRequest.getMontant())).doubleValue());
            mc.setLibelle("Alimentation de la Banque "+b.getNom());
            banqueRepository.save(b);
        }
        mc.setDate(LocalDate.now());
        mc.setCaisse(caisse);
        mc.setMouvement("+"+alimentationRequest.getMontant()+" DT");
        mouvementRepository.save(mc);

        return ResponseEntity.ok(new MessageResponse("Caisse alimentée avec succés"));
    }

    @Override
    public ResponseEntity<?> UpdateCaisse(CaisseRequest caisseRequest, long id) {
        if (!caisseRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Caisse n'existe pas !"));
        }
        Caisse caisse = caisseRepository.findById(id).get();
        caisse.setLibelle(caisseRequest.getLibelle());
        caisse.setSolde(caisseRequest.getSolde());
        caisse.setUser(userRepository.findById(caisseRequest.getIduser()).get());
        caisseRepository.save(caisse);
        return ResponseEntity.ok(new MessageResponse("Caisse modifié avec succés"));    }

    @Override
    public ResponseEntity<?> deleteCaisse(long id) {
        if (!caisseRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: La Caisse n'existe pas !"));
        }
        Caisse caisse = caisseRepository.findById(id).get();
        caisseRepository.delete(caisse);
        return ResponseEntity.ok(new MessageResponse("Caisse supprimé avec succés"));
    }




}
