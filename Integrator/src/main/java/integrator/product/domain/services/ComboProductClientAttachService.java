package integrator.product.domain.services;

import integrator.product.controller.dtos.PurchaseComboDTO;
import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.entities.ComboProduct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static integrator.product.domain.model.exceptions.ServiceExecutorExceptionHandler.execute;

@Service
public class ComboProductClientAttachService {

    private final ClientService clientService;
    private final ComboProductService comboProductService;
    private static final Logger logger = LoggerFactory.getLogger(ComboProductClientAttachService.class);


    public ComboProductClientAttachService(ClientService clientService, ComboProductService comboProductService) {
        this.clientService = clientService;
        this.comboProductService = comboProductService;
    }

    public PurchaseComboDTO purchaseProducts(PurchaseComboDTO purchaseComboDTO){

        return execute(logger, "Erro inesperado ao comprar os produtos", () ->{
            Client client = clientService.getClient(purchaseComboDTO.getDocument());

            for(Long id : purchaseComboDTO.getComboId()){
                ComboProduct existingComboProduct = comboProductService.getComboProduct(id);

            }

            return null;

        });
    }
}
