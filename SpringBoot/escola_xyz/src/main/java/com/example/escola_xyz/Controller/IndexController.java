package com.example.escola_xyz.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


// anotação de Classe Controller ( interagir view e model)
@Controller
public class IndexController {
    

    @GetMapping("/home")
    public ModelAndView acessoHomePage() {
        ModelAndView mv  = new ModelAndView("index");
        return mv;
    }

    @GetMapping("")
    public String acessoHomePage2() {
        return "index";
    }
    
    
}
