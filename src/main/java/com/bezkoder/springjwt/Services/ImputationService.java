package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.SageModels.F_REGLECH;
import com.bezkoder.springjwt.SageRepository.ReglechRepository;
import com.bezkoder.springjwt.SageRepository.ReglementRepository;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.AlimentationRequest;
import com.bezkoder.springjwt.payload.request.FactureImputationRequest;
import com.bezkoder.springjwt.payload.request.ImputationRequest;
import com.bezkoder.springjwt.payload.response.ImputationResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.*;
import com.bezkoder.springjwt.repository.ImputationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
class ImputationService implements IImputaionService {
    @Autowired
    ImputationRepository imputationRepository;
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    RegRepository reglementRepository;
    @Autowired
    ReglechRepository reglechRepository;
    @Autowired
    CaisseRepository caisseRepository;
    @Autowired
    BanqueRepository banqueRepository;
    @Autowired
    RegRepository regRepository;
    @Autowired
    MouvementRepository mouvementRepository;
    @Autowired
    MouvementBanqueRepository mouvementBanqueRepository;
    @Override
    public List<ImputationResponse> findall(long id) {
        Reglement reglement = reglementRepository.findById(id).get();
        List<ImputationReglement> Imputation= imputationRepository.findAllByReglement(reglement);
        List<ImputationResponse> imputationResponses = new ArrayList<>();

        Imputation.forEach(val->{
            ImputationResponse imputationResponse = new ImputationResponse();
            imputationResponse.setEtat(val.getFacture().getEtat());
            imputationResponse.setMontant(val.getMontant());
            imputationResponse.setCode(val.getReglement().getCode());
            imputationResponse.setPiece(val.getFacture().getId());
            imputationResponses.add(imputationResponse);
        });


        return imputationResponses;
    }

    @Override
    public ImputationReglement findbyId(long id) {
        return imputationRepository.findById(id).orElseThrow( () -> new RuntimeException("Erreur: Imputation not found! "));
    }

