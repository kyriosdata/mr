/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.common;

public interface Archetyped {

    /**
     * Adiciona um archetyped
     * ({@code ARCHETYPED}).
     *
     * @param archetypeId Globally unique archetype identifier.
     * @param templateId Globally unique template identifier.
     * @param rmVersion Version of the openEHR reference model used to create this object.
     *
     * @return O identificador único do identificador de terminologia na estrutura.
     */
    int adicionaArchetyped(String archetypeId, String templateId, String rmVersion);

}
