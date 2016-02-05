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

  @Test
  public void testAdicionaDvBoolean() throws Exception {
    new BasicImpl().adicionaDvBoolean(true);
  }

  @Test
  public void testAdicionaDvIdentifier() throws Exception {
    new BasicImpl().adicionaDvIdentifier(
        "abcd",
        "b",
        "c",
        "d"
    );
  }

  @Test
  public void testAdicionaDvState() throws Exception {
    new BasicImpl().adicionaDvState(2000450, true);
  }
}