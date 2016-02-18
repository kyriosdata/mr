/*
 * Copyright (c) 2015 - 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr.impl;

import br.inf.ufg.fabrica.mr.Mr;
import br.inf.ufg.fabrica.mr.Referencia;
import br.inf.ufg.fabrica.mr.datatypes.Text;
import br.inf.ufg.fabrica.mr.mrbuffers.MrBufferBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Implementação do modelo de referência.
 */
public class MrImpl implements Mr {

    MrBufferBuilder bb;
    MrBufferBuilder vectorBB;
    boolean nested = false;

    public MrImpl(int initial_size) {
        if (initial_size <= 0) initial_size = 1;
        // objects
        bb = new MrBufferBuilder(initial_size);
        // header
        bb.addInt(0); // ref to list index
        // list
        vectorBB = new MrBufferBuilder(initial_size);
    }

    public MrImpl() {
        this(1);
    }

    public static void printByteArray(byte[] buffer) {
        int i = 0;
        for (byte x : buffer) {
            System.out.println("" + i + " - " + x);
            i++;
        }
    }

    public int getListIndex() {
        return 0;
    }

    private MrBufferBuilder getBuilder() {
        return nested ? vectorBB : bb;
    }

    public MrBufferBuilder getBb() {
        return bb;
    }

    public MrBufferBuilder getVectorBB() {
        return vectorBB;
    }

    public void startVector(int type, int numElems) {
        if (!(type > 0 && type < 156)) throw new AssertionError("MrImplement: invalid object type.");

    }

    /**
     * Adiciona um valor lógico
     * - {@code DV_BOOLEAN}.
     *
     * @param valor Valor lógico (DV_BOOLEAN) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     */
    public int adicionaDvBoolean(boolean valor) {
        int id = getBuilder().addType(DV_BOOLEAN);
        getBuilder().addBoolean(valor);
        return id;
    }

