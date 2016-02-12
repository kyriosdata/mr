// automatically generated, do not modify

package com.google.flatbuffers.mr.data_types.uri;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class DvUri extends Table {
    public static DvUri getRootAsDvUri(ByteBuffer _bb) {
        return getRootAsDvUri(_bb, new DvUri());
    }

    public static DvUri getRootAsDvUri(ByteBuffer _bb, DvUri obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createDvUri(FlatBufferBuilder builder,
                                  int valueOffset) {
        builder.startObject(1);
        DvUri.addValue(builder, valueOffset);
        return DvUri.endDvUri(builder);
    }

    public static void startDvUri(FlatBufferBuilder builder) {
        builder.startObject(1);
    }

    public static void addValue(FlatBufferBuilder builder, int valueOffset) {
        builder.addOffset(0, valueOffset, 0);
    }

    public static int endDvUri(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public DvUri __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
        return this;
    }

    public String value() {
        int o = __offset(4);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer valueAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }
};

