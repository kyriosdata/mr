package com.google.flatbuffers.mr.data_types.basic;

import com.google.flatbuffers.FlatBufferBuilder;

public class DvIdentifierTest {

    @org.junit.Test
    public void testId() throws Exception {
        FlatBufferBuilder a = new FlatBufferBuilder(0);
        DvIdentifier.startDvIdentifier(a);
        DvIdentifier.addIssuer(a, a.createString("A"));
        DvIdentifier.addAssigner(a, a.createString("A"));
        DvIdentifier.addId(a, a.createString("A"));
        DvIdentifier.addType(a, a.createString("A"));
//        System.out.println(a.offset());
    }
}