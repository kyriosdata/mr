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
     * @param value
     * @param mappings           {@code TERM_MAPPING}
     * @param formatting
     * @param hyperlink          {@code DV_URI}
     * @param codePhraseLanguage {@code CODE_PHRASE}
     * @param codePhraseEncoding {@code CODE_PHRASE}
     * @return identificador único da exoressão de texto na extrutura.
     */
    int adicionaDvText(
            String value, String hyperlink, String formatting,
            int mappings, String codePhraseLanguage, String codePhraseEncoding);

    /**
     * Adiciona um código
     * - {@code CODE_PHRASE}.
     * @param value
     * @return O identificador único do código na estrutura.
     */
    int adicionaCodePhrase(String value);

    /**
     * @param value
     * @param mappings           {@code TERM_MAPPING}
     * @param formatting
     * @param hyperlink          {@code DV_URI}
     * @param codePhraseLanguage {@code CODE_PHRASE}
     * @param codePhraseEncoding {@code CODE_PHRASE}
     * @param definingCode
     * @return identificador único da exoressão de texto na extrutura.
     * @see #adicionaDvText(String, String, String, int, String, String)
     * @see #adicionaCodePhrase(String)
     */
    int adicionaDvCodedText(
            String value, String hyperlink, String formatting,
            int mappings, String codePhraseLanguage, String codePhraseEncoding, int definingCode);

    /**
     * TODO: como tratar referencias e valores nulos? - ocupar o espaço nulo com bytes vazios
     *
     * @param target  O termo alvo do mapeamento {@code CODE_PHRASE}.
     * @param match   Operador de equivalencia entre os termos.
     * @param purpose Finalidade do mapemento {@code DV_CODED_TEXT}. Ex: "automated", "data mining", "interoperability".
     * @return
     * @see #adicionaCodePhrase(String)
     * @see #adicionaDvCodedText(String, String, String, int, String, String, int)
     */
    int adicionaTermMapping(int target, char match, int purpose);
}