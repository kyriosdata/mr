package org.openehr.rm.formatters;

import br.inf.ufg.fabrica.mr.ModeloDeReferencia;

public class JsonObject implements ModeloDeReferencia{

    /* ÍNICIO MÉTODOS INTERFACE */
    public byte[] toBytes() {
        return new byte[0];
    }

    public void fromBytes(byte[] bytes) {

    }

    public String toXML() {
        return null;
    }

    public void fromXML(String xml) {

    }

    public String toJSON() {
        int idObjeto = obtemRaiz();
        //for(int i=0; i<totalObjetos(); i++){ //itera sobre cada nodo possível do grafo gerado e cria o json
        buildJson(idObjeto);
        //}
        return null;
    }

    public void fromJSON(String json) {

    }

    public void defineRaiz(int raiz) {

    }

    public int obtemRaiz() {
        return 0;
    }

    public int totalObjetos() {
        return 0;
    }

    public int obtemTipo(int id) {
        return 0;
    }

    public byte obtemByte(int id, int campo) {
        return 0;
    }

    public String obtemString(int id, int campo) {
        return null;
    }

    public boolean obtemLogico(int id, int campo) {
        return false;
    }

    public int obtemInteiro(int id, int campo) {
        return 0;
    }

    public float obtemFloat(int id, int campo) {
        return 0;
    }

    public double obtemDouble(int id, int campo) {
        return 0;
    }

    public int adicionaDvBoolean(boolean valor) {
        return 0;
    }

    public boolean obtemDvBoolean(int id) {
        return false;
    }

    public int adicionaDvIdentifier(String issuer, String assigner, String id, String type) {
        return 0;
    }

    public boolean obtemValorLogico(int id, int campo) {
        return false;
    }

    public String obtemTexto(int id, int campo) {
        return null;
    }

    public byte[] obtemVetorBytes(int id, int campo) {
        return new byte[0];
    }

    public int adicionaLista(int quantidade) {
        return 0;
    }

    public int adicionaItem(int lista, int item) {
        return 0;
    }

    public int buscaEmLista(int lista, int objeto) {
        return 0;
    }

    public void elimineObjeto(int objeto) {

    }

    public int adicionaHash(int chaves, int valores) {
        return 0;
    }

    public int adicionaDvUri(String uri) {
        return 0;
    }

    public int adicionaDvEhrUri(String uri) {
        return 0;
    }

    public int adicionaTerminologyId(String nome, String versao) {
        return 0;
    }

    public int adicionaTerminologyId(String valor) {
        return 0;
    }

    public int adicionaCodePhrase(String terminologyId, String codeString) {
        return 0;
    }

    public int adicionaDvParsable(String valor, String formalismo) {
        return 0;
    }

    public int adicionaDvParsable(String codePhraseCharSet, String codePhraseLanguage, String valor, String formalismo) {
        return 0;
    }

    public int adicionaDvMultimedia(String codePhraseCharSet, String codePhraseLinguagem, String textoAlternativo, String codePhraseTipoMidia, String codePhraseAlgoritmoCompressao, byte[] integridade, String codePhraseAlgoritmoIntegridade, int hDvMultimediaThumbnail, String dvUri, byte[] dado) {
        return 0;
    }

    public int adicionaIsoOid(String valor) {
        return 0;
    }

    public int adicionaUuid(String valor) {
        return 0;
    }

    public int adicionaInternetId(String valor) {
        return 0;
    }

    public int adicionaHierObjectId(String valor) {
        return 0;
    }

    public int adicionaHierObjectId(String raiz, String extensao) {
        return 0;
    }

    public int adicionaObjectVersionId(String valor) {
        return 0;
    }

    public int adicionaObjectVersionId(String objectId, String versionTreeId, String creatingSystemId) {
        return 0;
    }
    /* FIM MÉTODOS INTERFACE */

    /* ÍNICIO MÉTODOS AUXILIARES */
    private String buildJson(int idRaiz){
        String out = "";
        String template = "";
        String list = "";
        switch (obtemTipo(idRaiz)){
            case 1: //DvBoolean
                template = "{'value': #value}";
                template = template.replaceAll("#value", String.valueOf(obtemValorLogico(idRaiz, 0)));
                template = template.replaceAll("'","\"");
                break;
            case 2: //DvIdentifier
                template = "{'issuer': '#issuer', 'assigner': '#assigner', 'id': '#id', 'type': '#type'}";
                template = template.replaceAll("#issuer", obtemTexto(idRaiz, 0));
                template = template.replaceAll("#assigner", obtemTexto(idRaiz, 1));
                template = template.replaceAll("#id", obtemTexto(idRaiz,2));
                template = template.replaceAll("#type", obtemTexto(idRaiz, 3));
                template = template.replaceAll("'","\"");
                break;
            case 3: //DvParagraph -- Precisa validação
                template = "{'items': [#listDvText]}";
                for(int j=0; j<totalObjetos(); j++){
                    list += buildJson(j); //chama em recursividade o buildJson para cada um dos elementos da lista<DvText>
                    if(j<totalObjetos()-1){
                        list += ", ";
                    }
                }
                template = template.replaceAll("#listDvText", list);
                template = template.replaceAll("'","\"");
                list = ""; //clear list
                break;
            case 4: //TermMapping -- Precisa validação
                template = "{'target': #callCodePhrase, 'match': #callMatch, 'purpose': #callDvCodedText}";
                template = template.replaceAll("#callCodePhrase", buildJson(idRaiz+1)); // próximo elemento no grafo montado
                template = template.replaceAll("#callMatch", buildJson(idRaiz+2)); // próximo elemento no grafo montado
                template = template.replaceAll("#callDvCodedText", buildJson(idRaiz+2)); // próximo elemento no grafo montado
                template = template.replaceAll("'","\"");
                break;
        }
        out += template;
        return out;
    }
    /* FIM MÉTODOS AUXILIARES */
}