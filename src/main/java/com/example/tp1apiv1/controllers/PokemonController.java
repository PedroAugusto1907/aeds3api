package com.example.tp1apiv1.controllers;

import com.example.tp1apiv1.comandos.PokemonComando;
import com.example.tp1apiv1.dto.Pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = "http://localhost:3000")
public class PokemonController {
    //Rota especifica para requisiçao GET que retorna um pokemon com base no id informado
    @GetMapping
    public Pokemon getPokemonController(@RequestParam int id) throws Exception {
        return PokemonComando.getPokemon(id);
    }

    //Rota especifica para requisiçao POST que adiciona um pokemon
    @PostMapping
    public String addPokemonController(@RequestBody DataAddPokemon data) throws Exception {
        int id = PokemonComando.addPokemon(data.data.name, data.data.abilities, data.data.hp, data.data.releaseDate);

        return "Pokemon adicionado com ID: "+id;
    }

    //Rota especifica para requisiçao PUT que altera um pokemon
    @PutMapping
    public String alterPokemonController(@RequestBody DataAlterPokemon data) throws Exception {
        System.out.println(data.data);

        PokemonComando.alterPokemon(data.data.id, data.data.name, data.data.abilities, data.data.hp, data.data.releaseDate);

        return "Pokemon alterado";
    }

    //Rota especifica para requisiçao DELTETE que remove um pokemon
    @DeleteMapping
    public String deletePokemonController(@RequestParam int id) throws Exception {
        PokemonComando.deletePokemon(id);

        return "Pokemon excluído";
    }
}

class DataAddPokemon {
    @JsonProperty("data")
    AddPokemon data;
}

class DataAlterPokemon {
    @JsonProperty("data")
    AlterPokemon data;
}


class AddPokemon {
    @JsonProperty("name")
    String name;
    @JsonProperty("abilities")
    String[] abilities;
    @JsonProperty("hp")
    int hp;
    @JsonProperty("releaseDate")
    Date releaseDate;
}

class AlterPokemon {
    @JsonProperty("id")
    int id;
    @JsonProperty("name")
    String name;
    @JsonProperty("abilities")
    String[] abilities;
    @JsonProperty("hp")
    int hp;
    @JsonProperty("releaseDate")
    Date releaseDate;
}