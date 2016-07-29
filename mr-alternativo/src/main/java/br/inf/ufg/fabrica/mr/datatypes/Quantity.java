/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 * TODO: Definir a passagens dos atributos das classes abstratas para as classes concretas.
 */
public interface Quantity {

    /**
     *
     * @param otherReferenceRanges {@code REFERENCE_RANGE}
     * @param normalRange {@code DV_INTERVAL}
     * @param normalStatusCodePhrase {@code CODE_PHRASE}
     * @param value
     * @param symbolDvCodedText {@code DV_CODED_TEXT}
     * @return
     */
    int adicionaDvOrdinal(
            int otherReferenceRanges,
            int normalRange,
            String normalStatusCodePhrase,
            int value,
            int symbolDvCodedText);

    int adicionaDvInterval(
            int lowerOrdered,
            int upperOrdered,
            boolean lowerIncluded,
            boolean upperIncluded);

    int adicionaReferenceRange(
            int lowerOrdered,
            int upperOrdered,
            boolean lowerIncluded,
            boolean upperIncluded,
            String value,
            String hyperlink,
            String formatting,
            int mappings,
            String codePhraseLanguage,
            String codePhraseEncoding);

}
