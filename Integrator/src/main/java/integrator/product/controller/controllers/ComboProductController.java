package integrator.product.controller.controllers;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.controller.dtos.ComboProductStatusChangerDTO;
import integrator.product.controller.dtos.PurchaseComboDTO;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.mappers.ComboProductMapper;
import integrator.product.domain.services.ComboProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comboProduct")
public class ComboProductController {

    private final ComboProductService comboProductService;
    private final ComboProductMapper comboProductMapper;

    public ComboProductController(ComboProductService comboProductService, ComboProductMapper comboProductMapper) {
        this.comboProductService = comboProductService;
        this.comboProductMapper = comboProductMapper;
    }

    @PostMapping
    @SuccessMessage("Combo cadastrado com sucesso")
    public ComboProduct postNewComboProduct(@RequestBody @Valid ComboProductDTO comboProductDTO){
        return comboProductService.postNewComboProduct(comboProductDTO);
    }

    @GetMapping("/{id}")
    public ComboProduct getComboProduct(@PathVariable Long id){
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
    public ComboProduct updateComboProductInfos(ComboProductDTO comboProductDTO){
        return comboProductService.updateComboProduct(comboProductMapper.toEntity(comboProductDTO));
    }

    @PutMapping("/deactivate")
    @SuccessMessage("Combo desativado com sucesso")
    public ComboProduct deactivateComboProduct(ComboProductStatusChangerDTO productStatusChangerDTO){
        return comboProductService.deactivateComboProduct(productStatusChangerDTO);
    }

    @PutMapping("/reactivate")
    @SuccessMessage("Combo reativado com sucesso")
    public ComboProduct reactivateComboProduct(ComboProductStatusChangerDTO productStatusChangerDTO){
        return comboProductService.reactivateComboProduct(productStatusChangerDTO);
    }

    @PutMapping("/cancel")
    @SuccessMessage("Combo cancelado com sucesso")
    public ComboProduct cancelComboProduct(ComboProductStatusChangerDTO productStatusChangerDTO){
        return comboProductService.cancellingComboProduct(productStatusChangerDTO);
    }
}
