/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 *
 */
public interface Basic {

    /**
     * Adiciona um valor lógico
     * - {@code DV_BOOLEAN}.
     *
     * @param valor Valor lógico (DV_BOOLEAN) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     */
    int adicionaDvBoolean(boolean valor);

    /**
     * Adiciona um identificador
     * - {@code DV_IDENTIFIER}.
     *
     * @param issuer   Entidade que emite identificação.
     * @param assigner Entidade que assina identificação.
     * @param id       Identificador propriamente dito.
     * @param type     Tipo da identificação.
     * @return O identificador único deste identificador na estrutura.
     */
    int adicionaDvIdentifier(String issuer, String assigner, String id, String type);


    /**
     * Adiciona um valor de estado
     * - {@code DV_STATE}.
     *
     * @param value    {@code DV_CODED_TEXT}
     * @param terminal
     * @return
     * @see Text#adicionaDvCodedText(String, String, String, int, String, String, int)
     */
    int adicionaDvState(int value, boolean terminal);
}
