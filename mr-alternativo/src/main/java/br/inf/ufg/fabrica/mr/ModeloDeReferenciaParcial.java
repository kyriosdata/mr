/*
 * Copyright (c) 2016. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

/**
 * Fábrica de objetos baseados no Modelo de Referência
 * do openEHR.
 *
 * <p>Um objeto que implementa esta interface permite
 * criar objetos em conformidade com o
 * Modelo de Referência do openEHR.</p>
 *
 * <p>Uma implementação desta interface deve,
 * NECESSARIAMENTE, estar em conformidade com as
 * especificações (padrões) do Modelo de Referência
 * do openEHR.</p>
 *
 * <p>Um objeto baseado no Modelo de Referência do openEHR
 * é um objeto em conformidade com as especificações
 * desse Modelo de Referência. Tais especificações são
 * acompanhadas de uma implementação de referência em
 * Java, disponível em
 * <a href="https://github.com/openEHR/java-libs">aqui</a>.
 * </p>
 *
 * <p>A implementação desta interface é uma implementação
 * alternativa. Não é um <i>fork</i>,
 * nem trabalho derivado da implementação (citada acima).
 * Trata-se de uma nova implementação, que emprega
 * estratégia distinta.
 * </p>
 *
 * <p>Um dos principais objetivos dessa implementação
 * é atender o uso do Modelo de Referência por dispositivos
 * móveis que, em geral, apresentam restrições de capacidade de
 * processamento e memória.</p>
 *
 * <p>Neste sentido, duas decisões de projeto foram
 * estabelecidas: (a) reduzir o tamanho do arquivo jar
 * correspondente à implementação e (b) minimizar o espaço
 * exigido para guardar um grafo de objetos baseado no
 * openEHR.</p>
 *
 * <p>Em decorrência das decisões acima duas orientações
 * são experimentadas: (a) não é criada uma classe para
 * cada conceito (conforme implmentação de referência) e
 * (b) toBytes são armazenados em um vetor de bytes que
 * serializa um grafo típico baseado na implementação
 * de referência.</p>
 *
 * <h3>Visão geral da interface</h3>
 *
 * <p>As operações {@link #obtemTexto(int, int)},
 * {@link #obtemVetorBytes(int, int)} e
 * {@link #obtemLogico(int, int)}, dentre outras similares
 * para os demais tipos primitivos, permitem recuperar um
 * valor primitivo, ou seja, um membro de algum objeto. Em
 * consequência, todos eles fazem uso de dois parâmetros. O
 * primeiro identifica o objeto e o segundo identifica o campo
 * que contém a informação desejada.</p>
 *
 */
public interface ModeloDeReferenciaParcial extends Serializacao, Campo {

    /**
     * Identificador do tipo DV_BOOLEAN.
     */
    byte DV_PARAGRAPH = 0;

    /**
     * Identificador do tipo DV_BOOLEAN.
     */
    byte DV_TEXT = 1;

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
     * @param raiz Identificador único da raiz.
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
     * @param lista Lista de objetos a ser adicionada
     *              de um item.
     * @param ordem
     *@param item Identificador do objeto a ser
     *             inserido na lista.  @return Identificador único do item na lista.
     *
     * @throws IllegalArgumentException Nos seguintes casos:
     * (a) a lista não existe; (b) o item não existe.
     */
    int defineItemIemLista(int lista, int ordem, int item);

    int obtemItemEmLista(int lista, int ordem);

    /** 
     * Retorna o tamanho da lista de objetos.
     * 
     * @param lista Identificador da lista.
     * @throws IllegalArgumentException a lista não existe.
     */
    int obtemTamanhoLista(int lista);

    /**
     * Adiciona um valor lógico ({@code DV_BOOLEAN}).
     *
     * @param valor Valor lógico (DV_BOOLEAN) a ser adicionado.
     * @return Identificador do valor lógico adicionado.
     *
     * @see #obtemLogico(int, int)
     */
    int adicionaDvBoolean(boolean valor);

    /**
     * Adiciona uma lista de DV_TEXT, ou seja,
     * um DV_PARAGRAPH.
     *
     * @param lista Referência para a lista de DV_TEXT.
     *
     * @return Referência para o DV_PARAGRAPH criado.
     */
    int adicionaDvParagraph(int lista);

    /**
     * Adiciona um objeto DV_TEXT.
     *
     * @param valor
     * @param formatting
     * @param hperlink
     * @param languageName
     * @param languageVersion
     * @param languageCodeString
     * @param encodingName
     * @param encodingVersion
     * @param encodingCodeString
     * @param lista
     *
     * @return Índice da posição em que o objeto foi inserido.
     */
    int adicionaDvText(String valor,
                       String formatting,
                       String hperlink,
                       String languageName,
                       String languageVersion,
                       String languageCodeString,
                       String encodingName,
                       String encodingVersion,
                       String encodingCodeString,
                       int lista);
}
