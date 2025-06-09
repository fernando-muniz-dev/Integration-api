package integrator.product.controller.controllers;

import integrator.product.controller.dtos.PartnerDTO;
import integrator.product.controller.validator.constraints.CpfCnpj;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.entities.Partner;
import integrator.product.domain.model.mappers.PartnerMapper;
import integrator.product.domain.services.PartnerService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/partner")
@Validated
public class PartnerController {
    private final PartnerService partnerService;
    private final PartnerMapper partnerMapper;

    public PartnerController(PartnerService partnerService, PartnerMapper partnerMapper) {
        this.partnerService = partnerService;
        this.partnerMapper = partnerMapper;
    }

    @PostMapping
    @SuccessMessage("Parceiro cadastrado com sucesso")
    public Partner postNewPartner(@RequestBody @Valid PartnerDTO partnerDTO){
        return partnerService.postNewPartner(partnerMapper.toEntity(partnerDTO));
    }

    @GetMapping("/{document}")
    public Partner getPartner(@PathVariable @CpfCnpj String document){
        return partnerService.getPartner(document);
    }

    @PutMapping
    @SuccessMessage("Parceiro atualizado com sucesso")
    public Partner updatePartnerInfo(@RequestBody @Valid PartnerDTO partnerDTO){
        return partnerService.updatePartnerInfo(partnerMapper.toEntity(partnerDTO));
    }

    @PutMapping("/finishingContract/{document}")
    public Partner finishingPartnerContract(@RequestBody @CpfCnpj String document){
        return partnerService.finishPartnerContract(document);
    }
}
