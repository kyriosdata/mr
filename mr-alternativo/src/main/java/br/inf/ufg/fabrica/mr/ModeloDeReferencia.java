package br.inf.ufg.fabrica.mr;

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
public interface ModeloDeReferencia extends Uri, Text, Serializacao, Campo, Identification {

    /**
     * Identificador do tipo DV_BOOLEAN.
     */
    final int DV_BOOLEAN = 0;

    /**
     * Identificador do tipo DV_IDENTIFIER.
     */
    final int DV_IDENTIFIER = 1;

    // TODO acrescente uma constante para todos os demais tipos

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
    int obtemQtdeBytes(int id, int campo);

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
    byte[] obtemBytes(int id, int campo, int ini, int fim);

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
    byte[] obtemBytes(int id, int campo);

    /**
     * Define a raiz do presente objeto.
     * <p>
     * <p>Uma instância desta interface é um grafo com uma
     * raiz única. Após todos os objetos serem adicionados
     * ao grafo, partindo dos objetos "primitivos" até o objeto
     * de mais "alto nível" (raiz), este método deve ser chamado
     * a fim de guardar a identificação da raiz. Isso possibilita
     * que seja estabelecido um ponto de acesso único ao grafo
     * para uma posterior remontagem.</p>
     *
     * @param O identificador único da raiz.
     * @throws IllegalArgumentException O objeto raiz não existe.
     * @see #obtemRaiz()
     */
    void defineRaiz(int raiz);

    /**
     * Obtém o identificador da raiz do presente objeto.
     * <p>
     * <p>Este método retorna o identificador que determina
     * o ponto inicial para remontagem do grafo de objetos,
     * conforme a especificação do Modelo de Referência.</p>
     *
     * @return O identificador único da raiz.
     * @see #defineRaiz(int)
     */
    int obtemRaiz();

    /**
     * Obtém o total de objetos, instâncias de elementos
     * do Modelo de Referência, ocupados pelo presente
     * objeto.
     * <p>
     * <p>Uma instância desta interface é um grafo de
     * objetos. O presente método permite identificar
     * quantos objetos fazem parte deste grafo.</p>
     * <p>
     * <p>Objeto aqui deve ser interpretado como
     * instância de "classe" do Modelo de Referência
     * do openEHR. Ou seja, não necessariamente este valor
     * é quantidade de instâncias de classes em Java
     * empregadas para representar o presente grafo de
     * objetos.</p>
     * <p>
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
     * Cria uma lista de objetos.
     * Note que na montagem do grafo de objetos todos os
     * objetos "filhos" devem ser adicionados antes de se
     * adicionar o "pai" ao grafo. Logo, o tamanho da lista
     * é fixo porque todos seus objetos são previamente
     * conhecidos/adicionados.
     *
     * @param quantidade Quantidade de objetos da lista.
     * @return Identificador único da lista.
     */
    int adicionaLista(int quantidade);

    /**
     * Adiciona um item à lista.
     *
     * @param lista Lista de objetos a ser adicionada
     *              de um item.
     * @param item  Identificador do objeto a ser
     *              inserido na lista.
     * @return Identificador único do item na lista.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) a lista não existe; (b) o item não existe.
     */
    int adicionaItem(int lista, int item);

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
    int buscaEmLista(int lista, int objeto);

    /**
     * Elimina o objeto.
     * <p>
     * <p>Este método é particularmente útil
     * durante uma busca, onde um objeto foi
     * construído especificamente para esta
     * finalidade.</p>
     *
     * @param objeto Identificador do objeto
     *               a ser eliminado.
     */
    void elimineObjeto(int objeto);

    /**
     * Cria um dicionário (<i>hash table</i>).
     * <p>
     * <p>Um dicionário é tratado como uma combinação
     * de duas listas. Assim, para um par (Chave, Valor)
     * qualquer, se Chave se encontra na posição i
     * da lista de chaves, então Valor se encontra
     * na posição i da lista de valores.</p>
     *
     * @param chaves  Identificador único da lista
     *                de chaves.
     * @param valores Identificador único da lista de
     *                valores.
     * @return Identificador único do dicionário.
     * @throws IllegalArgumentException Nos seguintes casos:
     *                                  (a) a lista de chaves não existe;
     *                                  (b) a lista de valores não existe;
     *                                  (c) a lista de chaves é incompatível (contém elementos repetidos).
     */
    int adicionaHash(int chaves, int valores);

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
     * Adiciona dado encapsulado em uma sequência de caracteres
     * ({@code DV_PARSABLE}).
     *
     * @param valor      Dado encapsulado propriamente dito.
     * @param formalismo Formalismo empregado pelo encapsulamento.
     * @return O identificador único do dado encapsulado na
     * estrutura.
     */
    int adicionaDvParsable(String valor, String formalismo);


}
