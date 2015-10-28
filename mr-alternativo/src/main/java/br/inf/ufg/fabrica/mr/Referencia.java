/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

/**
 * Implementação de valor que indica posição em
 * vetor de bytes.
 */
public class Referencia {

    /**
     * * Converte um inteiro em um vetor de bytes.
     *
     * A conversão não necessariamente faz uso de
     * quatro bytes (cenário convencional). Por exemplo,
     * se o inteiro é o valor 50, então sabemos que
     * um único byte é suficiente. Abaixo é detalhada
     * a estratégia adotada.
     *
     * O inteiro será armazenado em uma sequência de bits
     * na qual os 2 bits mais significativos indicam a
     * quantidade de bytes empregada para armazenar o
     * inteiro. Esses dois bits podem indicar os valores
     * 1, 2, 3 ou 4, respectivamente as sequências
     * 00, 01, 10 e 11, em binários.
     *
     * Se os dois bits mais significativos são 00,
     * então um único byte é empregado, o próprio
     * byte que contém tais bits. Neste caso, restam
     * 6 bits úteis no byte. Tais bits podem armazenar
     * os valores na faixa de 0 (0x00) a 63 (0x3F0).
     *
     * Se os dois bits mais significativos são 01,
     * então 2 bytes são empregados. Nesse caso,
     * um valor na faixa de 64 (0x40) a 16383 (0x3FFF)
     * pode ser registrado.
     *
     * @param endereco
     * @return
     */
    public static byte[] empacota(int endereco) {
        return null;
    }

    /**
     * Obtém a quantidade de bytes necessária para
     * armazenar o valor.
     *
     * @param endereco Valor a ser armazenado.
     *
     * @return Quantidade de bytes, de 1 a 4,
     * inclusive, para armazenar o valor fornecido.
     */
    public static int totalBytes(int endereco) {
        if (endereco <= 0x3F) {
            return 1;
        }

        if (endereco <= 0x3FFF) {
            return 2;
        }

        if (endereco <= 0x3FFFFF) {
            return 3;
        }

        return 4;
    }
}
