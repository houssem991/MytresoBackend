package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.SageModels.F_CREGLEMENT;
import com.bezkoder.springjwt.SageModels.F_REGLECH;
import com.bezkoder.springjwt.SageModels.P_REGLEMENT;
import com.bezkoder.springjwt.SageRepository.P_ReglementRepository;
import com.bezkoder.springjwt.SageRepository.ReglechRepository;
import com.bezkoder.springjwt.SageRepository.ReglementRepository;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.RegRequest;
import com.bezkoder.springjwt.payload.response.EcheancierResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class ReglementService implements IRegelementService {
    @Autowired
    ReglementRepository reglementRepository;
    @Autowired
    RegRepository regRepository;
    @Autowired
    ReglechRepository reglechRepository;
    @Autowired
    InitialRepository initialRepository;
    @Autowired
    P_ReglementRepository pReglementRepository;
    @Autowired
    FournisseurRepository fournisseurRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ImputationRepository imputationRepository;

    @Override
    public List<F_CREGLEMENT> findallReglementFournisseurs() {
        return reglementRepository.findReglementFournisseurs();
    }
    @Override
    public List<F_CREGLEMENT> findallReglementClients() {
        return reglementRepository.findReglementClients();
    }

    @Override
    public List<Reglement> findallImpayesFournisseurs(long iduser) {
        return regRepository.findAllImpayesFournisseurs(iduser);
    }


    @Override
    public List<Reglement> findallReglementFournisseurs(@Param("iduser") long iduser) {
        return regRepository.findAllReglementFournisseurs(iduser);
    }

    @Override
    public List<Reglement> findallImpayesClients(long iduser) {
        return regRepository.findAllImpayesClients(iduser);
    }

    @Override
    public Reglement findById(long id) {
        return regRepository.findById(id).get();
    }

    @Override
    public List<Reglement> findallReglementClients(@Param("iduser") long iduser) {
        return regRepository.findAllReglementClients(iduser);
    }

    @Override
    public ResponseEntity<?> retourImpayeClients(@Param("iduser") long iduser,@Param("num") long num) {
        Reglement r = regRepository.findReglementClients(iduser,num);
        r.setImpaye(true);
        regRepository.save(r);
        return ResponseEntity.ok(new MessageResponse("Reglements retoune impaye avec success"));
    }

    @Override
    public ResponseEntity<?> retourImpayeFournisseurs( @Param("iduser") long iduser,@Param("num") long num) {
        Reglement r = regRepository.findReglementFournisseurs(iduser,num);
        r.setImpaye(true);
        regRepository.save(r);
        return ResponseEntity.ok(new MessageResponse("Reglements retoune impaye avec success"));
    }


    @Override
    public ResponseEntity<?> ImportReglementFournisseurs(LocalDate datedebut , LocalDate datefin) {
        List<F_CREGLEMENT> sageReglements = reglementRepository.findReglementFournisseurs();
        List<Reglement> Reglements = new ArrayList<>();
        sageReglements.forEach(val->{
            if ((val.getDateReglement().isBefore(datefin))  && (val.getDateReglement().isAfter(datedebut))){
                if (!regRepository.existsById(val.getNum())) {
                    Reglement reglement = new Reglement();
                    reglement.setNum(val.getNum());
                    reglement.setCode("0"+val.getNum());
                    while (reglement.getCode().length()<10){
                        reglement.setCode("0"+reglement.getCode());
                    }
                    P_REGLEMENT ps = pReglementRepository.findByType("RS");
                    if(ps.getId()==val.getN_Reglement()){
                        Initilal i = initialRepository.findByElement("Retenu à la source");
                        reglement.setCode(i.getInitial()+reglement.getCode());
                    }
                    else{
                        Initilal i = initialRepository.findByElement("Reglement Fournisseur");
                        reglement.setCode(i.getInitial()+reglement.getCode());
                    }

                    reglement.setDateReglement(val.getDateReglement());
                    reglement.setCaisse(val.getCaisse());
                    reglement.setLibelle(val.getLibelle());
                    reglement.setEstAffecte(val.getEstAffecte());
                    reglement.setN_Reglement(val.getN_Reglement());
                    reglement.setDateEcheance(val.getDateEcheance());
                    reglement.setSolde(val.getSolde());
                    if (val.getEstAffecte()) {
                        reglement.setEtat(EReglement.TotAffecter);
                        reglement.setSoldeRestant(0);
                    } else {
                        List<F_REGLECH> r = reglechRepository.ReglementNonAffecté(val.getNum());
                        if (r.isEmpty()) {
                            reglement.setEtat(EReglement.NonAffecter);
                            reglement.setSoldeRestant(val.getSolde());
                        } else {
                            reglement.setEtat(EReglement.PartAffecter);
                            BigDecimal result = BigDecimal.valueOf(val.getSolde());

                            // Rounding the result to one decimal place
                            for(F_REGLECH v:r){
                                result = result.subtract(BigDecimal.valueOf(v.getMonatnt()));
                            }

                            reglement.setSoldeRestant(result.doubleValue());
                        }
                    }
                    reglement.setReference(val.getReference());
                    reglement.setType(val.getType());
          /* if(val.getValide()==0){
               reglement.setValide("non");
           }else {
               reglement.setValide("oui");
           }*/
                    reglement.setCloture(val.getCloture());
                    reglement.setNumPiece(val.getNumPiece());
                    reglement.setSoldeDev(val.getSoldeDev());
                    reglement.setBanque(val.getBanque());
                    reglement.setEstComptablise(val.getEstComptablise());
                    reglement.setTypereglement(val.getTypereglement());
                    reglement.setTiers(val.getTiers());
                    reglement.setImpaye(false);
                    reglement.setFournisseur(fournisseurRepository.findById(val.getTiers()).get());
                    regRepository.save(reglement);

                }
                }
            });

        return ResponseEntity.ok(new MessageResponse("Reglements importé avec success"));
    }

    @Override
    public List<Reglement> findAllNonAffecteFournisseurs(long iduser) {
        return regRepository.findAllNonAffecteFournisseurs(iduser);
    }

    @Override
    public ResponseEntity<?> ImportReglementFournisseurss(LocalDate datedebut, LocalDate datefin) {
        List<F_CREGLEMENT> sageReglements = reglementRepository.findReglementFournisseurs();

        Set<Long> existingReglementIds = regRepository.findAllById(
                sageReglements.stream().map(F_CREGLEMENT::getNum).collect(Collectors.toList())
        ).stream().map(Reglement::getNum).collect(Collectors.toSet());

        Map<String, Fournisseur> fournisseurMap = fournisseurRepository
                .findAllById(sageReglements.stream().map(F_CREGLEMENT::getTiers).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(Fournisseur::getCode, Function.identity()));

        List<CompletableFuture<Void>> futures = sageReglements.stream()
                .filter(val -> val.getDateReglement().isBefore(datefin) && val.getDateReglement().isAfter(datedebut))
                .map(val -> CompletableFuture.runAsync(() -> {
                    if (!existingReglementIds.contains(val.getNum())) {
                        Reglement reglement = new Reglement();
                        reglement.setNum(val.getNum());
                        reglement.setDateReglement(val.getDateReglement());
                        reglement.setCaisse(val.getCaisse());
                        reglement.setLibelle(val.getLibelle());
                        reglement.setEstAffecte(val.getEstAffecte());
                        reglement.setSolde(val.getSolde());

                        if (val.getEstAffecte()) {
                            reglement.setEtat(EReglement.TotAffecter);
                            reglement.setSoldeRestant(0);
                        } else {
                            float soldeRestant = reglechRepository
                                    .findTotalMontantNonAffecté(val.getNum());  // Requête optimisée
                            reglement.setSoldeRestant(soldeRestant);
                            reglement.setEtat(soldeRestant==val.getSolde() ? EReglement.NonAffecter : EReglement.PartAffecter);
                        }

                        reglement.setReference(val.getReference());
                        reglement.setType(val.getType());
                        reglement.setCloture(val.getCloture());
                        reglement.setNumPiece(val.getNumPiece());
                        reglement.setSoldeDev(val.getSoldeDev());
                        reglement.setBanque(val.getBanque());
                        reglement.setEstComptablise(val.getEstComptablise());
                        reglement.setTypereglement(val.getTypereglement());
                        reglement.setTiers(val.getTiers());
                        reglement.setImpaye(false);
                        reglement.setFournisseur(fournisseurMap.get(val.getTiers()));

                        regRepository.save(reglement);
                    }
                }))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return ResponseEntity.ok(new MessageResponse("Reglements importé avec succès"));
    }

    @Override
    public ResponseEntity<?> ImportReglementClient(LocalDate datedebut , LocalDate datefin) {
        List<F_CREGLEMENT> sageReglements = reglementRepository.findReglementClients();
        List<Reglement> Reglements = new ArrayList<>();
        /*if(val.getValide()==0){
                reglement.setValide("non");
            }else {
                reglement.setValide("oui");
            }*/
        sageReglements.forEach(val -> {
               if ((val.getDateReglement().isBefore(datefin))  && (val.getDateReglement().isAfter(datedebut))){
                if (!regRepository.existsById(val.getNum())) {
                    Reglement reglement = new Reglement();
                    reglement.setNum(val.getNum());
                    reglement.setCode("0"+val.getNum());
                    while (reglement.getCode().length()<10){
                        reglement.setCode("0"+reglement.getCode());
                    }
                    P_REGLEMENT ps = pReglementRepository.findByType("RS");
                    if(ps.getId()==val.getN_Reglement()){
                        Initilal i = initialRepository.findByElement("Retenu à la source");
                        reglement.setCode(i.getInitial()+reglement.getCode());
                    }
                    else{
                        Initilal i = initialRepository.findByElement("Reglement Client");
                        reglement.setCode(i.getInitial()+reglement.getCode());
                    }
                    reglement.setDateReglement(val.getDateReglement());
                    reglement.setCaisse(val.getCaisse());
                    reglement.setLibelle(val.getLibelle());
                    reglement.setN_Reglement(val.getN_Reglement());
                    reglement.setDateEcheance(val.getDateEcheance());
                    reglement.setEstAffecte(val.getEstAffecte());
                    reglement.setSolde(val.getSolde());
                    if (val.getEstAffecte()){
                        reglement.setEtat(EReglement.TotAffecter);
                        reglement.setSoldeRestant(0);
                    } else {
                        List<F_REGLECH> r = reglechRepository.ReglementNonAffecté(val.getNum());
                        if (r.isEmpty()) {
                            reglement.setEtat(EReglement.NonAffecter);
                            reglement.setSoldeRestant(val.getSolde());
                          } else {
                            reglement.setEtat(EReglement.PartAffecter);
                            reglement.setSoldeRestant(val.getSolde());
                            r.forEach(v -> {
                                BigDecimal num1 = BigDecimal.valueOf(reglement.getSoldeRestant());
                                BigDecimal num3 = BigDecimal.valueOf(v.getMonatnt());
                                BigDecimal result = num1.subtract(num3);
                                reglement.setSoldeRestant(result.doubleValue());
                            });
                        }
                    }
                    reglement.setReference(val.getReference());
                    reglement.setType(val.getType());
                    reglement.setCloture(val.getCloture());
                    reglement.setNumPiece(val.getNumPiece());
                    reglement.setSoldeDev(val.getSoldeDev());
                    reglement.setImpaye(false);
                    reglement.setBanque(val.getBanque());
                    reglement.setEstComptablise(val.getEstComptablise());
                    reglement.setTypereglement(val.getTypereglement());
                    reglement.setTiers(val.getTiers());
                    reglement.setClient(clientRepository.findById(val.getTiers()).get());
                    regRepository.save(reglement);
                }
            }
        });

        return ResponseEntity.ok(new MessageResponse("Reglements importé avec success"));
    }
    public static String incrementIdentifier(String currentId) {
        // Vérifier que l'identifiant commence par "CD"
        if (currentId.startsWith("CD")) {
            // Extraire la partie numérique de l'identifiant
            String numericPart = currentId.substring(2);

            // Convertir en entier
            long number = Long.parseLong(numericPart);

            // Incrémenter l'entier
            number++;

            // Reformer la chaîne avec 10 chiffres, en ajoutant des zéros si nécessaire
            return "CD" + String.format("%010d", number);
        } else {
            throw new IllegalArgumentException("L'identifiant doit commencer par 'CD'.");
        }
    }

    @Override
    public ResponseEntity<?> AddReglementClient(RegRequest regRequest) {
        Reglement r = new Reglement();
        P_REGLEMENT p = pReglementRepository.findByType(regRequest.getType());
        r.setN_Reglement(p.getId());
        if(reglementRepository.getNumReglement()> regRepository.getNumReglement()){
            r.setNum(reglementRepository.getNumReglement()+1);
        } else  {
            r.setNum(regRepository.getNumReglement()+1);
        }
        r.setCode("0"+r.getNum());
        while (r.getCode().length()<10){
            r.setCode("0"+r.getCode());
        }
        Initilal i = initialRepository.findByElement("Reglement Client");
        r.setCode(i.getInitial()+r.getCode());
        r.setImpaye(false);
        r.setTypereglement(0);
        r.setSoldeRestant(regRequest.getSolde());
        r.setType(0);
        r.setEstComptablise(false);
        r.setTiers(clientRepository.findById(regRequest.getIdclient()).get().getCode());
        r.setCaisse(0);
        r.setSolde(regRequest.getSolde());
        r.setSoldeDev(regRequest.getSoldedev());
        r.setClient(clientRepository.findById(regRequest.getIdclient()).get());
        if(regRequest.getType().equals("CHEQUE")){
            r.setLibelle("REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }
        if(regRequest.getType().equals("ESPECE")){
            r.setLibelle("REG ESP");
            r.setDateEcheance(LocalDate.now());
        }
        if(regRequest.getType().equals("TRAITE")){
            r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }
        if(regRequest.getType().equals("VIREMENT")){
            r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }

        r.setCloture(0);
        r.setBanque(0);
        r.setReference(regRequest.getReference());
        r.setEstAffecte(false);
        r.setEtat(EReglement.NonAffecter);
        r.setDateReglement(LocalDate.now());
        r.setTransfere(0);
        r.setValide("oui");
        regRepository.save(r);
        if(regRequest.isRs()){
            BigDecimal bp =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSolde())).multiply(BigDecimal.valueOf(0.01));
            BigDecimal bpdev =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSoldeDev())).multiply(BigDecimal.valueOf(0.01));
            double solde = BigDecimal.valueOf(r.getSolde()).subtract(bp).doubleValue();
            r.setSolde(solde);
            r.setSoldeRestant(solde);
            r.setSoldeDev(BigDecimal.valueOf(r.getSoldeDev()).subtract(bpdev).doubleValue());
            Reglement rs = new Reglement();
            P_REGLEMENT ps = pReglementRepository.findByType("RS");
            rs.setN_Reglement(ps.getId());
            if(reglementRepository.getNumReglement()> regRepository.getNumReglement()){
                rs.setNum(reglementRepository.getNumReglement()+1);
            } else  {
                rs.setNum(regRepository.getNumReglement()+1);
            }
            rs.setCode("0"+rs.getNum());
            while (rs.getCode().length()<10){
                rs.setCode("0"+rs.getCode());
            }
            Initilal i1 = initialRepository.findByElement("Retenu à la source");
            rs.setCode(i1.getInitial()+rs.getCode());
            rs.setImpaye(false);
            rs.setTypereglement(0);
            rs.setSoldeRestant(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setType(0);
            rs.setEstComptablise(false);
            rs.setTiers(clientRepository.findById(regRequest.getIdclient()).get().getCode());
            rs.setCaisse(0);
            rs.setSolde(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setSoldeDev(BigDecimal.valueOf(regRequest.getSoldedev()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setClient(clientRepository.findById(regRequest.getIdclient()).get());
            if(regRequest.getType().equals("CHEQUE")){
                rs.setLibelle("R/S REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                rs.setDateEcheance(regRequest.getDateEcheance());
            }
            if(regRequest.getType().equals("ESPECE")){
                rs.setLibelle("R/S REG ESP");
                rs.setDateEcheance(LocalDate.now());
            }
            if(regRequest.getType().equals("TRAITE")){
                rs.setLibelle("R/S REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                rs.setDateEcheance(regRequest.getDateEcheance());
            }
            if(regRequest.getType().equals("VIREMENT")){
                rs.setLibelle("R/S REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                rs.setDateEcheance(regRequest.getDateEcheance());
            }

            rs.setCloture(0);
            rs.setBanque(0);
            rs.setReference(regRequest.getReference());
            rs.setEstAffecte(false);
            rs.setEtat(EReglement.NonAffecter);
            rs.setDateReglement(LocalDate.now());
            rs.setTransfere(0);
            rs.setValide("oui");
            regRepository.save(rs);
            regRepository.save(r);
        }
        return ResponseEntity.ok(new MessageResponse("Reglements "+ regRequest.getType() +" ajouté avec success"));
    }

    @Override
    public ResponseEntity<?> AddReglementFournisseurs(RegRequest regRequest) {
        Reglement r = new Reglement();
        P_REGLEMENT p = pReglementRepository.findByType(regRequest.getType());
        r.setN_Reglement(p.getId());
        if(reglementRepository.getNumReglement()> regRepository.getNumReglement()){
            r.setNum(reglementRepository.getNumReglement()+1);
        } else  {
            r.setNum(regRepository.getNumReglement()+1);
        }
        r.setCode("0"+r.getNum());
        while (r.getCode().length()<10){
            r.setCode("0"+r.getCode());
        }
        Initilal i = initialRepository.findByElement("Reglement Fournisseur");
        r.setCode(i.getInitial()+r.getCode());
        r.setImpaye(false);
        r.setTypereglement(0);
        r.setSoldeRestant(regRequest.getSolde());
        r.setType(1);
        r.setEstComptablise(false);
        r.setTiers(fournisseurRepository.findById(regRequest.getIdfournisseur()).get().getCode());
        r.setCaisse(0);
        r.setSolde(regRequest.getSolde());
        r.setSoldeDev(regRequest.getSoldedev());
        r.setFournisseur(fournisseurRepository.findById(regRequest.getIdfournisseur()).get());
        if(regRequest.getType().equals("CHEQUE")){
            r.setLibelle("REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }
        if(regRequest.getType().equals("ESPECE")){
            r.setLibelle("REG ESP");
            r.setDateEcheance(LocalDate.now());
        }
        if(regRequest.getType().equals("TRAITE")){
            r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }
        if(regRequest.getType().equals("VIREMENT")){
            r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }

        r.setCloture(0);
        r.setBanque(0);
        r.setReference(regRequest.getReference());
        r.setEstAffecte(false);
        r.setEtat(EReglement.NonAffecter);
        r.setDateReglement(LocalDate.now());
        r.setTransfere(0);
        r.setValide("oui");
        regRepository.save(r);
        if(regRequest.isRs()){
            BigDecimal bp =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSolde())).multiply(BigDecimal.valueOf(0.01));
            BigDecimal bpdev =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSoldeDev())).multiply(BigDecimal.valueOf(0.01));
            double solde = BigDecimal.valueOf(r.getSolde()).subtract(bp).doubleValue();
            r.setSolde(solde);
            r.setSoldeRestant(solde);
            r.setSoldeDev(BigDecimal.valueOf(r.getSoldeDev()).subtract(bpdev).doubleValue());
            Reglement rs = new Reglement();
            P_REGLEMENT ps = pReglementRepository.findByType("RS");
            rs.setN_Reglement(ps.getId());
            if(reglementRepository.getNumReglement()> regRepository.getNumReglement()){
                rs.setNum(reglementRepository.getNumReglement()+1);
            } else  {
                rs.setNum(regRepository.getNumReglement()+1);
            }
            rs.setCode("0"+rs.getNum());
            while (rs.getCode().length()<10){
                rs.setCode("0"+rs.getCode());
            }
            Initilal is = initialRepository.findByElement("Retenu à la source");
            rs.setCode(is.getInitial()+rs.getCode());
            rs.setImpaye(false);
            rs.setTypereglement(0);
            rs.setSoldeRestant(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setType(1);
            rs.setEstComptablise(false);
            rs.setTiers(fournisseurRepository.findById(regRequest.getIdfournisseur()).get().getCode());
            rs.setCaisse(0);
            rs.setSolde(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setSoldeDev(BigDecimal.valueOf(regRequest.getSoldedev()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setFournisseur(fournisseurRepository.findById(regRequest.getIdfournisseur()).get());
            if(regRequest.getType().equals("CHEQUE")){
                rs.setLibelle("R/S REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                rs.setDateEcheance(regRequest.getDateEcheance());
            }
            if(regRequest.getType().equals("ESPECE")){
                rs.setLibelle("R/S REG ESP");
                rs.setDateEcheance(LocalDate.now());
            }
            if(regRequest.getType().equals("TRAITE")){
                rs.setLibelle("R/S REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                rs.setDateEcheance(regRequest.getDateEcheance());
            }
            if(regRequest.getType().equals("VIREMENT")){
                rs.setLibelle("R/S REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                rs.setDateEcheance(regRequest.getDateEcheance());
            }

            rs.setCloture(0);
            rs.setBanque(0);
            rs.setReference(regRequest.getReference());
            rs.setEstAffecte(false);
            rs.setEtat(EReglement.NonAffecter);
            rs.setDateReglement(LocalDate.now());
            rs.setTransfere(0);
            rs.setValide("oui");
            regRepository.save(rs);
            regRepository.save(r);
        }
        return ResponseEntity.ok(new MessageResponse("Reglements "+ regRequest.getType() +" ajouté avec success"));
    }

    @Override
    public ResponseEntity<?> ReglerImpayer(long id , RegRequest regRequest) {
        Reglement imp = regRepository.findById(id).get();
        Reglement r = new Reglement();
        P_REGLEMENT p = pReglementRepository.findByType(regRequest.getType());
        r.setN_Reglement(p.getId());
        if(reglementRepository.getNumReglement()> regRepository.getNumReglement()){
            r.setNum(reglementRepository.getNumReglement()+1);
        } else  {
            r.setNum(regRepository.getNumReglement()+1);
        }
        r.setCode("0"+r.getNum());
        while (r.getCode().length()<10){
            r.setCode("0"+r.getCode());
        }
        Initilal i = initialRepository.findByElement("Reglement Fournisseur");
        r.setCode(i.getInitial()+r.getCode());
        r.setImpaye(false);
        r.setTypereglement(0);
        r.setSoldeRestant(imp.getSoldeRestant());
        r.setType(1);
        r.setEstComptablise(false);
        r.setTiers(imp.getTiers());
        r.setCaisse(0);
        r.setSolde(imp.getSolde());
        if(imp.getFournisseur()!=null) {
            r.setFournisseur(imp.getFournisseur());
        }else {
           r.setClient(imp.getClient());
        }
        if(regRequest.getType().equals("CHEQUE")){
            r.setLibelle("REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }
        if(regRequest.getType().equals("ESPECE")){
            r.setLibelle("REG ESP");
            r.setDateEcheance(LocalDate.now());
        }
        if(regRequest.getType().equals("TRAITE")){
            r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }
        if(regRequest.getType().equals("VIREMENT")){
            r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
            r.setDateEcheance(regRequest.getDateEcheance());
        }

        r.setCloture(0);
        r.setBanque(0);
        r.setReference(regRequest.getReference());
        r.setEstAffecte(false);
        r.setEtat(imp.getEtat());
        r.setDateReglement(LocalDate.now());
        r.setTransfere(0);
        r.setValide("oui");
        P_REGLEMENT pr =pReglementRepository.findById(imp.getN_Reglement()).get();

        if(pr.getType().equals("RS")){

            r.setCode("0"+r.getNum());
            while (r.getCode().length()<10){
                r.setCode("0"+r.getCode());
            }
            Initilal is = initialRepository.findByElement("Retenu à la source");
            r.setCode(is.getInitial()+r.getCode());

            if(regRequest.getType().equals("CHEQUE")){
                r.setLibelle("R/S REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                r.setDateEcheance(regRequest.getDateEcheance());
            }
            if(regRequest.getType().equals("ESPECE")){
                r.setLibelle("R/S REG ESP");
                r.setDateEcheance(LocalDate.now());
            }
            if(regRequest.getType().equals("TRAITE")){
                r.setLibelle("R/S REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                r.setDateEcheance(regRequest.getDateEcheance());
            }
            if(regRequest.getType().equals("VIREMENT")){
                r.setLibelle("R/S REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                r.setDateEcheance(regRequest.getDateEcheance());
            }

        }
        regRepository.save(r);
 List<ImputationReglement> ir = imputationRepository.findAllByReglement(imp);
     ir.forEach(val->{
         val.setReglement(r);
     });


        imputationRepository.saveAll(ir);
        return ResponseEntity.ok(new MessageResponse("Reglement reglé avec success"));
    }

    @Override
    public List<Reglement> findAllByDateEcheance(LocalDate date) {
        return null;
    }

   @Override
    public List<EcheancierResponse> findAllDateEcheanceFournisseurs(long iduser) {
        P_REGLEMENT p = pReglementRepository.findByType("CHEQUE");
        P_REGLEMENT p1 = pReglementRepository.findByType("TRAITE");
       LocalDate today = LocalDate.now();

       // Calculer le début et la fin de la semaine actuelle
       LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
       LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<LocalDate> dates = regRepository.findAllDateEcheance(iduser,p.getId(),startOfWeek,endOfWeek);
        List<EcheancierResponse> ech = new ArrayList<>();
        dates.forEach(val-> {
            EcheancierResponse e = new EcheancierResponse();
            List<Reglement> r = regRepository.findAllByDateEcheance(iduser,val , p.getId());
            List<Reglement> r1 = regRepository.findAllByDateEcheance(iduser,val , p1.getId());
            r.addAll(r1);
            e.setDate(val);
            e.setSomme(BigDecimal.valueOf(regRepository.SommeByDateEcheance(iduser,val, p.getId())).add(BigDecimal.valueOf(regRepository.SommeByDateEcheance(iduser,val, p1.getId()))).doubleValue());
            e.setReglements(r);
            ech.add(e);
        });
        return ech;
    }
    @Override
    public List<EcheancierResponse> findAllDateEcheanceClients(long iduser) {
        P_REGLEMENT p = pReglementRepository.findByType("CHEQUE");
        P_REGLEMENT p1 = pReglementRepository.findByType("TRAITE");
        LocalDate today = LocalDate.now();

        // Calculer le début et la fin de la semaine actuelle
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<LocalDate> dates = regRepository.findAllDateEcheanceC(iduser,p.getId(),startOfWeek,endOfWeek);
        List<EcheancierResponse> ech = new ArrayList<>();
        dates.forEach(val-> {
            EcheancierResponse e = new EcheancierResponse();
            List<Reglement> r = regRepository.findAllByDateEcheanceC(iduser,val , p.getId());
            List<Reglement> r1 = regRepository.findAllByDateEcheanceC(iduser,val , p1.getId());
            r.addAll(r1);
            e.setDate(val);
            e.setSomme(BigDecimal.valueOf(regRepository.SommeByDateEcheanceC(iduser,val, p.getId())).add(BigDecimal.valueOf(regRepository.SommeByDateEcheance(iduser,val, p1.getId()))).doubleValue());
            e.setReglements(r);
            ech.add(e);
        });
        return ech;
    }

    @Override
    public List<F_REGLECH> findReglementCHFournisseurs() {
        return reglechRepository.findReglementCHFournisseurs();
    }

    @Override
    public List<F_REGLECH> findReglementFournisseursByPiece(String piece) {
        return reglechRepository.findReglementFournisseursByPiece(piece);
    }

    @Override
    public List<F_REGLECH> findReglementClientByPiece(String piece) {
        return reglechRepository.findReglementClientByPiece(piece);
    }

    @Override
    public List<F_REGLECH> findReglementCHClients() {
        return reglechRepository.findReglementCHClients();
    }
}
