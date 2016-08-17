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
import dao.DAOGenerico;
import model.Curso;
import model.Usuario;

public class SincronizarUsuario {
	DAOGenerico dao = new DAOGenerico();
	Client cli = ClientBuilder.newClient();
	final String URL_BASE = "http://192.168.0.106:8080/DB_SERVICE/usuario/";
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
	}

	
	public void cadastrarUsuario(Usuario usuario){
		try {
				gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
				WebTarget target = cli.target(URL_BASE + "cadastrar");
				String userString = gson.toJson(usuario);
				Response response = target.request().post(Entity.entity(userString, MediaType.APPLICATION_JSON));
				System.out.println("Respose code: " + response.getStatus());
				if (response.getStatus() == 200) {
					gson = new Gson();
					String usuarioNovo = response.readEntity(String.class);
					Usuario user = gson.fromJson(usuarioNovo, Usuario.class);
				//Essa parte n�o vai ser necess�ria no algoritimo final...
					Curso curso = (Curso) dao.recuperaId(Curso.class, user.getCurso().getId());
					user.setCurso(curso);
					
					dao.inserir(user);
				}
		} catch (Exception e) {
			System.out.println("Conex�o n�o estabeleciada: " + e);
		}
		
		
		
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
			System.out.println("Conex�o n�o estabeleciada: " + e);
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
			System.out.println("Conex�o n�o estabeleciada: " + e);
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
			System.out.println("Conex�o n�o estabeleciada: " + e);
		}
	}


}
