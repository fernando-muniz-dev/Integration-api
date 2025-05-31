package integrator.product.controller.controllers;

import integrator.product.controller.dtos.ProductDTO;
import integrator.product.controller.dtos.ProductStatusChangerDTO;
import integrator.product.controller.validator.constraints.SuccessMessage;
import integrator.product.domain.model.entities.Product;
import integrator.product.domain.model.mappers.ProductMapper;
import integrator.product.domain.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @PostMapping
    @SuccessMessage("Produto cadastrado com sucesso")
    public Product postNewProduct(@RequestBody @Valid ProductDTO productDTO) {
        return productService.postNewProduct(productMapper.toEntity(productDTO));
    }

    @GetMapping("/{productSku}")
    public Product getProduct(@PathVariable String productSku) {
        return productService.getProduct(productSku);
    }

    @PutMapping
    @SuccessMessage("Produto atualizado com sucesso!")
    public Product updateProduct(@RequestBody @Valid ProductDTO productDTO) {
        return productService.updateProduct(productMapper.toEntity(productDTO));
    }

    @PutMapping("/reactivate/{productSku}")
    public Product reactivateProduct(@PathVariable String productSku){
        return productService.reactivateProduct(productSku);
    }

    @PutMapping("/cancel")
    @SuccessMessage("Produto cancelado com sucesso")
    public Product cancellingProduct(@RequestBody @Valid ProductStatusChangerDTO productStatusChangerDTO){
        return productService.cancellingProduct(productStatusChangerDTO);
    }

}
