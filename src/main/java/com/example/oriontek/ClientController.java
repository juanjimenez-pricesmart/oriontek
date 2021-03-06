package com.example.oriontek;

import com.example.oriontek.request.AddAddressRequest;
import com.example.oriontek.request.SaveClientRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Validated
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService service;

    private final ObjectMapper mapper;

    public ClientController(ClientService service) {
        this.service = service;
        this.mapper = new ObjectMapper();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getClientById(@PathVariable(name = "id") Long id) {

        var test = service.getClientById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(test);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getClientsByName(@PathVariable(name = "name") String name) {

        return ResponseEntity.status(HttpStatus.OK).body(service.getClientsByName(name));
    }

    @GetMapping("/clients-location/{location}")
    public ResponseEntity<?> getClientsByLocation(@PathVariable(name = "location") String location) {

        return ResponseEntity.status(HttpStatus.OK).body(service.getClientsByLocation(location));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllClients() {

        return ResponseEntity.status(HttpStatus.OK).body(service.getAllClients());
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveClient(@Valid @RequestBody SaveClientRequest client) throws JsonProcessingException {

        return ResponseEntity.status(HttpStatus.OK).body(service.saveClient(mapper.readValue(mapper.writeValueAsString(client), Client.class)));
    }

    @PutMapping("/add-address")
    public ResponseEntity<?> addAddressByClientId(@Valid @RequestBody AddAddressRequest client) throws JsonProcessingException {

        return ResponseEntity.status(HttpStatus.OK).body(service.addAddressByClientId(mapper.readValue(mapper.writeValueAsString(client), Client.class)));
    }

    @PutMapping("/update-client-name/{id}/new-name/{name}")
    public ResponseEntity<?> updateClientNameById(@PathVariable(name = "id") @NotNull Long id,
                                                  @PathVariable(name = "name") @NotNull @Size(min = 3, max = 40) String name) {

        return ResponseEntity.status(HttpStatus.OK).body(service.updateClientNameById(new Client() {{
            setId(id);
            setName(name);
        }}));
    }

    @DeleteMapping("/delete-client/id/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable(name = "id") @NotNull Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(service.deleteClientById(id));
    }

    @DeleteMapping("/delete-client/name/{name}")
    public ResponseEntity<?> deleteClientByName(@PathVariable(name = "name") @NotNull String name) {

        return ResponseEntity.status(HttpStatus.OK).body(service.deleteClientByName(name));
    }
}
