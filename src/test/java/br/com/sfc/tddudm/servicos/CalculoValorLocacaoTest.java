package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculoValorLocacaoTest {

    private LocacaoService service;

    private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
    private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
    private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
    private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
    private static Filme filme5 = new Filme("Filme 5", 2, 4.0);
    private static Filme filme6 = new Filme("Filme 5", 2, 4.0);
    private static Filme filme7 = new Filme("Filme 7", 2, 4.0);

    @BeforeEach
    public void setup() {
        this.service = new LocacaoService();
    }

   public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][] {
                {Arrays.asList(filme1, filme2, filme3), 11d},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13d},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14d},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14d},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18d}
        });
    }

    @ParameterizedTest(name = "#{index} - {0} - {1}")
    @MethodSource("getParametros")
    public void deveCaclularValorLocacaoConsiderandoDescontos(List<Filme> filmes, Double valorLocacao) throws Exception {
        // Cenário
        Usuario usuario = new Usuario();

        // Ação
        Locacao resultado = service.alugarFilme(usuario, filmes);

        // Verificação
        assertThat(resultado.getValor(), is(equalTo(valorLocacao)));
    }

}
