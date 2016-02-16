/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import java.io.ByteArrayInputStream;

//TODO: reduzir float e double caso os testes mostrem 
//que compensa reduzir o int e long

/**
 * Implementação de valor que indica posição em
 * vetor de bytes.
 */
public class Referencia {

    /**
     * Obtém a quantidade de bytes necessária para
     * armazenar o valor.
     *
     * @param endereco Valor a ser armazenado.
     * @return Quantidade de bytes, de 1 a 4,
     * inclusive, para armazenar o valor fornecido.
     * <p/>
     * TODO como otimizar?
     */
    public static int totalBytes(int inteiro) {
        if (inteiro <= 0x3F) {
            return 1;
        }

        if (inteiro <= 0x3FFF) {
            return 2;
        }

        if (inteiro <= 0x3FFFFF) {
            return 3;
        }

        return 4;
    }

    public static int totalBytes(long value) {
        if (value < Math.pow(2, 5)) {
            return 1;
        }

        if (value < Math.pow(2, 13)) {
            return 2;
        }

        if (value < Math.pow(2, 21)) {
            return 3;
        }

        if (value < Math.pow(2, 29)) {
            return 4;
        }

        if (value < Math.pow(2, 37)) {
            return 5;
        }

        if (value < Math.pow(2, 45)) {
            return 6;

        }

        if (value < Math.pow(2, 53)) {
            return 7;
        }

        return 8;
    }

    /**
     * o byte varia de -127 a 127, portanto é preciso
     * convertê-lo para int para pegar o valor sem sinal
     */
    public int getByte(byte b) {
        return b & 0xFF;
    }
    
        /**
     * Obtém o tamanho do inteiro a partir do
     * byte informado.
     * O byte passado por parâmetro contém 2 bits
     * referentes ao tamanho do inteiro que o segue
     * e 6 bits pertencentes ao próprio inteiro.
     * Por ex., caso a função retorne 1, nenhum  
     * outro byte precisa ser lido além do primeiro.
     */
    public static int tamanhoInt(byte b){
        return (b >>> 6) + 1;
    }


