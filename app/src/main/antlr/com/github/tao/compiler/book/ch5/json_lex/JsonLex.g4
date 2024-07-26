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


//// Lexical Rules

STRING: '"' (ESC | ~["\\])*? '"';
fragment ESC: '\\' (["\\/bfnrt] | UNICODE);
fragment UNICODE: 'u' HEX HEX HEX HEX;
fragment HEX: [0-9a-fA-F];


// 在 "-12.0034E+15" 中，"12" 是整数部分，"0034" 是小数部分， "E+15" 是指数部分
// 合法的数字：100 , 0 , 0.123 , 99.001 , -0 , 3E5 , 3E0, 3E00, 3E0001, 3E+5 , 3e-5 , -3e-5,
// 非法的数字：+9 , 09 , .123 , 12. , 3e , 3e2.3 , e , e5

NUMBER: '-'? INT ('.' FRAC)? EXP? ;

// 整数部分，必须存在
fragment INT: '0'
            | [1-9] DIGITS?
            ;

// 任意数字序列，长度至少为 1，允许 leading zero
fragment DIGITS: [0-9]+ ;

// 小数部分，不是必须的
fragment FRAC: DIGITS ;

// 指数部分：非必须
fragment EXP: [Ee] [+-]? DIGITS ;