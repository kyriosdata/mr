package br.inf.ufg.fabrica.mr;

import br.inf.ufg.fabrica.mr.datatypes.Basic;
import br.inf.ufg.fabrica.mr.datatypes.Encapsulated;
import br.inf.ufg.fabrica.mr.datatypes.Text;
import br.inf.ufg.fabrica.mr.datatypes.Uri;

/**
 * Fábrica de objetos baseados no Modelo de Referência
 * do openEHR.
 * <p>
 * <p>Um objeto que implementa esta interface permite
 * criar um grafo de objetos em conformidade com o
 * Modelo de Referência do openEHR.</p>
 * <p>
 * <p>Uma implementação desta interface deve,
 * NECESSARIAMENTE, estar em conformidade com as
 * especificações (padrões) do Modelo de Referência
 * do openEHR.</p>
 * <p>
 * <p>Um objeto baseado no Modelo de Referência do openEHR
 * é um objeto em conformidade com as especificações
 * desse Modelo de Referência. Tais especificações são
 * acompanhadas de uma implementação de referência em
 * Java, disponível em
 * <a href="https://github.com/openEHR/java-libs">aqui</a>.
 * </p>
 * <p>
 * <p>A implementação desta interface é uma implementação
 * alternativa. Não é um <i>fork</i>,
 * nem trabalho derivado da implementação (citada acima).
 * Trata-se de uma nova implementação, que emprega
 * estratégia distinta.
 * </p>
 * <p>
 * <p>Um dos principais objetivos dessa implementação
 * é atender o uso do Modelo de Referência por dispositivos
 * móveis que, em geral, apresentam restrições de capacidade de
 * processamento e memória.</p>
 * <p>
 * <p>Neste sentido, duas decisões de projeto foram
 * estabelecidas: (a) reduzir o tamanho do arquivo jar
 * correspondente à implementação e (b) minimizar o espaço
 * exigido para guardar um grafo de objetos baseado no
 * openEHR.</p>
 * <p>
 * <p>Em decorrência das decisões acima duas orientações
 * são experimentadas: (a) não é criada uma classe para
 * cada conceito (conforme implmentação de referência) e
 * (b) toBytes são armazenados em um vetor de bytes que
 * serializa um grafo típico baseado na implementação
 * de referência.</p>
 * <p>
 * <h3>Visão geral da interface</h3>
 * <p>
 * <p>As operações {@link #obtemTexto(int, int)},
 * {@link #obtemVetorBytes(int, int)} e
 * {@link #obtemLogico(int, int)}, dentre outras similares
 * para os demais tipos primitivos, permitem recuperar um
 * valor primitivo, ou seja, um membro de algum objeto. Em
 * consequência, todos eles fazem uso de dois parâmetros. O
 * primeiro identifica o objeto e o segundo identifica o campo
 * que contém a informação desejada.</p>
 * <p>
 * <p>As operações acima permitem recuperar valores presentes
 * no grafo, enquanto as operações como
 * {@link #adicionaDvBoolean(boolean)} e
 * {@link #adicionaDvEhrUri(String)} permitem inserir tais
 * valores. A inserção, contudo, ao contrário da recuperaçào,
 * não ocorre por campo, mas por toda a coleção de valores
 * que formam um objeto.</p>
 */
public interface Mr extends
        // datatypes
        Basic,
        Encapsulated,
//        Quantity,
        Text,
//        TimeSpecification,
        Uri
//        Identification,
        // common
//        Archetyped
        // support
//        Identification
        // extra
