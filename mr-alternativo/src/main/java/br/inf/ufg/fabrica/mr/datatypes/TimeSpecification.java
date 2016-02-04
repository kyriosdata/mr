/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 *
 */
public interface TimeSpecification {

    /**
     * Especifica pontos no tempo numa sintaxe geral
     * - {@code DV_GENERAL_TIME_SPECIFICATION}.
     *
     * @param codePhraseCharSet  A codificação empregada pelo dado encapsulado.
     * @param codePhraseLanguage A linguagem empregada pelo dado encapsulado.
     * @param value              O dado encapsulado propriamente dito.
     * @param formalism          O formalismo empregado pelo dado encapsulado.
     * @return O identificador único na estrutura.
     */
    int adicionaDvGeneralTimeSpecification(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String value,
            String formalism);

    /**
     * Especifica pontos periódicos no tempo, ligadas ao calendário ou a uma repetição de eventos do mundo real
     * - {@code DV_PERIODIC_TIME_SPECIFICATION}.
     *
     * @param codePhraseCharSet  A codificação empregada pelo dado encapsulado.
     * @param codePhraseLanguage A linguagem empregada pelo dado encapsulado.
     * @param value              O dado encapsulado propriamente dito.
     * @param formalism          O formalismo empregado pelo dado encapsulado.
     * @return O identificador único na estrutura.
     */
    int adicionaDvPeriodicTimeSpecification(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String value,
            String formalism);
}
