// automatically generated, do not modify

package com.google.flatbuffers.mr.data_types.uri;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class DvEhrUri extends Table {
    public static DvEhrUri getRootAsDvEhrUri(ByteBuffer _bb) {
        return getRootAsDvEhrUri(_bb, new DvEhrUri());
    }

    public static DvEhrUri getRootAsDvEhrUri(ByteBuffer _bb, DvEhrUri obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createDvEhrUri(FlatBufferBuilder builder,
                                     int valueOffset) {
        builder.startObject(1);
        DvEhrUri.addValue(builder, valueOffset);
        return DvEhrUri.endDvEhrUri(builder);
    }

    public static void startDvEhrUri(FlatBufferBuilder builder) {
        builder.startObject(1);
    }

    public static void addValue(FlatBufferBuilder builder, int valueOffset) {
        builder.addOffset(0, valueOffset, 0);
    }

    public static int endDvEhrUri(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public DvEhrUri __init(int _i, ByteBuffer _bb) {
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

