package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    List<Autor> findByFechaNacimiento(String fechaNacimiento);

    @Query("SELECT a FROM Autor a WHERE CAST(a.fechaNacimiento AS integer) <= :anio AND " +
            "(a.fechaMuerte IS NULL OR CAST(a.fechaMuerte AS integer) >= :anio)")
    List<Autor> buscarAutoresVivosEnAnio(@Param("anio") Integer anio);
}
