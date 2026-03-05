package com.example.rh.Repository;

import com.example.rh.Model.Funcionario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    Funcionario findByNome(String nome);

    @Query("select u from Funcionario u where lower(u.nome) like lower(concat('%', ?1, '%'))")
    List<Funcionario> findByNomes(String nome);
}
