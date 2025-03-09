package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.SageModels.F_DOCENTETE;
import com.bezkoder.springjwt.SageModels.F_DOCLIGNE;
import com.bezkoder.springjwt.SageModels.P_REGLEMENT;
import com.bezkoder.springjwt.SageRepository.DocRepository;
import com.bezkoder.springjwt.SageRepository.LigneFactureRepository;
import com.bezkoder.springjwt.SageRepository.P_ReglementRepository;
import com.bezkoder.springjwt.SageRepository.ReglementRepository;
import com.bezkoder.springjwt.models.*;
import com.bezkoder.springjwt.payload.request.RegRequest;
import com.bezkoder.springjwt.payload.response.DetailFactureResponse;
import com.bezkoder.springjwt.payload.response.LigneFactureResponse;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ReglementRepository reglementRepository;
    @Autowired
    CaisseRepository caisseRepository;
    @Autowired
    RegRepository regRepository;
    @Autowired
    BanqueRepository banqueRepository;
    @Autowired
    InitialRepository initialRepository;
    @Autowired
    ImputationRepository imputationRepository;
    @Autowired
    P_ReglementRepository pReglementRepository;
    @Autowired
    MouvementRepository mouvementRepository;
    @Autowired
    MouvementBanqueRepository mouvementBanqueRepository;
    @Autowired
    LigneFactureRepository ligneFactureRepository;

    @Override
    public List<F_DOCENTETE> findallFactureFournisseurs(@Param("idfournisseur") String idfournisseur) {
        return docRepository.findFactureFournisseur(idfournisseur);
    }
    @Override
    public List<F_DOCENTETE> findallFactureClients(@Param("idfournisseur") String idclient) {
        return docRepository.findFactureClient(idclient);
    }
    @Override
    public List<Facture> findallFacturesByFournisseur(@Param("idfournisseur") String idfournisseur) {
        return factureRepository.findAllFactureByFournisseur(idfournisseur) ;}
    @Override
    public List<Facture> findallFacturesByClient(@Param("idclient") String idclient) {
        return factureRepository.findAllFactureByClient(idclient) ;}

    @Override
    public List<Facture> findallFactureFournisseurs(@Param("iduser") long iduser) {
        return factureRepository.findAllFactureFournisseurs(iduser);
    }

    @Override
    public DetailFactureResponse findFactureClientByPiece(String piece) {
        Facture facture = factureRepository.findById(piece).get();
        List<F_DOCLIGNE> dl = ligneFactureRepository.findLigneFactureFournisseur(piece);
        List<LigneFactureResponse> l = new ArrayList<>();
        DetailFactureResponse d = new DetailFactureResponse();
        d.setPiece(facture.getId());
        d.setEtat(facture.getEtat());
        d.setDomaine(facture.getDomaine());
        d.setDocumentDate(facture.getDocumentDate());
        d.setClientCode(facture.getClientCode());
        d.setNameClient(facture.getClient().getNom());
        d.setNetAPayer(facture.getNetAPayer());
        d.setTotalAmountHT(facture.getTotalAmountHT());
        d.setTotalAmountTTC(facture.getTotalAmountTTC());
        d.setResteAPayer(facture.getResteAPayer());
        dl.forEach(val->{
            LigneFactureResponse lf = new LigneFactureResponse();
            lf.setDesignation(val.getDesignation());
            lf.setQuantity(val.getQuantity());
            lf.setReference(val.getReference());
            lf.setAmountTTC(val.getAmountTTC());
            lf.setUnitpriceHT(val.getUnitpriceHT());
            lf.setLineNumber(val.getLineNumber());
            lf.setRemise(0);
            lf.setAmountHT(val.getAmountHT());
            lf.setUnitPriceTTC(val.getUnitPriceTTC());
            lf.setId(val.getId());
            l.add(lf);
        });
        d.setLigneFactures(l);
        return d;
    }

    @Override
    public DetailFactureResponse findFactureFournisseursByPiece(String piece) {
        Facture facture = factureRepository.findById(piece).get();
        List<F_DOCLIGNE> dl = ligneFactureRepository.findLigneFactureFournisseur(piece);
        List<LigneFactureResponse> l = new ArrayList<>();
        DetailFactureResponse d = new DetailFactureResponse();
        d.setPiece(facture.getId());
        d.setEtat(facture.getEtat());
        d.setDomaine(facture.getDomaine());
        d.setDocumentDate(facture.getDocumentDate());
        d.setClientCode(facture.getClientCode());
        d.setNameClient(facture.getFournisseur().getNom());
        d.setNetAPayer(facture.getNetAPayer());
        d.setTotalAmountHT(facture.getTotalAmountHT());
        d.setTotalAmountTTC(facture.getTotalAmountTTC());
        d.setResteAPayer(facture.getResteAPayer());
        dl.forEach(val->{
            LigneFactureResponse lf = new LigneFactureResponse();
            lf.setDesignation(val.getDesignation());
            lf.setQuantity(val.getQuantity());
            lf.setReference(val.getReference());
            lf.setAmountHT(val.getAmountHT());
            lf.setAmountTTC(val.getAmountTTC());
            lf.setUnitpriceHT(val.getUnitpriceHT());
            lf.setLineNumber(val.getLineNumber());
            lf.setRemise(0);
            lf.setUnitPriceTTC(val.getUnitPriceTTC());
            lf.setId(val.getId());
            l.add(lf);
        });
        d.setLigneFactures(l);
        return d;
    }

    @Override
    public List<Facture> findallFactureClients(@Param("iduser") long iduser) {
        return factureRepository.findAllFactureClients(iduser);
    }

    @Override
    public ResponseEntity<?> ReglerFactureFournisseurs(RegRequest regRequest, String piece) {
        Facture f = factureRepository.findFactureFournisseur(piece);

        double solde = BigDecimal.valueOf(f.getResteAPayer()).subtract(BigDecimal.valueOf(regRequest.getSolde())).doubleValue();


        Reglement r = new Reglement();
        if(regRequest.getType().equals("ESPECE")) {
            Caisse c = caisseRepository.findById(regRequest.getIdcaisse()).get();
            if (c.getSolde() >= regRequest.getSolde()) {
                c.setSolde(BigDecimal.valueOf(c.getSolde()).subtract(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());
                r.setLibelle("REG ESP");
                r.setDateEcheance(LocalDate.now());
                MouvementCaisse m = new MouvementCaisse();
                m.setCaisse(c);
                m.setMouvement("-" + regRequest.getSolde() + " DT");
                m.setPiece(f.getId());
                m.setLibelle("REG ESP");
                m.setDate(LocalDate.now());
                mouvementRepository.save(m);
                caisseRepository.save(c);

            } else return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Solde Caisse Insuffisant "));
        } else{
            Banque b = banqueRepository.findById(regRequest.getIdbanque()).get();
            if (b.getSolde() >= regRequest.getSolde()) {

                b.setSolde(BigDecimal.valueOf(b.getSolde()).subtract(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());


                if(regRequest.getType().equals("CHEQUE")){
                    r.setLibelle("REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                }
                if(regRequest.getType().equals("TRAITE")){
                    r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                }
                if(regRequest.getType().equals("VIREMENT")){
                    r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                }

                r.setDateEcheance(regRequest.getDateEcheance());
                MouvementBanque m = new MouvementBanque();
                m.setBanque(b);
                m.setMouvement("-" + regRequest.getSolde() + " DT");
                m.setPiece(f.getId());
                m.setLibelle(r.getLibelle());
                m.setDate(LocalDate.now());
                mouvementBanqueRepository.save(m);
                banqueRepository.save(b);

            } else return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Solde Banque Insuffisant "));
        }
        P_REGLEMENT p = pReglementRepository.findByType(regRequest.getType());
        r.setN_Reglement(p.getId());
        if (reglementRepository.getNumReglement() > regRepository.getNumReglement()) {
            r.setNum(reglementRepository.getNumReglement() + 1);
        } else {
            r.setNum(regRepository.getNumReglement() + 1);
        }
        r.setCode("0"+r.getNum());
        while (r.getCode().length()<10){
            r.setCode("0"+r.getCode());
        }
        Initilal in = initialRepository.findByElement("Reglement Fournisseur");
        r.setCode(in.getInitial()+r.getCode());
        r.setImpaye(false);
        r.setTypereglement(0);
        r.setSoldeRestant(0);
        r.setType(1);
        r.setEstComptablise(false);
        r.setTiers(f.getNumPayeur());
        r.setCaisse(0);
        r.setSolde(regRequest.getSolde());
        r.setSoldeDev(regRequest.getSolde());
        r.setFournisseur(f.getFournisseur());
        r.setCloture(0);
        r.setBanque(0);
        r.setReference(regRequest.getReference());
        r.setEstAffecte(true);
        r.setEtat(EReglement.TotAffecter);
        r.setDateReglement(LocalDate.now());
        r.setTransfere(0);
        r.setValide("oui");
        f.setResteAPayer(BigDecimal.valueOf(f.getResteAPayer()).subtract(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());
        f.setMontantRegle(BigDecimal.valueOf(f.getMontantRegle()).add(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());
        if (solde == 0) {
            f.setEtat(EFacture.TotRegle);
        } else f.setEtat(EFacture.PartRegle);
        ImputationReglement i = new ImputationReglement();
        i.setMontant(regRequest.getSolde());
        i.setFacture(f);
        i.setReglement(r);
        i.setDomaine(f.getDomaine());
        i.setType(f.getType());
        regRepository.save(r);
        factureRepository.save(f);
        imputationRepository.save(i);
        if(regRequest.isRs()){
            BigDecimal bp =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSolde())).multiply(BigDecimal.valueOf(0.01));
            BigDecimal bpdev =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSoldeDev())).multiply(BigDecimal.valueOf(0.01));
            double solde1 = BigDecimal.valueOf(r.getSolde()).subtract(bp).doubleValue();
            r.setSolde(solde1);
            r.setSoldeRestant(0);
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
            rs.setSoldeRestant(0);
            rs.setType(1);
            rs.setEstComptablise(false);
            rs.setTiers(f.getNumPayeur());
            rs.setCaisse(0);
            rs.setSolde(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setSoldeDev(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setFournisseur(f.getFournisseur());
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
            rs.setEstAffecte(true);
            rs.setEtat(EReglement.TotAffecter);
            rs.setDateReglement(LocalDate.now());
            rs.setTransfere(0);
            rs.setValide("oui");

            ImputationReglement is = new ImputationReglement();
            is.setMontant(rs.getSolde());
            is.setFacture(f);
            is.setReglement(rs);
            is.setDomaine(f.getDomaine());
            is.setType(f.getType());

            regRepository.save(rs);
            regRepository.save(r);
            imputationRepository.save(is);
        }
        return ResponseEntity.ok(new MessageResponse("Factures reglée avec success"));
    }

    @Override
    public ResponseEntity<?> ReglerFactureClient(RegRequest regRequest, String piece) {
        Facture f = factureRepository.findFactureClient(piece);

        double solde = BigDecimal.valueOf(f.getResteAPayer()).subtract(BigDecimal.valueOf(regRequest.getSolde())).doubleValue();


        Reglement r = new Reglement();
        if(regRequest.getType().equals("ESPECE")) {
            Caisse c = caisseRepository.findById(regRequest.getIdcaisse()).get();
                c.setSolde(BigDecimal.valueOf(c.getSolde()).add(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());
                r.setLibelle("REG ESP");
                r.setDateEcheance(LocalDate.now());
                MouvementCaisse m = new MouvementCaisse();
                m.setCaisse(c);
                m.setMouvement("+" + regRequest.getSolde() + " DT");
                m.setPiece(f.getId());
                m.setLibelle("REG ESP");
                m.setDate(LocalDate.now());
                mouvementRepository.save(m);
                caisseRepository.save(c);

        } else{
            Banque b = banqueRepository.findById(regRequest.getIdbanque()).get();

                b.setSolde(BigDecimal.valueOf(b.getSolde()).add(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());


                if(regRequest.getType().equals("CHEQUE")){
                    r.setLibelle("REG CHQ N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                }
                if(regRequest.getType().equals("TRAITE")){
                    r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                }
                if(regRequest.getType().equals("VIREMENT")){
                    r.setLibelle("REG TRT N°"+regRequest.getNumpiece()+" Banque "+regRequest.getBanque());
                }
                r.setDateEcheance(regRequest.getDateEcheance());
            MouvementBanque m = new MouvementBanque();
            m.setBanque(b);
            m.setMouvement("+" + regRequest.getSolde() + " DT");
            m.setPiece(f.getId());
            m.setLibelle(r.getLibelle());
            m.setDate(LocalDate.now());
            mouvementBanqueRepository.save(m);
                banqueRepository.save(b);

            }

        P_REGLEMENT p = pReglementRepository.findByType(regRequest.getType());
        r.setN_Reglement(p.getId());
        if (reglementRepository.getNumReglement() > regRepository.getNumReglement()) {
            r.setNum(reglementRepository.getNumReglement() + 1);
        } else {
            r.setNum(regRepository.getNumReglement() + 1);
        }
        r.setCode("0"+r.getNum());
        while (r.getCode().length()<10){
            r.setCode("0"+r.getCode());
        }
        Initilal in = initialRepository.findByElement("Reglement Client");
        r.setCode(in.getInitial()+r.getCode());

        r.setImpaye(false);
        r.setTypereglement(0);
        r.setSoldeRestant(0);
        r.setType(0);
        r.setEstComptablise(false);
        r.setTiers(f.getNumPayeur());
        r.setCaisse(0);
        r.setSolde(regRequest.getSolde());
        r.setSoldeDev(regRequest.getSolde());
        r.setFournisseur(f.getFournisseur());
        r.setCloture(0);
        r.setBanque(0);
        r.setReference(regRequest.getReference());
        r.setEstAffecte(true);
        r.setEtat(EReglement.TotAffecter);
        r.setDateReglement(LocalDate.now());
        r.setTransfere(0);
        r.setValide("oui");
        f.setResteAPayer(BigDecimal.valueOf(f.getResteAPayer()).subtract(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());
        f.setMontantRegle(BigDecimal.valueOf(f.getMontantRegle()).add(BigDecimal.valueOf(regRequest.getSolde())).doubleValue());
        if (solde == 0) {
            f.setEtat(EFacture.TotRegle);
        } else f.setEtat(EFacture.PartRegle);
        ImputationReglement i = new ImputationReglement();
        i.setMontant(regRequest.getSolde());
        i.setFacture(f);
        i.setReglement(r);
        i.setDomaine(f.getDomaine());
        i.setType(f.getType());
        regRepository.save(r);
        factureRepository.save(f);
        imputationRepository.save(i);
        if(regRequest.isRs()){
            BigDecimal bp =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSolde())).multiply(BigDecimal.valueOf(0.01));
            BigDecimal bpdev =BigDecimal.valueOf(regRequest.getPercentageRs()).multiply(BigDecimal.valueOf(r.getSoldeDev())).multiply(BigDecimal.valueOf(0.01));
            double solde1 = BigDecimal.valueOf(r.getSolde()).subtract(bp).doubleValue();
            r.setSolde(solde1);
            r.setSoldeRestant(0);
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
            rs.setSoldeRestant(0);
            rs.setType(0);
            rs.setEstComptablise(false);
            rs.setTiers(f.getNumPayeur());
            rs.setCaisse(0);
            rs.setSolde(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setSoldeDev(BigDecimal.valueOf(regRequest.getSolde()).multiply(BigDecimal.valueOf(regRequest.getPercentageRs())).multiply(BigDecimal.valueOf(0.01)).doubleValue());
            rs.setFournisseur(f.getFournisseur());
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
            rs.setEstAffecte(true);
            rs.setEtat(EReglement.TotAffecter);
            rs.setDateReglement(LocalDate.now());
            rs.setTransfere(0);
            rs.setValide("oui");

            ImputationReglement is = new ImputationReglement();
            is.setMontant(rs.getSolde());
            is.setFacture(f);
            is.setReglement(rs);
            is.setDomaine(f.getDomaine());
            is.setType(f.getType());

            regRepository.save(rs);
            regRepository.save(r);
            imputationRepository.save(is);
        }
        return ResponseEntity.ok(new MessageResponse("Factures reglée avec success"));
    }


    @Override
    public ResponseEntity<?> ImportFactureFournisseurs(String idfournisseur) {
        List<F_DOCENTETE> sageFactures = docRepository.findFactureFournisseur(idfournisseur);
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
            facture.setResteAPayer(BigDecimal.valueOf(val.getNetAPayer()).subtract(BigDecimal.valueOf(val.getMontantRegle())).doubleValue());
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

    @Override
    public List<Facture> findAllNonTotRegleFournisseurs(String code) {
        return factureRepository.findAllNonTotRegleFournisseurs(code);
    }

    @Override
    public ResponseEntity<?> ImportFactureClient(String idclient) {
        List<F_DOCENTETE> sageFactures = docRepository.findFactureClient(idclient);
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
            facture.setResteAPayer(BigDecimal.valueOf(val.getNetAPayer()).subtract(BigDecimal.valueOf(val.getMontantRegle())).doubleValue());
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

            facture.setClient(clientRepository.findById(val.getNumPayeur()).get());
            factures.add(facture);
        });
        factureRepository.saveAll(factures);
        return ResponseEntity.ok(new MessageResponse("Factures importé avec success"));
    }
}
