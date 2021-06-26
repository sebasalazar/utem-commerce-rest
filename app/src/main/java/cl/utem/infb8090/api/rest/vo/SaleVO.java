package cl.utem.infb8090.api.rest.vo;

import cl.utem.infb8090.api.persistence.model.Sale;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;

@ApiModel(value = "sale")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SaleVO {

    private String sku = null;
    private String name = null;
    private Integer quantity = 0;
    private Long amount = 0L;
    private Long total = 0L;
    private LocalDateTime created = null;

    public SaleVO() {
    }

    public SaleVO(Sale sale) {
        this.sku = StringUtils.trim(sale.getProduct().getSku());
        this.name = StringUtils.upperCase(StringUtils.normalizeSpace(StringUtils.trimToEmpty(sale.getProduct().getName())));
        this.quantity = sale.getQuantity();
        this.amount = sale.getAmount();
        this.total = sale.getQuantity() * sale.getAmount();
        this.created = sale.getCreated();
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

    @ApiModelProperty(value = "Cantidad Adquirida",
            required = true, example = "1")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @ApiModelProperty(value = "Valor unitario del producto",
            required = true, example = "120000")
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @ApiModelProperty(value = "Monto final de la compra (cantidad * valor unitario)",
            required = true, example = "120000")
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @ApiModelProperty(value = "Fecha de compra",
            required = true)
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
