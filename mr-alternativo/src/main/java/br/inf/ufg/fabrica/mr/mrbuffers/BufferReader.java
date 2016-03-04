/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.mrbuffers;

import br.inf.ufg.fabrica.mr.Referencia;

import java.nio.ByteBuffer;

public abstract class BufferReader {

    ByteBuffer buffer;

    public BufferReader(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public int getType(int x) {
        return Referencia.getByte(buffer.get(x));
    }

    public abstract int getRef(int id, int field);

    public abstract byte getByte(int id, int field);
//    byte nextByte(int id);

    public abstract char getChar(int id, int field);
//    char nextChar(int id);

    public abstract boolean getBoolean(int id, int field);
//    boolean nextBoolean(int index);

    public abstract int getInt(int id, int field);
//    int nextInt(int id);

    public abstract float getFloat(int id, int field);
//    float nextFloat(int id);

    public abstract double getDouble(int id, int field);
//    double nextDouble(int id);

    public abstract long getLong(int id, int field);
//    long nextLog(int id);

    public abstract String getString(int id, int field);
//    String nextString(int id);
    public abstract int getStringLength(int x);

    public abstract int getVector(int id, int field);
}
