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

    public MrBufferBuilder getBb() {
        return bb;
    }
    
    public int obtemTamanhoCabecalho(){
        return tamanhoCabecalho;
    }

    public MrBufferBuilder getVectorBB() {
        return vectorBB;
    }

    /**
     * TODO: Implementar o vetor de objectos
     * @param type
     * @param numElems
     */
    public void startVector(int type, int numElems) {
        if (!(type > 0 && type < 156)) throw new AssertionError("Object type invalid.");

        nested = true;
        getBuilder().addInt(type);
    }

    public void endVector() {
        nested = false;
    }

    public int adicionaDvBoolean(boolean valor) {
        int id = getBuilder().addType(DV_BOOLEAN);
        getBuilder().addBoolean(valor);
        return id;
    }

    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        int issuerIndex = vectorBB.createString(issuer);
        int assignerIndex = vectorBB.createString(assigner);
        int idIndex = vectorBB.createString(id);
        int typeIndex = vectorBB.createString(type);

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
        int formattingIndex =  vectorBB.createString(formatting);
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
        int formattingIndex =  vectorBB.createString(formatting);
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
        return adicionaTerminologyId(valor, true);
    }

    public int adicionaTerminologyId(String valor, boolean withId) {
        int id = addIdFromType(TERMINOLOGY_ID, withId);
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

        int id = addIdFromType(DV_MULTIMEDIA, true);
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

        int id = addIdFromType(DV_MULTIMEDIA, true);
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

        int id = addIdFromType(DV_PARSABLE, true);
        getBuilder().addInt(charSetIndex);
        getBuilder().addInt(languageIndex);
        getBuilder().addInt(valueIndex);
        getBuilder().addInt(formalismIndex);
        return id;
    }

    public int adicionaDvOrdinal(int otherReferenceRanges, int normalRange,
                                 String normalStatusCodePhrase, int value, int symbolDvCodedText) {

        int normalStatus = vectorBB.createString(normalStatusCodePhrase);

        int id = addIdFromType(DV_ORDINAL, true);
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

    private int addIdFromType(int type, boolean withId) {
        return withId ? getBuilder().addType(type) : getBuilder().offset();
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

    public int getRef(int x) {
        return new Referencia().getByte(getBuilder().dataBuffer().getByte(x));
    }

    public int getType(int x) {
        return new Referencia().getByte(getBuilder().dataBuffer().getByte(x));
    }

    /**
     * Get a byte
     *
     * @param x
     * @return
     */
    public byte getByte(int x) {
        return bb.dataBuffer().getByte(x);
    }

    /**
     * Get a char
     *
     * @param x
     * @return
     */
    public char getChar(int x) {
        return bb.dataBuffer().getChar(x);
    }

    int fieldPosition = 1;

    /**
     * Get boolean
     *
     * @param position
     * @return
     */
    private boolean getBoolean(int position) {
        return getBuilder().dataBuffer().getBoolean(position);
    }

    public boolean getBoolean(int id, int field) {
        if (DV_BOOLEAN == getType(id)) {
            switch (field) {
                case 1:
                    return getBoolean(id + TYPE_SIZE);
                default:
                    throw new InvalidParameterException(String.format("MrImpl.getBoolean: The field %d not exists in a DV_BOOLEAN", field));
            }
        } else if (DV_STATE == getType(id)) {
            switch (field) {
                case 2:
                    return getBoolean(id + TYPE_SIZE + INT_SIZE);
                default:
                    throw new InvalidParameterException(String.format("MrImpl.getBoolean: The field %d not exists in a DV_STATE", field));
            }
        } else {
            throw new InvalidParameterException("MrImpl.getBoolean: Invalid Object index");
        }
    }

    public boolean nextBoolean(int index) {
        return getBoolean(index, fieldPosition++);
    }

    /**
     * Get an int
     *
     * @param position
     * @return
     */
    public int getInt(int position) {
        return getBuilder().dataBuffer().getInt(position);
    }

    public int getInt(int id, int field) {
        if(getByte(id) == DV_STATE){
            switch(field){
                case 1:
                    return obtemBytes(id, field).getInt(0);
                default:
                    throw new InvalidParameterException(String.format("The fieldPosition %d not exists in a DV_STATE", field));
            }
        } else {
            throw new InvalidParameterException("MrImpl.getInt: Invalid Object index");
        }
    }

    public int nextInt(int index) {
        return getInt(index, fieldPosition++);
    }

    /**
     * Get a float
     *
     * @param x
     * @return
     */
    public float getFloat(int x) {
        return bb.dataBuffer().getFloat(x);
    }

    /**
     * Get a double
     *
     * @param x
     * @return
     */
    public double getDouble(int x) {
        return bb.dataBuffer().getDouble(x);
    }

    /**
     * Get a long
     *
     * @param x
     * @return
     */
    public double getLong(int x) {
        return bb.dataBuffer().getLong(x);
    }

    /**
     * Get a string
     *
     * @param x
     * @return
     */
    public String getString(int x) {
        int length = getStringLength(x);
        return vectorBB.dataBuffer().toString(x + INT_SIZE, length, Charset.forName("UTF-8"));
    }

    public String getString(int id, int field) {
        if(getByte(id) == DV_URI){
            switch(field){
                case 1:
                    return getString(getInt(id + TYPE_SIZE));
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_URI", field));
            }
        } else if(getByte(id) == DV_EHR_URI){
            switch(field){
                case 1:
                    return getString(getInt(id + TYPE_SIZE));
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_EHR_URI", field));
            }
        } else if(getByte(id) == CODE_PHRASE){
            switch(field){
                case 1:
                    return getString(getInt(id + TYPE_SIZE));
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a CODE_PHRASE", field));
            }
        } else if(getByte(id) == DV_IDENTIFIER){
            switch(field){
                case 1:
                case 2:
                case 3:
                case 4:
                    return getString(getInt(id + TYPE_SIZE + ((field - 1) * INT_SIZE)));
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_IDENTIFIER", field));
            }
        } else {
            throw new InvalidParameterException("MrImpl.getString: Invalid Object index");
        }
    }

    public String nextString(int id) {
        return null;
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
    
    
    /**
    * Retorna o tamanho, em bytes, de um campo de um objeto.
    *
    * @param id O identificador único do objeto.
    * @param campo A ordem do campo, iniciada por 0.
    *
    * @return Quantidade de bytes do campo do objeto.
    *
    * @throws IllegalArgumentException Nos seguintes casos:
    * (a) o objeto não existe;
    * (b) o campo não existe.
    */
    public int obtemQtdeBytes(int id, int campo){
        return 0;
    }
    
    /**
    * Recupera parte do campo do objeto,
    * conforme a capacidade de memória suportada.
    *
    * @param id O identificador único do objeto.
    * @param campo A ordem do campo, iniciada por 0.
    * @param ini A posição do byte inicial.
    * @param fim A posição do byte final.
    *
    * @return Parte do campo do objeto.
    *
    * @throws IllegalArgumentException Nos seguintes casos:
    * (a) o objeto não existe;
    * (b) o campo não existe;
    * (c) ini negativo; (d) ini maior do que fim;
    * (e) fim maior do que o tamanho total do campo.
    */
    public byte[] obtemBytes(int id, int campo, int ini, int fim){
        return null;
    }
    
    
    /**
    * Recupera o campo do objeto.
    *
    * @param id O identificador único do objeto.
    * @param campo A ordem do campo, iniciada por 0.
    *
    * @return Sequência de bytes correspondente ao campo.
    *
    * @throws IllegalArgumentException Nos seguintes casos:
    * (a) o objeto não existe;
    * (b) o campo não existe;
    */
    public ByteBuf obtemBytes(int id, int campo){
        ByteBuf ret = Unpooled.buffer();
        
        if(getByte(id) == DV_BOOLEAN){
            ret.capacity(Mr.BOOLEAN_SIZE);
            bb.dataBuffer().getBytes(id + Mr.TYPE_SIZE, ret, Mr.BOOLEAN_SIZE);
        }
        
        if(getByte(id) == DV_STATE){
            ret.capacity(Mr.INT_SIZE + Mr.BOOLEAN_SIZE);
            switch(campo){
                case 1:
                    bb.dataBuffer().getBytes(id + Mr.TYPE_SIZE, ret, Mr.INT_SIZE);
                    break;
                case 2:
                    bb.dataBuffer().getBytes(id + Mr.TYPE_SIZE +
                            Mr.INT_SIZE, ret, Mr.BOOLEAN_SIZE);
                    break;
                default:
                    throw new InvalidParameterException(String.format("The field %d not exists in a DV_STATE", campo));
            }
        }
        
        return ret;
    }
    
    /**
    * Define a raiz do presente objeto.
    *
    * <p>Uma instância desta interface é um grafo com uma
    * raiz única. Após todos os objetos serem adicionados
    * ao grafo, partindo dos objetos "primitivos" até o objeto
    * de mais "alto nível" (raiz), este método deve ser chamado
    * a fim de guardar a identificação da raiz. Isso possibilita
    * que seja estabelecido um ponto de acesso único ao grafo
    * para uma posterior remontagem.</p>
    *
    * @see #obtemRaiz()
    *
    * @param raiz O identificador único da raiz.
    *
    * @throws IllegalArgumentException O objeto raiz não existe.
    */
    public void defineRaiz(int raiz){
        rootIndex = raiz;
    }
    
    /**
    * Obtém o identificador da raiz do presente objeto.
    *
    * <p>Este método retorna o identificador que determina
    * o ponto inicial para remontagem do grafo de objetos,
    * conforme a especificação do Modelo de Referência.</p>
    *
    * @see #defineRaiz(int)
    *
    * @return O identificador único da raiz.
    */
    public int obtemRaiz(){
        return 0;
    }
    
    /**
    * Obtém o total de objetos, instâncias de elementos
    * do Modelo de Referência, ocupados pelo presente
    * objeto.
    *
    * <p>Uma instância desta interface é um grafo de
    * objetos. O presente método permite identificar
    * quantos objetos fazem parte deste grafo.</p>
    *
    * <p>Objeto aqui deve ser interpretado como
    * instância de "classe" do Modelo de Referência
    * do openEHR. Ou seja, não necessariamente este valor
    * é quantidade de instâncias de classes em Java
    * empregadas para representar o presente grafo de
    * objetos.</p>
    *
    * <p>Se o valor retornado é 3, então existem,
    * no presente grafo, três objetos, cujos
    * identificadores são 0, 1 e 2.</p>
    *
    * @return Total de objetos mantidos pela instância. O
    * primeiro é zero.
    */
    public int totalObjetos(){
        return 0;
    }
    
    /**
    * Retorna inteiro que identifica o tipo do objeto
    * identificado.
    * @param id O identificador do objeto.
    * @return Valor inteiro correspondente ao tipo do
    * objeto.
    */
    public int obtemTipo(int id){
        return 0;
    }
        
    /**
    * Retorna o tamanho da lista de objetos.
    *
    * @param lista Identificador da lista.
    * @throws IllegalArgumentException a lista não existe.
    */
    public int obtemTamanhoLista(int lista){
        return 0;
    }
    
    /**
    * Procura pelo objeto na lista.
    *
    * @param lista Identificador da lista onde o
    * objeto será procurado.
    * @param objeto Identificador do objeto
    * a ser procurado. Esse é um
    * objeto temporário, construído
    * com a classe ObjectTemp.
    * @return Ordem na lista onde o objeto se
    * encontra, ou o valor -1, caso o objeto não
    * esteja presente na lista.
    *
    * @throws IllegalArgumentException a lista não existe.
    *
    */
    public int buscaEmLista(int lista, int objeto){
        return 0;
    }
    
    /**
    * Elimina o objeto.
    *
    * <p>Este método é particularmente útil
    * durante uma busca, onde um objeto foi
    * construído especificamente para esta
    * finalidade.</p>
    *
    * @param objeto Identificador do objeto
    * a ser eliminado.
    */
    public void elimineObjeto(int objeto){
        
    }
}
