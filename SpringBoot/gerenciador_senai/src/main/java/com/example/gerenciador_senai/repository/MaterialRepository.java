package com.example.gerenciador_senai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.gerenciador_senai.model.Material;

public interface MaterialRepository extends CrudRepository<Material, Long> {

	List<Material> findAllByOrderByNomeAsc();

	Optional<Material> findByNomeIgnoreCase(String nome);

	boolean existsByCategoriaId(Long categoriaId);
}
