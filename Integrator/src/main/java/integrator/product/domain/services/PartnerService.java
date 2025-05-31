package integrator.product.domain.services;

import integrator.product.domain.model.entities.Partner;
import integrator.product.domain.model.enums.PartnerStatus;
import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.model.exceptions.InternalServerErrorException;
import integrator.product.domain.model.exceptions.NotFoundException;
import integrator.product.domain.model.mappers.PartnerMapper;
import integrator.product.domain.repository.PartnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static integrator.product.controller.validator.utils.ServiceExecutorExceptionHandler.execute;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;
    private final PartnerMapper partnerMapper;
    private static final Logger logger = LoggerFactory.getLogger(PartnerService.class);

    public PartnerService(PartnerRepository partnerRepository, PartnerMapper partnerMapper) {
        this.partnerRepository = partnerRepository;
        this.partnerMapper = partnerMapper;
    }

    public Partner postNewPartner(Partner partner){
        return execute(logger,"Erro ao cadastrar o parceiro", () -> {
            partnerRepository.getPartnerByPartnerDocument(partner.getPartnerDocument()).ifPresent(
                    c -> {throw new BadRequestException("Parceiro ja esta cadastrado"); }
            );
            return partnerRepository.save(partner);

        });
    }

    public Partner getPartner(String document){
        return execute(logger, "Erro ao recuperar o parceiro", () ->  partnerRepository.getPartnerByPartnerDocument(document)
                    .orElseThrow(() -> new NotFoundException("Parceiro não encontrado"))
        );
    }

    public Partner updatePartnerInfo(Partner partner){
        return execute(logger, "Erro ao atualizar o parceiro", () -> {
            Partner existingPartner = partnerRepository.getPartnerByPartnerDocument(partner.getPartnerDocument())
                    .orElseThrow(() -> new NotFoundException("Parceiro não encontrado"));

            if (existingPartner.getPartnerStatus().equals(PartnerStatus.CONTRACT_CANCELLED)){
                throw new BadRequestException("Parceiro com contrato cancelado. Não é possivel mudar as informações");
            }

            partnerMapper.updateClientFromDto(partner, existingPartner);
            return partnerRepository.save(existingPartner);
        });
    }

    public Partner finishPartnerContract(String document){
        return execute(logger, "Erro ao finalizar o contrato do cliente", () -> {
            Partner existingPartner = partnerRepository.getPartnerByPartnerDocument(document).orElseThrow(() -> new NotFoundException("Parceiro não encontrado"));
            existingPartner.setPartnerStatus(PartnerStatus.CONTRACT_CANCELLED);
            return partnerRepository.save(existingPartner);
        });
    }
}
