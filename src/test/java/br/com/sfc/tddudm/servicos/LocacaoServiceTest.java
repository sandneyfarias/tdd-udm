package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import br.com.sfc.tddudm.excepions.FilmeSemEstoqueException;
import br.com.sfc.tddudm.excepions.LocadoraException;
import br.com.sfc.tddudm.utils.DataUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocacaoServiceTest {

    @Test
    public void testLocacao() throws Exception {
        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario();
        Filme filme = new Filme("Filme 1", 2, 4.0);

        //Ação
        Locacao locacao;
        locacao = service.alugarFilme(usuario, filme);

        //Verificação
        assertThat(locacao.getValor(), is(equalTo(4.0)));
        assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
    }

    @Test
    public void testLocacaoFilmeSemEstoque() throws Exception {
        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario();
        Filme filme = new Filme("Filme 1", 0, 4.0);

        //Ação
        assertThrows(FilmeSemEstoqueException.class, () -> {
            service.alugarFilme(usuario, filme);
        });
    }

    @Test
    public void testLocacaoUsuarioVazio() throws FilmeSemEstoqueException {
        //Cenário
        LocacaoService service = new LocacaoService();
        Filme filme = new Filme("Filme 1", 2, 4.0);

        Exception exception = assertThrows(LocadoraException.class, () -> {
            service.alugarFilme(null, filme);
        });

        String expectedMessage = "Usuário vazio";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

    @Test
    public void testLocacaoFilmeVazio() throws FilmeSemEstoqueException {
        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario();

        Exception exception = assertThrows(LocadoraException.class, () -> {
            service.alugarFilme(usuario, null);
        });

        String expectedMessage = "Filme vazio";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage, is(equalTo(expectedMessage)));
    }

}
