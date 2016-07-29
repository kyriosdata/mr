/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

/**
 *
 */
public interface Encapsulated {

    /**
     * Adiciona dado codificado
     * - {@code DV_MULTIMEDIA}.
     *
     * @param codePhraseCharSet                 A codificação empregada.
     * @param codePhraseLanguage                A linguagem empregada.
     * @param alternateText                     Texto alternativo para os dados.
     * @param codePhraseMediaType               A codificação do tipo de mídia.
     * @param codePhraseCompressionAlgorithm    O algoritmo de compressão empregado.
     * @param integrityCheck                    A sequência de bytes que serve para verificar a integridade dos dados.
     * @param codePhraseIntegrityCheckAlgorithm O algoritmo de verificação de integridade dos dados.
     * @param hDvMultimediaThumbnail            O identificador único de dados codificados que serve como representação
     *                                          comprimida do presente dado codificado.
     * @param dvUri                             Sequência de caracteres que é a URI do dado codificado.
     * @param data                              O dado codificado propriamente dito.
     * @return O identificador únido do dado codificado.
     */
    int adicionaDvMultimedia(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String alternateText,
            String codePhraseMediaType,
            String codePhraseCompressionAlgorithm,
            byte[] integrityCheck,
            String codePhraseIntegrityCheckAlgorithm,
            int hDvMultimediaThumbnail,
            String dvUri,
            byte[] data);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * - {@code DV_PARSABLE}.
     *
     * @param codePhraseCharSet  A codificação empregada pelo dado encapsulado.
     * @param codePhraseLanguage A linguagem empregada pelo dado encapsulado.
     * @param value              O dado encapsulado propriamente dito.
     * @param formalism          O formalismo empregado pelo dado encapsulado.
     * @return O identificador único do dado encapsulado na estrutura.
     */
    int adicionaDvParsable(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String value,
            String formalism);
}
