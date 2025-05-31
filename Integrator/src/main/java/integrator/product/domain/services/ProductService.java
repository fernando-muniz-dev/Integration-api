package integrator.product.domain.services;

import integrator.product.controller.dtos.ProductStatusChangerDTO;
import integrator.product.domain.model.entities.Product;
import integrator.product.domain.model.enums.ProductStatus;
import integrator.product.domain.model.exceptions.BadRequestException;
import integrator.product.domain.model.exceptions.InternalServerErrorException;
import integrator.product.domain.model.exceptions.NotFoundException;
import integrator.product.domain.model.mappers.ProductMapper;
import integrator.product.domain.repository.PartnerRepository;
import integrator.product.domain.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static integrator.product.controller.validator.utils.ServiceExecutorExceptionHandler.*;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PartnerRepository partnerRepository;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository, PartnerRepository partnerRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.partnerRepository = partnerRepository;
        this.productMapper = productMapper;
    }

    public Product postNewProduct(Product product){

        return execute(logger, "Erro ao cadastrar o produto", () ->{

            productRepository.getProductBySKU(product.getProductSku())
                    .ifPresent( c -> {throw new BadRequestException("Produto ja cadastrado"); }
                    );

            partnerRepository.getPartnerByPartnerDocument(product.getProductPartner().getPartnerDocument())
                    .orElseThrow(() -> new NotFoundException("Parceiro do produto não encontrado"));

            return productRepository.save(product);
        });
    }

    public Product getProduct(String productSku){

        return execute(logger, "Erro ao recuperar o produto", () -> productRepository.getProductBySKU(productSku)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado")));
    }

    public Product updateProduct(Product product){

        return execute(logger, "Erro ao atualizar o produto", () ->{
            Product existingProduct = productRepository.getProductBySKU(product.getProductSku())
                    .orElseThrow(()-> new NotFoundException("Produto não encontrado"));

            productMapper.updateProductFromDto(product, existingProduct);
            return existingProduct;

        });
    }

    public Product reactivateProduct(String productSku){
        return execute(logger, "Erro ao reativar o produto", () -> {

            Product existingProduct = productRepository.getProductBySKU(productSku)
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

            if(existingProduct.getProductStatus() == null){
                throw new InternalServerErrorException("Status do produto é null, verificar inconsistencia");
            }

            if(existingProduct.getProductStatus().equals(ProductStatus.AVAILABLE)){
                throw new BadRequestException("Produto ja se encontra ativo");
            }

            if(existingProduct.getProductStatus().canBeCancelled()){
                throw new BadRequestException("Status atual não permite reativação");
            }

            existingProduct.setProductStatus(ProductStatus.AVAILABLE);
            productRepository.save(existingProduct);

            return existingProduct;

        });
    }

    public Product cancellingProduct(ProductStatusChangerDTO productStatusChangerDTO){
        return execute(logger, "Erro ao cancelar o produto", () ->{

            Product existingProduct = productRepository.getProductById(productStatusChangerDTO.getId())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

            if(existingProduct.getProductStatus() == null){
                throw new InternalServerErrorException("Status do produto é null, verificar inconsistencia");
            }

            if(existingProduct.getProductStatus().canBeCancelled()){
                throw new BadRequestException("Produto ja se encontra cancelado, Status do produto é esse: " + existingProduct.getProductStatus().getStatusDescription());
            }

            if(productStatusChangerDTO.getProductStatus().canBeCancelled()){
                throw new BadRequestException("Status inválido para cancelamento do produto");
            }

            existingProduct.setProductStatus(productStatusChangerDTO.getProductStatus());
            return productRepository.save(existingProduct);

        });
    }
}
