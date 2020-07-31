package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import br.com.sfc.tddudm.excepions.FilmeSemEstoqueException;
import br.com.sfc.tddudm.excepions.LocadoraException;
import br.com.sfc.tddudm.utils.DataUtils;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocacaoServiceTest {

    private LocacaoService service;

    @BeforeEach
    public void before() {
        this.service = new LocacaoService();
    }

    @Test
    public void testLocacao() throws Exception {
        //Cenário
        Usuario usuario = new Usuario();
        Filme filme1 = new Filme("Filme 1", 2, 4.0);
        Filme filme2 = new Filme("Filme 2", 2, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes.add(filme1);
        filmes.add(filme2);

        //Ação
        Locacao locacao;
        locacao = service.alugarFilme(usuario, filmes);

        //Verificação
        assertThat(locacao.getValor(), is(equalTo(8.0)));
        assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }

    @Test
    public void testLocacaoFilmeSemEstoque() throws Exception {
        //Cenário
        Usuario usuario = new Usuario();
        Filme filme1 = new Filme("Filme 1", 0, 4.0);
        Filme filme2 = new Filme("Filme 2", 0, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes.add(filme1);
        filmes.add(filme2);

        //Ação
        assertThrows(FilmeSemEstoqueException.class, () -> {
            service.alugarFilme(usuario, filmes);
        });
    }

    @Test
    public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException {
        //Cenário
        Filme filme1 = new Filme("Filme 1", 2, 4.0);
        Filme filme2 = new Filme("Filme 2", 2, 4.0);
        List<Filme> filmes = new ArrayList<>();
        filmes.add(filme1);
        filmes.add(filme2);

        Exception exception = assertThrows(LocadoraException.class, () -> {
            service.alugarFilme(null, filmes);
        });

        String expectedMessage = "Usuário vazio";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

    @Test
    public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException {
        //Cenário
        Usuario usuario = new Usuario();

        Exception exception = assertThrows(LocadoraException.class, () -> {
            service.alugarFilme(usuario, null);
        });

        String expectedMessage = "Filme vazio";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

}
