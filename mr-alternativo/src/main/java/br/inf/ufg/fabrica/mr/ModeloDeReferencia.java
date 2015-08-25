package br.inf.ufg.fabrica.mr;

/**
 * Fábrica de objetos baseados no Modelo de Referência
 * do openEHR.
 * <p/>
 * <p>Um objeto que implementa esta interface permite
 * criar um grafo de objetos em conformidade com o
 * Modelo de Referência do openEHR.</p>
 * <p/>
 * <p>Uma implementação desta interface deve,
 * NECESSARIAMENTE, estar em conformidade com as
 * especificações (padrões) do Modelo de Referência
 * do openEHR.</p>
 * <p/>
 * <p>Um objeto baseado no Modelo de Referência do openEHR
 * é um objeto em conformidade com as especificações
 * desse Modelo de Referência. Tais especificações são
 * acompanhadas de uma implementação de referência em
 * Java, disponível em
 * <a href="https://github.com/openEHR/java-libs">aqui</a>.
 * </p>
 * <p/>
 * <p>A implementação desta interface é uma implementação
 * alternativa. Não é um <i>fork</i>,
 * nem trabalho derivado da implementação (citada acima).
 * Trata-se de uma nova implementação, que emprega
 * estratégia distinta.
 * </p>
 * <p/>
 * <p>Um dos principais objetivos dessa implementação
 * é atender o uso do Modelo de Referência por dispositivos
 * móveis que, em geral, apresentam restrições de capacidade de
 * processamento e memória.</p>
 * <p/>
 * <p>Neste sentido, duas decisões de projeto foram
 * estabelecidas: (a) reduzir o tamanho do arquivo jar
 * correspondente à implementação e (b) minimizar o espaço
 * exigido para guardar um grafo de objetos baseado no
 * openEHR.</p>
 * <p/>
 * <p>Em decorrência das decisões acima duas orientações
 * são experimentadas: (a) não é criada uma classe para
 * cada conceito (conforme implmentação de referência) e
 * (b) toBytes são armazenados em um vetor de bytes que
 * serializa um grafo típico baseado na implementação
 * de referência.</p>
 * <p/>
 * <h3>Visão geral da interface</h3>
 * <p/>
 * <p>As operações {@link #obtemTexto(int, int)},
 * {@link #obtemVetorBytes(int, int)} e
 * {@link #obtemValorLogico(int, int)}, dentre outras similares
 * para os demais tipos primitivos, permitem recuperar um
 * valor primitivo, ou seja, um membro de algum objeto. Em
 * consequência, todos eles fazem uso de dois parâmetros. O
 * primeiro identifica o objeto e o segundo identifica o campo
 * que contém a informação desejada.</p>
 * <p/>
 * <p>As operações acima permitem recuperar valores presentes
 * no grafo, enquanto as operações como
 * {@link #adicionaDvBoolean(boolean)} e
 * {@link #adicionaDvEhrUri(String)} permitem inserir tais
 * valores. A inserção, contudo, ao contrário da recuperaçào,
 * não ocorre por campo, mas por toda a coleção de valores
 * que formam um objeto.</p>
 */
public interface ModeloDeReferencia {

