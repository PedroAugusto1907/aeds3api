package com.example.tp1apiv1.dto;

import com.google.common.primitives.Bytes;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Objeto {
    public boolean Lapide;
    public int TamanhoRegistro;
    public byte[] Pokemon;

    //Construtores
    public Objeto(byte[] pokemon) {
        Lapide = false;
        TamanhoRegistro = pokemon.length;
        Pokemon = pokemon;
    }

    public Objeto(boolean lapide, int tamanhoRegistro, byte[] pokemon) {
        Lapide = lapide;
        TamanhoRegistro = tamanhoRegistro;
        Pokemon = pokemon;
    }

    //Conversao para byte array
    public byte[] getBytes() {
        byte[] objeto = ByteBuffer
                .allocate(1 + Integer.BYTES + Pokemon.length)
                .put((byte) (Lapide ? 1 : 0))
                .put(ByteBuffer.allocate(Integer.BYTES).putInt(TamanhoRegistro).array())
                .put(Pokemon)
                .array();

        return objeto;
    }

    //Retorna pokemon contido no objeto
    public Pokemon getPokemon() {
        List<Byte> pokemonAux = new LinkedList<>(Bytes.asList(Pokemon));

        int id = ByteBuffer.wrap(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 0, 4)).getInt();
        int tamanhoNome = ByteBuffer.wrap(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 4, 8)).getInt();
        String name = new String(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 8, 8+tamanhoNome));
        int tamanhoAbilidades = ByteBuffer.wrap(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 8+tamanhoNome, 8+tamanhoNome+4)).getInt();
        String abilities = new String(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 8+tamanhoNome+4, 8+tamanhoNome+4+tamanhoAbilidades));
        int hp = ByteBuffer.wrap(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 8+tamanhoNome+4+tamanhoAbilidades, 8+tamanhoNome+4+tamanhoAbilidades+4)).getInt();
        long generation = ByteBuffer.wrap(Arrays.copyOfRange(Bytes.toArray(pokemonAux), 8+tamanhoNome+4+tamanhoAbilidades+4, 8+tamanhoNome+4+tamanhoAbilidades+4+8)).getLong();

        return new Pokemon(id, name, abilities, hp, generation);
    }
}