/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import br.inf.ufg.fabrica.mr.impl.MrImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MrImplTest {

    private Mr mr;

    @Before
    public void setUp() throws Exception {
        mr = new MrFactory().getMr();
    }

    @Test
    public void testAdicionaDvBoolean() throws Exception {
        int index = mr.adicionaDvBoolean(true);

        assertEquals(Mr.DV_BOOLEAN, mr.getType(index));
        assertTrue(mr.getBoolean(index, 1));
        assertNotEquals(false, mr.getBoolean(index, 1));
    }

    @Test
    public void testAdicionaDvState() throws Exception {
        int index = mr.adicionaDvState(19875, false);

        assertEquals(Mr.DV_STATE, mr.getType(index));
        assertEquals(19875, mr.getInt(index, 1));
        assertFalse(mr.getBoolean(index, 2));
        assertNotEquals(true, mr.getBoolean(index, 2));
    }

    @Test
    public void testAdicionaDvIdentifier() throws Exception {
        int index = mr.adicionaDvIdentifier("issuer", "assigner", "id", "type");

        assertEquals(Mr.DV_IDENTIFIER, mr.getType(index));
        assertEquals("issuer", mr.getString(index, 1));
        assertEquals("assigner", mr.getString(index, 2));
        assertEquals("id", mr.getString(index, 3));
        assertEquals("type", mr.getString(index, 4));
    }

    @Test
    public void testAdicionaDvUri() throws Exception {
        int index = mr.adicionaDvUri("http://www.openehr.org/releases");

        assertEquals(Mr.DV_URI, mr.getType(index));
        assertEquals("http://www.openehr.org/releases", mr.getString(index, 1));
    }

    @Test
    public void testAdicionaDvEhrUri() throws Exception {
        int index = mr.adicionaDvEhrUri("http://inf.ufg.br/");

        assertEquals(Mr.DV_EHR_URI, mr.getType(index));
        assertEquals("http://inf.ufg.br/", mr.getString(index, 1));
    }

    @Test
    public void testAdicionaCodePhrase() throws Exception {
        int index = mr.adicionaCodePhrase("centc251::nnnnnnn");

        assertEquals(Mr.CODE_PHRASE, mr.getType(index));
        assertEquals("centc251::nnnnnnn", mr.getString(index, 1));
    }

    @Test
    public void testAdicionaDvParagraph() throws Exception {

    }

    @Test
    public void testAdicionaDvText() throws Exception {
        int index = mr.adicionaDvText(
                mr.adicionaDvUri("http://google.com"),
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                mr.adicionaCodePhrase("centc252::nnnnnnn"),
                createListTermMapping(mr, 16),
                "a", "Neosaldina"
        );

        assertEquals(Mr.DV_TEXT, mr.getType(index));
        assertEquals("http://google.com", mr.getString(mr.getRef(index, 1), 1));
        assertEquals("centc251::nnnnnnn", mr.getString(mr.getRef(index, 2), 1));
        assertEquals("centc252::nnnnnnn", mr.getString(mr.getRef(index, 3), 1));
        assertEquals(16, mr.obtemTamanhoLista(mr.getList(index, 4)));
        assertEquals("a", mr.getString(index, 5));
        assertEquals("Neosaldina", mr.getString(index, 6));
    }

    private int createListTermMapping(Mr mr, int count) {
        int[] offset = new int[count];
        mr.startVector(offset.length);
        for (int i = 0; i < offset.length; i++) {
            offset[i] = mr.adicionaTermMapping(mr.adicionaCodePhrase("centc251::nnnnnnn"), '>', createDvCodedText(mr));
        }
        return mr.endVector(offset);
    }

    @Test
    public void testAdicionaDvCodedText() throws Exception {
        int index = mr.adicionaDvCodedText(
                mr.adicionaDvUri("http://google.com.br"),
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                mr.adicionaCodePhrase("centc252::nnnnnnn"),
                mr.adicionaCodePhrase("centc253::nnnnnnn"),
                createListTermMapping(mr, 13),
                "a", "b"
        );

        assertEquals(Mr.DV_CODED_TEXT, mr.getType(index));
        assertEquals("http://google.com.br", mr.getString(mr.getRef(index, 1), 1));
        assertEquals("centc251::nnnnnnn", mr.getString(mr.getRef(index, 2), 1));
        assertEquals("centc252::nnnnnnn", mr.getString(mr.getRef(index, 3), 1));
        assertEquals("centc253::nnnnnnn", mr.getString(mr.getRef(index, 4), 1));
        assertEquals(13, mr.obtemTamanhoLista(mr.getList(index, 5)));
        assertEquals("a", mr.getString(index, 6));
        assertEquals("b", mr.getString(index, 7));
    }

    @Test
    public void testAdicionaTermMapping() throws Exception {
        int index = mr.adicionaTermMapping(
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                '>',
                createDvCodedText(mr)
        );

        assertEquals(Mr.TERM_MAPPING, mr.getType(index));
        assertEquals("centc251::nnnnnnn", mr.getString(mr.getRef(index, 1), 1));
        assertEquals(Mr.DV_CODED_TEXT, mr.getType(mr.getRef(index, 2)));
        assertEquals('>', mr.getChar(index, 3));
    }

    private int createDvCodedText(Mr mr) {
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

        int index = mr.adicionaTerminologyId("centc251");

        assertEquals(Mr.TERMINOLOGY_ID, mr.getType(index));
        assertEquals("centc251", mr.getString(index, 1));
    }
}