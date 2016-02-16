package br.inf.ufg.fabrica.mr;

import br.inf.ufg.fabrica.mr.datatypes.*;

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
// , Encapsulated, Quantity,
        Text,
//        TimeSpecification,
        Uri
        // common
//        Archetyped,
        // support
//        Identification,
        // extra
//        Campo
{

    final int TYPE_SIZE = 1;
    final int BYTE_SIZE = 1;
    final int CHAR_SIZE = 1;
    final int BOOLEAN_SIZE = 2;
    final int SHORT_SIZE = 2;
    final int INT_SIZE = 4;
    final int FLOAT_SIZE = 4;
    final int DOUBLE_SIZE = 8;
    final int LONG_SIZE = 8;

    /**
     * Identificador do tipo DV_URI.
     */
    final int DV_URI = 1;

    /**
     * Identificador do tipo DV_EHR_URI.
     */
    final int DV_EHR_URI = 2;

    /**
     * Identificador do tipo DV_PARAGRAPH.
     */
    final int DV_PARAGRAPH = 3;

    /**
     * Identificador do tipo DV_TEXT.
     */
    final int DV_TEXT = 4;

    /**
     * Identificador do tipo CODE_PHRASE.
     */
    final int CODE_PHRASE = 5;

    /**
     * Identificador do tipo DV_CODED_TEXT.
     */
    final int DV_CODED_TEXT = 6;

    /**
     * Identificador do tipo TERM_MAPPING.
     */
    final int TERM_MAPPING = 7;

    /**
     * Identificador do tipo DV_BOOLEAN.
     */
    final int DV_BOOLEAN = 8;

    /**
     * Identificador do tipo DV_IDENTIFIER.
     */
    final int DV_IDENTIFIER = 9;

    /**
     * Identificador do tipo DV_STATE.
     */
    final int DV_STATE = 10;

    /**
     * Identificador do tipo DV_MULTIMEDIA.
     */
    final int DV_MULTIMEDIA = 11;

    /**
     * Identificador do tipo DV_PARSABLE.
     */
    final int DV_PARSABLE = 12;

    /**
     * Identificador do tipo DV_GENERAL_TIME_SPECIFICATION.
     */
    final int DV_GENERAL_TIME_SPECIFICATION = 13;

    /**
     * Identificador do tipo DV_PERIODIC_TIME_SPECIFICATION.
     */
    final int DV_PERIODIC_TIME_SPECIFICATION = 14;

    final int TERMINOLOGY_ID = 15;
}
