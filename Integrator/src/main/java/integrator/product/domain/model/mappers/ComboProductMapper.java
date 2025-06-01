package integrator.product.domain.model.mappers;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.domain.model.entities.ComboProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComboProductMapper {

    @Mapping(target = "comboProductAttaches", ignore = true)
    ComboProductDTO toDto(ComboProduct comboProduct);

    ComboProduct toEntity(ComboProductDTO comboProductDTO);
}
