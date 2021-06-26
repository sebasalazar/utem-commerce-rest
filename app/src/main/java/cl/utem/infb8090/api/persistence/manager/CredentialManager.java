package cl.utem.infb8090.api.persistence.manager;

import cl.utem.infb8090.api.persistence.model.Credential;
import cl.utem.infb8090.api.persistence.repository.CredentialRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CredentialManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private transient CredentialRepository credentialRepository;

    /**
     *
     * @param id Identificador de la fila en base de datos.
     * @return un objeto credencial o null si no lo encuentra
     */
    public Credential getCredential(final Long id) {
        Credential credential = null;
        if (id != null && id > 0L) {
            Optional<Credential> finded = credentialRepository.findById(id);
            if (finded.isPresent()) {
                credential = finded.get();
            }
        }
        return credential;
    }

    /**
     *
     * @param token Token que identifica la credencial.
     * @return un objeto credencial o null si no lo encuentra
     */
    public Credential getCredential(final String token) {
        Credential credential = null;
        if (StringUtils.isNotBlank(token)) {
            credential = credentialRepository.findByToken(token);
        }
        return credential;
    }

    /**
     *
     * @return Listado completo de credenciales existentes en base de datos.
     */
    public List<Credential> getCredentials() {
        return credentialRepository.findAll(Sort.by(Sort.Direction.ASC, "created"));
    }

    /**
     *
     * @param credential Objeto credencial
     * @return un objeto persistido, null en caso de ingresar null y una
     * excepción en caso de error.
     */
    @Transactional
    public Credential save(Credential credential) {
        Credential saved = null;
        if (credential != null) {
            saved = credentialRepository.save(credential);
        }
        return saved;
    }

    /**
     *
     * @param credential Objeto credencial
     * @return true si se elimino, false si el parámetro es nulo o una excepción
     * en caso de error.
     */
    @Transactional
    public boolean delete(Credential credential) {
        boolean ok = false;
        if (credential != null) {
            credentialRepository.delete(credential);
            ok = false;
        }
        return ok;
    }

    /**
     *
     * @param app Identificador de la credencial
     * @param password Contraseña usada para autenticar
     * @return true si es correcto o false en cualquier otro caso.
     */
    public boolean authenticate(final String app, final String password) {
        boolean ok = false;

        if (StringUtils.isNotBlank(app) && StringUtils.isNotBlank(password)) {
            Credential credential = credentialRepository.findByAppIgnoreCase(app);
            if (credential != null && credential.isActive()) {
                ok = StringUtils.equals(credential.getPassword(), password);
            }
        }

        return ok;
    }
}
