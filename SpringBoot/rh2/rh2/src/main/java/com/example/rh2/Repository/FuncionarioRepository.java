package com.example.rh2.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.rh2.Model.Funcionario;
import java.util.List;


public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {
    //permitir a urilização dos métodos do crud do JPA

    //métodos auxiliares

    Funcionario findById(long id); // buscar um funcionario pelo id

    Funcionario findByNome(String nome); //buscar funcionario pelo nome

    //Buscar funcionário por parte do nome
    @Query(value = "select u from Funcionario u where u.nome like %?1%")
    List<Funcionario> findByLetras(String letras);


}
