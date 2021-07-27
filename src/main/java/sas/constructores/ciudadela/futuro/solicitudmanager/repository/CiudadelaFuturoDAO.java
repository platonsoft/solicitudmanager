package sas.constructores.ciudadela.futuro.solicitudmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sas.constructores.ciudadela.futuro.solicitudmanager.entities.CiudadelaFuturoEntity;

@Repository
public interface CiudadelaFuturoDAO extends JpaRepository<CiudadelaFuturoEntity, Long> {
}
