package main;

import java.net.ConnectException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import dao.DAOCurso;
import dao.DAOCurso;
import model.Curso;

public class SincronizarCurso {
	DAOCurso dao = new DAOCurso();
	Client cli = ClientBuilder.newClient();
	final String URL_BASE = "http://192.168.0.106:8080/DB_SERVICE/curso/";
	// 169.254.183.109
	Gson gson;
	GregorianCalendar d = new GregorianCalendar();

	public void buscaAtualizacao() {
		try {
			WebTarget target = cli.target(URL_BASE + "listaatualizacao");
			String str = target.request().get(String.class);
			gson = new Gson();
			ArrayList<Curso> listaAtualizacao = new ArrayList<Curso>();
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(str).getAsJsonArray();
			// For para preencher a lista
			for (int i = 0; i < array.size(); i++) {
				listaAtualizacao.add(gson.fromJson(array.get(i), Curso.class));
			}
			inserirAlterar(listaAtualizacao);
		} catch (Exception e) {
			System.out.println("Conex�o n�o estabeleciada: " + e.getMessage());
		}
	}

	private void inserirAlterar(final List<Curso> listaCursos) {
		try {
			for (Curso curso : listaCursos) {
				// Busca o curso pelo id
				Curso cursoRecuperado = (Curso) dao.recuperaId(Curso.class, curso.getId());
				// Se a lista for igual a zero, insere os novos cursos
				if (cursoRecuperado == null) {
					Curso cur = new Curso();
					cur = curso;
					dao.inserir(cur);
					System.out.println("Curso inserido");
				}
				// Sen�o altera o curso
				else {
					cursoRecuperado.setNome(curso.getNome());
					cursoRecuperado.setDescricao(curso.getDescricao());
					cursoRecuperado.setStatusSincronizacao(curso.getStatusSincronizacao());
					dao.alterar(cursoRecuperado);
					System.out.println("Curso Atualizado");
				}
			}
			System.out.println("TODOS OS DADOS FORAM SINCRONIZADOS");
		} catch (Exception e) {
			System.out.println("Conex�o n�o estabeleciada: " + e);
		}
	}

	/*
	 * private void desabilitar(final List<Curso> listaAlterarDesabilitar) { try
	 * { // Percorre a lista for (Curso cur : listaAlterarDesabilitar) { //
	 * Verifica se esta desabilitado if
	 * (cur.getStatusSincronizacao().equals("DESABILITADO")) { List<Curso>
	 * listaRegistros = new ArrayList<Curso>(); listaRegistros =
	 * dao.procuraObjeto(Curso.class, cur.getRegistroUnico()); // Percorre a
	 * lista de com o curso que possui o RU for (Curso curso : listaRegistros) {
	 * Curso rec = (Curso) dao.recuperaId(Curso.class, curso.getId());
	 * rec.setStatusSincronizacao("DESABILITADO"); dao.alterar(rec);
	 * System.out.println("Curso Desabilitado"); } } }
	 * System.out.println("TODOS OS DADOS FORAM SINCRONIZADOS2"); } catch
	 * (Exception e) { System.out.println("Conex�o n�o estabeleciada: " + e); }
	 * }
	 */

}
