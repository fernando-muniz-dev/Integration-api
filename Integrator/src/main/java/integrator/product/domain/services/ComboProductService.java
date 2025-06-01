package integrator.product.domain.services;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.mappers.ComboProductMapper;
import integrator.product.domain.repository.ComboProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static integrator.product.controller.validator.utils.ServiceExecutorExceptionHandler.execute;

@Service
public class ComboProductService {

    private final ComboProductRepository comboProductRepository;
    private final ComboProductAttachService comboProductAttachService;
    private final ComboProductMapper comboProductMapper;
    private static final Logger logger = LoggerFactory.getLogger(ComboProductService.class);

    public ComboProductService(ComboProductRepository comboProductRepository, ComboProductAttachService comboProductAttachService, ComboProductMapper comboProductMapper) {
        this.comboProductRepository = comboProductRepository;
        this.comboProductAttachService = comboProductAttachService;
        this.comboProductMapper = comboProductMapper;
    }

    public ComboProduct postNewComboProduct(ComboProductDTO comboProductDTO){
        return execute(logger,"Erro ao cadastrar o combo", () -> {

            ComboProduct comboProduct = comboProductMapper.toEntity(comboProductDTO);

            comboProduct = comboProductRepository.save(comboProduct);

            if(!comboProductDTO.getComboProductAttaches().isEmpty()){
                comboProductAttachService.attachComboProductOnCreation(comboProductDTO.getComboProductAttaches(), comboProduct);
            }

            return comboProduct;
        });
    }

    public ComboProduct getComboProduct(Long id){
        return execute(logger,"Erro ao recuperar o combo", () -> comboProductRepository.getReferenceById(id));
    }

    public List<ComboProduct> getAllComboProducts(){
        return execute(logger,"Erro ao recurar todos os combos", comboProductRepository::getAllCombos);
    }
}
