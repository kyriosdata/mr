/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

import br.inf.ufg.fabrica.mr.Mr;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;

/**
 */
public class BasicImpl implements Basic {

  private ByteBuf b;

  public BasicImpl() {
    b = Unpooled.buffer(0);
  }

  public int adicionaDvBoolean(boolean valor) {
    b.capacity(b.capacity() + 2);

    b.writeByte(Mr.DV_BOOLEAN);
    b.writeByte((byte) (valor ? 1 : 0));

    return b.capacity();
  }

  public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
    b.capacity(b.capacity() + 5);

    b.writeByte(Mr.DV_IDENTIFIER);
    b.writeBytes(issuer.getBytes());
    b.writeBytes(assigner.getBytes());
    b.writeBytes(id.getBytes());
    b.writeBytes(type.getBytes());

    return b.capacity();
  }

  public int adicionaDvState(int value, boolean terminal) {
    b.capacity(b.capacity() + 3);

    b.writeByte(Mr.DV_STATE);
    b.writeByte((byte) value);
    b.writeByte((byte)(terminal?1:0));

    return b.capacity();
  }
}