    /**
     * Converte um inteiro em um vetor de bytes.
     * <p/>
     * A conversão não necessariamente faz uso de
     * quatro bytes (cenário convencional). Por exemplo,
     * se o inteiro é o valor 50, então sabemos que
     * um único byte é suficiente. Abaixo é detalhada
     * a estratégia adotada.
     * <p/>
     * O inteiro será armazenado em uma sequência de bits
     * na qual os 2 bits mais significativos indicam a
     * quantidade de bytes empregada para armazenar o
     * inteiro. Esses dois bits podem indicar os valores
     * 1, 2, 3 ou 4, respectivamente as sequências
     * 00, 01, 10 e 11, em binários.
     * <p/>
     * Se os dois bits mais significativos são 00,
     * então um único byte é empregado, o próprio
     * byte que contém tais bits. Neste caso, restam
     * 6 bits úteis no byte. Tais bits podem armazenar
     * os valores na faixa de 0 (0x00) a 63 (0x3F0).
     * <p/>
     * Se os dois bits mais significativos são 01,
     * então 2 bytes são empregados. Nesse caso,
     * um valor na faixa de 64 (0x40) a 16383 (0x3FFF)
     * pode ser registrado.
     * <p/>
     * O int é convertido para um array de byte
     * com o menor tamanho possivel.
     * O tamanho do array é guardado nos dois primeiros
     * bits, assim, por exemplo, um valor de ate 2^6 é guardado em
     * 1 byte. Os dois primeiros bits são setados
     * conforme o tamanho do array: 00 indica que é utilizado
     * apenas 1 byte; 01 para 2 byte; 10 para 3 bytes;
     * 11 para 4 bytes.
     */
    public static byte[] intToByteArray(int value) {
        if (value < 0 || value >= (Math.pow(2, 30))) {
            System.out.println(value + " unsupported");
            return null;
        }
        if (value < Math.pow(2, 6)) {
            return new byte[]{
                    (byte) value
            };
        }

        if (value < Math.pow(2, 14)) {
            return new byte[]{
                    (byte) ((value >>> 8) | (1 << 6)),
                    (byte) value
            };
        }

        if (value < Math.pow(2, 22)) {
            return new byte[]{
                    (byte) ((value >>> 16) | (2 << 6)),
                    (byte) (value >>> 8),
                    (byte) value
            };
        }

        return new byte[]{
                (byte) ((value >>> 24) | (3 << 6)),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    /**
     * O long é convertido para um array de byte
     * com o menor tamanho possivel.
     * O tamanho do array é guardado nos dois primeiros
     * bits, assim, por exemplo, um valor de ate 2^6 é guardado em
     * 1 byte. Os dois primeiros bits são setados
     * conforme o tamanho do array: 000 indica que é utilizado
     * apenas 1 byte; 001 para 2 byte; 010 para 3 bytes;
     * 011 para 4 bytes; 100 para 5 bytes; 101 para 6 bytes;
     * 110 para 7 bytes; 111 para 8 bytes.
     */
    public static byte[] longToByteArray(long value) {
        if (value < 0 || value >= (Math.pow(2, 61))) {
            System.out.println(value + " unsupported");
            return null;
        }
        if (value < Math.pow(2, 5)) {
            return new byte[]{
                    (byte) value
            };
        }

        if (value < Math.pow(2, 13)) { // <= 0001 1111 1111 1111
            return new byte[]{
                    (byte) ((value >>> 8) | (1 << 5)),
                    (byte) value
            };

        }

        if (value < Math.pow(2, 21)) {
            return new byte[]{
                    (byte) ((value >>> 16) | (2 << 5)),
                    (byte) (value >>> 8),
                    (byte) value
            };
        }

        if (value < Math.pow(2, 29)) {
            return new byte[]{
                    (byte) ((value >>> 24) | (3 << 5)),
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value
            };
        }

        if (value < Math.pow(2, 37)) {
            return new byte[]{
                    (byte) ((value >>> 32) | (4 << 5)),
                    (byte) (value >>> 24),
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value
            };
        }

        if (value < Math.pow(2, 45)) {
            return new byte[]{
                    (byte) ((value >>> 40) | (5 << 5)),
                    (byte) (value >>> 32),
                    (byte) (value >>> 24),
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value
            };

        }

        if (value < Math.pow(2, 53)) {
            return new byte[]{
                    (byte) ((value >>> 48) | (6 << 5)),
                    (byte) (value >>> 40),
                    (byte) (value >>> 32),
                    (byte) (value >>> 24),
                    (byte) (value >>> 16),
                    (byte) (value >>> 8),
                    (byte) value
            };
        }

        return new byte[]{
                (byte) ((value >>> 56) | (7 << 5)),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    /**
     * Converte o array de bytes em um int.
     */
    public int byteArrayToInt(byte[] array) {
        int x = 0;
        for (byte b : array) {
            x = (x << 8) | getByte(b);
        }
        return x;
    }

    //TODO: integrar int e long em metodo unico

    /**
     * Pega o proximo inteiro em um buffer.
     * Os primeiros dois bits informam o tamanho
     * do array de bytes que formam um int.
     * Assim o int é formado pelo primeiro byte
     * (com exceção dos 2 primeiros bits) agregado
     * ao restante, conforme o tamanho descoberto.
     */
    public int nextInt(ByteArrayInputStream buf) {
        byte b = (byte) buf.read();
        int i = tamanhoInt(b);
        byte ini = (byte) (b & 0x3F);

        switch (i) {
            case 0:
                return ini;
            case 1:
                byte[] temp = {ini, (byte) buf.read()};
                return byteArrayToInt(temp);
            case 2:
                byte[] temp2 = {ini, (byte) buf.read(), (byte) buf.read()};
                return byteArrayToInt(temp2);
            case 3:
                byte[] temp3 = {ini, (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read()};
                return byteArrayToInt(temp3);
            default:
                return 0;
        }
    }

    /**
     * Converte o array de bytes em um long.
     */
    public long byteArrayToLong(byte[] array) {
        long x = 0;
        for (byte b : array) {
            x = (x << 8) | getByte(b);
        }
        return x;
    }

    /**
     * Pega o proximo inteiro em um buffer.
     * Os primeiros dois bits informam o tamanho
     * do array de bytes que formam um int.
     * Assim o long é formado pelo primeiro byte
     * (com exceção dos 2 primeiros bits) agregado
     * ao restante, conforme o tamanho descoberto.
     */
    public long nextLong(ByteArrayInputStream buf) {
        byte b = (byte) buf.read();
        int i = getByte((byte) (b & 0xE0));
        byte ini = (byte) (b & 0x1F);

        switch (i) {
            case 0:
                return ini;
            case 0x20:
                byte[] temp = {ini, (byte) buf.read()};
                return byteArrayToLong(temp);
            case 0x40:
                byte[] temp2 = {ini, (byte) buf.read(), (byte) buf.read()};
                return byteArrayToLong(temp2);
            case 0x60:
                byte[] temp3 = {ini, (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read()};
                return byteArrayToLong(temp3);
            case 0x80:
                byte[] temp4 = {ini, (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read(),
                        (byte) buf.read()};
                return byteArrayToLong(temp4);
            case 0xA0:
                byte[] temp5 = {ini, (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read()};
                return byteArrayToLong(temp5);
            case 0xC0:
                byte[] temp6 = {ini, (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read(),
                        (byte) buf.read()};
                return byteArrayToLong(temp6);
            case 0xE0:
                byte[] temp7 = {ini, (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read(),
                        (byte) buf.read(), (byte) buf.read()};
                return byteArrayToLong(temp7);
            default:
                return 0;
        }
    }
}
