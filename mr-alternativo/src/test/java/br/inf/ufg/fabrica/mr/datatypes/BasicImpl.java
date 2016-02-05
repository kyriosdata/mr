/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.datatypes;

import br.inf.ufg.fabrica.mr.Mr;
import br.inf.ufg.fabrica.mr.Referencia;
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
    // alloc class identifier
    b.capacity(b.capacity() + Referencia.totalBytes(Mr.DV_IDENTIFIER));

    // Class identifier
    b.writeByte(Mr.DV_IDENTIFIER);

    // Issuer
    b.capacity(b.capacity() + Referencia.totalBytes(Mr.STRING) + Referencia.totalBytes(issuer.length()) + issuer.length());
    b.writeByte(Mr.STRING);
    b.writeByte(issuer.length());
    b.writeBytes(issuer.getBytes());
    // Assigner
    b.capacity(b.capacity() + Referencia.totalBytes(Mr.STRING) + Referencia.totalBytes(assigner.length()) + assigner.length());
    b.writeByte(Mr.STRING);
    b.writeByte(assigner.length());
    b.writeBytes(assigner.getBytes());
    // Id
    b.capacity(b.capacity() + Referencia.totalBytes(Mr.STRING) + Referencia.totalBytes(id.length()) + id.length());
    b.writeByte(Mr.STRING);
    b.writeByte(id.length());
    b.writeBytes(id.getBytes());
    // Type
    b.capacity(b.capacity() + Referencia.totalBytes(Mr.STRING) + Referencia.totalBytes(type.length()) + type.length());
    b.writeByte(Mr.STRING);
    b.writeByte(type.length());
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
