/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 *
 */
public interface Text {

    /**
     * Adiciona uma série de textos simples para ser exibido como um parágrafo
     * - {@code DV_PARAGRAPH }.
     *
     * @param itemsList A sequência de {@code DV_TEXT}s.
     * @return O identificador único do parágrafo na estrutura.
     */
    int adicionaDvParagraph(int itemsList);

    /**
     * Adiciona uma expressão de texto com qualquer quantidade de caracteres podendo formar palavras,
     * sentenças e etc. Formatação visual e hiperlinks podem ser incluídos
     * - {@code DV_TEXT}.
     *
     * @param hyperlink  {@code DV_URI}
     * @param language   {@code CODE_PHRASE}
     * @param encoding   {@code CODE_PHRASE}
     * @param mappings   {@code TERM_MAPPING}
     * @param formatting
     * @param value
     * @return identificador único da exoressão de texto na extrutura.
     */
    int adicionaDvText(int hyperlink, int language, int encoding, int mappings, String formatting, String value);

    /**
     * Adiciona um código
     * - {@code CODE_PHRASE}.
     *
     * @param value
     * @return O identificador único do código na estrutura.
     */
    int adicionaCodePhrase(String value);

    /**
     * @param hyperlink    {@code DV_URI}
     * @param language     {@code CODE_PHRASE}
     * @param encoding     {@code CODE_PHRASE}
     * @param definingCode {@code CODE_PHRASE}
     * @param mappings     {@code TERM_MAPPING}
     * @param formatting
     * @param value
     * @return identificador único da exoressão de texto na extrutura.
     * @see #adicionaDvText(int, int, int, int, String, String)
     * @see #adicionaCodePhrase(String)
     */
    int adicionaDvCodedText(
            int hyperlink, int language, int encoding, int definingCode, int mappings, String formatting, String value);

    /**
     * @param target  O termo alvo do mapeamento {@code CODE_PHRASE}.
     * @param match   Operador de equivalencia entre os termos.
     * @param purpose Finalidade do mapemento {@code DV_CODED_TEXT}. Ex: "automated", "data mining", "interoperability".
     * @return
     * @see #adicionaCodePhrase(String)
     * @see #adicionaDvCodedText(int, int, int, int, int, String, String)
     */
    int adicionaTermMapping(int target, char match, int purpose);
}