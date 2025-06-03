package integrator.product.controller.controllers;

import integrator.product.controller.dtos.PurchaseComboDTO;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.services.ComboProductClientAttachService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/comboProduct")
public class ComboProductClientAttachController {

    private final ComboProductClientAttachService comboProductClientAttachService;

    public ComboProductClientAttachController(ComboProductClientAttachService comboProductClientAttachService) {
        this.comboProductClientAttachService = comboProductClientAttachService;
    }

    @PostMapping("/purchase")
    @SuccessMessage("Combo(s) comprado(s) com sucesso")
    public PurchaseComboDTO purchaseComboProduct(PurchaseComboDTO purchaseComboDTO){
        return comboProductClientAttachService.purchaseProducts(purchaseComboDTO);
    }
}
