package com.lecosBurguer.api.cadastro.Api.de.Cadastro.V1.enums;

public enum CadastroEnums {

    CP_0001("CP-0001"),
    CP_0002("CP-0002"),
    CP_0003("CP-0003"),
    CP_0004("CP-0004"),
    CP_0005("CP-0005"),
    CP_0006("CP-0006"),
    CP_0007("CP-0007"),
    CP_0008("CP-0008"),
    CP_0009("CP-0009"),
    CP_0010("CP-0010"),
    CP_0011("CP-0011"),
    CP_0012("CP-0012"),
    CP_0013("CP-0013"),
    CP_0014("CP-0014"),
    CP_0015("CP-0015"),
    CP_0016("CP-0016"),
    CP_0017("CP-0017"),
    CP_0018("CP-0018");

    private String code;

    CadastroEnums(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
