package com.docusign.bp.dto;

import lombok.Data;

@Data
public class SignerDto {

    private String name;
    private String email;
    private String cpf;
    private String nacionalidade;
    private String dataNascimento;
    private String sexo;
    private String estadoCivil;
    private String profissao;
    private String renda;
    private String documento;
    private String orgaoExpedidor;
    private String dataEmissao;
    private String endereco;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private String telefone;
    private Boolean impresso;
    private String nomeFundo;
    private String siglaReferencia;
    private String cnpjFundo;
    private String taf;

}