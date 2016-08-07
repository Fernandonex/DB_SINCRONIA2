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

import dao.DAOGenerico;
import model.Usuario;

public class SincModBK {
	DAOGenerico dao = new DAOGenerico();
	Client cli = ClientBuilder.newClient();
	final String URL_BASE = "http://192.168.0.100:8080/DB_SERVICE/usuario/";
	// 169.254.183.109
	Gson gson;
	GregorianCalendar d = new GregorianCalendar();

	public void sincronizarCliToService() {
		List<Usuario> listaInserir = dao.listaCondicaoStatus("INSERIR");
		List<Usuario> listaAlterar = dao.listaCondicaoStatus("ALTERAR");
		List<Usuario> listaDeletar = dao.listaCondicaoStatus("DELETAR");
		System.out.println("Total de 'INSERIR': " + listaInserir.size() + "\n Total de 'ALTERAR': "
				+ listaAlterar.size() + "\n Total de 'DELETAR': " + listaDeletar.size());
		if (listaInserir.size() > 0) {
			inserir(listaInserir);
		} else if (listaAlterar.size() > 0) {
			alterar(listaAlterar);
		} else if (listaDeletar.size() > 0) {
			deletar(listaDeletar);
		}
		buscaAtualizacao();
	}

	private void inserir(List<Usuario> listaInserir) {
		try {
			for (Usuario obj : listaInserir) {
				gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				WebTarget target = cli.target(URL_BASE + "inserir");
				String userString = gson.toJson(obj);
				Response response = target.request().post(Entity.entity(userString, MediaType.APPLICATION_JSON));
				System.out.println("Respose code: " + response.getStatus());
				if (response.getStatus() == 200) {
					obj.setStatusSincronizacao("SINCRONIZADO");
					dao.alterar(obj);
				}
			}
			System.out.println("TODOS OS DADOS FORAM SINCRONIZADOS");
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e);
		}
	}

	private void alterar(List<Usuario> listaAlterar) {
		try {
			for (Usuario obj : listaAlterar) {
				gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				WebTarget target = cli.target(URL_BASE + "alterar");
				String userString = gson.toJson(obj);
				Response response = target.request().post(Entity.entity(userString, MediaType.APPLICATION_JSON));
				if (response.getStatus() == 200) {
					obj.setStatusSincronizacao("SINCRONIZADO");
					dao.alterar(obj);
				}
			}
			System.out.println("TODOS OS DADOS FORAM SINCRONIZADOS");
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e);
		}
	}

	private void deletar(List<Usuario> listaDeletar) {
		try {
			for (Usuario obj : listaDeletar) {
				gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				WebTarget target = cli.target(URL_BASE + "deletar");
				String userString = gson.toJson(obj);
				Response response = target.request().post(Entity.entity(userString, MediaType.APPLICATION_JSON));
				System.out.println("Respose code: " + response.getStatus());
				if (response.getStatus() == 200) {
					dao.deletar(obj);
				}
			}
			System.out.println("TODOS OS DADOS FORAM SINCRONIZADOS");
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e);
		}
	}

	private void buscaAtualizacao() {
		try {
			WebTarget target = cli.target(URL_BASE + "listaatualizacao");
			String str = target.request().get(String.class);
			dao = new DAOGenerico();
			gson = new Gson();			
			ArrayList<Usuario> listaAtualizacao = new ArrayList<Usuario>();
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(str).getAsJsonArray();
			//For para preencher a lista
			for (int i = 0; i < array.size(); i++) {
				listaAtualizacao.add(gson.fromJson(array.get(i),Usuario.class ));
			}
			//For para inserir no banco
			for (Usuario user : listaAtualizacao){
				Usuario usuario = new Usuario();
				usuario=user;
				dao.inserir(usuario);
			}
			System.out.println("TODOS OS DADOS FORAM SINCRONIZADOS");
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e.getMessage());
		}
	}

}
