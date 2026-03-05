package com.example.rh2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.rh2.Model.Funcionario;
import com.example.rh2.Repository.FuncionarioRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class FuncionarioController {

    @Autowired
    FuncionarioRepository fr;

    // métodos para abrir a página

    @GetMapping("/funcionario")
    public String abrirFuncionario() {
        return "funcionario/funcionario";
    }

    //método para salvar o funcionário no banco
    @PostMapping("/funcionario")
    public String gravarFuncionario(Funcionario funcionario) { // pega os dados do formulário (nome e email)
        fr.save(funcionario);// salava o funcionario no banco de dados
        return "redirect:/funcionario"; //recarrega a página após salvar o funcionario no banco
    }
    
    

    
}
