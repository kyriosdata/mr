// automatically generated, do not modify

package com.google.flatbuffers.mr.data_types.basic;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Struct;

import java.nio.ByteBuffer;

@SuppressWarnings("unused")
public final class DvBoolean extends Struct {
    public static int createDvBoolean(FlatBufferBuilder builder, boolean value) {
        builder.prep(1, 1);
        builder.putBoolean(value);
        return builder.offset();
    }

    public DvBoolean __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
        return this;
    }

    public boolean value() {
        return 0 != bb.get(bb_pos + 0);
    }
}

