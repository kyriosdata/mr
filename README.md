# mr
Modelo de Referência do openEHR: 
Analisando Possibilidades de Persistência

[<img src="https://api.travis-ci.org/kyriosdata/mr.svg?branch=master">](https://travis-ci.org/kyriosdata/mr)

O presente projeto é derivado do conteúdo
disponível em https://github.com/openehr/java-libs.git.
TODOS OS CRÉDITOS DEVEM SER DEVIDAMENTE ATRIBUÍDOS
AOS DEVIDOS AUTORES. 

O presente "fork" inclui apenas parte do disponível na
URL acima, denominada de Modelo de Referência do openEHR,
doravante apenas MR. Tal "fork" é estendido com uma
proposta de implementação alternativa do MR. O código
do "fork" é utilizado como referência para facilitar a
verificação e validação da implementação alternativa.

O objetivo é facilitar a experimentação de estratégias 
de persistência de informações registradas segundo o
MR. 

Tarefas
=======
- Verificar desempenho com Realm (http://realm.io) 
- Gerenciar memória (https://grizzly.java.net/memory.html)
- Buffers (http://stackoverflow.com/questions/28511541/libuv-allocated-memory-buffers-re-use-techniques)
- https://www.infoq.com/news/2016/07/apple-lzfse-lossless-opensource 
- https://en.wikipedia.org/wiki/BSON
- http://ubjson.org/
- http://source.wiredtiger.com/2.8.0/index.html 
- Data Serialization formats (https://en.wikipedia.org/wiki/Comparison_of_data_serialization_formats)
- Compressão usando lz4 (https://cyan4973.github.io/lz4/). Aparentemente ainda não há lzfse para Java.
