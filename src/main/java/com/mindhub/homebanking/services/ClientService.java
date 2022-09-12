package com.mindhub.homebanking.services;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
  public List<Client> getClients();
  public Client findById(Long id);
  public Client findByEmail (String email);
  public void saveClient (Client client);
}