    /**
     * Adiciona um identificador
     * - {@code DV_IDENTIFIER}.
     *
     * @param issuer   Entidade que emite identificação.
     * @param assigner Entidade que assina identificação.
     * @param id       Identificador propriamente dito.
     * @param type     Tipo da identificação.
     * @return O identificador único deste identificador na estrutura.
     */
    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        int i = getBuilder().addType(DV_IDENTIFIER);
        getBuilder().addInt(vectorBB.createString(issuer));
        getBuilder().addInt(vectorBB.createString(assigner));
        getBuilder().addInt(vectorBB.createString(id));
        getBuilder().addInt(vectorBB.createString(type));
        return i;
    }

    public int adicionaDvState(int value, boolean terminal) {
        int id = getBuilder().addType(DV_STATE);
        getBuilder().addInt(value);
        getBuilder().addBoolean(terminal);
        return id;
    }

    public int adicionaDvUri(String uri) {
        int id = getBuilder().addType(DV_URI);
        getBuilder().addInt(vectorBB.createString(uri));
        return id;
    }

    public int adicionaDvEhrUri(String uri) {
        int id = getBuilder().addType(DV_EHR_URI);
        getBuilder().addInt(vectorBB.createString(uri));
        return id;
    }

    public int adicionaDvParagraph(int itemsList) {
        int id = getBuilder().addType(DV_PARAGRAPH);
        getBuilder().addInt(itemsList);
        return id;
    }

    public int adicionaCodePhrase(String value) {
        int id = getBuilder().addType(CODE_PHRASE);
        getBuilder().addInt(vectorBB.createString(value));
        return id;
    }

    public int adicionaDvText(int hyperlink, int language, int encoding, int mappings, String formatting, String value) {
        int id = getBuilder().addType(DV_TEXT);
        getBuilder().addRef(id, hyperlink, 1);
        getBuilder().addRef(id, language, 2);
        getBuilder().addRef(id, encoding, 3);
        getBuilder().addInt(mappings);
        getBuilder().addInt(vectorBB.createString(formatting));
        getBuilder().addInt(vectorBB.createString(value));
        return id;
    }

    public int adicionaDvCodedText(int hyperlink, int language, int encoding, int definingCode, int mappings, String formatting, String value) {
        int id = getBuilder().addType(DV_CODED_TEXT);
        getBuilder().addRef(id, hyperlink, 1);
        getBuilder().addRef(id, language, 2);
        getBuilder().addRef(id, encoding, 3);
        getBuilder().addRef(id, definingCode, 4);
        getBuilder().addInt(mappings);
        getBuilder().addInt(vectorBB.createString(formatting));
        getBuilder().addInt(vectorBB.createString(value));
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

        int id = addIdFromType(DV_MULTIMEDIA, true);
        getBuilder().addInt(vectorBB.createString(codePhraseCharSet));
        getBuilder().addInt(vectorBB.createString(codePhraseLanguage));
        getBuilder().addInt(vectorBB.createString(alternateText));
        getBuilder().addInt(vectorBB.createString(codePhraseMediaType));
        getBuilder().addInt(vectorBB.createString(codePhraseCompressionAlgorithm));
        getBuilder().addInt(vectorBB.addByteArray(integrityCheck));
        getBuilder().addInt(vectorBB.createString(codePhraseIntegrityCheckAlgorithm));
        getBuilder().addInt(hDvMultimediaThumbnail);
        getBuilder().addInt(vectorBB.createString(dvUri));
        getBuilder().addInt(vectorBB.addByteArray(integrityCheck));

        return id;
    }

    public int adicionaDvMultimedia(String codePhraseCharSet,
                                    String codePhraseLanguage, String alternateText,
                                    String codePhraseMediaType, String codePhraseCompressionAlgorithm,
                                    byte[] integrityCheck, String codePhraseIntegrityCheckAlgorithm,
                                    String dvUri, byte[] data) {

        int id = addIdFromType(DV_MULTIMEDIA, true);
        getBuilder().addInt(vectorBB.createString(codePhraseCharSet));
        getBuilder().addInt(vectorBB.createString(codePhraseLanguage));
        getBuilder().addInt(vectorBB.createString(alternateText));
        getBuilder().addInt(vectorBB.createString(codePhraseMediaType));
        getBuilder().addInt(vectorBB.createString(codePhraseCompressionAlgorithm));
        getBuilder().addInt(vectorBB.addByteArray(integrityCheck));
        getBuilder().addInt(vectorBB.createString(codePhraseIntegrityCheckAlgorithm));
        getBuilder().addInt(vectorBB.createString(dvUri));
        getBuilder().addInt(vectorBB.addByteArray(integrityCheck));

        return id;
    }

    public int adicionaDvParsable(String codePhraseCharSet,
                                  String codePhraseLanguage, String value, String formalism) {

        int id = addIdFromType(DV_PARSABLE, true);
        getBuilder().addInt(vectorBB.createString(codePhraseCharSet));
        getBuilder().addInt(vectorBB.createString(codePhraseLanguage));
        getBuilder().addInt(vectorBB.createString(value));
        getBuilder().addInt(vectorBB.createString(formalism));

        return id;
    }

    public int adicionaDvOrdinal(int otherReferenceRanges, int normalRange,
                                 String normalStatusCodePhrase, int value, int symbolDvCodedText) {

        int id = addIdFromType(DV_ORDINAL, true);
        getBuilder().addInt(otherReferenceRanges);
        getBuilder().addInt(normalRange);
        getBuilder().addInt(vectorBB.createString(normalStatusCodePhrase));
        getBuilder().addInt(value);
        getBuilder().addInt(symbolDvCodedText);

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

    /**
     * @return
     * @see #getListIndex()
     */
    public ByteBuffer toByteBuffer() {
        ByteBuf byteBuf = bb.dataBuffer().copy();
        byteBuf.setInt(getListIndex(), byteBuf.writerIndex());
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

    /**
     * Get boolean
     *
     * @param x
     * @return
     */
    public boolean getBoolean(int x) {
        return bb.dataBuffer().getBoolean(x);
    }

    /**
     * Get an int
     *
     * @param x
     * @return
     */
    public int getInt(int x) {
        return bb.dataBuffer().getInt(x);
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
    public byte[] obtemBytes(int id, int campo){
        return null;
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
