package com.mvdact3_t4.actividadcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvdact3_t4.actividadcrud.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