    @Override
    public ResponseEntity<?> Imputation(ImputationRequest imputationRequest) {
        Reglement r =reglementRepository.findById(imputationRequest.getIdreglement()).get();
        BigDecimal solde = BigDecimal.valueOf(r.getSoldeRestant());
        MouvementCaisse m = new MouvementCaisse();
        MouvementBanque m1 = new MouvementBanque();
        for (String val : imputationRequest.getPiece()) {
            ImputationReglement imputation = new ImputationReglement();
            if(imputationRequest.getIdbanque()!=0){
                if (m.getPiece()==null){
                    m.setPiece(val);
                } else {
                    m1.setPiece(m.getPiece() + val + ",");
                }
            }
            if (imputationRequest.getIdcaisse()!=0){
                if (m.getPiece()==null){
                    m.setPiece(val);
                }else {
                    m.setPiece(m.getPiece() + val + ",");
                }
            }


            if (r.getType() == 1) {
                Facture f = factureRepository.findFactureFournisseur(val);
                if(solde.doubleValue() >= f.getResteAPayer()) {
                    solde = solde.subtract(BigDecimal.valueOf(f.getResteAPayer()));
                    imputation.setMontant(f.getResteAPayer());
                    f.setResteAPayer(0);
                    f.setEtat(EFacture.TotRegle);
                    f.setMontantRegle(f.getNetAPayer());


                } else {
                    imputation.setMontant(solde.doubleValue());
                    f.setResteAPayer(BigDecimal.valueOf(f.getResteAPayer()).subtract(solde).doubleValue());
                    f.setMontantRegle(BigDecimal.valueOf(f.getMontantRegle()).add(solde).doubleValue());
                    f.setEtat(EFacture.PartRegle);
                    solde = BigDecimal.valueOf(0);
                }
                imputation.setDomaine(f.getDomaine());
                imputation.setType(f.getType());
                imputation.setDR_No(r.getNum());
                imputation.setFacture(f);
                imputation.setReglement(r);
                imputationRepository.save(imputation);
                factureRepository.save(f);
            } else {
                Facture f = factureRepository.findFactureClient(val);
                if(solde.doubleValue() >= f.getResteAPayer()) {
                    solde = solde.subtract(BigDecimal.valueOf(f.getResteAPayer()));
                    imputation.setMontant(f.getResteAPayer());
                    f.setResteAPayer(0);
                    f.setEtat(EFacture.TotRegle);
                    f.setMontantRegle(f.getNetAPayer());


                } else {
                    imputation.setMontant(solde.doubleValue());
                    f.setResteAPayer(BigDecimal.valueOf(f.getResteAPayer()).subtract(solde).doubleValue());
                    f.setMontantRegle(BigDecimal.valueOf(f.getMontantRegle()).add(solde).doubleValue());
                    f.setEtat(EFacture.PartRegle);
                    solde = BigDecimal.valueOf(0);
                }

                imputation.setDomaine(f.getDomaine());
                imputation.setType(f.getType());

                imputation.setDR_No(r.getNum());
                imputation.setFacture(f);
                imputation.setReglement(r);
                imputationRepository.save(imputation);
                factureRepository.save(f);
            }

        }
        if(imputationRequest.getIdcaisse()!=0){
            Caisse c = caisseRepository.findById(imputationRequest.getIdcaisse()).get();

            if(r.getType()==1){
                if(imputationRequest.getMontant()> c.getSolde()) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Solde Caisse insuffisant!"));
                }

                m.setCaisse(c);
                m.setMouvement("-"+imputationRequest.getMontant()+" DT");

                m.setLibelle(r.getLibelle());
                m.setDate(LocalDate.now());
                c.setSolde(BigDecimal.valueOf(c.getSolde()).subtract(BigDecimal.valueOf(imputationRequest.getMontant())).doubleValue());
            }else  {
                c.setSolde(BigDecimal.valueOf(c.getSolde()).add(BigDecimal.valueOf(imputationRequest.getMontant())).doubleValue());
                m.setCaisse(c);
                m.setMouvement("+"+imputationRequest.getMontant()+" DT");
                m.setLibelle(r.getLibelle());
                m.setDate(LocalDate.now());
            }
            mouvementRepository.save(m);
            caisseRepository.save(c);
        }
        if(imputationRequest.getIdbanque()!=0){
            Banque b = banqueRepository.findById(imputationRequest.getIdbanque()).get();

            if(r.getType()==1){
                if(imputationRequest.getMontant()> b.getSolde()) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Solde Banque insuffisant!"));
                }

                m1.setBanque(b);
                m1.setMouvement("-"+imputationRequest.getMontant()+" DT");

                m1.setLibelle(r.getLibelle());
                m1.setDate(LocalDate.now());
                b.setSolde(BigDecimal.valueOf(b.getSolde()).subtract(BigDecimal.valueOf(imputationRequest.getMontant())).doubleValue());
            }else  {
                b.setSolde(BigDecimal.valueOf(b.getSolde()).add(BigDecimal.valueOf(imputationRequest.getMontant())).doubleValue());
                m1.setBanque(b);
                m1.setMouvement("+"+imputationRequest.getMontant()+" DT");
                m1.setLibelle(r.getLibelle());
                m1.setDate(LocalDate.now());
            }
            mouvementBanqueRepository.save(m1);
            banqueRepository.save(b);
        }




        r.setSoldeRestant(solde.doubleValue());
        if (solde.doubleValue()==0) {
            r.setEtat(EReglement.TotAffecter);
        } else {
            r.setEtat(EReglement.PartAffecter);
        }
            reglementRepository.save(r);
        return ResponseEntity.ok(new MessageResponse("Imputation ajouté avec succés"));
    }

    @Override
    public ResponseEntity<?> ImportImputation(long num) {
        List<F_REGLECH> reglech = reglechRepository.ReglementNonAffecté(num);
        for(F_REGLECH r : reglech ) {
            if (!imputationRepository.existsById(r.getDR_No())) {
                ImputationReglement imp = new ImputationReglement();
                imp.setDomaine(r.getDomaine());
                imp.setType(r.getType());
                imp.setMontant(r.getMonatnt());
                imp.setDR_No(r.getDR_No());
                if(factureRepository.existsById(r.getPiece())) {
                    imp.setFacture(factureRepository.findById(r.getPiece()).get());
                }
                else {
                        return ResponseEntity
                                .badRequest()
                                .body(new MessageResponse("Error: Les factures du ce tier n'ont pas encore été importé !"));
                }
                imp.setReglement(reglementRepository.findById(r.getNum()).get());
                imputationRepository.save(imp);
            }
        }
        return ResponseEntity.ok(new MessageResponse("Imputation importé avec succés"));
    }


    @Override
    public ResponseEntity<?> UpdateImputation(ImputationRequest imputationRequest, long id) {


        Reglement r = reglementRepository.findById(imputationRequest.getIdreglement()).get();
       /* Facture f = factureRepository.findById(imputationRequest.getPiece()).get();
        ImputationReglement imputation = imputationRepository.findById(id).get();
        double solde = BigDecimal.valueOf(imputationRequest.getMontant()).subtract(BigDecimal.valueOf(imputation.getMontant())).doubleValue() ;

        if (solde > f.getResteAPayer()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Le montant saisie depasse le reste à payer de la facture!"));
        }

        if (solde > r.getSoldeRestant()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Solde Reglement insuffisant!"));
        }
        if(imputationRequest.getIdcaisse()!=0){
            Caisse c = caisseRepository.findById(imputationRequest.getIdcaisse()).get();

            if(r.getType()==1){
                if(solde> c.getSolde()) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: Solde Caisse insuffisant!"));
                }
                MouvementCaisse m = new MouvementCaisse();
                m.setCaisse(c);
                if(solde<0){
                    m.setMouvement("+"+-solde+" DT");
                }else m.setMouvement("-"+solde+" DT");
                m.setPiece(f.getId());
                m.setLibelle(r.getLibelle());
                m.setDate(LocalDate.now());
                mouvementRepository.save(m);
                c.setSolde(BigDecimal.valueOf(c.getSolde()).subtract(BigDecimal.valueOf(solde)).doubleValue());
            }else {
                c.setSolde(BigDecimal.valueOf(c.getSolde()).add(BigDecimal.valueOf(solde)).doubleValue());
                MouvementCaisse m = new MouvementCaisse();
                m.setCaisse(c);
                if(solde<0){
                    m.setMouvement("-"+-solde+" DT");
                }else m.setMouvement("+"+solde+" DT");
                m.setPiece(f.getId());
                m.setLibelle(r.getLibelle());
                m.setDate(LocalDate.now());
                mouvementRepository.save(m);
            }
            caisseRepository.save(c) ;
        }
        if (f.getResteAPayer() - solde == 0) {
            f.setEtat(EFacture.TotRegle);
        } else f.setEtat(EFacture.PartRegle);
        if (r.getSoldeRestant() - solde == 0) {
            r.setEtat(EReglement.TotAffecter);
        } else r.setEtat(EReglement.PartAffecter);
        imputation.setDomaine(f.getDomaine());
        imputation.setType(f.getType());
        imputation.setMontant(imputationRequest.getMontant());
        imputation.setDR_No(r.getNum());
        imputation.setFacture(f);
        imputation.setReglement(r);
        f.setResteAPayer(BigDecimal.valueOf(f.getResteAPayer()).add(BigDecimal.valueOf(solde)).doubleValue());
        f.setMontantRegle(BigDecimal.valueOf(f.getMontantRegle()).add(BigDecimal.valueOf(solde)).doubleValue() );
        r.setSoldeRestant(BigDecimal.valueOf(r.getSoldeRestant()).subtract(BigDecimal.valueOf(solde)).doubleValue() );
        imputationRepository.save(imputation);
        factureRepository.save(f);
        reglementRepository.save(r);*/

    return ResponseEntity.ok(new MessageResponse("Imputation modifié avec succés"));
    }
    @Override
    public ResponseEntity<?> deleteImputation(long id) {
        if (!imputationRepository.existsById(id)) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: L'Imputation n'existe pas !"));
        }
        ImputationReglement imputation = imputationRepository.findById(id).get();
        Facture f = imputation.getFacture();
        Reglement r = imputation.getReglement();
        if (f.getResteAPayer()+imputation.getMontant()==f.getNetAPayer()){
            f.setEtat(EFacture.NonRegle);
        }else f.setEtat(EFacture.PartRegle);
        if(r.getSoldeRestant()+imputation.getMontant()==r.getSolde()){
            r.setEtat(EReglement.NonAffecter);
        }
        else r.setEtat(EReglement.PartAffecter);
        f.setResteAPayer(BigDecimal.valueOf(f.getResteAPayer()).add(BigDecimal.valueOf(imputation.getMontant())).doubleValue());
        f.setMontantRegle(BigDecimal.valueOf(f.getMontantRegle()).add(BigDecimal.valueOf(imputation.getMontant())).doubleValue() );
        r.setSoldeRestant(BigDecimal.valueOf(r.getSoldeRestant()).subtract(BigDecimal.valueOf(imputation.getMontant())).doubleValue() );
        reglementRepository.save(r);
        factureRepository.save(f);
        imputationRepository.delete(imputation);
        return ResponseEntity.ok(new MessageResponse("Imputation supprimé avec succés"));
    }




}
