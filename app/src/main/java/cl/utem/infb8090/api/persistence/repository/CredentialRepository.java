package cl.utem.infb8090.api.persistence.repository;

import cl.utem.infb8090.api.persistence.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {

    public Credential findByToken(String token);

    public Credential findByAppIgnoreCase(String app);
}
