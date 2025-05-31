package integrator.product.domain.model.mappers;

import integrator.product.controller.dtos.ClientDTO;
import integrator.product.domain.model.entities.Client;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toDto(Client client);

    Client toEntity(ClientDTO clientDTO);

    @Mapping(target = "clientDocument", ignore = true)
    @Mapping(target = "clientStatus", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientFromDto(Client source, @MappingTarget Client target);

    @Mapping(target = "clientStatus", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateClientChangingTitularity(Client source, @MappingTarget Client target);
}