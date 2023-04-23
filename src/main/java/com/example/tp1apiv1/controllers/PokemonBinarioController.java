package com.example.tp1apiv1.controllers;

import com.example.tp1apiv1.comandos.PokemonComando;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemonsbinario")
@CrossOrigin(origins = "http://localhost:3000")
public class PokemonBinarioController {
    @GetMapping
    public String gerarBinarioController() throws Exception {
        PokemonComando.gerarBinario();

        return "Binário gerado";
    }
}
