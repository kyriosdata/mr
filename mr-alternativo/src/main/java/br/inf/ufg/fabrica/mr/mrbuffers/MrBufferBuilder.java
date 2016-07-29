/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.mrbuffers;


import br.inf.ufg.fabrica.mr.Mr;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;

public class MrBufferBuilder implements BufferBuilder {

    private ByteBuf bb;

    /**
     * Start with a buffer of size `initial_size`, then grow as required.
     *
     * @param initial_size The initial size of the internal buffer to use.
     */
    public MrBufferBuilder(int initial_size) {
        if (initial_size <= 0) initial_size = 1;
        bb = newByteBuffer(initial_size);
    }

    /**
     * Create a `ByteBuffer` with a given capacity.
     *
     * @param capacity The size of the `ByteBuffer` to allocate.
     * @return Returns the new `ByteBuffer` that was allocated.
     */
    static ByteBuf newByteBuffer(int capacity) {
        ByteBuf newbb = Unpooled.buffer(capacity);
        newbb.order(ByteOrder.LITTLE_ENDIAN);
        return newbb;
    }

    public void prep(int size, int additional_bytes) {
        bb.capacity(bb.capacity() + size + additional_bytes);
    }

    public int offset() {
        return bb.writerIndex();
    }

    public void addBoolean(boolean x) {
        prep(Mr.BOOLEAN_SIZE, 0);
        putBoolean(x);
    }

    public void addByte(byte x) {
        prep(Mr.BYTE_SIZE, 0);
        putByte(x);
    }

    public void addShort(short x) {
        prep(Mr.SHORT_SIZE, 0);
        putShort(x);
    }

    public int addInt(int x) {
        int id = offset();
        prep(Mr.INT_SIZE, 0);
        putInt(x);
        return id;
    }

    public void addLong(long x) {
        prep(Mr.LONG_SIZE, 0);
        putLong(x);
    }

    public void addFloat(float x) {
        prep(Mr.FLOAT_SIZE, 0);
        putFloat(x);
    }

    public void addDouble(double x) {
        prep(Mr.DOUBLE_SIZE, 0);
        putDouble(x);
    }

    public void addChar(char x) {
        prep(Mr.CHAR_SIZE, 0);
        putChar(x);
    }

    public int addType(int x) {
        prep(Mr.TYPE_SIZE, 0);
        return putType(x);
    }

    public int addRef(int parent, int index, int position) {
        prep(Mr.REF_SIZE, 0);
        int ref = parent - index + position;
        bb.writeByte(ref);
        return ref;
    }

    public int addByteArray(byte[] arr) {
        int id = offset();
        addInt(arr.length);
        prep(arr.length, 0);
        bb.writeBytes(arr);
        return id;
    }

    public void putBoolean(boolean x) {
        bb.writeBoolean(x);
    }

    public void putByte(byte x) {
        bb.writeByte(x);
    }

    public void putShort(short x) {
        bb.writeShort(x);
    }

    public void putInt(int x) {
        bb.writeInt(x);
    }

    public void putLong(long x) {
        bb.writeLong(x);
    }

    public void putFloat(float x) {
        bb.writeFloat(x);
    }

    public void putDouble(double x) {
        bb.writeDouble(x);
    }

    public void putChar(char x) {
        bb.writeChar(x);
    }

    public int putType(int x) {
        if (x < 0 || x > 156)
            throw new IllegalArgumentException("The value must be an object type valid. Between 0 and 156");

        int index = offset();
        bb.writeByte((byte) x);
        return index;
    }

    public int createString(String s) {
        byte[] utf8 = s.getBytes(UTF_8_CHARSET);
        return addByteArray(utf8);
    }

    public byte[] build() {
        return bb.array();
    }

    /**
     * Get the ByteBuffer representing the MrBufferBuilder.
     */
    public ByteBuf dataBuffer() {
        return bb;
    }
}
