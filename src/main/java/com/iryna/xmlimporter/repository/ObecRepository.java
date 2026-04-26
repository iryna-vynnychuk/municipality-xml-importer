package com.iryna.xmlimporter.repository;

import com.iryna.xmlimporter.model.Obec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ObecRepository extends JpaRepository<Obec, UUID> {

    Optional<Obec> findByKodObce(Integer kodObce);

}
