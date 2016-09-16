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
        //
        // id não está sendo guardado em lugar algum
        // Sabe-se que é um DV_BOOLEAN durante o armazenamento, mas a recuperação é feita em outro momento,
        // onde não se conhece a estrutura do grafo até que o tipo da raiz seja recuperado e
        // não se conhece os dados até que o grafo seja remontado.
        // deveria ser feito:
        // ModeloDeReferenciaParcial recuperado = new ImplementacaoDeReferencia(registro);
        // recuperado.obtemIds() --a implementação recupera os ids,
        // -- caso o registro não tenha sido rearranjado, definem-se os ids de acordo com os ids armazenados no cabeçalho (+ espaço)
        // -- ou pulando a partir do id inicial (+ espaço + processamento),
        // -- caso o registro tenha sido rearranjado (ordenado conforme a árvore do MR) percorre-se o registro a partir da raiz, definem-se
        // -- os ids como os inicios dos objetos e se avança conforme os tipos (tamanhos conhecidos) ou tamanhos armazenados
        // -- também podem ser retornados os tipos em um hashmap(útil para a busca polimórfica),
        // -- nesse caso a proxima linha pode ser desconsiderada. Agora se conhecem os dados.
        // byte tipoRaiz = recuperado.obtemTipo(RAIZ); em que RAIZ = 0 se rearranjado c.c. int RAIZ = recuperado.obtemRaiz(). Se os tipos
        // -- já não foram recuperado, agora se conhece a estrutura, mas esse método
        // -- também seria útil para conhecer a estrutura de diferentes registros (apenas ele é chamado)
        // public void remontaGrafoTest()  --deveria ser feito na implementação passando o campo específico que se quer  assetTrue(recuperado.obtemBoolean(raiz, 'COMPOSITION', 'COMPOSITION_CONTENT', X, 'EVALUATION', ..., 'DV_BOOLEAN_VALUE'))
        // if (tipoRaiz == DV_BOOLEAN) assertTrue(recuperado.obtemLogico(raiz, 0));
        // else if (tipoRaiz == COMPOSITION){
        //      int id1 = obtemId(raiz, 'COMPOSITION_CONTENT'); -- id e campo. ids podem ser byte[] usando Referencia.IntToByteArray()
        //      int t = obtemTamanho(id1);
        //      byte tipoContentItem;
        //      if (t >= X){
        //          tipoContentItem = obtemTipoLista(id1, X);
        //          if (tipoContentItem == EVALUATION){
        //              id2 = obtemId(id1, 'EVALUATION_DATA') ....
        //          }
        //      }
        //
        //
        //      assertTrue(recuperado.obtemString(raiz, '' 0)
        // }
        // assertTrue(recuperado.obtemLogico(raiz, 0));
        ModeloDeReferenciaParcial recuperado = new ImplementacaoDeReferencia(registro);
        int raiz = recuperado.obtemRaiz();
        assertTrue(recuperado.obtemLogico(raiz, 0));
    }

    @Test
    public void testDvParagraphUmUnicoDvText() {

        // DV_TEXT sem TERM_MAPPING
        // -- DV_TEXT pode ser ser dv_coded_text
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