package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import br.com.sfc.tddudm.servicos.LocacaoService;
import br.com.sfc.tddudm.utils.DataUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class LocacaoServiceTest {

    @Test
    public void test() {
        //Cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario();
        Filme filme = new Filme("Filme 1", 2, 4.0);

        //Ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        //Verificação
        Assertions.assertTrue(locacao.getValor().equals(4.0));
        Assertions.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
        Assertions.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

    }


}
