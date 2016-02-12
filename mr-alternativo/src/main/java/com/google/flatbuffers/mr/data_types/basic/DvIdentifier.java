// automatically generated, do not modify

package com.google.flatbuffers.mr.data_types.basic;

import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class DvIdentifier extends Table {
    public static DvIdentifier getRootAsDvIdentifier(ByteBuffer _bb) {
        return getRootAsDvIdentifier(_bb, new DvIdentifier());
    }

    public static DvIdentifier getRootAsDvIdentifier(ByteBuffer _bb, DvIdentifier obj) {
        _bb.order(ByteOrder.LITTLE_ENDIAN);
        return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb));
    }

    public static int createDvIdentifier(FlatBufferBuilder builder,
                                         int issuerOffset,
                                         int assignerOffset,
                                         int idOffset,
                                         int typeOffset) {
        builder.startObject(4);
        DvIdentifier.addType(builder, typeOffset);
        DvIdentifier.addId(builder, idOffset);
        DvIdentifier.addAssigner(builder, assignerOffset);
        DvIdentifier.addIssuer(builder, issuerOffset);
        return DvIdentifier.endDvIdentifier(builder);
    }

    public static void startDvIdentifier(FlatBufferBuilder builder) {
        builder.startObject(4);
    }

    public static void addIssuer(FlatBufferBuilder builder, int issuerOffset) {
        builder.addOffset(0, issuerOffset, 0);
    }

    public static void addAssigner(FlatBufferBuilder builder, int assignerOffset) {
        builder.addOffset(1, assignerOffset, 0);
    }

    public static void addId(FlatBufferBuilder builder, int idOffset) {
        builder.addOffset(2, idOffset, 0);
    }

    public static void addType(FlatBufferBuilder builder, int typeOffset) {
            builder.addOffset(3, typeOffset, 0);
    }

    public static int endDvIdentifier(FlatBufferBuilder builder) {
        int o = builder.endObject();
        return o;
    }

    public DvIdentifier __init(int _i, ByteBuffer _bb) {
        bb_pos = _i;
        bb = _bb;
        return this;
    }

    public String issuer() {
        int o = __offset(4);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer issuerAsByteBuffer() {
        return __vector_as_bytebuffer(4, 1);
    }

    public String assigner() {
        int o = __offset(6);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer assignerAsByteBuffer() {
        return __vector_as_bytebuffer(6, 1);
    }

    public String id() {
        int o = __offset(8);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer idAsByteBuffer() {
        return __vector_as_bytebuffer(8, 1);
    }

    public String type() {
        int o = __offset(10);
        return o != 0 ? __string(o + bb_pos) : null;
    }

    public ByteBuffer typeAsByteBuffer() {
        return __vector_as_bytebuffer(10, 1);
    }
}

