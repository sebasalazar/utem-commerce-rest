package cl.utem.infb8090.api.rest.v1;

import cl.utem.infb8090.api.exception.CpydException;
import cl.utem.infb8090.api.persistence.manager.ProductManager;
import cl.utem.infb8090.api.persistence.model.Product;
import cl.utem.infb8090.api.rest.vo.ErrorVO;
import cl.utem.infb8090.api.rest.vo.ProductVO;
import cl.utem.infb8090.api.utils.IPUtils;
import cl.utem.infb8090.api.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/products", consumes = {"application/json;charset=utf-8"}, produces = {"application/json;charset=utf-8"})
public class ProductRest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient ProductManager productManager;

    @Autowired
    private transient HttpServletRequest httpRequest;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRest.class);

    @ApiOperation(value = "Permite obtener el listado de productos")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Respuesta fue exitosa", response = ProductVO.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Acceso no autorizado", response = ErrorVO.class),
        @ApiResponse(code = 403, message = "No tiene permisos", response = ErrorVO.class),
        @ApiResponse(code = 404, message = "No se ha encontrado la informaci??n solicitada", response = ErrorVO.class),
        @ApiResponse(code = 412, message = "Fall?? alguna precondici??n", response = ErrorVO.class)
    })
    @GetMapping(value = "/all", consumes = {"*/*"}, produces = {"application/json;charset=utf-8"})
    public ResponseEntity getAll(@ApiParam(name = "Authentication", value = "Cabecera de autenticaci??n", required = true, example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
            @RequestHeader(name = "Authentication", required = true) String bearer) {
        if (StringUtils.isBlank(bearer)) {
            LOGGER.error("Sin token de autenticaci??n");
            throw new CpydException(401, "Credenciales inv??lidas");
        }

        final String ip = IPUtils.getClientIpAddress(httpRequest);
        if (!InetAddressValidator.getInstance().isValid(ip)) {
            LOGGER.error("IP NO v??lida, posible ataque");
            throw new CpydException(403, "No tiene permiso para acceder a este recurso");
        }

        final String jwt = StringUtils.trimToEmpty(StringUtils.removeStartIgnoreCase(bearer, "bearer"));
        boolean valid = JwtUtils.isValid(ip, jwt);
        if (!valid) {
            LOGGER.error("El token de autenticaci??n no es v??lido");
            throw new CpydException(401, "Credenciales inv??lidas");
        }

        List<Product> products = productManager.getProducts();
        if (CollectionUtils.isEmpty(products)) {
            LOGGER.error("Lista de productos vac??a");
            throw new CpydException(404, "No se han encontrado productos");
        }

        List<ProductVO> resultList = new ArrayList<>();
        for (Product product : products) {
            resultList.add(new ProductVO(product));
        }
        products.clear();

        return ResponseEntity.ok(resultList);
    }

}
