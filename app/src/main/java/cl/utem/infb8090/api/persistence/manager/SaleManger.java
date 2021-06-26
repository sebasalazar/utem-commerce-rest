package cl.utem.infb8090.api.persistence.manager;

import cl.utem.infb8090.api.persistence.model.Product;
import cl.utem.infb8090.api.persistence.model.Sale;
import cl.utem.infb8090.api.persistence.repository.SaleRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleManger implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient SaleRepository saleRepository;

    public List<Sale> getSales(final Product product) {
        List<Sale> sales = new ArrayList<>();
        if (product != null) {
            sales = saleRepository.findByProduct(product);
        }
        return sales;
    }
}
