package com.example.rh2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.rh2.Repository.FuncionarioRepository;
import org.springframework.web.bind.annotation.GetMapping;


//anotação de classe controller do SpringBoot
@Controller
public class IndexController {
    
    //atributo
    @Autowired
    FuncionarioRepository fr; // Executar o CRUD

    @GetMapping("/")
    public String abrirIndex() {
        //será criado futuramente uma mensagem de saudação
        return "index";
    }

    
    

}
