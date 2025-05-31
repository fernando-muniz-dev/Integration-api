package integrator.product.domain.model.mappers;

import integrator.product.controller.dtos.ProductDTO;
import integrator.product.domain.model.entities.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface ProductMapper {
    ProductDTO toDto(Product product);

    Product toEntity(ProductDTO productDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(Product source, @MappingTarget Product target);
}
