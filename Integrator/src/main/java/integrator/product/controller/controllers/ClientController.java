package integrator.product.controller.controllers;

import integrator.product.controller.dtos.ClientDTO;
import integrator.product.controller.dtos.ReactivateDTO;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.mappers.ClientMapper;
import integrator.product.domain.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@Validated
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping()
    @SuccessMessage("Cliente criado com sucesso")
    public Client postNewClient(@RequestBody @Valid ClientDTO client){
        return clientService.postNewClient(clientMapper.toEntity(client));
    }

    @GetMapping("/{document}")
    public Client getClient(@PathVariable @CpfCnpj String document){
        return clientService.getClient(document);
    }

    @PutMapping()
    @SuccessMessage("Cliente atualizado com sucesso")
    public Client changeClientInfos(@RequestBody @Valid ClientDTO clientDTO){
        return clientService.updateClientInfos(clientMapper.toEntity(clientDTO));
    }

    @PutMapping("/changeTitularity")
    @SuccessMessage("Mudança de titularidade com sucesso")
    public Client changeClientTitularity(@RequestBody @Valid ClientDTO clientDTO){
        return clientService.changeClientTitulatiry(clientMapper.toEntity(clientDTO));
    }

    @PutMapping("/reactivate")
    @SuccessMessage("Cliente reativado com sucesso")
    public Client reactivateClient(@RequestBody @Valid ReactivateDTO reactivateDTO){
        return clientService.reactivateClient(reactivateDTO);
    }

    @PutMapping("/suspend")
    @SuccessMessage("Cliente suspenso com sucesso")
    public Client deactivateClient(@RequestBody @Valid ReactivateDTO reactivateDTO){
        return clientService.deactivateClient(reactivateDTO);
    }

    @PutMapping("/cancel")
    @SuccessMessage("Cliente cancelado com sucesso")
    public Client cancellingClient(@RequestBody @Valid ReactivateDTO reactivateDTO){
        return clientService.cancellingClient(reactivateDTO);
    }
}
