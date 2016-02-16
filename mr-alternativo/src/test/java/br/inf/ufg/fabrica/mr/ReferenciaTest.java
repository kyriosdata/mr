/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import br.inf.ufg.fabrica.mr.impl.MrImpl;
import br.inf.ufg.fabrica.mr.impl.MrImplTest;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static junit.framework.Assert.assertEquals;

public class ReferenciaTest {

    @Test
    public void testTotalBytes() throws Exception {
        Referencia referencia = new Referencia();
        byte[] a = Referencia.intToByteArray(12801223);
//        System.out.println(Referencia.intSize(a[0]));
        int i = referencia.nextInt(new ByteArrayInputStream(a));
        System.out.println("Int = " + i);
//        assertEquals(1, Referencia.intToByteArray(0));
//        assertEquals(1, Referencia.totalBytes(63));
//        assertEquals(2, Referencia.totalBytes(64));
//        assertEquals(2, Referencia.totalBytes(16383));
//        assertEquals(3, Referencia.totalBytes(16384));
//        assertEquals(3, Referencia.totalBytes(4194303));
//        assertEquals(4, Referencia.totalBytes(4194304));
    }
}