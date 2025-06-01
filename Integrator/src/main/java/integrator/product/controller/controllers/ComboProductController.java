package integrator.product.controller.controllers;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.services.ComboProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comboProduct")
public class ComboProductController {

    private final ComboProductService comboProductService;

    public ComboProductController(ComboProductService comboProductService) {
        this.comboProductService = comboProductService;
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
    public ComboProduct updateComboProductInfos(){
        return null;
    }

    @PutMapping("/deactivate")
    @SuccessMessage("Combo desativado com sucesso")
    public ComboProduct deactivateComboProduct(){
        return null;
    }

    @PutMapping("/reactivate")
    @SuccessMessage("Combo reativado com sucesso")
    public ComboProduct reactivateComboProduct(){
        return null;
    }

    @PutMapping("/cancel")
    @SuccessMessage("Combo cancelado com sucesso")
    public ComboProduct cancelComboProduct(){
        return null;
    }
}
