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
   * ({@code DV_PARAGRAPH }).
   *
   * @param itemsList A sequência de {@code DV_TEXT}s
   *
   * @return O identificador único do parágrafo na estrutura.
   */
  int adicionaDvParagraph(int itemsList);

  /**
   * Adiciona uma expressão de texto com qualquer quantidade de caracteres podendo formar palavras,
   * sentenças e etc. Formatação visual e hiperlinks podem ser incluídos {@code DV_TEXT}.
   *
   * @param value
   * @param mappings   {@code TERM_MAPPING}
   * @param formatting
   * @param hyperlink  {@code DV_URI}
   * @param language   {@code CODE_PHRASE}
   * @param encoding   {@code CODE_PHRASE}
   *
   * @return identificador único da exoressão de texto na extrutura
   */
  int adicionaDvText(
      String value, String hyperlink, String formatting,
      int mappings, int language, int encoding);

  /**
   * Adiciona um código ({@code CODE_PHRASE}).
   *
   * @param terminologyId Um identificador de terminologia.
   * @param codeString    A sequência correspondente ao código.
   *
   * @return O identificador único do código na estrutura.
   */
  int adicionaCodePhrase(int terminologyId, String codeString);

  /**
   *
   * @param dvText     {@code DV_TEXT}
   * @param codePharse {@code CODE_PHRASE}
   *
   * @return identificador único da exoressão de texto na extrutura
   *
   * @see #adicionaDvText(String, String, String, int, int, int)
   * @see #adicionaCodePhrase(int, String)
   */
  int adicionaDvCodedText(int dvText, int codePharse);

  /**
   *
   *
   * @param target  O termo alvo do mapeamento {@code CODE_PHRASE}
   * @param match
   * @param purpose Finalidade do mapemento {@code DV_CODED_TEXT}.
   *                Ex: "automated", "data mining", "interoperability".
   *
   * @return
   *
   * @see  #adicionaCodePhrase(int, String)
   * @see  #adicionaDvCodedText(int, int)
   */
  int adicionaTermMapping(int target, char match, int purpose);

}
