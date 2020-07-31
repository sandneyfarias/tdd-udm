package br.com.sfc.tddudm.servicos;

import br.com.sfc.tddudm.entidades.Filme;
import br.com.sfc.tddudm.entidades.Locacao;
import br.com.sfc.tddudm.entidades.Usuario;
import br.com.sfc.tddudm.excepions.FilmeSemEstoqueException;
import br.com.sfc.tddudm.excepions.LocadoraException;
import br.com.sfc.tddudm.utils.DataUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocacaoService {

    private Usuario usuario;
    private List<Filme> filmes;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Filme> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<Filme> filmes) {
        this.filmes = filmes;
    }

    public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws Exception {
        this.usuario = usuario;
        this.filmes = filmes;

        validarLocacao();

        Double valor = calcularTotalLocacao();

        Locacao locacao = new Locacao();
        locacao.setFilmes(filmes);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(new Date());
        locacao.setValor(valor);
        locacao.setDataRetorno(calcularDataRetorno());

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }

    private void validarLocacao() throws LocadoraException, FilmeSemEstoqueException {
        verificarSeUsuarioFoiInformado();
        verificarSeFilmesForamInformados();
        verificarSeFilmesTemEstoque();
    }

    private void verificarSeUsuarioFoiInformado() throws LocadoraException {
        if (usuario == null) {
            throw new LocadoraException("Usuário vazio");
        }
    }

    private void verificarSeFilmesForamInformados() throws LocadoraException {
        if (filmes == null || filmes.isEmpty()) {
            throw new LocadoraException("Filme vazio");
        }
    }

    private void verificarSeFilmesTemEstoque() throws FilmeSemEstoqueException {
        for (Filme filme : filmes) {
            if (filme.getEstoque().equals(0)) {
                throw new FilmeSemEstoqueException();
            }
        }
    }

    private double calcularTotalLocacao() {
        Double totalLocacao = 0d;

        for (int pos = 0; pos < this.filmes.size(); pos++) {
            Filme filme = filmes.get(pos);
            Double precoLocacao = filme.getPrecoLocacao();

            if (pos == 2) {
                precoLocacao = precoLocacao * 0.75;
            } else if (pos == 3) {
                precoLocacao = precoLocacao * 0.50;
            } else if (pos == 4) {
                precoLocacao = precoLocacao * 0.25;
            } else if (pos == 5) {
                precoLocacao = 0d;
            }

            totalLocacao += precoLocacao;
        }

        return totalLocacao;
    }

    private Date calcularDataRetorno() {
        Date dataEntrega = new Date();
        dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);

        if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
        }

        return dataEntrega;
    }

}