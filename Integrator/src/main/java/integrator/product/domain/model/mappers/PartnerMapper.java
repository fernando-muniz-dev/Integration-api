package integrator.product.domain.model.mappers;


import integrator.product.controller.dtos.PartnerDTO;
import integrator.product.domain.model.entities.Partner;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PartnerMapper {
    PartnerDTO toDto(Partner partner);

    Partner toEntity(PartnerDTO partnerDTO);

    @Mapping(target = "partnerDocument", ignore = true)
    @Mapping(target = "partnerStatus", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientFromDto(Partner source, @MappingTarget Partner target);
}
