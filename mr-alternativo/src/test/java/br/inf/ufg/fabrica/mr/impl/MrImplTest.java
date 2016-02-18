/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import br.inf.ufg.fabrica.mr.Mr;
import org.junit.Test;

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

        assertEquals(6, index);
        assertEquals(Mr.DV_BOOLEAN, mr.getByte(6));
        assertEquals(true, mr.getBoolean(7));
    }

    @Test
    public void testAdicionaDvState() throws Exception {
        MrImpl mr = new MrImpl(1);
        mr.adicionaDvState(99999, true);

        int index = mr.adicionaDvState(19875, false);

        assertEquals(10, index);
        assertEquals(Mr.DV_STATE, mr.getByte(index));
        assertEquals(19875, mr.getInt(11));
        assertEquals(false, mr.getBoolean(15));
    }

    @Test
    public void testAdicionaDvIdentifier() throws Exception {
        MrImpl mr = new MrImpl(1);

        int index = mr.adicionaDvIdentifier("issuer", "assigner", "id", "type");

        assertEquals(4, index);
        int type = mr.getByte(index);
        assertEquals(Mr.DV_IDENTIFIER, type);

        int issuerIndex = mr.getInt(index + Mr.TYPE_SIZE);
        assertEquals(0, issuerIndex);
        int issuerLenght = mr.getStringLength(issuerIndex);
        assertEquals(6, issuerLenght);
        assertEquals("issuer", mr.getString(issuerIndex));

        int assignerIndex = mr.getInt(index + Mr.TYPE_SIZE + Mr.INT_SIZE);
        assertEquals(10, assignerIndex);
        int assignerLenght = mr.getStringLength(assignerIndex);
        assertEquals(8, assignerLenght);
        assertEquals("assigner", mr.getString(assignerIndex));

        int idIndex = mr.getInt(index + Mr.TYPE_SIZE + 2 * Mr.INT_SIZE);
        assertEquals(22, idIndex);
        int idLenght = mr.getStringLength(idIndex);
        assertEquals(2, idLenght);
        assertEquals("id", mr.getString(idIndex));

        int typeIndex = mr.getInt(index + Mr.TYPE_SIZE + 3 * Mr.INT_SIZE);
        assertEquals(28, typeIndex);
        int typeLenght = mr.getStringLength(typeIndex);
        assertEquals(4, typeLenght);
        assertEquals("type", mr.getString(typeIndex));
    }

    @Test
    public void testAdicionaDvUri() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaDvUri("http://www.rnp.br");

        String uri = "http://www.openehr.org/releases";
        int index = mr.adicionaDvUri(uri);

        assertEquals(9, index);
        int type = mr.getByte(index);
        assertEquals(Mr.DV_URI, type);
        int uriIndex = mr.getInt(index + Mr.TYPE_SIZE);
        assertEquals(21, uriIndex);
        int uriLenght = mr.getStringLength(uriIndex);
        assertEquals(31, uriLenght);
        assertEquals("http://www.openehr.org/releases", mr.getString(uriIndex));
    }

    @Test
    public void testAdicionaDvEhrUri() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaDvEhrUri("https://ufgnet.ufg.br");

        String uri = "http://inf.ufg.br/";
        int index = mr.adicionaDvEhrUri(uri);

        assertEquals(9, index);
        int type = mr.getByte(index);
        assertEquals(Mr.DV_EHR_URI, type);
        int uriIndex = mr.getInt(index + Mr.TYPE_SIZE);
        assertEquals(25, uriIndex);
        int uriLenght = mr.getStringLength(uriIndex);
        assertEquals(18, uriLenght);
        assertEquals("http://inf.ufg.br/", mr.getString(uriIndex));
    }

    @Test
    public void testAdicionaCodePhrase() throws Exception {
        MrImpl mr = new MrImpl();

        mr.adicionaCodePhrase("centc251::nnnnnnn");
    }

    @Test
    public void testAdicionaDvParagraph() throws Exception {

    }

    @Test
    public void testAdicionaDvText() throws Exception {
        MrImpl mr = new MrImpl();

        int index = mr.adicionaDvText(
            mr.adicionaDvUri("http://google.com"),
            mr.adicionaCodePhrase("centc251::nnnnnnn"),
            mr.adicionaCodePhrase("centc252::nnnnnnn"),
            5, "a", "b"
        );
        assertEquals(Mr.DV_TEXT, mr.getType(index));
        assertEquals(16, mr.getRef(index + 1));
        assertEquals(12, mr.getRef(index + 2));
        assertEquals( 8, mr.getRef(index + 3));
        assertEquals( 5, mr.getInt(index + 4));
        assertEquals(63, mr.getInt(index + 8));
        assertEquals(68, mr.getInt(index + 12));
    }

    @Test
    public void testAdicionaDvCodedText() throws Exception {
        MrImpl mr = new MrImpl();

        int index = mr.adicionaDvCodedText(
                mr.adicionaDvUri("http://google.com.br"),
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                mr.adicionaCodePhrase("centc252::nnnnnnn"),
                mr.adicionaCodePhrase("centc253::nnnnnnn"),
                18, "a", "b"
        );

        assertEquals(Mr.DV_CODED_TEXT, mr.getType(index));
        assertEquals(21, mr.getRef(index + 1));
        assertEquals(17, mr.getRef(index + 2));
        assertEquals(13, mr.getRef(index + 3));
        assertEquals( 9, mr.getRef(index + 4));
        assertEquals(18, mr.getInt(index + 5));
        assertEquals(87, mr.getInt(index + 9));
        assertEquals(92, mr.getInt(index + 13));
    }

    @Test
    public void testAdicionaTermMapping() throws Exception {
        MrImpl mr = new MrImpl();

        int index = mr.adicionaTermMapping(
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                '>',
                createDvCodedText(mr)
        );

        assertEquals(Mr.TERM_MAPPING, mr.getType(index));
        assertEquals(43, mr.getRef(index + 1));
        assertEquals(19, mr.getRef(index + 2));
        assertEquals('>', mr.getChar(index + 3));
    }

    private int createDvCodedText(MrImpl mr) {
        return mr.adicionaDvCodedText(
                mr.adicionaDvUri("http://google.com.br"),
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                mr.adicionaCodePhrase("centc252::nnnnnnn"),
                mr.adicionaCodePhrase("centc253::nnnnnnn"),
                18, "a", "b"
        );
    }

    @Test
    public void testAdicionaTerminologyId() throws Exception {
        MrImpl mr = new MrImpl();
        mr.adicionaTerminologyId("openehr");

        int index = mr.adicionaTerminologyId("centc251");
        assertEquals(9, index);
        assertEquals(Mr.TERMINOLOGY_ID, mr.getByte(index));
        assertEquals(11, mr.getInt(index + Mr.TYPE_SIZE));

        int indexWithoutType = mr.adicionaTerminologyId("snomed-ct", false);
        assertEquals(14, indexWithoutType);
        assertEquals(23, mr.getInt(indexWithoutType));
    }
}