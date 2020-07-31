package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import br.com.sfc.tddudm.excepions.FilmeSemEstoqueException;
import br.com.sfc.tddudm.excepions.LocadoraException;
import br.com.sfc.tddudm.utils.DataUtils;

import java.util.Date;
import java.util.List;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws Exception {

        if (usuario == null) {
            throw new LocadoraException("Usuário vazio");
        }

        if (filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("Filme vazio");
        }

        Double valor = 0.0;
        for (Filme filme : filmes) {
            if (filme.getEstoque().equals(0)) {
                throw new FilmeSemEstoqueException();
            } else {
                valor +=   filme.getPrecoLocacao();
            }
        }

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(valor);

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }


    public static void main(String[] args) {

    }
}