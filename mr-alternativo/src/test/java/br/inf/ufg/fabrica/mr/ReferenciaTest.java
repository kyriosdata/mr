/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ReferenciaTest {

    @Test
    public void testTotalBytes() throws Exception {
        assertEquals(1, Referencia.totalBytes(0));
        assertEquals(1, Referencia.totalBytes(63));
        assertEquals(2, Referencia.totalBytes(64));
        assertEquals(2, Referencia.totalBytes(16383));
        assertEquals(3, Referencia.totalBytes(16384));
        assertEquals(3, Referencia.totalBytes(4194303));
        assertEquals(4, Referencia.totalBytes(4194304));
    }
}