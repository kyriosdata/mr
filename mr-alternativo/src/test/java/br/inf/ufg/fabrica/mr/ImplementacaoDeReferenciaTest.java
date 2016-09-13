/*
 * Copyright (c) 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ImplementacaoDeReferenciaTest {

    private ModeloDeReferenciaParcial mrp;

    @Before
    public void setUp() {
        mrp = new ImplementacaoDeReferencia();
    }

    @Test
    public void testDvBoolean() throws Exception {
        int id = mrp.adicionaDvBoolean(true);
        byte[] registro = mrp.toBytes();

        // Sei que se trata de DB_BOOLEAN, afinal, se não soubesse
        // como poderia estar consultando?!
        ModeloDeReferenciaParcial recuperado = new ImplementacaoDeReferencia(registro);
        int raiz = recuperado.obtemRaiz();
        assertTrue(recuperado.obtemLogico(raiz, 0));
    }

    @Test
    public void testDvParagraphUmUnicoDvText() {

        // DV_TEXT sem TERM_MAPPING
        int dvt1 = mrp.adicionaDvText(
                "valor",
                "formatting",
                "hyperlink",
                "languageName",
                "languageVersion",
                "languageCodeString",
                "encodingName",
                "encodingVersion",
                "encodingCodeString",
                ImplementacaoDeReferencia.NULL);

        // Lista contendo o único DV_TEXT
        int lista = mrp.adicionaLista(1);

        // Ordem do item é zero
        mrp.defineItemIemLista(lista, 0, dvt1);

        int id = mrp.adicionaDvParagraph(lista);

        // Oh! Essa é a raiz!
        mrp.defineRaiz(id);

        // Obtém os bytes, possivelmente serão simplesmente
        // depositados em um arquivo.
        byte[] buffer = mrp.toBytes();

        // Momento mágico... com um buffer recuperado...
        ModeloDeReferenciaParcial mr = new ImplementacaoDeReferencia(buffer);

        // Sabemos que é um DV_PARAGRAPH
        int raiz = mr.obtemRaiz();

        // Podemos criar constantes DV_PARAGRAPH_LISTA em vez de 0, por exemplo.
        int chaveLista = mr.obtemInteiro(ImplementacaoDeReferencia.DV_PARAGRAPH,
                raiz,
                0);

        // Só tem um DV_TEXT na lista
        int chaveDvText = mr.obtemItemEmLista(chaveLista, 0);

        assertEquals("valor", mr.obtemString(
                ImplementacaoDeReferencia.DV_TEXT,
                chaveDvText,
                0));
    }
}