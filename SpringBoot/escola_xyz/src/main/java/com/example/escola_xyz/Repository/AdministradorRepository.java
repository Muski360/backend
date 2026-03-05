package com.example.escola_xyz.Repository;

import org.springframework.data.repository.CrudRepository;
import com.example.escola_xyz.Model.Administrador;

public interface AdministradorRepository extends CrudRepository<Administrador,String>{
    
    Administrador findByCpf(String cpf);
}
