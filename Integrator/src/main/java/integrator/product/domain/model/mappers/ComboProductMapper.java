package integrator.product.domain.model.mappers;

import integrator.product.controller.dtos.ComboProductDTO;
import integrator.product.domain.model.entities.ComboProduct;
import integrator.product.domain.model.entities.Partner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComboProductMapper {

    @Mapping(target = "productsSkuToAttaches", ignore = true)
    ComboProductDTO toDto(ComboProduct comboProduct);

    ComboProduct toEntity(ComboProductDTO comboProductDTO);

    void updateComboProductFromDto(ComboProduct source, @MappingTarget ComboProduct target);
}
