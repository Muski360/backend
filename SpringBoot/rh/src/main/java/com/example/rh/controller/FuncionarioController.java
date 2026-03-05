package com.example.rh.controller;

import com.example.rh.Model.Funcionario;
import com.example.rh.Repository.FuncionarioRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/funcionario")
    public String abrirFuncionario() {
        return "funcionario/funcionario";
    }

    @PostMapping("/funcionario")
    public String gravarFuncionario(Funcionario funcionario) {
        funcionarioRepository.save(funcionario);
        return "redirect:/listar";
    }

    @GetMapping("/listar")
    public ModelAndView listarFuncionarios() {
        ModelAndView mv = new ModelAndView("funcionario/listarfuncionarios");
        mv.addObject("funcionarios", funcionarioRepository.findAll());
        return mv;
    }

    @GetMapping("/deletarfuncionario/{id}")
    public String deletarFuncionario(@PathVariable("id") Long id) {
        funcionarioRepository.deleteById(id);
        return "redirect:/listar";
    }

    @GetMapping("/editarfuncionario/{id}")
    public ModelAndView abrirEditarFuncionario(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            return new ModelAndView("redirect:/listar");
        }
        ModelAndView mv = new ModelAndView("funcionario/editarfuncionario");
        mv.addObject("funcionario", funcionarioOpt.get());
        return mv;
    }

    @PostMapping("/editarfuncionario")
    public String editarFuncionario(
            @RequestParam("id") Long id,
            @RequestParam("nome") String nome,
            @RequestParam("email") String email) {
        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(id);
        if (funcionarioOpt.isEmpty()) {
            return "redirect:/listar";
        }
        Funcionario funcionario = funcionarioOpt.get();
        funcionario.setNome(nome);
        funcionario.setEmail(email);
        funcionarioRepository.save(funcionario);
        return "redirect:/listar";
    }
}
