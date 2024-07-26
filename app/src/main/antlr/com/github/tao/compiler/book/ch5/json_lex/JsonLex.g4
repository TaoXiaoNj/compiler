grammar JsonLex ;

@header {
    package com.github.tao.compiler.book.ch5.json_lex;
}

// 可以参考 JSON 组织官方的非正式文法描述
// https://www.json.org/json-en.html
json: object
    | array
    ;

object: '{' '}'
      | '{' pair (',' pair)* '}'
      ;

array: '[' ']'
     | '[' value (',' value)* ']'
     ;

pair: STRING ':' value;

value: STRING
     | NUMBER
     | object
     | array
     | 'true'
     | 'false'
     | 'null'
     ;

STRING: '"' (ESC | ~["\\])*? '"';
fragment ESC: '\\' (["\\/bfnrt] | UNICODE);
fragment UNICODE: 'u' HEX HEX HEX HEX;
fragment HEX: [0-9a-fA-F];