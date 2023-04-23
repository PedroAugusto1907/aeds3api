package com.example.tp1apiv1.dto;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

public class Pokemon {
    public int Id;
    public String Name;
    public String[] Abilities;
    public int HP;
    public Date ReleaseDate;

    public Pokemon(int id, String name, String abilities, int hp, int releaseDate) {
        setId(id);
        setName(name);
        setAbilities(abilities);
        setHP(hp);
        setReleaseDate(releaseDate);
    }

    public Pokemon(int id, String name, String[] abilities, int hp, Date releaseDate) {
        setId(id);
        setName(name);
        Abilities = abilities;
        setHP(hp);
        ReleaseDate = releaseDate;
    }

    public Pokemon(int id, String name, String abilities, int hp, long releaseDate) {
        setId(id);
        setName(name);
        setAbilities(abilities);
        setHP(hp);
        setGeneration(releaseDate);
    }

    public void setInfos(String name, String[] abilities, int hp, Date generation) {
        setName(name);
        Abilities = abilities;
        setHP(hp);
        ReleaseDate = generation;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAbilities(String abilities) {
        Abilities = abilities
                .replace("[", "")
                .replace("]", "")
                .replace("'", "")
                .replace(" ", "")
                .split(",");
    }

    public void setHP(int hp) {
        HP = hp;
    }

    public void setReleaseDate(int releaseDate) {
        switch (releaseDate) {
            case 1 -> ReleaseDate = new GregorianCalendar(1998, 1, 1).getTime();
            case 2 -> ReleaseDate = new GregorianCalendar(2001, 1, 1).getTime();
            case 3 -> ReleaseDate = new GregorianCalendar(2005, 1, 1).getTime();
            case 4 -> ReleaseDate = new GregorianCalendar(2009, 1, 1).getTime();
            case 5 -> ReleaseDate = new GregorianCalendar(2012, 1, 1).getTime();
            case 6 -> ReleaseDate = new GregorianCalendar(2015, 1, 1).getTime();
            case 7 -> ReleaseDate = new GregorianCalendar(2018, 1, 1).getTime();
            case 8 -> ReleaseDate = new GregorianCalendar(2021, 1, 1).getTime();
            case 9 -> ReleaseDate = new GregorianCalendar(2022, 1, 1).getTime();
            default -> ReleaseDate = new GregorianCalendar(2023, 1, 1).getTime();
        }
    }

    public void setGeneration(long generation) {
        ReleaseDate = new Date(generation);
    }

    //Conversao para byte array
    public byte[] getBytes() {
        byte[] id = ByteBuffer.allocate(Integer.BYTES).putInt(Id).array();
        byte[] name = Name.getBytes();
        byte[] tamanhoNome = ByteBuffer.allocate(Integer.BYTES).putInt(name.length).array();
        byte[] abilities = Arrays.toString(Abilities).getBytes();
        byte[] tamanhoAbilidades = ByteBuffer.allocate(Integer.BYTES).putInt(abilities.length).array();
        byte[] hp = ByteBuffer.allocate(4).putInt(HP).array();
        byte[] generation = ByteBuffer.allocate(Long.BYTES).putLong(ReleaseDate.getTime()).array();

        byte[] pokemon = ByteBuffer
                .allocate(id.length + name.length + tamanhoNome.length + abilities.length + tamanhoAbilidades.length + hp.length + generation.length)
                .put(id)
                .put(tamanhoNome)
                .put(name)
                .put(tamanhoAbilidades)
                .put(abilities)
                .put(hp)
                .put(generation)
                .array();

        return pokemon;
    }
}