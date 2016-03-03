/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.mrbuffers;

import java.nio.ByteBuffer;

public class MrBufferReader extends BufferReader {

    public MrBufferReader(ByteBuffer buffer) {
        super(buffer);
    }

    public int getRef(int id, int field) {
        return 0;
    }

    public byte getByte(int id, int field) {
        return 0;
    }

    public char getChar(int id, int field) {
        return 0;
    }

    public boolean getBoolean(int id, int field) {
        return false;
    }

    public int getInt(int id, int field) {
        return 0;
    }

    public float getFloat(int id, int field) {
        return 0;
    }

    public double getDouble(int id, int field) {
        return 0;
    }

    public long getLong(int id, int field) {
        return 0;
    }

    public String getString(int id, int field) {
        return null;
    }

    public int getStringLength(int x) {
        return 0;
    }

    public int getVector(int id, int field) {
        return 0;
    }
}
