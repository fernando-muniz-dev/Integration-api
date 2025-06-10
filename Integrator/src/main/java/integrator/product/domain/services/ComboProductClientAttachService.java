package integrator.product.domain.services;

import integrator.product.controller.dtos.PurchaseComboDTO;
import integrator.product.controller.response.ComboProductCreatedAndAttached;
import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.ComboProductClientAttach;
import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.repository.ComboProductClientAttachRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static integrator.product.domain.model.exceptions.ServiceExecutorExceptionHandler.execute;

@Service
public class ComboProductClientAttachService {

    private final ClientService clientService;
    private final ComboProductService comboProductService;
    private final ComboProductClientAttachRepository comboProductClientAttachRepository;
    private static final Logger logger = LoggerFactory.getLogger(ComboProductClientAttachService.class);


    public ComboProductClientAttachService(ClientService clientService, ComboProductService comboProductService, ComboProductClientAttachRepository comboProductClientAttachRepository) {
        this.clientService = clientService;
        this.comboProductService = comboProductService;
        this.comboProductClientAttachRepository = comboProductClientAttachRepository;
    }

    public List<ComboProductCreatedAndAttached> purchaseProducts(PurchaseComboDTO purchaseComboDTO){

        return execute(logger, "Erro inesperado ao comprar os produtos", () ->{
            List<ComboProductCreatedAndAttached> comboProductCreatedAndAttached = new ArrayList<>();

            Client client = clientService.getClient(purchaseComboDTO.getDocument());

            for(Long id : purchaseComboDTO.getComboId()){
                ComboProduct existingComboProduct = comboProductService.getComboProductNotThrowingException(id);

                if (existingComboProduct != null){
                    if(comboProductClientAttachRepository.getAttachmentClientAndComboProduct(existingComboProduct, client).isEmpty()){
                        comboProductClientAttachRepository.save(new ComboProductClientAttach(null, existingComboProduct,client, 0, LocalDateTime.now()));
                        comboProductCreatedAndAttached.add(new ComboProductCreatedAndAttached(true, "Produto "+ existingComboProduct.getComboName() +": comprado com sucesso"));
                    }else{
                        comboProductCreatedAndAttached.add(new ComboProductCreatedAndAttached(false, "O cliente ja possui este produto: " + existingComboProduct.getComboName()));
                    }
                }else{
                    comboProductCreatedAndAttached.add(new ComboProductCreatedAndAttached(false, "Erro ao comprar o combo com o id: " + id));
                }
            }

            return comboProductCreatedAndAttached;

        });
    }
}
