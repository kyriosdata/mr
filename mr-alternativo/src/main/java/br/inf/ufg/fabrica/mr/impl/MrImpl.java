/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import br.inf.ufg.fabrica.mr.Mr;
import br.inf.ufg.fabrica.mr.Referencia;
import br.inf.ufg.fabrica.mr.mrbuffers.MrBufferBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidParameterException;

/**
 * Implementação do modelo de referência.
 */
public class MrImpl implements Mr {

    private static final int NULL_VALUE = -1;
    int fieldPosition = 1;
    private MrBufferBuilder bb;
    private MrBufferBuilder vectorBB;
    private boolean nested = false;
    private int rootIndex;
    private int tamanhoCabecalho;

    public MrImpl(int initial_size) {
        if (initial_size <= 0) initial_size = 1;
        // objects
        bb = new MrBufferBuilder(initial_size);
        // header
        bb.addInt(0); // ref to root index
        bb.addInt(0); // ref to list index
        // list
        this.tamanhoCabecalho = bb.offset();
        vectorBB = new MrBufferBuilder(initial_size);
    }

    public MrImpl() {
        this(1);
    }

    private MrBufferBuilder getBuilder() {
        return nested ? vectorBB : bb;
    }

    private ByteBuf getDataBuffer() {
        return getBuilder().dataBuffer();
    }

    public int obtemTamanhoCabecalho() {
        return tamanhoCabecalho;
    }

    int numElements;
    public void startVector(int numElems) {
        nested = true;
        numElements = numElems;
    }

    public int endVector(int[] ofsset) {
        int id = getBuilder().addInt(numElements);
        for (int i: ofsset) {
            getBuilder().addInt(i);
        }
        nested = false;
        return id;
    }

    public int adicionaDvBoolean(boolean valor) {
        int id = getBuilder().addType(DV_BOOLEAN);
        getBuilder().addBoolean(valor);
        return id;
    }

    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        int issuerIndex = (issuer != null) ? vectorBB.createString(issuer) : NULL_VALUE;
        int assignerIndex = (assigner != null) ? vectorBB.createString(assigner) : NULL_VALUE;
        int idIndex = (id != null) ? vectorBB.createString(id) : NULL_VALUE;
        int typeIndex = (type != null) ? vectorBB.createString(type) : NULL_VALUE;

