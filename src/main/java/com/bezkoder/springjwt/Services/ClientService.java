package com.bezkoder.springjwt.Services;


import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.SageRepository.TiersSageRepository;
import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.Fournisseur;
import com.bezkoder.springjwt.payload.response.MessageResponse;
import com.bezkoder.springjwt.repository.ClientRepository;
import com.bezkoder.springjwt.repository.FournisseurRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class ClientService implements IClientService {
    @Autowired
    TiersSageRepository tiersSageRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<F_COMPTET> findall() {
        return tiersSageRepository.findClients();
    }
    @Override
    public List<Client> findallClients(@Param("identreprise") int identreprise, @Param("iduser") long iduser) {
        if (identreprise!=0){
            return  clientRepository.findallClientsByEntrepriseId(identreprise);}
        else return clientRepository.findallClientsByUserId(iduser);
    }

    @Override
    public ResponseEntity<?> ImportClients(long iduser) {
        List<F_COMPTET> sageClients = tiersSageRepository.findClients();
        List<Client> clients = new ArrayList<>();
        sageClients.forEach(val->{
            if(!clientRepository.existsByCode(val.getCode())) {
                Client client = new Client();
                client.setAdresse(val.getAdresse());
                client.setCode(val.getCode());
                client.setApe(val.getApe());
                client.setContact(val.getContact());
                client.setComplement(val.getComplement());
                client.setIdentifiant(val.getIdentifiant());
                client.setSite(val.getSite());
                client.setNom(val.getNom());
                client.setPays(val.getPays());
                client.setEmail(val.getEmail());
                client.setSiret(val.getSiret());
                client.setTelecopie(val.getTelecopie());
                client.setUser(userRepository.findById(iduser).get());
                clients.add(client);
            }
        });
        clientRepository.saveAll(clients);
        return ResponseEntity.ok(new MessageResponse("Clients importé avec success"));
    }
}
