package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.excepions.NaoPodeDividirPorZeroException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculadoraTest {

    private Calculadora calc;

    @BeforeEach
    public void setup() {
        calc = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        // Cenário
        int a = 5;
        int b = 3;

        // Ação
        int resultado = calc.somar(a, b);

        // Verificação
        Assertions.assertEquals(8, resultado);
    }

    @Test
    public void deveSubtrairDoisValores() {
        // Cenário
        int a = 8;
        int b = 5;

        // Ação
        int resultado = calc.subtrair(a, b);

        // Verificação
        Assertions.assertEquals(3, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        // Cenário
        int a = 6;
        int b = 3;

        // Ação
        int resultado = calc.dividir(a, b);

        // Verificação
        Assertions.assertEquals(2, resultado);
    }

    @Test
    public void deveLancarExcecaoAoDividrPorZero() {
        // Cenário
        int a = 10;
        int b = 0;

        // Ação e Verificação
        assertThrows(NaoPodeDividirPorZeroException.class, () -> {
            calc.dividir(a, b);
        });
    }

}
