package com.bezkoder.springjwt.Services;

import com.bezkoder.springjwt.SageModels.F_COMPTET;
import com.bezkoder.springjwt.models.Client;
import com.bezkoder.springjwt.models.Fournisseur;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IClientService {

    List<F_COMPTET> findall();
    List<Client> findallClients(int identreprise, long iduser);
    ResponseEntity<?> ImportClients(long iduser);
}
