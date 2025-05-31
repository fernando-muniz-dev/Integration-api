package integrator.product.domain.model.mappers;

import integrator.product.controller.dtos.ProductDTO;
import integrator.product.domain.model.entities.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

    @Mapping(target = "productStatus",ignore = true)
    @Mapping(target = "productPartner", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(Product source, @MappingTarget Product target);

}
