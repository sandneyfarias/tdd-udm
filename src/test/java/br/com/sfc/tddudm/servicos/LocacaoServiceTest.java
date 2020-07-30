package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import br.com.sfc.tddudm.utils.DataUtils;
import org.junit.jupiter.api.Assertions;
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

    // Forma elegante
    @Test
    public void testLocacaoFilmeSemEstoque() throws Exception {
        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario();
        Filme filme = new Filme("Filme 1", 0, 4.0);

        //Ação
        assertThrows(Exception.class, () -> {
            service.alugarFilme(usuario, filme);
        });
    }

    @Test
    public void testLocacaoFilmeSemEstoqueRobusta() {
        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario();
        Filme filme = new Filme("Filme 1", 0, 4.0);

        //Ação
        try {
            service.alugarFilme(usuario, filme);
            Assertions.fail("Deveria ter chamado uma exceção");
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

}
