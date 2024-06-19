package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.SageRepository.DocRepository;
import com.bezkoder.springjwt.SageRepository.TiersSageRepository;
import com.bezkoder.springjwt.models.EFacture;
import com.bezkoder.springjwt.models.Facture;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.FactureRepository;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class FactureService implements IFactureService {
    @Autowired
    DocRepository docRepository;
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    FournisseurRepository fournisseurRepository;

    @Override
    public List<F_DOCENTETE> findall(@Param("idfournisseur") String idfournisseur) {
        return docRepository.findFacture(idfournisseur);
    }
    @Override
    public List<Facture> findallFactures() {
        return factureRepository.findAll() ;}


    @Override
    public ResponseEntity<?> ImportFacture(String idfournisseur) {
        List<F_DOCENTETE> sageFactures = docRepository.findFacture(idfournisseur);
        List<Facture> factures = new ArrayList<>();
        sageFactures.forEach(val->{
                Facture facture = new Facture();
           facture.setId(val.getId());
           facture.setCondition(val.getCondition());
           facture.setDomaine(val.getDomaine());
           facture.setDocumentDate(val.getDocumentDate());
           facture.setExpedit(val.getExpedit());
           facture.setCG_Num(val.getCG_Num());
           facture.setMontantRegle(val.getMontantRegle());
            facture.setResteAPayer(val.getNetAPayer()-val.getMontantRegle());
           if (val.getMontantRegle()== 0.0){
               facture.setEtat(EFacture.NonRegle);
           }else if (facture.getResteAPayer()>0){
               facture.setEtat(EFacture.PartRegle);
           }else if (facture.getResteAPayer()==0){
               facture.setEtat(EFacture.TotRegle);
           }
           facture.setNetAPayer(val.getNetAPayer());
           facture.setTotalAmountHT(val.getTotalAmountHT());
           if(val.getValide()==0){
               facture.setValide("non");
           }else {
               facture.setValide("oui");
           }
           facture.setNumPayeur(val.getNumPayeur());
           facture.setClientCode(val.getClientCode());
           facture.setTotalAmountTTC(val.getTotalAmountTTC());

                facture.setFournisseur(fournisseurRepository.findById(val.getNumPayeur()).get());
                factures.add(facture);
            });
        factureRepository.saveAll(factures);
        return ResponseEntity.ok(new MessageResponse("Factures importé avec success"));
    }
}
