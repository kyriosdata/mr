/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import org.junit.Test;

import static org.junit.Assert.*;

public class MrImplTest {

    @Test
    public void testAdicionaDvBoolean() throws Exception {
        MrImpl mr = new MrImpl(5);
        int index = mr.adicionaDvBoolean(true);
        assertEquals(3, index);
    }

    @Test
    public void testAdicionaDvState() throws Exception {
        MrImpl mr = new MrImpl(10);

        int index = mr.adicionaDvState(4, true);
        assertEquals(4, index);
    }

    private void printByteArray(byte[] buffer) {
        for (byte x : buffer) {
            System.out.println(x);
        }
    }
}