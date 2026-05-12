package com.gbitd.lode_manutencoes.equipamentos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class EquipamentosController {
    @GetMapping
    public String hello(){
        return "Tudo funcionando!";
    }
}
