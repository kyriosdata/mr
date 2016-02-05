/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class BasicTest extends TestCase {

    BasicImpl basic;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        basic = new BasicImpl();
    }

    @Test
  public void testAdicionaDvBoolean() throws Exception {
    basic.adicionaDvBoolean(true);
  }

  @Test
  public void testAdicionaDvIdentifier() throws Exception {
      System.out.println(basic.adicionaDvIdentifier(
            "a",
            "b",
            "c",
            "d"
    ));
  }

  @Test
  public void testAdicionaDvState() throws Exception {
    System.out.println(basic.adicionaDvState(2000450, true));
  }
}