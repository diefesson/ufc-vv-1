package com.diefesson.difcomp.dl.token;

public enum TokenType {

    KW_INT(1),
    KW_PRINT(2),
    OP_ASSING(3),
    OP_ADD(4),
    OP_SUB(5),
    OP_MUL(6),
    OP_DIV(7),
    IDENTIFIER(8),
    CONST_STR(9),
    CONST_INT(10),
    PUNC_END(11);

    public final int id;

    TokenType(int id) {
        this.id = id;
    }

    public static TokenType ofId(int id) {
        for (TokenType tt : values()) {
            if (tt.id == id) {
                return tt;
            }
        }
        return null;
    }

}
