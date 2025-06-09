package integrator.product.domain.services;

import integrator.product.controller.dtos.ReactivateDTO;
import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.enums.ClientStatus;
import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.model.exceptions.InternalServerErrorException;
import integrator.product.domain.model.exceptions.NotFoundException;
import integrator.product.domain.model.mappers.ClientMapper;
import integrator.product.domain.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static integrator.product.domain.model.exceptions.ServiceExecutorExceptionHandler.execute;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public Client postNewClient(Client client){

        return execute(logger, "Erro ao cadastrar o cliente", () -> {

            if(client.getClientStatus().equals(ClientStatus.ACTIVE)){
                throw new BadRequestException("Status inválido para esta operação");
            }

            clientRepository.getClientByClientDocument(client.getClientDocument()).ifPresent(c -> {throw new BadRequestException("Cliente ja esta cadastrado");});
            return clientRepository.save(client);

        });
    }

    public Client getClient(String document){
        return execute(logger, "Erro ao recuperar o cliente", () -> clientRepository.getClientByClientDocument(document).orElseThrow(() -> new NotFoundException("Cliente não encontrado")));
    }

    public Client updateClientInfos(Client client){

        return execute(logger, "Erro ao atualizar o cliente", () -> {

            Client existingClient = clientRepository.getClientByClientDocument(client.getClientDocument())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            clientMapper.updateClientFromDto(client, existingClient);
            clientRepository.save(existingClient);
            return client;
        });
    }

    public Client changeClientTitulatiry(Client client){

        return execute(logger, "Erro ao atualizar a titularidade do cliente", () -> {
            Client existingClient = clientRepository.getClientByClientDocument(client.getClientDocument())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
            clientMapper.updateClientChangingTitularity(client, existingClient);
            clientRepository.save(client);

            return client;
        });
    }

    public Client reactivateClient(ReactivateDTO reactivateDTO){

        return execute(logger,"Erro ao reativar o cliente", () ->{
            Client existingClient = clientRepository.getClientByClientDocument(reactivateDTO.getDocument())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

            if (!reactivateDTO.getClientStatus().equals(ClientStatus.ACTIVE))
                throw new BadRequestException("Status inválido para esta operação");

            if(existingClient.getClientStatus() == null){
                throw new InternalServerErrorException("Status do cliente é null, verificar inconsistencia");
            }

            if(existingClient.getClientStatus().equals(ClientStatus.ACTIVE)){
                throw new BadRequestException("Cliente ja esta ativo");
            }

            if (existingClient.getClientStatus().canBeReactivated()) {
                throw new BadRequestException("Status atual do cliente não permite reativação");
            }

            existingClient.setClientStatus(reactivateDTO.getClientStatus());
            clientRepository.save(existingClient);

            return existingClient;
        });
    }

    public Client deactivateClient(ReactivateDTO reactivateDTO){

        return execute(logger,"Erro ao desativar o cliente", () ->{
            Client existingClient = clientRepository.getClientByClientDocument(reactivateDTO.getDocument())
                    .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

            if(existingClient.getClientStatus() == null){
                throw new InternalServerErrorException("Status do cliente é null, verificar inconsistencia");
            }

            if (!reactivateDTO.getClientStatus().canBeSuspended()) {
                throw new BadRequestException("Status atual não permite desativação");
            }

            if(existingClient.getClientStatus().canBeSuspended()){
                throw new BadRequestException("Cliente ja esta suspenso");
            }

            existingClient.setClientStatus(reactivateDTO.getClientStatus());
            clientRepository.save(existingClient);

            return existingClient;
        });
    }

    public Client cancellingClient(ReactivateDTO reactivateDTO){

        return execute(logger,"Erro ao cancelar o cliente", () ->{
            Client existingClient = clientRepository.getClientByClientDocument(reactivateDTO.getDocument()).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

            if(!reactivateDTO.getClientStatus().canBeReactivated()){
                throw new BadRequestException("Status inválido para esta operação");
            }

            if (existingClient.getClientStatus().canBeReactivated()){
                throw new BadRequestException("Cliente ja esta cancelado");
            }

            existingClient.setClientStatus(ClientStatus.CANCELLED_BY_NO_PAYMENT);
            clientRepository.save(existingClient);

            return existingClient;
        });
    }

}
