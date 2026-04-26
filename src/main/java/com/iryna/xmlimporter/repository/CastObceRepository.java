package com.iryna.xmlimporter.repository;

import com.iryna.xmlimporter.model.CastObce;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CastObceRepository extends JpaRepository<CastObce, UUID> {

    //List<CastObce> findByObec_KodObce(Integer kodObce);
    Optional<CastObce> findByKodCastiObce(Integer kodCastiObce);
}