    final int AUTHORED_RESOURCE = 100;
    final int REVISION_HISTORY_ITEM = 101;
    final int REVISION_HISTORY = 102;
    final int AUDITY_DETAILS = 103;
    final int ATTESTATION = 104;
    final int TEMPLATE_ID = 105;
    final int TERMINOLOGY_ID = 106;
    final int LINK = 107;
    final int GENERIC_ID = 108;
    final int OBJECT_ID = 109;
    final int ARCHETYPE_ID = 110;
    final int UID_BASED_ID = 111;
    final int HIER_OBJECT_ID = 112;
    final int OBJECT_VERSION_ID = 113;
    final int ISM_TRANSITION = 114;
    final int OBJECT_REF = 115;
    final int ACCESS_GROUP_REF = 116;
    final int PARTY_REF = 117;
    final int LOCATABLE_REF = 118;
    final int TRANSLATIONDETAILS = 119;
    final int VERSION = 120;
    final int ORIGINALVERSION = 121;
    final int IMPORTED_VERSION = 122;
    final int PATHABLE = 123;
    final int LOCATABLE = 124;
    final int DATA_STRUCTURE = 125;
    final int HISTORY = 126;
    final int ITEM_STRUCTURE = 127;
    final int ITEM_TREE = 128;
    final int ITEM_LIST = 129;
    final int ITEM_TABLE = 130;
    final int ITEM_SINGLE = 131;
    final int ITEM = 132;
    final int ELEMENT = 133;
    final int CLUSTER = 134;
    final int FOLDER = 135;
    final int PARTY_RELATIONSHIP = 136;
    final int XFOLDER = 137;
    final int COMPOSITION = 138;
    final int ADDRESS = 139;
    final int PARTY = 140;
    final int ROLE = 141;
    final int ACTOR = 142;
    final int AGENT = 143;
    final int PERSON = 144;
    final int GROUP = 145;
    final int ORGANISATION = 146;
    final int EHR_STATUS = 147;
    final int ACTIVITY = 148;
    final int EVENT = 149;
    final int INTERVAL_EVENT = 150;
    final int POINT_EVENT = 151;
    final int MESSAGE_CONTENT = 152;
    final int EHR_ACCESS = 153;
    final int PARTY_IDENTITY = 154;
    final int CONTENT_ITEM = 155;
    final int ENTRY = 156;
    final int ADMIN_ENTRY = 157;
    final int CARE_ENTRY = 158;
    final int OBSERVATION = 159;
    final int INSTRUCTION = 160;
    final int ACTION = 161;
    final int EVALUTATION = 162;
    final int SECTION = 163;
    final int GENERIC_ENTRY = 164;
    final int CAPABILITY = 165;
    final int CONTACT = 166;
    final int PARTY_IDENTIFIED = 167;
    final int PARTY_RELATED = 168;
    final int PART_PROXY = 169;
    final int PARTY_SELF = 170;
    final int RESOURCE_DESCRIPTION_ITEM = 171;
    final int FEEDER_AUDIT = 172;
    final int EHR = 173;
    final int VERSION_TREE_ID = 174;


    // TODO acrescente uma constante para todos os demais tipos

    /**
     * Dados propriamente ditos correspondentes a objetos
     * compatíveis com o Modelo de Referência.
     * <p/>
     * <p>Este vetor de bytes mantém os dados correspondentes
     * a um grafo de objetos, baseados no Modelo de Referência,
     * conforme o modelo de dados estabelecido pela
     * implementação da presente interface.</p>
     * <p/>
     * <p>Um acréscimo de um elemento de dado é
     * serializado neste vetor. Metainformações
     * correspondentes devem ser registradas em
     * outra estrutura.</p>
     * <p/>
     * <p>A estrutura desta sequência de bytes é
     * obtida por {@code #estrutura}.</p>
     * <p/>
     * <p>O retorno deste método, em geral, é persistido.
     * Quando uma consulta aos dados correspondentes
     * for necessária, será "consumido" pelo
     * método {@link #fromBytes(byte[])}.</p>
     *
     * @return Vetor de bytes contendo uma instância
     * do Modelo de Referência (MR) devidamente serializada.
     * @see #fromBytes(byte[])
     * @see #toJSON()
     * @see #toXML()
     */
    byte[] toBytes();

    /**
     * Realiza processo inverso à serialização, geralmente
     * empregado para permitir busca sobre os dados em
     * conformidade com o Modelo de Referência.
     *
     * @param bytes Vetor de bytes serializados por meio
     *              do método {@link #toBytes()}.
     */
    void fromBytes(byte[] bytes);

