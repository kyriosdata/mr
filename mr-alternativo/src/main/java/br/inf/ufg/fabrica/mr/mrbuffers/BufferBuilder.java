/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.mrbuffers;

import java.nio.charset.Charset;

/**
 *
 */
public interface BufferBuilder {

    Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    /**
     * Prepare to write an element of `size` after `additional_bytes`
     * have been written.
     *
     * @param size             This is the of the new element to write.
     * @param additional_bytes The padding size.
     */
    void prep(int size, int additional_bytes);

    /**
     * Offset relative to the end of the buffer.
     *
     * @return Offset relative to the end of the buffer.
     */
    int offset();

    /**
     * Add a `boolean` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `boolean` to put into the buffer.
     */
    void addBoolean(boolean x);

    /**
     * Add a `byte` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `byte` to put into the buffer.
     */
    void addByte(byte x);

    /**
     * Add a `short` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `short` to put into the buffer.
     */
    void addShort(short x);

    /**
     * Add an `int` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x An `int` to put into the buffer.
     */
    int addInt(int x);

    /**
     * Add a `long` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `long` to put into the buffer.
     */
    void addLong(long x);

    /**
     * Add a `float` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `float` to put into the buffer.
     */
    void addFloat(float x);

    /**
     * Add a `double` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `double` to put into the buffer.
     */
    void addDouble(double x);

    /**
     * Add a `char` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `char` to put into the buffer.
     */
    void addChar(char x);

    /**
     * Add a `type` to the buffer.
     *
     * @param x An `int` to put into the buffer.
     */
    int addType(int x);

    int addRef(int parent, int index, int position);

    int addByteArray(byte[] arr);

    void putBoolean(boolean x);

    void putByte(byte x);

    void putShort(short x);

    void putInt(int x);

    void putLong(long x);

    void putFloat(float x);

    void putDouble(double x);

    void putChar(char x);

    int putType(int x);

    /**
     * Encode the string `s` in the buffer using UTF-8.
     *
     * @param s The string to encode.
     * @return The offset in the buffer where the encoded string starts.
     */
    int createString(String s);

    /**
     * Build buffer to vector of bytes
     *
     * @return
     */
    byte[] build();
}
