/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import br.inf.ufg.fabrica.mr.Mr;

public class MrTestUtil {

    protected int createListTermMapping(Mr mr, int count) {
        int[] items = new int[count];
        mr.startVector(items.length);
        for (int i = 0; i < items.length; i++) {
            items[i] = mr.adicionaTermMapping(mr.adicionaCodePhrase("centc251::nnnnnnn"), '>', createDvCodedText(mr));
        }
        return mr.endVector(items);
    }

    protected int createDvCodedText(Mr mr) {
        return mr.adicionaDvCodedText(
                mr.adicionaDvUri("http://google.com.br"),
                mr.adicionaCodePhrase("centc251::nnnnnnn"),
                mr.adicionaCodePhrase("centc252::nnnnnnn"),
                mr.adicionaCodePhrase("centc253::nnnnnnn"),
                18, "a", "b"
        );
    }
}
