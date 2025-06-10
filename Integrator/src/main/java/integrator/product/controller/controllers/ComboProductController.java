package integrator.product.controller.controllers;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.controller.dtos.ComboProductStatusChangerDTO;
import integrator.product.controller.dtos.PurchaseComboDTO;
import integrator.product.controller.response.ComboProductCreated;
import integrator.product.controller.response.ComboProductCreatedAndAttached;
import integrator.product.controller.validator.constraints.MultiStatusOnPartialFailure;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.mappers.ComboProductMapper;
import integrator.product.domain.services.ComboProductClientAttachService;
import integrator.product.domain.services.ComboProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comboProduct")
public class ComboProductController {

    private final ComboProductService comboProductService;
    private final ComboProductMapper comboProductMapper;
    private final ComboProductClientAttachService comboProductClientAttachService;


    public ComboProductController(ComboProductService comboProductService, ComboProductMapper comboProductMapper, ComboProductClientAttachService comboProductClientAttachService) {
        this.comboProductService = comboProductService;
        this.comboProductMapper = comboProductMapper;
        this.comboProductClientAttachService = comboProductClientAttachService;
    }

    @PostMapping
    @SuccessMessage("Combo cadastrado com sucesso")
    @MultiStatusOnPartialFailure("Erro ao cadastrar o combo ou associar com combo criado")
    public ComboProductCreated postNewComboProduct(@RequestBody @Valid ComboProductDTO comboProductDTO){
        return comboProductService.postNewComboProduct(comboProductDTO);
    }

    @GetMapping("/{id}")
    public ComboProduct getComboProduct(@Validated @PathVariable @Min(value = 1, message = "Valor inválido para o parametro") Long id){
        return comboProductService.getComboProduct(id);
    }

    @GetMapping("/all")
    public List<ComboProduct> getAllComboProduct(){
        return comboProductService.getAllComboProducts();
    }

    @GetMapping("/availableToClient")
    public List<ComboProduct> getAllAvailableToClient(){
        return null;
    }

    @PutMapping
    @SuccessMessage("Combo atualizado com sucesso")
    public ComboProduct updateComboProductInfos(@RequestBody ComboProductDTO comboProductDTO){
        return comboProductService.updateComboProduct(comboProductMapper.toEntity(comboProductDTO));
    }

    @PutMapping("/deactivate")
    @SuccessMessage("Combo desativado com sucesso")
    public ComboProduct deactivateComboProduct(@RequestBody ComboProductStatusChangerDTO productStatusChangerDTO){
        return comboProductService.deactivateComboProduct(productStatusChangerDTO);
    }

    @PutMapping("/reactivate")
    @SuccessMessage("Combo reativado com sucesso")
    public ComboProduct reactivateComboProduct(@RequestBody ComboProductStatusChangerDTO productStatusChangerDTO){
        return comboProductService.reactivateComboProduct(productStatusChangerDTO);
    }

    @PutMapping("/cancel")
    @SuccessMessage("Combo cancelado com sucesso")
    public ComboProduct cancelComboProduct(@RequestBody ComboProductStatusChangerDTO productStatusChangerDTO){
        return comboProductService.cancellingComboProduct(productStatusChangerDTO);
    }

    @PostMapping("/purchase")
    @SuccessMessage("Combo(s) comprado(s) com sucesso")
    @MultiStatusOnPartialFailure("Erro ao comprar os combos selecionados")
    public List<ComboProductCreatedAndAttached> purchaseComboProduct(@RequestBody @Valid PurchaseComboDTO purchaseComboDTO){
        return comboProductClientAttachService.purchaseProducts(purchaseComboDTO);
    }
}
