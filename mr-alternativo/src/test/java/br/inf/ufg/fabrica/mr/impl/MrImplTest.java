/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import br.inf.ufg.fabrica.mr.Mr;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public class MrImplTest {

    public static void printByteArray(byte[] buffer) {
        int i = 0;
        for (byte x : buffer) {
            System.out.println("" + i + " - " + x);
            i++;
        }
    }

    @Test
    public void testAdicionaDvBoolean() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaDvBoolean(true);

        int index = mr.adicionaDvBoolean(true);

        assertEquals(2, index);
        assertEquals(Mr.DV_BOOLEAN, mr.getBufferBuilder().dataBuffer().getByte(2));
        assertEquals(true, mr.getBufferBuilder().dataBuffer().getBoolean(3));
    }

    @Test
    public void testAdicionaDvState() throws Exception {
        MrImpl mr = new MrImpl(1);
        mr.adicionaDvState(99999, true);

        int index = mr.adicionaDvState(19875, false);

        assertEquals(6, index);
        assertEquals(Mr.DV_STATE, mr.getBufferBuilder().dataBuffer().getByte(index));
        assertEquals(19875, mr.getBufferBuilder().dataBuffer().getInt(7));
        assertEquals(false, mr.getBufferBuilder().dataBuffer().getBoolean(11));
    }

    @Test
    public void testAdicionaDvIdentifier() throws Exception {
        MrImpl mr = new MrImpl(1);

        int index = mr.adicionaDvIdentifier("issuer", "assigner", "id", "type");

        assertEquals(0, index);
        int type = mr.getBufferBuilder().dataBuffer().getByte(index);
        assertEquals(Mr.DV_IDENTIFIER, type);

        int issuerIndex = mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE);
        assertEquals(0, issuerIndex);
        int issuerLenght = mr.getVectorBB().dataBuffer().getInt(issuerIndex);
        assertEquals(6, issuerLenght);
        assertEquals("issuer", mr.getVectorBB().dataBuffer().toString(issuerIndex + Mr.INT_SIZE, issuerLenght, Charset.forName("UTF-8")));

        int assignerIndex = mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE + Mr.INT_SIZE);
        assertEquals(10, assignerIndex);
        int assignerLenght = mr.getVectorBB().dataBuffer().getInt(assignerIndex);
        assertEquals(8, assignerLenght);
        assertEquals("assigner", mr.getVectorBB().dataBuffer().toString(assignerIndex + Mr.INT_SIZE, assignerLenght, Charset.forName("UTF-8")));

        int idIndex = mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE + 2 * Mr.INT_SIZE);
        assertEquals(22, idIndex);
        int idLenght = mr.getVectorBB().dataBuffer().getInt(idIndex);
        assertEquals(2, idLenght);
        assertEquals("id", mr.getVectorBB().dataBuffer().toString(idIndex + Mr.INT_SIZE, idLenght, Charset.forName("UTF-8")));

        int typeIndex = mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE + 3 * Mr.INT_SIZE);
        assertEquals(28, typeIndex);
        int typeLenght = mr.getVectorBB().dataBuffer().getInt(typeIndex);
        assertEquals(4, typeLenght);
        assertEquals("type", mr.getVectorBB().dataBuffer().toString(typeIndex + Mr.INT_SIZE, typeLenght, Charset.forName("UTF-8")));
    }

    @Test
    public void testAdicionaDvUri() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaDvUri("http://www.rnp.br");

        String uri = "http://www.openehr.org/releases";
        int index = mr.adicionaDvUri(uri);

        assertEquals(5, index);
        int type = mr.getBufferBuilder().dataBuffer().getByte(index);
        assertEquals(Mr.DV_URI, type);
        int uriIndex = mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE);
        assertEquals(21, uriIndex);
        int uriLenght = mr.getVectorBB().dataBuffer().getInt(uriIndex);
        assertEquals(31, uriLenght);
        int uriStringIndex = uriIndex + Mr.INT_SIZE;
        assertEquals("http://www.openehr.org/releases", mr.getVectorBB().dataBuffer().toString(uriStringIndex, uriLenght, Charset.forName("UTF-8")));
    }

    @Test
    public void testAdicionaDvEhrUri() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaDvEhrUri("https://ufgnet.ufg.br");

        String uri = "http://inf.ufg.br/";
        int index = mr.adicionaDvEhrUri(uri);

        assertEquals(5, index);
        int type = mr.getBufferBuilder().dataBuffer().getByte(index);
        assertEquals(Mr.DV_EHR_URI, type);
        int uriIndex = mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE);
        assertEquals(25, uriIndex);
        int uriLenght = mr.getVectorBB().dataBuffer().getInt(uriIndex);
        assertEquals(18, uriLenght);
        int uriStringIndex = uriIndex + Mr.INT_SIZE;
        assertEquals("http://inf.ufg.br/", mr.getVectorBB().dataBuffer().toString(uriStringIndex, uriLenght, Charset.forName("UTF-8")));
    }

    @Test
    public void testAdicionaCodePhrase() throws Exception {
        MrImpl mr = new MrImpl();

        mr.adicionaCodePhrase("centc251::nnnnnnn");

        printByteArray(mr.getBufferBuilder().dataBuffer().array());
    }

    @Test
    public void testAdicionaDvParagraph() throws Exception {

    }

    @Test
    public void testAdicionaDvText() throws Exception {

    }

    @Test
    public void testAdicionaDvCodedText() throws Exception {

    }

    @Test
    public void testAdicionaTermMapping() throws Exception {
        MrImpl mr = new MrImpl();

        int index = mr.adicionaTermMapping(mr.adicionaCodePhrase("centc251::nnnnnnn"), '>', 1);
    }

    @Test
    public void testAdicionaTerminologyId() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaTerminologyId("openehr");

        int index = mr.adicionaTerminologyId("centc251");
        assertEquals(5, index);
        assertEquals(Mr.TERMINOLOGY_ID, mr.getBufferBuilder().dataBuffer().getByte(index));
        assertEquals(11, mr.getBufferBuilder().dataBuffer().getInt(index + Mr.TYPE_SIZE));

        assertEquals(10, mr.adicionaTerminologyId("snomed-ct", false));
        assertEquals(23, mr.getBufferBuilder().dataBuffer().getInt(10));
    }
}