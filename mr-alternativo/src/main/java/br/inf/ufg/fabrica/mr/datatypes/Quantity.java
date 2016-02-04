/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 *
 */
public interface Quantity {

    /**
     *
     * @param normalStatusCodePhrase {@code CODE_PHRASE}
     * @param symbolDvCodedText       {@code DV_CODED_TEXT}
     * @param value
     * @return
     */
    int adicionaDvOrdinal(
            int otherReferenceRanges,
            int normalRange,
            String normalStatusCodePhrase,
            int value,
            int symbolDvCodedText);

}
