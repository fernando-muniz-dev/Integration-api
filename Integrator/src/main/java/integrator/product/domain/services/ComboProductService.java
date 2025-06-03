package integrator.product.domain.services;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.controller.dtos.ComboProductStatusChangerDTO;
import integrator.product.controller.dtos.PurchaseComboDTO;
import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.enums.ComboProductStatus;
import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.model.exceptions.NotFoundException;
import integrator.product.domain.model.mappers.ComboProductMapper;
import integrator.product.domain.repository.ComboProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static integrator.product.domain.model.exceptions.ServiceExecutorExceptionHandler.execute;

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

            if(!comboProductDTO.getComboProductAttaches().isEmpty())
                comboProductAttachService.attachComboProductOnCreation(comboProductDTO.getComboProductAttaches(), comboProduct);


            return comboProduct;
        });
    }

    public ComboProduct getComboProduct(Long id){
        return execute(logger,"Erro ao recuperar o combo", () -> comboProductRepository.getComboProductById(id)
                .orElseThrow(() -> new NotFoundException("Combo não encontrado")));
    }

    public List<ComboProduct> getAllComboProducts(){
        return execute(logger,"Erro ao recuperar todos os combos", comboProductRepository::getAllCombos);
    }

    public ComboProduct updateComboProduct(ComboProduct comboProduct){
        return execute(logger, "Erro ao atualizar o combo",() -> {
            ComboProduct existingComboProduct = comboProductRepository.getComboProductById(comboProduct.getId())
                    .orElseThrow(() -> new NotFoundException("Combo não encontrado"));

            comboProductMapper.updateComboProductFromDto(comboProduct, existingComboProduct);

            return comboProductRepository.save(existingComboProduct);
        });
    }

    public ComboProduct deactivateComboProduct(ComboProductStatusChangerDTO productStatusChangerDTO){
        return execute(logger,"Erro ao desativar o combo", () ->{
            ComboProduct existingComboProduct = comboProductRepository.getComboProductById(productStatusChangerDTO.getComboProductId())
                    .orElseThrow(() -> new NotFoundException("Combo não encontrado"));

            if(existingComboProduct.getComboProductStatus().checkingStatusToCancelOrReactive())
                throw new BadRequestException("Combo não pode ser desativado, ja se encontra cancelado");

            if(productStatusChangerDTO.getComboProductStatus().verifyStatusToReactivate(productStatusChangerDTO.getComboProductStatus()))
                throw new BadRequestException("Parametro status invalido para esta operacao");


            existingComboProduct.setComboProductStatus(productStatusChangerDTO.getComboProductStatus());

            return comboProductRepository.save(existingComboProduct);

        });
    }

    public ComboProduct reactivateComboProduct(ComboProductStatusChangerDTO productStatusChangerDTO){

        return execute(logger,"Erro ao reativar o combo", () ->{
            ComboProduct existingComboProduct = comboProductRepository.getComboProductById(productStatusChangerDTO.getComboProductId())
                    .orElseThrow(() -> new NotFoundException("Combo não encontrado"));


            if(existingComboProduct.getComboProductStatus().equals(ComboProductStatus.ACTIVE))
                    throw new BadRequestException("Combo produto ja se encontra ativo");

            if(existingComboProduct.getComboProductStatus().checkingStatusToCancelOrReactive())
                throw new BadRequestException("Combo não pode ser reativado, precisa criar um novamente");

            if(productStatusChangerDTO.getComboProductStatus().verifyStatusToReactivate(productStatusChangerDTO.getComboProductStatus()))
                throw new BadRequestException("Parametro status invalido para esta operacao");


            existingComboProduct.setComboProductStatus(ComboProductStatus.ACTIVE);

            return comboProductRepository.save(existingComboProduct);

        });
    }

    public ComboProduct cancellingComboProduct(ComboProductStatusChangerDTO productStatusChangerDTO){

        return execute(logger,"Erro ao desativar o combo", () ->{
            ComboProduct existingComboProduct = comboProductRepository.getComboProductById(productStatusChangerDTO.getComboProductId())
                    .orElseThrow(() -> new NotFoundException("Combo não encontrado"));

            if(existingComboProduct.getComboProductStatus().checkingStatusToCancelOrReactive())
                throw new BadRequestException("Combo ja se encontra cancelado");

            if(!productStatusChangerDTO.getComboProductStatus().checkingStatusToCancelOrReactive())
                throw new BadRequestException("Status invalido para esta operacao");

            existingComboProduct.setComboProductStatus(productStatusChangerDTO.getComboProductStatus());

            return comboProductRepository.save(existingComboProduct);

        });
    }
}
