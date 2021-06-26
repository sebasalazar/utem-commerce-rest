package cl.utem.infb8090.api.rest.vo;

import cl.utem.infb8090.api.persistence.model.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

@ApiModel(value = "product")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductVO {

    private String sku = null;
    private String name = null;

    public ProductVO() {
    }

    public ProductVO(Product product) {
        this.sku = StringUtils.trim(product.getSku());
        this.name = StringUtils.upperCase(StringUtils.normalizeSpace(StringUtils.trimToEmpty(product.getName())));
    }

    @ApiModelProperty(value = "Identificador único del producto",
            required = true, example = "123456789")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @ApiModelProperty(value = "Nombre del producto",
            required = true, example = "Celular Xiaomi Redmi 9")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
