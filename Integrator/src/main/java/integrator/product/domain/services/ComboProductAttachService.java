package integrator.product.domain.services;

import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.ComboProductAttach;
import integrator.product.domain.model.enums.ComboProductStatus;
import integrator.product.domain.model.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComboProductAttachService {

    public List<Object> attachComboProductOnCreation(List<ComboProductAttach> provisioningAttaches, ComboProduct comboProduct){
        if(comboProduct.getComboProductStatus().equals(ComboProductStatus.UNIQUE_PRODUCT)){
            if(provisioningAttaches.size() > 1){
                throw new BadRequestException("Há mais de um produto querendo ser associado para um combo unico");
            }
        }

        return null;
    }
}