    /**
     * Serializa as informações do presente objeto, baseado
     * no MR, em um documento XML.
     * <p/>
     * <p>O documento XML produzido pelo presente método,
     * sequência de caracteres, deve estar em conformidade
     * com os esquemas adotados pelo openEHR.</p>
     *
     * @return Documento XML correspondente ao grafo
     * de objetos.
     */
    String toXML();

    /**
     * Cria um grafo de objetos, em conformidade com o
     * Modelo de Referência, correspondente ao documento
     * XML fornecido.
     *
     * @param xml Documento XML contendo grafo de objetos
     *            baseados no Modelo de Referência.
     */
    void fromXML(String xml);

    /**
     * Serializa a instância em uma sequência de caracteres
     * no formato JSON.
     *
     * @return Sequência de caracteres, no formato JSON,
     * correspondente à serialização do presente objeto.
     * @see #fromJSON(String)
     * @see #toBytes()
     * @see #toXML()
     */
    String toJSON();

    /**
     * Cria o grafo de objetos, representado pelo presente
     * objeto, em conformidade com o Modelo de Referência e
     * serializado em JSON.
     * <p/>
     * <p>Este método faz o processo inverso ao do método
     * {@see #toJSON}.</p>
     *
     * @param json Sequência de caracteres, no formato JSON,
     *             correspondentes a um grafo de objetos
     *             serializado do Modelo de Referência do
     *             openEHR.
     * @see #toJSON()
     * @see #fromXML(String)
     * @see #fromBytes(byte[])
     */
    void fromJSON(String json);

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
    int totalObjetos();

    /**
     * Retorna inteiro que identifica o tipo do objeto
     * identificado.
     *
     * @param id O identificador do objeto.
     * @return Valor inteiro correspondente ao tipo do
     * objeto.
     */
    int obtemTipo(int id);

    /**
     * Recupera o valor lógico do objeto.
     *
     * @param id    O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cujo valor lógico é desejado.
     * @return Valor lógico do campo do objeto.
     * @throws IllegalArgumentException Se pelo menos uma das
     *                                  condições abaixo for verificada:
     *                                  (a) o campo não é do tipo lógico; (b) o campo não existe;
     *                                  (c) o objeto não existe.
     * @see #obtemTipo(int)
     * @see #obtemTexto(int, int)
     * @see #obtemVetorBytes(int, int)
     */
    boolean obtemValorLogico(int id, int campo);

    /**
     * Recupera texto do objeto.
     *
     * @param id    O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, para o
     *              campo cuja sequência de caracteres
     *              correspondente é desejada.
     * @return Sequência de caracteres correspondente ao
     * campo do objeto.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) o campo não é texto; (b) o campo não existe;
     *                                  (c) o objeto não existe.
     */
    String obtemTexto(int id, int campo);

    /**
     * Recupera vetor de bytes (valor do campo do objeto).
     *
     * @param id    O identificador único do objeto.
     * @param campo A ordem do campo, iniciada por 0, cujo
     *              valor, um vetor de bytes, é desejado.
     * @return Valor do campo do objeto.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) o campo não é texto; (b) o campo não existe;
     *                                  (c) o objeto não existe.
     * @see #obtemTexto(int, int)
     * @see #obtemTipo(int)
     */
    byte[] obtemVetorBytes(int id, int campo);

    /**
     * Adiciona um valor lógico ({@code DV_BOOLEAN}).
     *
     * @param valor Valor lógico (DV_BOOLEAN) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     * @see #obtemValorLogico(int, int)
     */
    int adicionaDvBoolean(boolean valor);

    /**
     * Adiciona um identificador ({@code DV_IDENTIFIER}).
     *
     * @param issuer   Entidade que emite identificação.
     * @param assigner Entidade que assina identificação.
     * @param id       Identificador propriamente dito.
     * @param type     Tipo da identificação.
     * @return O identificador único deste identificador
     * na estrutura.
     */
    int adicionaDvIdentifier(
            String issuer,
            String assigner,
            String id,
            String type);

