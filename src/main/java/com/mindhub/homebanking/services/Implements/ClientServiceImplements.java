package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImplements implements com.mindhub.homebanking.services.ClientService{
  @Autowired
  private ClientRepository clientRepository;

  @Override
  public List<Client> getClients() {
    return clientRepository.findAll();
  }
  @Override
  public Client findById(Long id) {
    return clientRepository.findById(id).orElse(null);
  }
  @Override
  public Client findByEmail(String email){
    return clientRepository.findByEmail(email);
  }
  @Override
  public void saveClient(Client client){
    clientRepository.save(client);
  }

}
