package com.ioco.discovery.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hjd
 */
@Service
public class ClientService {
  
  @Autowired
  private ClientRepository clientRepository;
  
  
}
