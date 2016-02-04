/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 *
 */
public interface Uri {

  /**
   * Adiciona um {@link java.net.URI} ({@code DV_URI}).
   *
   * @param uri Sequência de caracteres correspondentes
   *            à {@link java.net.URI}.
   *
   * @return O identificador único desta URI na estrutura.
   */
  int adicionaDvUri(String uri);

  /**
   * Adiciona um {@link java.net.URI} cujo esquema é
   * "ehr" ({@code DvEHRURI}).
   *
   * @param uri Sequência de caracteres correspondentes
   *            à {@link java.net.URI}.
   * @return O identificador único desta DvEHRURI na estrutura.
   */
  int adicionaDvEhrUri(String uri);

}
