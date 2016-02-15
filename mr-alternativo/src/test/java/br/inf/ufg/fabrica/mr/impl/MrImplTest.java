/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class MrImplTest {

    @Test
    public void testAdicionaDvBoolean() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaDvBoolean(true);
        int index = mr.adicionaDvBoolean(true);
        printByteArray(mr.getBufferBuilder().dataBuffer().array());
        assertEquals(2, index);
        assertEquals(MrImpl.DV_BOOLEAN, mr.getBufferBuilder().dataBuffer().getByte(2));
        assertEquals(true, mr.getBufferBuilder().dataBuffer().getBoolean(3));
    }

    @Test
    public void testAdicionaDvState() throws Exception {
        MrImpl mr = new MrImpl(1);
        mr.adicionaDvState(99999, true);
        int index = mr.adicionaDvState(19875, false);

        assertEquals(6, index);
        assertEquals(19875, mr.getBufferBuilder().dataBuffer().getInt(7));
        assertEquals(false, mr.getBufferBuilder().dataBuffer().getBoolean(11));
    }

    @Test
    public void testAdicionaDvIdentifier() throws Exception {
        MrImpl mr = new MrImpl(1);

        mr.adicionaDvIdentifier("issuer", "assigner", "id", "type");

        int issuerIndex = mr.getBufferBuilder().dataBuffer().getInt(1);
        assertEquals(0, issuerIndex);
        int issuerLenght = mr.getVectorBB().dataBuffer().getInt(issuerIndex + 1);
        assertEquals(6, issuerLenght);
        assertEquals("issuer", mr.getVectorBB().dataBuffer().toString(issuerIndex + 5, issuerLenght, Charset.forName("UTF-8")));
    }

    private void printByteArray(byte[] buffer) {
        int i = 0;
        for (byte x : buffer) {
            System.out.println("" + i + " - " + x);
            i++;
        }
    }
}