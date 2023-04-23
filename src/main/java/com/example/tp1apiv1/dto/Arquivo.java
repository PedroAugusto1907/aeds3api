package com.example.tp1apiv1.dto;

import com.google.common.primitives.Bytes;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Arquivo {
    public int UltimoId;
    public byte[] Objetos;

    //Construtores
    public Arquivo(int ultimoId, byte[] objetos) {
        UltimoId = ultimoId;
        Objetos = objetos;
    }

    public Arquivo(byte[] bytes) {
        UltimoId = ByteBuffer.wrap(Arrays.copyOfRange(bytes, 0, 4)).getInt();
        Objetos = Arrays.copyOfRange(bytes, 4, bytes.length);
    }

    //Conversao para byte array
    public byte[] getBytes() {
        byte[] arquivo = ByteBuffer
                .allocate(Integer.BYTES + Objetos.length)
                .put(ByteBuffer.allocate(Integer.BYTES).putInt(UltimoId).array())
                .put(Objetos)
                .array();

        return arquivo;
    }

    //Retorna lista de objetos contidos no arquivo
    public List<Objeto> getObjetos() {
        List<Objeto> objetosAux = new ArrayList<>();
        List<Byte> bytesAux = new LinkedList<>(Bytes.asList(Objetos));

        while (!bytesAux.isEmpty()) {
            boolean lapide = bytesAux.get(0) != 0;
            bytesAux.remove(0);

            int tamanhoRegistro = ByteBuffer.wrap(Arrays.copyOfRange(Bytes.toArray(bytesAux), 0, 4)).getInt();
            for(int i = 0; i < Integer.BYTES; i++) {
                bytesAux.remove(0);
            }

            byte[] pokemon = Arrays.copyOfRange(Bytes.toArray(bytesAux), 0, tamanhoRegistro);
            for(int i = 0; i < tamanhoRegistro; i++) {
                bytesAux.remove(0);
            }

            objetosAux.add(new Objeto(lapide, tamanhoRegistro, pokemon));
        }

        return objetosAux;
    }
}
