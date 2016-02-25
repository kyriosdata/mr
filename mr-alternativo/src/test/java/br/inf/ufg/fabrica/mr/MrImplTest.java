/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import br.inf.ufg.fabrica.mr.impl.MrImpl;
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
        Mr mr = new MrFactory().getMr();

        int index = mr.adicionaDvBoolean(true);

        assertEquals(Mr.DV_BOOLEAN, mr.getType(index));
        assertEquals(true, mr.getBoolean(index, 1));
    }

    @Test
    public void testAdicionaDvState() throws Exception {
        Mr mr = new MrFactory().getMr();

        int index = mr.adicionaDvState(19875, false);

        assertEquals(Mr.DV_STATE, mr.getType(index));
        assertEquals(19875, mr.getInt(index, 1));
        assertEquals(false, mr.getBoolean(index, 2));
    }

    @Test
    public void testAdicionaDvIdentifier() throws Exception {
        MrImpl mr = new MrImpl(1);

        int index = mr.adicionaDvIdentifier("issuer", "assigner", "id", "type");

        assertEquals(Mr.DV_IDENTIFIER, mr.getType(index));
        assertEquals("issuer", mr.getString(index, 1));
        assertEquals("assigner", mr.getString(index, 2));
        assertEquals("id", mr.getString(index, 3));
        assertEquals("type", mr.getString(index, 4));
    }

    @Test
    public void testAdicionaDvUri() throws Exception {
        MrImpl mr = new MrImpl();

        int index = mr.adicionaDvUri("http://www.openehr.org/releases");

        assertEquals(Mr.DV_URI, mr.getType(index));
        assertEquals("http://www.openehr.org/releases", mr.getString(index, 1));
    }

    @Test
    public void testAdicionaDvEhrUri() throws Exception {
        Mr mr = new MrFactory().getMr();

        int index = mr.adicionaDvEhrUri("http://inf.ufg.br/");

        assertEquals(Mr.DV_EHR_URI, mr.getType(index));
        assertEquals("http://inf.ufg.br/", mr.getString(index, 1));
    }

    @Test
    public void testAdicionaCodePhrase() throws Exception {
        MrImpl mr = new MrImpl();

        int index = mr.adicionaCodePhrase("centc251::nnnnnnn");

        assertEquals(Mr.CODE_PHRASE, mr.getType(index));
        assertEquals("centc251::nnnnnnn", mr.getString(index, 1));
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

        assertEquals(23, index);
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

        assertEquals(28, index);
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

        assertEquals(13, index);
        assertEquals(Mr.TERMINOLOGY_ID, mr.getType(index));
        assertEquals(11, mr.getInt(index + Mr.TYPE_SIZE));

        int indexWithoutType = mr.adicionaTerminologyId("snomed-ct", false);
        assertEquals(18, indexWithoutType);
        assertEquals(23, mr.getInt(indexWithoutType));
    }
}