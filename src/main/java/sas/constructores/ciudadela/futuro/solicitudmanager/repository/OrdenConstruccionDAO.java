package sas.constructores.ciudadela.futuro.solicitudmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sas.constructores.ciudadela.futuro.solicitudmanager.entities.OrdenConstruccionEntity;

@Repository
public interface OrdenConstruccionDAO extends JpaRepository<OrdenConstruccionEntity, Long> {
}
