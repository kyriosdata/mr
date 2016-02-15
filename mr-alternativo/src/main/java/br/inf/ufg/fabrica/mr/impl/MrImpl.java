/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import br.inf.ufg.fabrica.mr.Mr;
import br.inf.ufg.fabrica.mr.datatypes.Text;
import br.inf.ufg.fabrica.mr.mrbuffers.MrBufferBuilder;

/**
 * Implementação do modelo de referência.
 */
public class MrImpl implements Mr {

    MrBufferBuilder bufferBuilder;
    MrBufferBuilder vectorBB;

    public MrImpl(int initial_size) {
        this.bufferBuilder = new MrBufferBuilder(initial_size);
        this.vectorBB = new MrBufferBuilder(initial_size);
    }

    public MrImpl() {
        this(1);
    }

    public MrBufferBuilder getBufferBuilder() {
        return bufferBuilder;
    }

    public MrBufferBuilder getVectorBB() {
        return vectorBB;
    }

    /**
     * Adiciona um valor lógico
     * - {@code DV_BOOLEAN}.
     *
     * @param valor Valor lógico (DV_BOOLEAN) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     */
    public int adicionaDvBoolean(boolean valor) {
        int id = bufferBuilder.addType(DV_BOOLEAN);
        bufferBuilder.addBoolean(valor);
        return id;
    }

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
    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        int i = bufferBuilder.addType(DV_IDENTIFIER);
        bufferBuilder.addInt(vectorBB.createString(issuer));
        bufferBuilder.addInt(vectorBB.createString(assigner));
        bufferBuilder.addInt(vectorBB.createString(id));
        bufferBuilder.addInt(vectorBB.createString(type));
        return i;
    }

    /**
     * Adiciona um valor de estado
     * - {@code DV_STATE}.
     *
     * @param value    {@code DV_CODED_TEXT}
     * @param terminal
     * @return
     * @see Text#adicionaDvCodedText(String, String, String, int, String, String, int)
     */
    public int adicionaDvState(int value, boolean terminal) {
        int id = bufferBuilder.addType(DV_STATE);
        bufferBuilder.addInt(value);
        bufferBuilder.addBoolean(terminal);
        return id;
    }
}
