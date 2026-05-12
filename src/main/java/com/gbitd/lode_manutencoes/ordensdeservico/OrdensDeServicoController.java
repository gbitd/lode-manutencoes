package com.gbitd.lode_manutencoes.ordensdeservico;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class OrdensDeServicoController {
    @GetMapping
    public String hello(){
        return "Tudo funcionando!";
    }
}
