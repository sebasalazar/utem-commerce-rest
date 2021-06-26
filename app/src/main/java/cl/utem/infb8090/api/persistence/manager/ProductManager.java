package cl.utem.infb8090.api.persistence.manager;

import cl.utem.infb8090.api.persistence.model.Product;
import cl.utem.infb8090.api.persistence.repository.ProductRepository;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient ProductRepository productRepository;

    /**
     *
     * @param sku SKU del producto
     * @return objeto producto
     */
    public Product getProduct(final String sku) {
        Product product = null;
        if (StringUtils.isNotBlank(sku)) {
            product = productRepository.findBySku(sku);
        }
        return product;
    }

    /**
     * 
     * @return Listado de productos
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
