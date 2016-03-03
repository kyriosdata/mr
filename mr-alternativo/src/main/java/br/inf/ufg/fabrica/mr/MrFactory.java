/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import br.inf.ufg.fabrica.mr.impl.MrImpl;

public class MrFactory {

    public Mr getMr() {
        return MrImpl.getInstance();
    }
}
