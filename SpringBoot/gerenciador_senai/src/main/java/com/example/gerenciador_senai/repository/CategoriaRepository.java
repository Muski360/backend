package com.example.gerenciador_senai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.gerenciador_senai.model.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {

	List<Categoria> findAllByOrderByNomeAsc();

	Optional<Categoria> findByNomeIgnoreCase(String nome);

	boolean existsByNomeIgnoreCase(String nome);

	boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
