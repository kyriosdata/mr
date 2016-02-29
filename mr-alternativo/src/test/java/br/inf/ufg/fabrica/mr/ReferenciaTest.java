/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ReferenciaTest {

    @Test
    public void testTotalBytes() throws Exception {
        Referencia referencia = new Referencia();
        byte[] a = Referencia.intToByteArray(12801223);
        int i = referencia.nextInt(new ByteArrayInputStream(a));
        System.out.println("Int = " + i);
    }
}