//        Campo
{
    // ---- Type Size

    int TYPE_SIZE = 1;
    int BYTE_SIZE = 1;
    int BOOLEAN_SIZE = 1;
    int CHAR_SIZE = 2;
    int SHORT_SIZE = 2;
    int INT_SIZE = 4;
    int FLOAT_SIZE = 4;
    int DOUBLE_SIZE = 8;
    int LONG_SIZE = 8;
    int REF_SIZE = 1;

    // ---- Header

    /**
     * Root position in byte array
     */
    int ROOT_INDEX = 0;

    /**
     * List position in byte array
     */
    int LIST_INDEX = 4;

    // ---- Object Identifiers

    /**
     * Identificador do tipo DV_BOOLEAN.
     */
    int DV_BOOLEAN = 0;

    /**
     * Identificador do tipo DV_IDENTIFIER.
     */
    int DV_IDENTIFIER = 1;

    /**
     * Identificador do tipo DV_URI.
     */
    int DV_URI = 2;

    /**
     * Identificador do tipo DV_STATE.
     */
    int DV_STATE = 3;

    /**
     * Identificador do tipo DV_EHRURI.
     */
    int DV_EHR_URI = 4;

    /**
     * Identificador do tipo DV_TERMINOLOGY_ID.
     */
    int DV_TERMINOLOGY_ID = 5;

    /**
     * Identificador do tipo DV_CODE_PHRASE.
     */
    int DV_CODE_PHRASE = 6;

    /**
     * Identificador do tipo DV_PARSABLE.
     */
    int DV_PARSABLE = 7;

    /**
     * Identificador do tipo DV_MULTIMEDIA.
     */
    int DV_MULTIMEDIA = 8;

    /**
     * Identificador do tipo DV_TEXT.
     */
    int DV_TEXT = 9;

    /**
     * Identificador do tipo DV_CODED_TEXT.
     */
    int DV_CODED_TEXT = 10;

    /**
     * Identificador do tipo DV_PARAGRAPH.
     */
    int DV_PARAGRAPH = 11;

    /**
     * Identificador do tipo TERM_MAPPING.
     */
    int TERM_MAPPING = 12;

    /**
     * Identificador do tipo CODE_PHRASE.
     */
    int CODE_PHRASE = 13;

    /**
     * Identificador do tipo DV_ORDERED.
     */
    int DV_ORDERED = 14;

    /**
     * Identificador do tipo DV_INTERVAL.
     */
    int DV_INTERVAL = 15;

    /**
     * Identificador do tipo REFERENCE_RANGE.
     */
    int REFERENCE_RANGE = 16;

    /**
     * Identificador do tipo DV_ORDINAL.
     */
    int DV_ORDINAL = 17;

    /**
     * Identificador do tipo DV_QUANTIFIED.
     */
    int DV_QUANTIFIED = 18;

    /**
     * Identificador do tipo DV_AMOUNT.
     */
    int DV_AMOUNT = 19;

    /**
     * Identificador do tipo DV_QUANTITY.
     */
    int DV_QUANTITY = 20;

    /**
     * Identificador do tipo DV_COUNT.
     */
    int DV_COUNT = 21;

    /**
     * Identificador do tipo DV_PROPORTION.
     */
    int DV_PROPORTION = 22;

    /**
     * Identificador do tipo PROPORTION_KIND.
     */
    int PROPORTION_KIND = 23;

    /**
     * Identificador do tipo DV_ABSOLUTE_QUANTITY.
     */
    int DV_ABSOLUTE_QUANTITY = 24;

    /**
     * Identificador do tipo DV_TEMPORAL.
     */
    int DV_TEMPORAL = 25;

    /**
     * Identificador do tipo DV_DATE.
     */
    int DV_DATE = 26;

    /**
     * Identificador do tipo DV_TIME.
     */
    int DV_TIME = 27;

    /**
     * Identificador do tipo DV_DATE_TIME.
     */
    int DV_DATE_TIME = 28;

    /**
     * Identificador do tipo DV_DURATION.
     */
    int DV_DURATION = 29;

    /**
     * Identificador do tipo DV_TIME_SPECIFICATION.
     */
    int DV_TIME_SPECIFICATION = 30;

    /**
     * Identificador do tipo DV_PERIODIC_TIME_SPECIFICATION.
     */
    int DV_PERIODIC_TIME_SPECIFICATION = 31;

    /**
     * Identificador do tipo DV_GENERAL_TIME_SPECIFICATION.
     */
    int DV_GENERAL_TIME_SPECIFICATION = 32;

    /**
     * Identificador do tipo DV_ENCAPSULATED.
     */
    int DV_ENCAPSULATED = 33;

    /**
     * Identificador do tipo GENERIC_ENTRY.
     */
    int GENERIC_ENTRY = 34;

    /**
     * Identificador do tipo PARTY.
     */
    int PARTY = 35;

    /**
     * Identificador do tipo ROLE.
     */
    int ROLE = 36;

    /**
     * Identificador do tipo PARTY_RELATIONSHIP.
     */
    int PARTY_RELATIONSHIP = 37;

    /**
     * Identificador do tipo PARTY_IDENTITY.
     */
    int PARTY_IDENTITY = 38;

    /**
     * Identificador do tipo CONTACT.
     */
    int CONTACT = 39;

    /**
     * Identificador do tipo ADDRESS.
     */
    int ADDRESS = 40;

    /**
     * Identificador do tipo CAPABILITY.
     */
    int CAPABILITY = 41;

    /**
     * Identificador do tipo ACTOR.
     */
    int ACTOR = 42;

    /**
     * Identificador do tipo PERSON.
     */
    int PERSON = 43;

    /**
     * Identificador do tipo ORGANIZATION.
     */
    int ORGANIZATION = 44;

    /**
     * Identificador do tipo GROUP.
     */
    int GROUP = 45;

    /**
     * Identificador do tipo AGENT.
     */
    int AGENT = 46;

    /**
     * Identificador do tipo EXTERNAL_ENVIROMENT_ACCESS.
     */
    int EXTERNAL_ENVIROMENT_ACCESS = 47;

    /**
     * Identificador do tipo UID.
     */
    int UID = 48;

    /**
     * Identificador do tipo ISO_OID.
     */
    int ISO_OID = 49;

    /**
     * Identificador do tipo UUID.
     */
    int UUID = 50;

    /**
     * Identificador do tipo INTERNET_ID.
     */
    int INTERNET_ID = 51;

    /**
     * Identificador do tipo OBJECT_ID.
     */
    int OBJECT_ID = 52;

    /**
     * Identificador do tipo UID_BASED_ID.
     */
    int UID_BASED_ID = 53;

    /**
     * Identificador do tipo HIER_OBJECT_ID.
     */
    int HIER_OBJECT_ID = 54;

    /**
     * Identificador do tipo OBJECT_VERSION_ID.
     */
    int OBJECT_VERSION_ID = 55;

    /**
     * Identificador do tipo VERSION_TREE_ID.
     */
    int VERSION_TREE_ID = 56;

    /**
     * Identificador do tipo ARCHETYPE_ID.
     */
    int ARCHETYPE_ID = 57;

    /**
     * Identificador do tipo TEMPLATE_ID.
     */
    int TEMPLATE_ID = 58;

    /**
     * Identificador do tipo TERMINOLOGY_ID.
     */
    int TERMINOLOGY_ID = 59;

    /**
     * Identificador do tipo GENERIC_ID.
     */
    int GENERIC_ID = 60;

    /**
     * Identificador do tipo OBJECT_REF.
     */
    int OBJECT_REF = 61;

    /**
     * Identificador do tipo ACCESS_GROUP_REF.
     */
    int ACCESS_GROUP_REF = 62;

    /**
     * Identificador do tipo PARTY_REF.
     */
    int PARTY_REF = 63;

    /**
     * Identificador do tipo LOCATABLE_REF.
     */
    int LOCATABLE_REF = 64;

    /**
     * Identificador do tipo TERMINOLOGY_SERVICE.
     */
    int TERMINOLOGY_SERVICE = 65;

    /**
     * Identificador do tipo TERMINOLOGY_ACCESS.
     */
    int TERMINOLOGY_ACCESS = 66;

    /**
     * Identificador do tipo CODE_SET_ACCESS.
     */
    int CODE_SET_ACCESS = 67;

    /**
     * Identificador do tipo OPENEHR_TERMINOLOGY_GROUP.
     */
    int OPENEHR_TERMINOLOGY_GROUP = 68;

    /**
     * Identificador do tipo OPENEHR_CODE_SET_IDENTIFIERS.
     */
    int OPENEHR_CODE_SET_IDENTIFIERS = 69;

    /**
     * Identificador do tipo TERMINOLOGY_SERVICES.
     */
    int TERMINOLOGY_SERVICES = 70;

    /**
     * Identificador do tipo OPENEHR_DEFINITIONS.
     */
    int OPENEHR_DEFINITIONS = 71;

    /**
     * Identificador do tipo BASIC_DEFINITIONS.
     */
    int BASIC_DEFINITIONS = 72;

    /**
     * Identificador do tipo EHR.
     */
    int EHR = 73;

    /**
     * Identificador do tipo VESRIONED_EHR_ACCESS.
     */
    int VERSIONED_EHR_ACCESS = 74;

    /**
     * Identificador do tipo EHR_ACCESS.
     */
    int EHR_ACCESS = 75;

    /**
     * Identificador do tipo VERSIONED_EHR_STATUS.
     */
    int VERSIONED_EHR_STATUS = 76;

    /**
     * Identificador do tipo EHR_STATUS.
     */
    int EHR_STATUS = 77;

    /**
     * Identificador do tipo VERSIONED_COMPOSTION.
     */
    int VERSIONED_COMPOSITION = 78;

    /**
     * Identificador do tipo COMPOSITION.
     */
    int COMPOSITION = 79;

    /**
     * Identificador do tipo EVENT_CONTEXT.
     */
    int EVENT_CONTEXT = 80;

    /**
     * Identificador do tipo CONTENT_ITEM.
     */
    int CONTENT_ITEM = 81;

    /**
     * Identificador do tipo SECTION.
     */
    int SECTION = 82;

    /**
     * Identificador do tipo ENTRY.
     */
    int ENTRY = 83;

    /**
     * Identificador do tipo ADMIN_ENTRY.
     */
    int ADMIN_ENTRY = 84;

    /**
     * Identificador do tipo CARE_ENTRY.
     */
    int CARE_ENTRY = 85;

    /**
     * Identificador do tipo OBSERVATION.
     */
    int OBSERVATION = 86;

    /**
     * Identificador do tipo EVALUATION.
     */
    int EVALUATION = 87;

    /**
     * Identificador do tipo INSTRUCTION.
     */
    int INSTRUCTION = 88;

    /**
     * Identificador do tipo ACTIVITY.
     */
    int ACTIVITY = 89;

    /**
     * Identificador do tipo ACTION.
     */
    int ACTION = 90;

    /**
     * Identificador do tipo INSTRUCTION_DETAILS.
     */
    int INSTRUCTION_DETAILS = 91;

    /**
     * Identificador do tipo ISM_TRANSITION.
     */
    int ISM_TRANSITION = 92;

    /**
     * Identificador do tipo PATHABLE.
     */
    int PATHABLE = 93;

    /**
     * Identificador do tipo DATA_VALUE.
     */
    int DATA_VALUE = 94;

    /**
     * Identificador do tipo LOCATABLE.
     */
    int LOCATABLE = 95;

    /**
     * Identificador do tipo ARCHETYPED.
     */
    int ARCHETYPED = 96;

    /**
     * Identificador do tipo LINK.
     */
    int LINK = 97;

    /**
     * Identificador do tipo FEEDER_AUDIT.
     */
    int FEEDER_AUDIT = 98;

    /**
     * Identificador do tipo FEEDER_AUDIT_DETAILS.
     */
    int FEEDER_AUDIT_DETAILS = 100;

    /**
     * Identificador do tipo PARTY_PROXY.
     */
    int PARTY_PROXY = 101;

    /**
     * Identificador do tipo PARTY_SELF.
     */
    int PARTY_SELF = 102;

    /**
     * Identificador do tipo PARTY_IDENTIFIED.
     */
    int PARTY_IDENTIFIED = 103;

    /**
     * Identificador do tipo PARTY_RELATED.
     */
    int PARTY_RELATED = 104;

    /**
     * Identificador do tipo PARTICIPATION.
     */
    int PARTICIPATION = 105;

    /**
     * Identificador do tipo AUDIT_DETAILS.
     */
    int AUDIT_DETAILS = 106;

    /**
     * Identificador do tipo ATTESTATION.
     */
    int ATTESTATION = 107;

    /**
     * Identificador do tipo REVISION_HISTORY.
     */
    int REVISION_HISTORY = 108;

    /**
     * Identificador do tipo REVISION_HISTORY_ITEM.
     */
    int REVISION_HISTORY_ITEM = 109;

    /**
     * Identificador do tipo VERSIONED_FOLDER.
     */
    int VERSIONED_FOLDER = 110;

    /**
     * Identificador do tipo FOLDER.
     */
    int FOLDER = 111;

    /**
     * Identificador do tipo VERSIONED_OBJECT.
     */
    int VERSIONED_OBJECT = 112;

    /**
     * Identificador do tipo VERSION.
     */
    int VERSION = 113;

    /**
     * Identificador do tipo ORIGINAL_VERSION.
     */
    int ORIGINAL_VERSION = 114;

    /**
     * Identificador do tipo IMPORTED_VERSION.
     */
    int IMPORTED_VERSION = 115;

    /**
     * Identificador do tipo CONTRIBUTION.
     */
    int CONTRIBUTION = 116;

    /**
     * Identificador do tipo AUTORED_RESOURCE.
     */
    int AUTHORED_RESOURCE = 117;

    /**
     * Identificador do tipo TRANSLATION_DETAILS.
     */
    int TRANSLATION_DETAILS = 118;

    /**
     * Identificador do tipo RESOURCE_DESCRIPTION.
     */
    int RESOURCE_DESCRIPTION = 119;

    /**
     * Identificador do tipo RESOURCE_DESCRIPTION_ITEM.
     */
    int RESOURCE_DESCRIPTION_ITEM = 120;

    /**
     * Identificador do tipo DATA_STRUCTURE.
     */
    int DATA_STRUCTURE = 121;

    /**
     * Identificador do tipo ITEM_STRUCTURE.
     */
    int ITEM_STRUCTURE = 122;

    /**
     * Identificador do tipo ITEM_SINGLE.
     */
    int ITEM_SINGLE = 123;

    /**
     * Identificador do tipo ITEM_LIST.
     */
    int ITEM_LIST = 124;

    /**
     * Identificador do tipo ITEM_TABLE.
     */
    int ITEM_TABLE = 125;

    /**
     * Identificador do tipo ITEM_TREE.
     */
    int ITEM_TREE = 126;

    /**
     * Identificador do tipo ITEM.
     */
    int ITEM = 127;

    /**
     * Identificador do tipo CLUSTER.
     */
    int CLUSTER = 128;

    /**
     * Identificador do tipo ELEMENT.
     */
    int ELEMENT = 129;

    /**
     * Identificador do tipo HISTORY.
     */
    int HISTORY = 130;

    /**
     * Identificador do tipo EVENT.
     */
    int EVENT = 131;

    /**
     * Identificador do tipo POINT_EVENT.
     */
    int POINT_EVENT = 132;

    /**
     * Identificador do tipo INTERVAL_EVENT.
     */
    int INTERVAL_EVENT = 133;

    /**
     * Identificador do tipo EXTRACT_REQUEST.
     */
    int EXTRACT_REQUEST = 134;

    /**
     * Identificador do tipo EXTRACT_ACTION_REQUEST.
     */
    int EXTRACT_ACTION_REQUEST = 135;

    /**
     * Identificador do tipo EXTRACT_SPEC.
     */
    int EXTRACT_SPEC = 136;

    /**
     * Identificador do tipo EXTRACT_MANIFEST.
     */
    int EXTRACT_MANIFEST = 137;

    /**
     * Identificador do tipo EXTRACT_ENTITY_MANIFEST.
     */
    int EXTRACT_ENTITY_MANIFEST = 138;

    /**
     * Identificador do tipo EXTRACT_VERSION_SPEC.
     */
    int EXTRACT_VERSION_SPEC = 139;

    /**
     * Identificador do tipo EXTRACT_UPDATE_SPEC.
     */
    int EXTRACT_UPDATE_SPEC = 140;

    /**
     * Identificador do tipo EXTRACT.
     */
    int EXTRACT = 141;

    /**
     * Identificador do tipo EXTRACT_CHAPTER.
     */
    int EXTRACT_CHAPTER = 142;

    /**
     * Identificador do tipo EXTRACT_ENTITY_CHAPTER.
     */
    int EXTRACT_ENTITY_CHAPTER = 143;

    /**
     * Identificador do tipo EXTRACT_ITEM.
     */
    int EXTRACT_ITEM = 144;

    /**
     * Identificador do tipo EXTRACT_FOLDER.
     */
    int EXTRACT_FOLDER = 145;

    /**
     * Identificador do tipo EXTRACT_CONTENT_ITEM.
     */
    int EXTRACT_CONTENT_ITEM = 146;

    /**
     * Identificador do tipo EXTRACT_PARTICIPATION.
     */
    int EXTRACT_PARTICIPATION = 147;

    /**
     * Identificador do tipo OPENEHR_CONTENT_ITEM.
     */
    int OPENEHR_CONTENT_ITEM = 148;

    /**
     * Identificador do tipo X_VERSIONED_OBJECT.
     */
    int X_VERSIONED_OBJECT = 149;

    /**
     * Identificador do tipo GENERIC_CONTENT_ITEM.
     */
    int GENERIC_CONTENT_ITEM = 150;

    /**
     * Identificador do tipo SYNC_EXTRACT_REQUEST.
     */
    int SYNC_EXTRACT_REQUEST = 151;

    /**
     * Identificador do tipo SYNC_EXTRACT.
     */
    int SYNC_EXTRACT = 152;

    /**
     * Identificador do tipo SYNC_EXTRACT_SPEC.
     */
    int SYNC_EXTRACT_SPEC = 153;

    /**
     * Identificador do tipo X_CONTRIBUTION.
     */
    int X_CONTRIBUTION = 154;

    /**
     * Identificador do tipo ADDRESSED_MESSAGE.
     */
    int ADDRESSED_MESSAGE = 155;

    /**
     * Identificador do tipo MESSAGE.
     */
    int MESSAGE = 156;
    
    byte[] getHeader();
    
    int getRef(int x);

    int getType(int x);

    /**
     * Get a byte
     *
     * @param x
     * @return
     */
    byte getByte(int x);

    /**
     * Get a char
     *
     * @param x
     * @return
     */
    char getChar(int x);

    /**
     * Get boolean
     *
     * @param x
     * @return
     */
    boolean getBoolean(int x);

    /**
     * Get an int
     *
     * @param x
     * @return
     */
    int getInt(int x);

    /**
     * Get a float
     *
     * @param x
     * @return
     */
    float getFloat(int x);
    
    /**
     * Get a double
     *
     * @param x
     * @return
     */
    double getDouble(int x);
    
    /**
     * Get a long
     *
     * @param x
     * @return
     */
    double getLong(int x);
    
    /**
     * Get a string
     *
     * @param x
     * @return
     */
    String getString(int x);
    
    /**
     * Get string length
     *
     * @param x
     * @return
     */
    int getStringLength(int x);
    
    
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
    int obtemQtdeBytes(int id, int campo);
    
    /**
    * Recupera parte do campo do objeto,
    * conforme a capacidade de memória suportada.
    *
    * @param id O identificador único do objeto.
    * @param campo A ordem do campo, iniciada por 0.
    * @param ini A posição do byte inicial.
    * @param fim A posição do byte
    *
    * @return Parte do campo do objeto.
    *
    * @throws IllegalArgumentException Nos seguintes casos:
    * (a) o objeto não existe;
    * (b) o campo não existe;
    * (c) ini negativo; (d) ini maior do que fim;
    * (e) fim maior do que o tamanho total do campo.
    */
    byte[] obtemBytes(int id, int campo, int ini, int fim);
    
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
    byte[] obtemBytes(int id, int campo);
    
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
    * @param O identificador único da raiz.
    *
    * @throws IllegalArgumentException O objeto raiz não existe.
    */
    void defineRaiz(int raiz);
    
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
    int obtemRaiz();
    
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
    int totalObjetos();
    
    /**
    * Retorna inteiro que identifica o tipo do objeto
    * identificado.
    * @param id O identificador do objeto.
    * @return Valor inteiro correspondente ao tipo do
    * objeto.
    */
    int obtemTipo(int id);
        
    /**
    * Retorna o tamanho da lista de objetos.
    *
    * @param lista Identificador da lista.
    * @throws IllegalArgumentException a lista não existe.
    */
    int obtemTamanhoLista(int lista);
    
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
    int buscaEmLista(int lista, int objeto);
    
    /**
    * Elimina o objeto.
    *
    * <p>Este método é particularmente útil
    * durante uma busca, onde um objeto foi
    * construído especificamente para esta
    * dade.</p>
    *
    * @param objeto Identificador do objeto
    * a ser eliminado.
    */
    void elimineObjeto(int objeto);
}
