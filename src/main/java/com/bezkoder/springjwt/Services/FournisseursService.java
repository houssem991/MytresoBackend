package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageRepository.TiersSageRepository;
import com.bezkoder.springjwt.models.Access;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.AccessRepository;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class FournisseursService implements IFournisseursService {
    @Autowired
    TiersSageRepository tiersSageRepository;
    @Autowired
    FournisseurRepository fournisseurRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<F_COMPTET> findall() {
        return tiersSageRepository.findFournisseurs();
    }
    @Override
    public List<Fournisseur> findallFournisseurs(@Param("identreprise") int identreprise, @Param("iduser") long iduser) {
        if (identreprise!=0){
            return  fournisseurRepository.findallFournisseursByEntrepriseId(identreprise);}
        else return fournisseurRepository.findallFournisseursByUserId(iduser);
    }

    @Override
    public ResponseEntity<?> ImportFournisseurs(long iduser) {
        List<F_COMPTET> sageFournisseurs = tiersSageRepository.findFournisseurs();
        List<Fournisseur> fournisseurs = new ArrayList<>();
        sageFournisseurs.forEach(val->{
            if(!fournisseurRepository.existsByCode(val.getCode())) {
                Fournisseur fournisseur = new Fournisseur();
                fournisseur.setAdresse(val.getAdresse());
                fournisseur.setCode(val.getCode());
                fournisseur.setApe(val.getApe());
                fournisseur.setContact(val.getContact());
                fournisseur.setComplement(val.getComplement());
                fournisseur.setIdentifiant(val.getIdentifiant());
                fournisseur.setSite(val.getSite());
                fournisseur.setNom(val.getNom());
                fournisseur.setPays(val.getPays());
                fournisseur.setEmail(val.getEmail());
                fournisseur.setSiret(val.getSiret());
                fournisseur.setTelecopie(val.getTelecopie());
                fournisseur.setUser(userRepository.findById(iduser).get());
                fournisseurs.add(fournisseur);
            }
        });
        fournisseurRepository.saveAll(fournisseurs);
        return ResponseEntity.ok(new MessageResponse("Fournisseurs importé avec success"));
    }
}
