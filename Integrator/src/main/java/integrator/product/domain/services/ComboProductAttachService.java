package integrator.product.domain.services;

import integrator.product.controller.response.ComboProductAttachResponse;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.ComboProductAttach;
import integrator.product.domain.model.entities.Product;
import integrator.product.domain.model.enums.ComboProductAttachStatus;
import integrator.product.domain.model.enums.ComboProductStatus;
import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.repository.ComboProductAttachRepository;
import integrator.product.domain.repository.ComboProductClientAttachRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ComboProductAttachService {

    private final ComboProductAttachRepository comboProductAttachRepository;
    private final ProductService productService;

    public ComboProductAttachService(ComboProductAttachRepository comboProductAttachRepository, ProductService productService) {
        this.comboProductAttachRepository = comboProductAttachRepository;
        this.productService = productService;
    }

    public List<ComboProductAttachResponse> attachComboProductOnCreation(List<String> provisioningAttaches, ComboProduct comboProduct){
        List<ComboProductAttachResponse> comboProductAttaches = new ArrayList<>();

        if(comboProduct.getComboProductStatus().equals(ComboProductStatus.UNIQUE_PRODUCT)){
            if(provisioningAttaches.size() > 1){
                throw new BadRequestException("Há mais de um produto querendo ser associado para um combo unico");
            }
        }

        for(String item : provisioningAttaches){

            Product product = productService.getProductWithoutException(item);

            if(product != null){
                ComboProductAttach comboProductAttach = comboProductAttachRepository.save(new ComboProductAttach(null, comboProduct,product, ComboProductAttachStatus.LINKED));
                comboProductAttaches.add(new ComboProductAttachResponse(true, "Produto associado " + item +" com sucesso!"));
            }else{
                comboProductAttaches.add(new ComboProductAttachResponse(false, "Produto não existente: " + item));
            }
        }

        if(comboProductAttaches.isEmpty()){
            comboProductAttaches.add(new ComboProductAttachResponse(false, "Erro ao associar os produtos com o combo"));
        }

        return comboProductAttaches;
    }
}
