/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufg.fabrica.mr;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Beatriz
 */
public class TesteBytes {
    /**
     * o byte varia de -127 a 127, portanto é preciso
     * convertê-lo para int para pegar o valor sem sinal
     */
    public static int getByte(byte b) {
        return b & 0xFF;
    }


    /**
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
     * Converte o array de bytes em um int.
     */
    public static int byteArrayToInt(byte[] b) {
        int x = 0;
        for (int i = 0; i < b.length; i++) {
            x = (x << 8) | getByte(b[i]);
        }
        return x;
    }

    /**
     * Pega o proximo inteiro em um buffer.
     * Os primeiros dois bits informam o tamanho
     * do array de bytes que formam um int.
     * Assim o int é formado pelo primeiro byte
     * (com exceção dos 2 primeiros bits) agregado
     * ao restante, conforme o tamanho descoberto.
     */
    public static int nextInt(ByteArrayInputStream buf) {
        int b = buf.read();
        byte i = (byte) (b >>> 6);
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

        }
        return 0;
    }
    

    //TODO: integrar int e long em metodo unico
    
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
     * Converte o array de bytes em um long.
     */
    public static long byteArrayToLong(byte[] b) {
        long x = 0;
        for (int i = 0; i < b.length; i++) {
            x = (x << 8) | getByte(b[i]);
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
    public static long nextLong(ByteArrayInputStream buf) {
        byte b = (byte)buf.read();
        //System.out.println("b "+getByte(b));
        int i = getByte((byte)(b & 0xE0));
        byte ini = (byte)(b & 0x1F);
        
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
            
        }
        return 0;
    }

    /**
     * @param args the command line arguments
     *             <p/>
     *             Testes.
     */
    public static void main(String[] args) throws IOException {
        int a = 32; //0001 0000
        byte[] x1 = intToByteArray(a);
        System.out.println(a + ", tamanho " + x1.length + ", bytes = " + getByte(x1[0]));
        ByteArrayInputStream buf1 = new ByteArrayInputStream(x1);
        System.out.println("Inteiro lido: " + nextInt(buf1));

        a = 511; //0000 0001 1111 1111
        byte[] x2 = intToByteArray(a);
        System.out.println("\n" + a + ", tamanho " + x2.length + ", bytes = "
                + getByte(x2[0]) + " " + getByte(x2[1]));
        ByteArrayInputStream buf2 = new ByteArrayInputStream(x2);
        System.out.println("Inteiro lido: " + nextInt(buf2));

        a = 1048576; //0001 0000 0000 0000 0000 0000
        byte[] x3 = intToByteArray(a);
        System.out.println("\n" + a + ", tamanho " + x3.length + ", bytes = " + getByte(x3[0]) + " "
                + getByte(x3[1]) + " " + getByte(x3[2]));
        ByteArrayInputStream buf3 = new ByteArrayInputStream(x3);
        System.out.println("Inteiro lido: " + nextInt(buf3));

        a = 1073741823; //0011 1111 1111 1111 1111 1111 1111 1111
        byte[] x4 = intToByteArray(a);
        System.out.println("\n" + a + ", tamanho " + x4.length + ", bytes = " + getByte(x4[0]) + " "
                + getByte(x4[1]) + " " + getByte(x4[2]) + " " + getByte(x4[3]));
        ByteArrayInputStream buf4 = new ByteArrayInputStream(x4);
        System.out.println("Inteiro lido: " + nextInt(buf4));
        
        
        
        
        
        
        //LONG
        long c = 64L;
        byte[] x6 = longToByteArray(c);
        System.out.println(c + ", tamanho " + x6.length + ", bytes = " + getByte(x6[0]));
        ByteArrayInputStream buf6 = new ByteArrayInputStream(x6);
        System.out.println("Long lido: " + nextLong(buf6));

        c = 16384L;
        byte[] x7 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x7.length + ", bytes = "
                + getByte(x7[0]) + " " + getByte(x7[1]));
        ByteArrayInputStream buf7 = new ByteArrayInputStream(x7);
        System.out.println("Long lido: " + nextLong(buf7));

        c = 1048576L; 
        byte[] x8 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x8.length + ", bytes = " + getByte(x8[0]) + " "
                + getByte(x8[1]) + " " + getByte(x8[2]));
        ByteArrayInputStream buf8 = new ByteArrayInputStream(x8);
        System.out.println("Long lido: " + nextLong(buf8));

        c =  4194304L;
        byte[] x9 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x9.length + ", bytes = " + getByte(x9[0]) + " "
                + getByte(x9[1]) + " " + getByte(x9[2]) + " " + getByte(x9[3]));
        ByteArrayInputStream buf9 = new ByteArrayInputStream(x9);
        System.out.println("Long lido: " + nextLong(buf9));
        
        c = 1073741824L; ////1000 0000    0100 0000    0000 0000    0000 0000    0000 0000
        byte[] x10 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x10.length + ", bytes = " + getByte(x10[0]) + " "
                + getByte(x10[1]) + " " + getByte(x10[2]) + " " + getByte(x10[3])+ " " + getByte(x10[4]));
        ByteArrayInputStream buf10 = new ByteArrayInputStream(x10);
        System.out.println("Long lido: " + nextLong(buf10));
        
        c = 274877906944L; 
        byte[] x11 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x11.length + ", bytes = " + getByte(x11[0]) + " "
                + getByte(x10[1]) + " " + getByte(x10[2]) + " " + getByte(x10[3]));
        ByteArrayInputStream buf11 = new ByteArrayInputStream(x11);
        System.out.println("Long lido: " + nextLong(buf11));
        
        c = (long) Math.pow(2, 46);
        byte[] x12 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x12.length + ", bytes = " + getByte(x12[0]) + " "
                + getByte(x12[1]) + " " + getByte(x8[2]));
        ByteArrayInputStream buf12 = new ByteArrayInputStream(x12);
        System.out.println("Long lido: " + nextLong(buf12));

        c = (long) Math.pow(2, 54);
        byte[] x13 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x13.length + ", bytes = " + getByte(x9[0]) + " "
                + getByte(x9[1]) + " " + getByte(x9[2]) + " " + getByte(x9[3]));
        ByteArrayInputStream buf13 = new ByteArrayInputStream(x13);
        System.out.println("Long lido: " + nextLong(buf13));
        
        c = (long) Math.pow(2, 60);
        byte[] x14 = longToByteArray(c);
        System.out.println("\n" + c + ", tamanho " + x14.length + ", bytes = " + getByte(x10[0]) + " "
                + getByte(x10[1]) + " " + getByte(x10[2]) + " " + getByte(x10[3]));
        ByteArrayInputStream buf14 = new ByteArrayInputStream(x14);
        System.out.println("Long lido: " + nextLong(buf14));
        
        
        /*
         * Inserção e leitura de um unico buffer.
         */
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(x1);
        out.write(x2);
        out.write(x3);
        out.write(x4);
        out.write(x6);
        out.write(x7);
        out.write(x8);
        out.write(x9);
        out.write(x10);
        out.write(x11);
        out.write(x12);
        out.write(x13);
        out.write(x14);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        System.out.print("\nLeitura de buffer unico: ");
        for (int k = 0 ; k < 4; k++){
            System.out.print(nextInt(in)+" -- ");
        }
        for (int k = 0 ; k < 9; k++){
            System.out.print(nextLong(in)+" -- ");
        }
        System.out.println();

    }

}