        int i = getBuilder().addType(DV_IDENTIFIER);
        getBuilder().addInt(issuerIndex);
        getBuilder().addInt(assignerIndex);
        getBuilder().addInt(idIndex);
        getBuilder().addInt(typeIndex);
        return i;
    }

    public int adicionaDvState(int value, boolean terminal) {
        int id = getBuilder().addType(DV_STATE);
        getBuilder().addInt(value);
        getBuilder().addBoolean(terminal);
        return id;
    }

    public int adicionaDvUri(String uri) {
        int uriIndex = vectorBB.createString(uri);

        int id = getBuilder().addType(DV_URI);
        getBuilder().addInt(uriIndex);
        return id;
    }

    public int adicionaDvEhrUri(String uri) {
        int uriIndex = vectorBB.createString(uri);

        int id = getBuilder().addType(DV_EHR_URI);
        getBuilder().addInt(uriIndex);
        return id;
    }

    public int adicionaDvParagraph(int itemsList) {
        int id = getBuilder().addType(DV_PARAGRAPH);
        getBuilder().addInt(itemsList);
        return id;
    }

    public int adicionaCodePhrase(String value) {
        int valueIndex = vectorBB.createString(value);

        int id = getBuilder().addType(CODE_PHRASE);
        getBuilder().addInt(valueIndex);
        return id;
    }

    public int adicionaDvText(int hyperlink, int language, int encoding, int mappings, String formatting, String value) {
        int formattingIndex = vectorBB.createString(formatting);
        int valueIndex = vectorBB.createString(value);

        int id = getBuilder().addType(DV_TEXT);
        getBuilder().addRef(id, hyperlink, 1);
        getBuilder().addRef(id, language, 2);
        getBuilder().addRef(id, encoding, 3);
        getBuilder().addInt(mappings);
        getBuilder().addInt(formattingIndex);
        getBuilder().addInt(valueIndex);
        return id;
    }

    public int adicionaDvCodedText(int hyperlink, int language, int encoding, int definingCode, int mappings, String formatting, String value) {
        int formattingIndex = vectorBB.createString(formatting);
        int valueIndex = vectorBB.createString(value);

        int id = getBuilder().addType(DV_CODED_TEXT);
        getBuilder().addRef(id, hyperlink, 1);
        getBuilder().addRef(id, language, 2);
        getBuilder().addRef(id, encoding, 3);
        getBuilder().addRef(id, definingCode, 4);
        getBuilder().addInt(mappings);
        getBuilder().addInt(formattingIndex);
        getBuilder().addInt(valueIndex);
        return id;
    }

    public int adicionaTermMapping(int target, char match, int purpose) {
        int id = getBuilder().addType(TERM_MAPPING);
        getBuilder().addRef(id, target, 1);
        getBuilder().addRef(id, purpose, 2);
        getBuilder().addChar(match);
        return id;
    }

    public int adicionaTerminologyId(String valor) {
        int id = getBuilder().addType(TERMINOLOGY_ID);
        getBuilder().addInt(vectorBB.createString(valor));
        return id;
    }

    public int adicionaDvMultimedia(String codePhraseCharSet,
                                    String codePhraseLanguage, String alternateText,
                                    String codePhraseMediaType, String codePhraseCompressionAlgorithm,
                                    byte[] integrityCheck, String codePhraseIntegrityCheckAlgorithm,
                                    int hDvMultimediaThumbnail, String dvUri, byte[] data) {

        int charSetIndex = vectorBB.createString(codePhraseCharSet);
        int languageIndex = vectorBB.createString(codePhraseLanguage);
        int mediaTypeIndex = vectorBB.createString(codePhraseMediaType);
        int compressionAlgorithmIndex = vectorBB.createString(codePhraseCompressionAlgorithm);
        int integrityCheckAlgorithmIndex = vectorBB.createString(codePhraseIntegrityCheckAlgorithm);
        int alternateTextIndex = vectorBB.createString(alternateText);
        int integrityCheckIndex = vectorBB.addByteArray(integrityCheck);
        int dvUriIndex = vectorBB.createString(dvUri);
        int dataIndex = vectorBB.addByteArray(data);

        int id = getBuilder().addType(DV_MULTIMEDIA);
        getBuilder().addInt(charSetIndex);
        getBuilder().addInt(languageIndex);
        getBuilder().addInt(mediaTypeIndex);
        getBuilder().addInt(compressionAlgorithmIndex);
        getBuilder().addInt(integrityCheckAlgorithmIndex);
        getBuilder().addInt(alternateTextIndex);
        getBuilder().addInt(integrityCheckIndex);
        getBuilder().addInt(dvUriIndex);
        getBuilder().addInt(dataIndex);
        getBuilder().addInt(hDvMultimediaThumbnail);
        return id;
    }

    public int adicionaDvMultimedia(String codePhraseCharSet,
                                    String codePhraseLanguage, String alternateText,
                                    String codePhraseMediaType, String codePhraseCompressionAlgorithm,
                                    byte[] integrityCheck, String codePhraseIntegrityCheckAlgorithm,
                                    String dvUri, byte[] data) {

        int charSetIndex = vectorBB.createString(codePhraseCharSet);
        int languageIndex = vectorBB.createString(codePhraseLanguage);
        int mediaTypeIndex = vectorBB.createString(codePhraseMediaType);
        int compressionAlgorithmIndex = vectorBB.createString(codePhraseCompressionAlgorithm);
        int integrityCheckAlgorithmIndex = vectorBB.createString(codePhraseIntegrityCheckAlgorithm);
        int alternateTextIndex = vectorBB.createString(alternateText);
        int integrityCheckIndex = vectorBB.addByteArray(integrityCheck);
        int dvUriIndex = vectorBB.createString(dvUri);
        int dataIndex = vectorBB.addByteArray(data);

        int id = getBuilder().addType(DV_MULTIMEDIA);
        getBuilder().addInt(charSetIndex);
        getBuilder().addInt(languageIndex);
        getBuilder().addInt(mediaTypeIndex);
        getBuilder().addInt(compressionAlgorithmIndex);
        getBuilder().addInt(integrityCheckAlgorithmIndex);
        getBuilder().addInt(alternateTextIndex);
        getBuilder().addInt(integrityCheckIndex);
        getBuilder().addInt(dvUriIndex);
        getBuilder().addInt(dataIndex);
        return id;
    }

    public int adicionaDvParsable(String codePhraseCharSet,
                                  String codePhraseLanguage, String value, String formalism) {

        int charSetIndex = vectorBB.createString(codePhraseCharSet);
        int languageIndex = vectorBB.createString(codePhraseLanguage);
        int valueIndex = vectorBB.createString(value);
        int formalismIndex = vectorBB.createString(formalism);

        int id = getBuilder().addType(DV_PARSABLE);
        getBuilder().addInt(charSetIndex);
        getBuilder().addInt(languageIndex);
        getBuilder().addInt(valueIndex);
        getBuilder().addInt(formalismIndex);
        return id;
    }

    public int adicionaDvOrdinal(int otherReferenceRanges, int normalRange,
                                 String normalStatusCodePhrase, int value, int symbolDvCodedText) {

        int normalStatus = vectorBB.createString(normalStatusCodePhrase);

        int id = getBuilder().addType(DV_ORDINAL);
        getBuilder().addRef(id, symbolDvCodedText, 1);
        getBuilder().addInt(otherReferenceRanges);
        getBuilder().addInt(normalRange);
        getBuilder().addInt(normalStatus);
        getBuilder().addInt(value);
        return id;
    }

    public int adicionaDvInterval(int lowerOrdered, int upperOrdered,
                                  boolean lowerIncluded, boolean upperIncluded) {
        return 0;
    }

    public int adicionaReferenceRange(int lowerOrdered, int upperOrdered, boolean lowerIncluded, boolean upperIncluded, String value, String hyperlink, String formatting, int mappings, String codePhraseLanguage, String codePhraseEncoding) {
        return 0;
    }

    public byte[] toBytes() {
        return toByteBuffer().array();
    }

    public ByteBuffer toByteBuffer() {
        if (rootIndex == 0) throw new AssertionError("You first need to set the root index");

        ByteBuf byteBuf = bb.dataBuffer().copy();
        byteBuf.setInt(ROOT_INDEX, rootIndex);
        byteBuf.setInt(LIST_INDEX, byteBuf.writerIndex());
        byteBuf.writeBytes(vectorBB.dataBuffer().duplicate());
        return byteBuf.capacity(byteBuf.nioBuffer().remaining()).nioBuffer();
    }

    public int getRef(int id, int field) {
        if (getType(id) == DV_TEXT) {
            switch (field) {
                case 1:
                case 2:
                case 3:
                    return id + field - Referencia.getByte(obtemBytes(id, field).readByte());
                default:
                    throw new InvalidParameterException(String.format("The ref field %d not exists in a DV_TEXT", field));
            }
        } else if (getType(id) == DV_CODED_TEXT) {
            switch (field) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return id + field - Referencia.getByte(obtemBytes(id, field).readByte());
                default:
                    throw new InvalidParameterException(String.format("The ref field %d not exists in a DV_TEXT", field));
            }
        } else if (getType(id) == TERM_MAPPING) {
            switch (field) {
                case 1:
                case 2:
                    return id + field - Referencia.getByte(obtemBytes(id, field).readByte());
                default:
                    throw new InvalidParameterException(String.format("The ref field %d not exists in a TERM_MAPPING", field));
            }
        } else {
            throw new InvalidParameterException("Invalid class identifier");
        }
    }

    public int getType(int x) {
        return Referencia.getByte(getDataBuffer().getByte(x));
    }

    /**
     * Get a byte
     *
     * @param id
     * @param field
     * @return
     */
    public byte getByte(int id, int field) {
        return 0;
    }

    public char getChar(int id, int field) {
        if (TERM_MAPPING == getType(id)) {
            switch (field) {
                case 3:
                    return obtemBytes(id, field).readChar();
                default:
                    throw new InvalidParameterException(String.format("The char field %d not exists in a TERM_MAPPING", field));
            }
        } else {
            throw new InvalidParameterException("Invalid Object");
        }
    }

    /**
     * Get boolean
     *
     * @param id
     * @param field
     * @return
     */
    public boolean getBoolean(int id, int field) {
        if (DV_BOOLEAN == getType(id)) {
            switch (field) {
                case 1:
                    return obtemBytes(id, field).readBoolean();
                default:
                    throw new InvalidParameterException(String.format("The boolean field %d not exists in a DV_BOOLEAN", field));
            }
        } else if (DV_STATE == getType(id)) {
            switch (field) {
                case 2:
                    return obtemBytes(id, field).readBoolean();
                default:
                    throw new InvalidParameterException(String.format("The boolean field %d not exists in a DV_STATE", field));
            }
        } else {
            throw new InvalidParameterException("Invalid Object");
        }
    }

    public boolean nextBoolean(int index) {
        return getBoolean(index, fieldPosition++);
    }

    /**
     * Get an int
     *
     * @param id
     * @param field
     * @return
     */
    public int getInt(int id, int field) {
        if (getType(id) == DV_STATE) {
            switch (field) {
                case 1:
                    return obtemBytes(id, field).readInt();
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_STATE", field));
            }
        } else if (getType(id) == DV_TEXT) {
            switch (field) {
                case 4:
                    return obtemBytes(id, field).readInt();
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_TEXT", field));
            }
        } else if (getType(id) == DV_CODED_TEXT) {
            switch (field) {
                case 5:
                    return obtemBytes(id, field).readInt();
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_CODED_TEXT", field));
            }
        } else {
            throw new InvalidParameterException("Invalid identifier");
        }
    }

    public int nextInt(int index) {
        return getInt(index, fieldPosition++);
    }

    /**
     * Get a float
     *
     * @param id
     * @param field
     * @return
     */
    public float getFloat(int id, int field) {
        return 0;
    }

    /**
     * Get a double
     *
     * @param id
     * @param field
     * @return
     */
    public double getDouble(int id, int field) {
        return 0;
    }

    /**
     * Get a long
     *
     * @param id
     * @param field
     * @return
     */
    public double getLong(int id, int field) {
        return 0;
    }

    /**
     *
     * @param x
     * @return
     */
    private String getString(int x) {
        if (x == NULL_VALUE) {
            return null;
        }
        return vectorBB.dataBuffer().toString(x + INT_SIZE, getStringLength(x), Charset.forName("UTF-8"));
    }

    /**
     * Get string
     *
     * @param id
     * @param field
     * @return
     */
    public String getString(int id, int field) {
        if (getType(id) == DV_URI) {
            switch (field) {
                case 1:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_URI", field));
            }
        } else if (getType(id) == DV_EHR_URI) {
            switch (field) {
                case 1:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_EHR_URI", field));
            }
        } else if (getType(id) == CODE_PHRASE) {
            switch (field) {
                case 1:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a CODE_PHRASE", field));
            }
        } else if (getType(id) == DV_IDENTIFIER) {
            switch (field) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_IDENTIFIER", field));
            }
        } else if (getType(id) == DV_TEXT) {
            switch (field) {
                case 5:
                case 6:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_IDENTIFIER", field));
            }
        } else if (getType(id) == DV_CODED_TEXT) {
            switch (field) {
                case 6:
                case 7:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_CODED_TEXT", field));
            }
        } else if (getType(id) == TERMINOLOGY_ID) {
            switch (field) {
                case 1:
                    return getString(obtemBytes(id, field).readInt());
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a TERMINOLOGY_ID", field));
            }
        } else {
            throw new InvalidParameterException("Invalid Object index");
        }
    }

    public String nextString(int id) {
        return getString(id, fieldPosition++);
    }

    /**
     * Get string length
     *
     * @param x
     * @return
     */
    public int getStringLength(int x) {
        return vectorBB.dataBuffer().getInt(x);
    }

    public int getList(int id, int field) {
        if (getType(id) == DV_TEXT) {
            switch (field) {
                case 4:
                    return obtemBytes(id, field).readInt();
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_TEXT", field));
            }
        } else if (getType(id) == DV_CODED_TEXT) {
            switch (field) {
                case 5:
                    return obtemBytes(id, field).readInt();
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_CODED_TEXT", field));
            }
        } else {
            throw new InvalidParameterException("Invalid Object");
        }
    }

    /**
     * Retorna o tamanho, em bytes, de um campo de um objeto.
     *
     * @param id    O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0.
     * @return Quantidade de bytes do campo do objeto.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) o objeto não existe;
     *                                  (b) o campo não existe.
     */
    public int obtemQtdeBytes(int id, int campo) {
        return 0;
    }

    /**
     * Recupera parte do campo do objeto,
     * conforme a capacidade de memória suportada.
     *
     * @param id    O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0.
     * @param ini   A posição do byte inicial.
     * @param fim   A posição do byte final.
     * @return Parte do campo do objeto.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) o objeto não existe;
     *                                  (b) o campo não existe;
     *                                  (c) ini negativo; (d) ini maior do que fim;
     *                                  (e) fim maior do que o tamanho total do campo.
     */
    public byte[] obtemBytes(int id, int campo, int ini, int fim) {
        return null;
    }


    /**
     * Recupera o campo do objeto.
     *
     * @param id    O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0.
     * @return Sequência de bytes correspondente ao campo.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) o objeto não existe;
     *                                  (b) o campo não existe;
     */
    public ByteBuf obtemBytes(int id, int campo) {
        ByteBuf ret = Unpooled.buffer();

        if (getType(id) == DV_BOOLEAN) {
            switch (campo) {
                case 1:
                    ret.capacity(Mr.BOOLEAN_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE, ret, Mr.BOOLEAN_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_BOOLEAN", campo));
            }
        } else if (getType(id) == DV_STATE) {
            switch (campo) {
                case 1:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE, ret, Mr.INT_SIZE);
                    break;
                case 2:
                    ret.capacity(Mr.BOOLEAN_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + Mr.INT_SIZE, ret, Mr.BOOLEAN_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_STATE", campo));
            }
        } else if (getType(id) == DV_IDENTIFIER) {
            switch (campo) {
                case 1:
                case 2:
                case 3:
                case 4:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + TYPE_SIZE + ((campo - 1) * INT_SIZE), ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_IDENTIFIER", campo));
            }
        } else if (getType(id) == DV_URI) {
            switch (campo) {
                case 1:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + TYPE_SIZE, ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_URI", campo));
            }
        } else if (getType(id) == DV_EHR_URI) {
            switch (campo) {
                case 1:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + TYPE_SIZE, ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_EHR_URI", campo));
            }
        } else if (getType(id) == CODE_PHRASE) {
            switch (campo) {
                case 1:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + TYPE_SIZE, ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a CODE_PHRASE", campo));
            }
        } else if (getType(id) == DV_TEXT) {
            switch (campo) {
                case 1:
                case 2:
                case 3:
                    ret.capacity(Mr.REF_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + ((campo - 1) * Mr.REF_SIZE), ret, Mr.REF_SIZE);
                    break;
                case 4:
                case 5:
                case 6:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + (3 * Mr.REF_SIZE) + ((campo - 4) * Mr.INT_SIZE), ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_TEXT", campo));
            }
        } else if (getType(id) == DV_CODED_TEXT) {
            switch (campo) {
                case 1:
                case 2:
                case 3:
                case 4:
                    ret.capacity(Mr.REF_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + ((campo - 1) * Mr.REF_SIZE), ret, Mr.REF_SIZE);
                    break;
                case 5:
                case 6:
                case 7:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + (4 * Mr.REF_SIZE) + ((campo - 5) * Mr.INT_SIZE), ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_CODED_TEXT", campo));
            }
        } else if (getType(id) == TERM_MAPPING) {
            switch (campo) {
                case 1:
                case 2:
                    ret.capacity(Mr.REF_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + ((campo - 1) * Mr.REF_SIZE), ret, Mr.REF_SIZE);
                    break;
                case 3:
                    ret.capacity(Mr.CHAR_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE + (2 * Mr.REF_SIZE), ret, Mr.CHAR_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a TERM_MAPPING", campo));
            }
        } else if (getType(id) == TERMINOLOGY_ID) {
            switch (campo) {
                case 1:
                    ret.capacity(Mr.INT_SIZE);
                    getDataBuffer().getBytes(id + Mr.TYPE_SIZE, ret, Mr.INT_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a TERMINOLOGY_ID", campo));
            }
        }

        return ret;
    }

    /**
     * Define a raiz do presente objeto.
     * <p/>
     * <p>Uma instância desta interface é um grafo com uma
     * raiz única. Após todos os objetos serem adicionados
     * ao grafo, partindo dos objetos "primitivos" até o objeto
     * de mais "alto nível" (raiz), este método deve ser chamado
     * a fim de guardar a identificação da raiz. Isso possibilita
     * que seja estabelecido um ponto de acesso único ao grafo
     * para uma posterior remontagem.</p>
     *
     * @param raiz O identificador único da raiz.
     * @throws IllegalArgumentException O objeto raiz não existe.
     * @see #obtemRaiz()
     */
    public void defineRaiz(int raiz) {
        rootIndex = raiz;
    }

    /**
     * Obtém o identificador da raiz do presente objeto.
     * <p/>
     * <p>Este método retorna o identificador que determina
     * o ponto inicial para remontagem do grafo de objetos,
     * conforme a especificação do Modelo de Referência.</p>
     *
     * @return O identificador único da raiz.
     * @see #defineRaiz(int)
     */
    public int obtemRaiz() {
        return rootIndex;
    }

    /**
     * Obtém o total de objetos, instâncias de elementos
     * do Modelo de Referência, ocupados pelo presente
     * objeto.
     * <p/>
     * <p>Uma instância desta interface é um grafo de
     * objetos. O presente método permite identificar
     * quantos objetos fazem parte deste grafo.</p>
     * <p/>
     * <p>Objeto aqui deve ser interpretado como
     * instância de "classe" do Modelo de Referência
     * do openEHR. Ou seja, não necessariamente este valor
     * é quantidade de instâncias de classes em Java
     * empregadas para representar o presente grafo de
     * objetos.</p>
     * <p/>
     * <p>Se o valor retornado é 3, então existem,
     * no presente grafo, três objetos, cujos
     * identificadores são 0, 1 e 2.</p>
     *
     * @return Total de objetos mantidos pela instância. O
     * primeiro é zero.
     */
    public int totalObjetos() {
        return 0;
    }

    /**
     * Retorna inteiro que identifica o tipo do objeto
     * identificado.
     *
     * @param id O identificador do objeto.
     * @return Valor inteiro correspondente ao tipo do
     * objeto.
     */
    public int obtemTipo(int id) {
        return getType(id);
    }

    /**
     * Retorna o tamanho da lista de objetos.
     *
     * @param lista Identificador da lista.
     * @throws IllegalArgumentException a lista não existe.
     */
    public int obtemTamanhoLista(int lista) {
        return vectorBB.dataBuffer().getInt(lista);
    }

    /**
     * Procura pelo objeto na lista.
     *
     * @param lista  Identificador da lista onde o
     *               objeto será procurado.
     * @param objeto Identificador do objeto
     *               a ser procurado. Esse é um
     *               objeto temporário, construído
     *               com a classe ObjectTemp.
     * @return Ordem na lista onde o objeto se
     * encontra, ou o valor -1, caso o objeto não
     * esteja presente na lista.
     * @throws IllegalArgumentException a lista não existe.
     */
    public int buscaEmLista(int lista, int objeto) {
        return 0;
    }

    /**
     * Elimina o objeto.
     * <p/>
     * <p>Este método é particularmente útil
     * durante uma busca, onde um objeto foi
     * construído especificamente para esta
     * finalidade.</p>
     *
     * @param objeto Identificador do objeto
     *               a ser eliminado.
     */
    public void elimineObjeto(int objeto) {

    }
}
