package cl.utem.infb8090.api.persistence.repository;

import cl.utem.infb8090.api.persistence.model.Product;
import cl.utem.infb8090.api.persistence.model.Sale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    public List<Sale> findByProduct(Product product);

}
