package com.example.rh.controller;

import com.example.rh.Model.Funcionario;
import com.example.rh.Repository.FuncionarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView abrirIndex() {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("msg", "Ola, seja bem-vinda(o)!");
        return mv;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView buscarIndex(@RequestParam("buscar") String buscar) {
        ModelAndView mv = new ModelAndView("index");
        List<Funcionario> funcionarios = funcionarioRepository.findByNomes(buscar);
        mv.addObject("msg", "Resultado da busca");
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }
}
