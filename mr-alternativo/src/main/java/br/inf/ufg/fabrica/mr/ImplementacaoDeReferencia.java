/*
 * Copyright (c) 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Implementação de valor que indica posição em
 * vetor de bytes.
 */
public class ImplementacaoDeReferencia implements ModeloDeReferenciaParcial {

    /**
     * Indica NULL.
     */
    public static final int NULL = -1;

    /**
     * Um tamanho arbitrário inicial. Gerenciamento de memória
     * deverá ser considerado para tratar possibilidade de
     * "ampliação" desse tamanho durante processo de
     * construção (criação) e de leitura.
     */
    public static final int MEGA = 1024 * 1024;

    /**
     * Local onde, de fato, dados serão armazenados.
     */
    private ByteBuffer bytes;

    /**
     * Última posição ocupada é o último byte do
     * identificador da raiz.
     */
    private int pos = 3;

    /**
     * Processo usado na "construção" de um objeto.
     */
    public ImplementacaoDeReferencia() {
        bytes = ByteBuffer.allocate(MEGA);
    }

    /**
     * Processo usado na leitura ou consulta a um objeto.
     *
     * @param payload Dados previamente serializados
     *                por uma instância dessa classe
     *                por meio do método {@link #toBytes()}.
     */
    public ImplementacaoDeReferencia(byte[] payload) {
        bytes = ByteBuffer.wrap(payload);
    }

    public int obtemTipo(int id, int campo) {
        return 0;
    }

    public byte obtemByte(int id, int campo) {
        return 0;
    }

    public String obtemString(int tipo, int id, int campo) {
        if (tipo == DV_TEXT) {
            // TODO
            // Não vou checar se o campo é válido
            // Valor abaixo só funciona para o primeiro campo
            // Demais campos exigem "canguru"
            return obtemString(id);
        }

        return null;
    }

    public boolean obtemLogico(int id, int campo) {
        return bytes.get(id + campo) != 0;
    }

    public int obtemChave(int id, int campo) {
        return 0;
    }

    public int obtemInteiro(int tipo, int id, int campo) {
        if (tipo == DV_PARAGRAPH) {
            // TODO
            // Não vou checar se o campo é válido
            // Valor abaixo só funciona para o primeiro campo
            // Demais campos exigem "canguru"
            return bytes.getInt(id);
        }

        return 0;
    }

    public float obtemFloat(int id, int campo) {
        return 0;
    }

    public double obtemDouble(int id, int campo) {
        return 0;
    }

    public String obtemTexto(int id, int campo) {
        return null;
    }

    public byte[] obtemVetorBytes(int id, int campo) {
        return new byte[0];
    }

    public int obtemTamanhoVetorBytes(int id, int campo) {
        return 0;
    }

    public InputStream obtemStreamVetorBytes(int id, int campo) {
        return null;
    }

    public int obtemQtdeBytes(int id, int campo) {
        return 0;
    }

    public byte[] obtemBytes(int id, int campo, int ini, int fim) {
        return new byte[0];
    }

    public byte[] obtemBytes(int id, int campo) {
        return new byte[0];
    }

    public void defineRaiz(int raiz) {
        bytes.putInt(0, raiz);
    }

    public int obtemRaiz() {
        return bytes.getInt(0);
    }

    public int totalObjetos() {
        return 0;
    }

    public int adicionaLista(int quantidade) {

        // Posição da lista (primeiro byte do tamanho)
        int index = pos + 1;

        bytes.putInt(index, quantidade);

        // Ajusta posição para último byte da lista
        pos = pos + (quantidade + 1) * 4;

        return index;
    }

    public int defineItemIemLista(int lista, int ordem, int item) {
        int index = lista + 4 + (ordem * 4);
        bytes.putInt(index, item);
        return index;
    }

    public int obtemItemEmLista(int lista, int ordem) {
        // TODO checar o tamanho da lista
        return bytes.getInt(lista + 4 * (ordem + 1));
    }

    public int obtemTamanhoLista(int lista) {
        return bytes.getInt(lista);
    }

    public int adicionaDvBoolean(boolean valor) {
        bytes.put(++pos, valor ? (byte)1 : (byte)0);
        return pos;
    }

    public int adicionaDvParagraph(int lista) {
        int index = pos + 1;
        bytes.putInt(index, lista);
        pos = pos + 4;

        return index;
    }

    public int adicionaDvText(String valor,
                              String formatting,
                              String hperlink,
                              String languageName,
                              String languageVersion,
                              String languageCodeString,
                              String encodingName,
                              String encodingVersion,
                              String encodingCodeString,
                              int lista) {
        int index = adicionaString(valor);
        adicionaString(formatting);
        adicionaString(hperlink);
        adicionaString(languageName);
        adicionaString(languageVersion);
        adicionaString(languageCodeString);
        adicionaString(encodingName);
        adicionaString(encodingVersion);
        adicionaString(encodingCodeString);

        // Adiciona referência para a lista
        bytes.putInt(++pos, lista);

        // Atualiza para último byte empregado
        pos = pos + 3;

        return index;
    }

    public byte[] toBytes() {
        return bytes.array();
    }

    public void toBytes(OutputStream destino) {

    }

    public void fromBytes(byte[] bytes) {

    }

    public void fromBytes(InputStream entrada) {

    }

    public String toXml() {
        return null;
    }

    public void toXml(OutputStream stream) {

    }

    public void fromXml(String xml) {

    }

    public void fromXml(InputStream stream) {

    }

    public String toJson() {
        return null;
    }

    public void toJson(OutputStream stream) {

    }

    public void fromJson(String json) {

    }

    public void fromJson(InputStream entrada) {

    }

    public int adicionaString(String str) {
        byte[] strBytes;
        try {
            strBytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("usar outra exceção, outra msg");
        }

        // Posição em que é inserida a string (primeiro byte do tamanho)
        int index = pos + 1;

        // Coloca o tamanho da sequência de bytes
        bytes.putInt(index, strBytes.length);

        // Atualiza para último byte utilizado
        pos = pos + 4;

        // Deposita a sequência propriamente dita
        for (byte b : strBytes) {
            bytes.put(++pos, b);
        }

        return index;
    }

    public String obtemString(int index) {
        int tamanho = bytes.getInt(index);

        // Atualiza index para 4 bytes seguintes
        index += 4;

        byte[] buffer = new byte[tamanho];
        for (int i = 0; i < tamanho; i++) {
            buffer[i] = bytes.get(index + i);
        }

        try {
            return new String(buffer, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("outra exception, outra msg");
        }
    }
}
