package com.bolsadeideas.springboot.horariointerceptor.app.controllers;

import com.bolsadeideas.springboot.horariointerceptor.app.interceptors.HorarioInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Autowired
    private HorarioInterceptor horario;

    @GetMapping({"/", "/index"})
    public String index(Model model){
        model.addAttribute("titulo", "Bienvenido al horario de atención a clientes");
        return "index";
    }

    @GetMapping("/cerrado")
    public String cerrar(Model model){

        StringBuilder mensaje = new StringBuilder("Cerrado por favor visítenos desde las ");
        mensaje.append(horario.getApertura());
        mensaje.append(" hrs y las ");
        mensaje.append(horario.getCierre());
        mensaje.append(" hrs. Gracias. ");

        model.addAttribute("titulo", "Fuera del horario de atención");
        model.addAttribute("mensaje", mensaje);
        return "cerrado";
    }
}
