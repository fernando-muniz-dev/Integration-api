package integrator.product.domain.repository;

import integrator.product.domain.model.entities.Client;
import integrator.product.domain.model.enums.ClientStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Client testClient;

    @BeforeEach
    public void setUp() {
        // Insira um cliente no banco de dados para o teste
        testClient = Client.builder()
                .clientName("João da Silva")
                .clientEmail("joao@teste.com")
                .clientDocument("12345678901") // Coloque o CPF ou CNPJ que você está usando
                .clientAddress("Rua X, 123")
                .clientCep("12345678")
                .clientStatus(ClientStatus.ACTIVE) // Substitua pelo status adequado
                .build();

        clientRepository.save(testClient);  // Salva no banco de dados de teste
    }

    @Test
    public void testGetClientByClientDocument() {
        // Agora, faz a busca utilizando o CPF ou CNPJ
//        Client foundClient = clientRepository.getClientByClientDocument("452.967.818-05");

        // Verifique se o resultado não é null
//        assertNotNull(foundClient, "O cliente não deve ser null");
//        assertEquals(testClient.getClientDocument(), foundClient.getClientDocument(), "Os documentos devem ser iguais");
    }
}