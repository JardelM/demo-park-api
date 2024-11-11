package com.mballem.demoparkapi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    //nao coloquei static como na aula, e assim eu nem conseguia acessar o metodo na outra classe, apos trocar pra static deu certo
    public static String gerarRecibo (){
        LocalDateTime date  = LocalDateTime.now();
        String recibo = date.toString().substring(0, 19);
        return recibo
                .replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }
}
