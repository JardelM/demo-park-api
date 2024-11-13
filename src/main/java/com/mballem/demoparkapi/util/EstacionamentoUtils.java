package com.mballem.demoparkapi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {


    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;


    //nao coloquei static como na aula, e assim eu nem conseguia acessar o metodo na outra classe, apos trocar pra static deu certo
    public static String gerarRecibo (){
        LocalDateTime date  = LocalDateTime.now();
        String recibo = date.toString().substring(0, 19);
        return recibo
                .replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }


    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        long minutes = entrada.until(saida, ChronoUnit.MINUTES);
        double total = 0.0;

        if (minutes <= 15) {

            total = PRIMEIROS_15_MINUTES;

        } else if (minutes <= 60) {

            total = PRIMEIROS_60_MINUTES;

        } else {

            total = PRIMEIROS_60_MINUTES;
            long minutosExtras = minutes - 60;
            long faixasDe15MinutosExtras = (long) Math.ceil(minutosExtras / 15.0);

            total += faixasDe15MinutosExtras * ADICIONAL_15_MINUTES;

        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {
        BigDecimal desconto = ((numeroDeVezes > 0) && (numeroDeVezes % 10 == 0))
                ? custo.multiply(new BigDecimal(DESCONTO_PERCENTUAL))
                : new BigDecimal(0);
        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }


}