    /**
     * Adiciona um {@link java.net.URI} ({@code DV_URI}).
     *
     * @param uri Sequência de caracteres correspondentes
     *            à {@link java.net.URI}.
     * @return O identificador único desta URI na estrutura.
     */
    int adicionaDvUri(String uri);

    /**
     * Adiciona um {@link java.net.URI} cujo esquema é
     * "ehr" ({@code DvEHRURI}).
     *
     * @param uri Sequência de caracteres correspondentes
     *            à {@link java.net.URI}.
     * @return O identificador único desta DvEHRURI na estrutura.
     */
    int adicionaDvEhrUri(String uri);

    /**
     * Adiciona um identificador de terminologia
     * ({@code TERMINOLOGY_ID}).
     *
     * @param nome   Nome da terminologia.
     * @param versao Versão da terminologia.
     * @return O identificador único do identificador de
     * terminologia na estrutura.
     */
    int adicionaTerminologyId(String nome, String versao);

    /**
     * Adiciona um identificador de terminologia
     * ({@code TERMINOLOGY_ID}).
     *
     * @param valor Sequência de caracteres que é uma
     *              serialização de um identificador de
     *              terminologia ({TERMINOLOGY_ID}).
     * @return O identificador único deste identificador de
     * terminologia na estrutura.
     */
    int adicionaTerminologyId(String valor);

    /**
     * Adiciona um código ({@code CODE_PHRASE}).
     *
     * @param terminologyId Um identificador de terminologia.
     * @param codeString    A sequência correspondente ao código.
     * @return O identificador único do código na estrutura.
     */
    int adicionaCodePhrase(String terminologyId, String codeString);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param valor      Dado encapsulado propriamente dito.
     * @param formalismo Formalismo empregado pelo encapsulamento.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(String valor, String formalismo);

    /**
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param codePhraseCharSet  A codificação empregada pelo
     *                           dado encapsulado.
     * @param codePhraseLanguage A linguagem empregada pelo
     *                           dado encapsulado.
     * @param valor              O dado encapsulado propriamente dito.
     * @param formalismo         O formalismo empregado pelo dado
     *                           encapsulado.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(
            String codePhraseCharSet,
            String codePhraseLanguage,
            String valor,
            String formalismo);

    /**
     * Adiciona dado codificado
     * ({@code DV_MULTIMEDIA}).
     *
     * @param codePhraseCharSet              A codificação empregada.
     * @param codePhraseLinguagem            A linguagem empregada.
     * @param textoAlternativo               Texto alternativo para os dados.
     * @param codePhraseTipoMidia            A codificação do tipo de mídia.
     * @param codePhraseAlgoritmoCompressao  O algoritmo de
     *                                       compressão empregado.
     * @param integridade                    A sequência de bytes que serve para
     *                                       verificar a integridade dos dados.
     * @param codePhraseAlgoritmoIntegridade O algoritmo de
     *                                       verificação de
     *                                       integridade dos
     *                                       dados.
     * @param hDvMultimediaThumbnail         O identificador único de
     *                                       dados codificados que serve
     *                                       como representação
     *                                       comprimida do presente
     *                                       dado codificado.
     * @param dvUri                          Sequência de caracteres que é a URI do
     *                                       dado codificado.
     * @param dado                           O dado codificado propriamente dito.
     * @return O identificador únido do dado codificado.
     */
    int adicionaDvMultimedia(
            String codePhraseCharSet,
            String codePhraseLinguagem,
            String textoAlternativo,
            String codePhraseTipoMidia,
            String codePhraseAlgoritmoCompressao,
            byte[] integridade,
            String codePhraseAlgoritmoIntegridade,
            int hDvMultimediaThumbnail,
            String dvUri,
            byte[] dado);
}
