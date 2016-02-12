/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.mrbuffers;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MrBufferBuilder {

    // Header

    ByteBuffer bb;                  // Where we construct the FlatBuffer.
    int space;                      // Remaining space in the ByteBuffer.
    int minalign = 1;               // Minimum alignment encountered so far.

    /**
     * Start with a buffer of size `initial_size`, then grow as required.
     *
     * @param initial_size The initial size of the internal buffer to use.
     */
    public MrBufferBuilder(int initial_size) {
        if (initial_size <= 0) initial_size = 1;
        space = initial_size;
        bb = newByteBuffer(initial_size);
    }

    /**
     * Create a `ByteBuffer` with a given capacity.
     *
     * @param capacity The size of the `ByteBuffer` to allocate.
     * @return Returns the new `ByteBuffer` that was allocated.
     */
    static ByteBuffer newByteBuffer(int capacity) {
        ByteBuffer newbb = ByteBuffer.allocate(capacity);
        newbb.order(ByteOrder.LITTLE_ENDIAN);
        return newbb;
    }

    /**
     * Doubles the size of the backing {@link ByteBuffer} and copies the old data towards the
     * end of the new buffer (since we build the buffer backwards).
     *
     * @param bb The current buffer with the existing data.
     * @return A new byte buffer with the old data copied copied to it.  The data is
     * located at the end of the buffer.
     */
    static ByteBuffer growByteBuffer(ByteBuffer bb) {
        int old_buf_size = bb.capacity();
        if ((old_buf_size & 0xC0000000) != 0)  // Ensure we don't grow beyond what fits in an int.
            throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
        int new_buf_size = old_buf_size << 1;
        bb.position(0);
        ByteBuffer nbb = newByteBuffer(new_buf_size);
        nbb.position(new_buf_size - old_buf_size);
        nbb.put(bb);
        return nbb;
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
        // Track the biggest thing we've ever aligned to.
        if (size > minalign) minalign = size;
        // Find the amount of alignment needed such that `size` is properly
        // aligned after `additional_bytes`
        int align_size = ((~(bb.capacity() - space + additional_bytes)) + 1) & (size - 1);
        // Reallocate the buffer if needed.
        while (space < align_size + size + additional_bytes) {
            int old_buf_size = bb.capacity();
            bb = growByteBuffer(bb);
            space += bb.capacity() - old_buf_size;
        }
        System.out.println("capacity " + bb.capacity() + " space " + space + " minalign " + minalign + " align_size " + align_size);
//        pad(align_size);
    }

    /**
     * Offset relative to the end of the buffer.
     *
     * @return Offset relative to the end of the buffer.
     */
    public int offset() {
        return bb.capacity() - space;
    }

    /**
     * Add zero valued bytes to prepare a new entry to be added.
     *
     * @param byte_size Number of bytes to add.
     */
    public void pad(int byte_size) {
        for (int i = 0; i < byte_size; i++) bb.put(--space, (byte) 0);
    }

    /**
     * Add a `boolean` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `boolean` to put into the buffer.
     */
    public void addBoolean(boolean x) { prep(1, 0); putBoolean(x); }

    /**
     * Add a `byte` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `byte` to put into the buffer.
     */
    public void addByte   (byte    x) { prep(1, 0); putByte(x); }

    /**
     * Add a `short` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `short` to put into the buffer.
     */
    public void addShort  (short   x) { prep(2, 0); putShort(x); }

    /**
     * Add an `int` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x An `int` to put into the buffer.
     */
    public void addInt    (int     x) { prep(4, 0); putInt    (x); }

    /**
     * Add a `long` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `long` to put into the buffer.
     */
    public void addLong   (long    x) { prep(8, 0); putLong   (x); }

    /**
     * Add a `float` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `float` to put into the buffer.
     */
    public void addFloat  (float   x) { prep(4, 0); putFloat(x); }

    /**
     * Add a `double` to the buffer, properly aligned, and grows the buffer (if necessary).
     *
     * @param x A `double` to put into the buffer.
     */
    public void addDouble (double  x) { prep(8, 0); putDouble(x); }

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
        bb.put(space -= 1, (byte) (x ? 1 : 0));
    }

    /**
     * Add a `byte` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `byte` to put into the buffer.
     */
    public void putByte(byte x) {
        bb.put(space -= 1, x);
    }

    /**
     * Add a `short` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `short` to put into the buffer.
     */
    public void putShort(short x) {
        bb.putShort(space -= 2, x);
    }

    /**
     * Add an `int` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x An `int` to put into the buffer.
     */
    public void putInt(int x) {
        bb.putInt(space -= 4, x);
    }

    /**
     * Add a `long` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `long` to put into the buffer.
     */
    public void putLong(long x) {
        bb.putLong(space -= 8, x);
    }

    /**
     * Add a `float` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `float` to put into the buffer.
     */
    public void putFloat(float x) {
        bb.putFloat(space -= 4, x);
    }

    /**
     * Add a `double` to the buffer, backwards from the current location. Doesn't align nor
     * check for space.
     *
     * @param x A `double` to put into the buffer.
     */
    public void putDouble(double x) {
        bb.putDouble(space -= 8, x);
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
        int index = space -= 1;
        bb.put(index, (byte) x);
        return index;
    }

    /**
     * Get the ByteBuffer representing the MrBufferBuilder.
     */
    public ByteBuffer dataBuffer() {
        return bb;
    }
}
