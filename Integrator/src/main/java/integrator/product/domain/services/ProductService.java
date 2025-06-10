package integrator.product.domain.services;

import integrator.product.controller.dtos.ProductDTO;
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

import java.util.List;
import java.util.Optional;

import static integrator.product.domain.model.exceptions.ServiceExecutorExceptionHandler.execute;

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

    public Product postNewProduct(ProductDTO product){

        return execute(logger, "Erro ao cadastrar o produto", () ->{

            if(!product.getProductStatus().equals(ProductStatus.AVAILABLE)){
                throw new BadRequestException("Status inválido para esta operação");
            }

            productRepository.getProductBySKU(product.getProductSku())
                    .ifPresent( c -> {throw new BadRequestException("Produto ja cadastrado"); }
                    );

            Product newProduct = productMapper.toEntity(product);

            newProduct.setProductPartner(partnerRepository.getPartnerByPartnerDocument(product.getProductPartnerDocument())
                    .orElseThrow(() -> new NotFoundException("Parceiro do produto não encontrado")));

            return productRepository.save(newProduct);
        });
    }

    public Product getProduct(String productSku){

        return execute(logger, "Erro ao recuperar o produto", () -> productRepository.getProductBySKU(productSku)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado")));
    }

    public Product getProductWithoutException(String productSku){

        return execute(logger, "Erro ao recuperar o produto", () -> productRepository.getProductBySKU(productSku).orElseGet(() ->null));
    }

    public List<Product> getAllAvailableProducts(){
        return execute(logger, "Erro ao recuperar o produto", () -> {

            List<Product> allActiveProducts =productRepository.getAllActiveProducts();

            if(allActiveProducts.isEmpty()){
                throw new BadRequestException("Não foi possivel recuperar nenhum produto disponivel");
            }

            return allActiveProducts;

        });
    }

    public Product updateProduct(Product product){

        return execute(logger, "Erro ao atualizar o produto", () ->{
            Product existingProduct = productRepository.getProductBySKU(product.getProductSku())
                    .orElseThrow(()-> new NotFoundException("Produto não encontrado"));

            productMapper.updateProductFromDto(product, existingProduct);
            productRepository.save(existingProduct);
            return existingProduct;

        });
    }

    public Product reactivateProduct(ProductStatusChangerDTO productStatusChangerDTO){
        return execute(logger, "Erro ao reativar o produto", () -> {

            Product existingProduct = productRepository.getProductBySKU(productStatusChangerDTO.getProductSku())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

            if(existingProduct.getProductStatus() == null)
                throw new InternalServerErrorException("Status do produto é null, verificar inconsistencia");

            if(existingProduct.getProductStatus().equals(ProductStatus.AVAILABLE))
                throw new BadRequestException("Produto ja se encontra ativo");

            if(existingProduct.getProductStatus().canBeCancelled())
                throw new BadRequestException("Status atual não permite reativação");

            if(productStatusChangerDTO.getProductStatus().canBeCancelled())
                throw new BadRequestException("Status não é permitido nesta operação");

            existingProduct.setProductStatus(ProductStatus.AVAILABLE);
            productRepository.save(existingProduct);

            return existingProduct;

        });
    }

    public Product deactivateProduct(ProductStatusChangerDTO productStatusChangerDTO){
        return execute(logger, "Erro ao desativar o produto", () ->{
            Product existingProduct = productRepository.getProductBySKU(productStatusChangerDTO.getProductSku())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

            if (existingProduct.getProductStatus() == null)
                 throw new InternalServerErrorException("Status do produto é null, verifique a inconsistencia");

            if (existingProduct.getProductStatus().canBeCancelled())
                throw new BadRequestException("Produto se encontra cancelado e não pode ser reativado");

            if (existingProduct.getProductStatus().isProductSuspended())
                throw new BadRequestException("Produto ja se encontra desativado");

            if(!productStatusChangerDTO.getProductStatus().isProductSuspended())
                throw new BadRequestException("Status atual não permite a desativação");

            existingProduct.setProductStatus(productStatusChangerDTO.getProductStatus());
            productRepository.save(existingProduct);

            return existingProduct;
        });
    }

    public Product cancellingProduct(ProductStatusChangerDTO productStatusChangerDTO){
        return execute(logger, "Erro ao cancelar o produto", () ->{

            Product existingProduct = productRepository.getProductBySKU(productStatusChangerDTO.getProductSku())
                    .orElseThrow(() -> new NotFoundException("Produto não encontrado"));

            if(existingProduct.getProductStatus() == null)
                throw new InternalServerErrorException("Status do produto é null, verificar inconsistencia");

            if(existingProduct.getProductStatus().canBeCancelled())
                throw new BadRequestException("Produto ja se encontra cancelado, Status do produto é esse: "
                        + existingProduct.getProductStatus().getStatusDescription());

            if(productStatusChangerDTO.getProductStatus().canBeCancelled())
                throw new BadRequestException("Status inválido para cancelamento do produto");

            existingProduct.setProductStatus(productStatusChangerDTO.getProductStatus());
            return productRepository.save(existingProduct);

        });
    }
}
