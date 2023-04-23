package com.example.tp1apiv1.comandos;

import com.example.tp1apiv1.dto.Arquivo;
import com.example.tp1apiv1.dto.Objeto;
import com.example.tp1apiv1.dto.Pokemon;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PokemonComando {
    //Gera binario com base no CSV fornecido
    public static void gerarBinario() throws Exception {
        List<Pokemon> pokemons = new ArrayList<>();

        try {
            CSVReader reader = new CSVReaderBuilder(new FileReader("src/main/data/All_Pokemon.csv")).withSkipLines(1).build();
            List<String[]> lista = reader.readAll();

            lista.forEach(x -> {
                int id = Integer.parseInt(x[0]);
                String name = x[1];
                String abilities = x[4];
                int hp = Integer.parseInt(x[5]);
                int generation = Integer.parseInt(x[14].split("\\.")[0]);

                if (!pokemons.stream().anyMatch(y -> y.Id == id)) {
                    Pokemon pokemon = new Pokemon(id, name, abilities, hp, generation);
                    pokemons.add(pokemon);
                }
            });
        } catch (Exception e) {
            throw new Exception(e);
        }

        List<Objeto> objetos = new ArrayList<>();

        pokemons.forEach(x -> {
            Objeto objeto = new Objeto(x.getBytes());
            objetos.add(objeto);
        });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        objetos.forEach(x -> {
            try {
                outputStream.write(x.getBytes());
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        Arquivo arquivo = new Arquivo(1, outputStream.toByteArray());

        try {
            FileOutputStream stream = new FileOutputStream("src/main/data/dataBase");
            stream.write(arquivo.getBytes());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //Escreve arquivo com base na lista de pokemons recebida
    private static void escreverArquivoPokemon(List<Pokemon> pokemons, int id) throws Exception {
        List<Objeto> objetos = new ArrayList<>();

        pokemons.forEach(x -> {
            Objeto objeto = new Objeto(x.getBytes());
            objetos.add(objeto);
        });

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        objetos.forEach(x -> {
            try {
                outputStream.write(x.getBytes());
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        Arquivo arquivo = new Arquivo(id, outputStream.toByteArray());

        try {
            FileOutputStream stream = new FileOutputStream("src/main/data/dataBase");
            stream.write(arquivo.getBytes());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //Escreve arquivo com base na lista de objetos recebida
    private static void escreverArquivoObjeto(List<Objeto> objetos, int id) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        objetos.forEach(x -> {
            try {
                outputStream.write(x.getBytes());
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        Arquivo arquivo = new Arquivo(id, outputStream.toByteArray());

        try {
            FileOutputStream stream = new FileOutputStream("src/main/data/dataBase");
            stream.write(arquivo.getBytes());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //Retorna lista dos objetos contidos no arquivo
    private static List<Objeto> getObjetos() throws Exception {
        Arquivo aux = null;

        try {
            byte[] arq = Files.readAllBytes(Path.of("src/main/data/dataBase"));

            aux = new Arquivo(arq);

        } catch (Exception e) {
            throw new Exception("Arquivo binário não foi gerado");
        }

        List<Objeto> objetosAux = aux.getObjetos();

        return objetosAux;
    }

    //Lista todos os pokemons
    public static List<Pokemon> getPokemons() throws Exception {
        List<Pokemon> pokemonsAux = new ArrayList<>();
        Arquivo aux = null;

        try {
            byte[] arq = Files.readAllBytes(Path.of("src/main/data/dataBase"));

            aux = new Arquivo(arq);

        } catch (Exception e) {
            throw new Exception("Arquivo binário não foi gerado");
        }

        List<Objeto> objetosAux = aux.getObjetos();

        objetosAux.forEach(x -> {
            if (!x.Lapide) {
                pokemonsAux.add(x.getPokemon());
            }
        });

        return pokemonsAux;
    }

    //Retorna pokemon com base no id fornecido
    public static Pokemon getPokemon(int id) throws Exception {
        List<Pokemon> pokemonsAux = getPokemons();

        if (pokemonsAux.stream().anyMatch(x -> x.Id == id)) {
            return pokemonsAux.stream().filter(x -> x.Id == id).findFirst().get();
        }

        return null;
    }

    //Adicona um pokemon e escreve no arquivo
    public static int addPokemon(String name, String[] abilities, int hp, Date generation) throws Exception {
        List<Pokemon> pokemonsAux = getPokemons();
        int lastId = Collections.max(pokemonsAux.stream().map(x -> x.Id).toList());

        Pokemon newPokemon = new Pokemon(lastId + 1, name, abilities, hp, generation);
        pokemonsAux.add(newPokemon);

        escreverArquivoPokemon(pokemonsAux, newPokemon.Id);

        return newPokemon.Id;
    }

    //Altera um pokemon e escreve no arquivo
    public static void alterPokemon(int id, String name, String[] abilities, int hp, Date generation) throws Exception {
        List<Pokemon> pokemonsAux = getPokemons();
        pokemonsAux
                .stream()
                .filter(x -> x.Id == id)
                .findFirst()
                .get()
                .setInfos(name, abilities, hp, generation);

        escreverArquivoPokemon(pokemonsAux, id);
    }

    //Altera o byte lapide respectivo ao id informado
    public static void deletePokemon(int id) throws Exception {
        List<Objeto> objetosAux = getObjetos();

        objetosAux.forEach(x -> {
            if (x.getPokemon().Id == id) {
                x.Lapide = true;
            }
        });

        escreverArquivoObjeto(objetosAux, id);
    }
}