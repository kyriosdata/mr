/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.mrbuffers;


import br.inf.ufg.fabrica.mr.Referencia;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

public class MrBufferBuilder {

    // Header

    static final Charset utf8charset = Charset.forName("UTF-8"); // The UTF-8 character set used by FlatBuffers.
    ByteBuf bb;                  // Where we construct the FlatBuffer.
    ByteBuf bbVector;

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

    /**
     * Prepare to write an element of `size` after `additional_bytes`
     * have been written, e.g. if you write a string, you need to align such
     * the int length field is aligned to {@link com.google.flatbuffers.Constants#SIZEOF_INT}, and
     * the string data follows it directly.  If all you need to do is alignment, `additional_bytes`
     * will be 0.
     *
     * @param size             This is the of the new element to write.
     * @param additional_bytes The padding size.
     */
    public void prep(int size, int additional_bytes) {
        bb.capacity(bb.capacity() + size + additional_bytes);
    }

    /**
     * Offset relative to the end of the buffer.
     *
     * @return Offset relative to the end of the buffer.
     */
    public int offset() {
        return bb.writerIndex();
    }

    /**
     * Add a `boolean` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `boolean` to put into the buffer.
     */
    public void addBoolean(boolean x) {
        prep(1, 0);
        putBoolean(x);
    }

    /**
     * Add a `byte` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `byte` to put into the buffer.
     */
    public void addByte(byte x) {
        prep(1, 0);
        putByte(x);
    }

    /**
     * Add a `short` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `short` to put into the buffer.
     */
    public void addShort(short x) {
        prep(2, 0);
        putShort(x);
    }

    /**
     * Add an `int` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x An `int` to put into the buffer.
     */
    public void addInt(int x) {
        prep(4, 0);
        putInt(x);
    }

    /**
     * Add a `long` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `long` to put into the buffer.
     */
    public void addLong(long x) {
        prep(8, 0);
        putLong(x);
    }

    /**
     * Add a `float` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `float` to put into the buffer.
     */
    public void addFloat(float x) {
        prep(4, 0);
        putFloat(x);
    }

    /**
     * Add a `double` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `double` to put into the buffer.
     */
    public void addDouble(double x) {
        prep(8, 0);
        putDouble(x);
    }

    /**
     * Add a `type` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x An `int` to put into the buffer.
     */
    public int addType(int x) {
        if (x >= 0x3F) {
            throw new IllegalArgumentException("The value must be an integer of 1 byte");
        }
        prep(1, 0);
        return putType(x);
    }

    /**
     * Add a `boolean` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `boolean` to put into the buffer.
     */
    public void putBoolean(boolean x) {
        bb.writeBoolean(x);
    }

    /**
     * Add a `byte` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `byte` to put into the buffer.
     */
    public void putByte(byte x) {
        bb.writeByte(x);
    }

    /**
     * Add a `short` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `short` to put into the buffer.
     */
    public void putShort(short x) {
        bb.writeShort(x);
    }

    /**
     * Add an `int` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x An `int` to put into the buffer.
     */
    public void putInt(int x) {
        //bb.writeInt(x);
        bb.writeBytes(Referencia.intToByteArray(x));
        
    }

    /**
     * Add a `long` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `long` to put into the buffer.
     */
    public void putLong(long x) {
        //bb.writeLong(x);
        bb.writeBytes(Referencia.longToByteArray(x));
    }

    /**
     * Add a `float` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `float` to put into the buffer.
     */
    public void putFloat(float x) {
        bb.writeFloat(x);
    }

    /**
     * Add a `double` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `double` to put into the buffer.
     */
    public void putDouble(double x) {
        bb.writeDouble(x);
    }

    /**
     * Add an `int` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x An `int` to put into the buffer.
     */
    public int putType(int x) {
        if (x >= 0x3F) {
            throw new IllegalArgumentException("The value must be an integer of 1 byte");
        }
        int index = offset();
        bb.writeByte((byte) x);
        return index;
    }

    /**
     * Encode the string `s` in the buffer using UTF-8.
     *
     * @param s The string to encode.
     * @return The offset in the buffer where the encoded string starts.
     */
    public int createString(String s) {
        byte[] utf8 = s.getBytes(utf8charset);
        int id = addType(20);
        addInt(utf8.length); // size
        prep(utf8.length, 0);
        bb.writeBytes(utf8);
        return id;
    }

    /**
     * Get the ByteBuffer representing the MrBufferBuilder.
     */
    public ByteBuf dataBuffer() {
        return bb;
    }
}
