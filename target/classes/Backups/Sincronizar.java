package main;

import java.util.GregorianCalendar;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.DAOGenerico;
import model.Usuario;

public class Sincronizar {
	DAOGenerico dao = new DAOGenerico();
	Client cli = ClientBuilder.newClient();
	String ip = "192.168.0.107";
	
	GregorianCalendar d = new GregorianCalendar();
	public void sincronizarCliToService() {
		List<Usuario> listaInserir = dao.listaCondicaoStatus("INSERIR");
		List<Usuario> listaAlterar = dao.listaCondicaoStatus("ALTERAR");
		List<Usuario> listaDeletar = dao.listaCondicaoStatus("DELETAR");
		System.out.println("Total de 'INSERIR': " + listaInserir.size() + "\n Total de 'ALTERAR': "
				+ listaAlterar.size() + "\n Total de 'DELETAR': " + listaDeletar.size());
		
		
		/* ### INSERIR ### */
		try {
			for (Usuario obj : listaInserir) {
				WebTarget target = cli.target("http://" + ip + ":8080/DB_SERVICE/usuario/inserir");
				Form form = new Form();
				form.param("nome", obj.getNome());
				form.param("senha", obj.getSenha());
				form.param("email", obj.getEmail());
				form.param("usuario", obj.getUsuario());
				form.param("registroUnico", obj.getId() + "-" + obj.getEmail() + "-" + this.d);
				Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
				System.out.println("Respose code: " + response.getStatus());
				if (response.getStatus() == 204) {
					obj.setStatusSincronizacao("SINCRONIZADO");
					dao.alterar(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e);
		}

		/* ### ALTERAR ### */
		try {
			for (Usuario obj : listaAlterar) {
				WebTarget target = cli.target("http://" + ip + ":8080/DB_SERVICE/usuario/alterar");
				Form form = new Form();
				form.param("nome", obj.getNome());
				form.param("senha", obj.getSenha());
				form.param("email", obj.getEmail());
				form.param("usuario", obj.getUsuario());
				form.param("registroUnico", obj.getId() + "-" + obj.getEmail() + "-" + this.d);
				Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
				System.out.println("Respose code: " + response.getStatus());
				if (response.getStatus() == 204) {
					obj.setStatusSincronizacao("SINCRONIZADO");
					dao.alterar(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e);
		}
		/* ### DELETAR ### */
		try {
			for (Usuario obj : listaDeletar) {
				WebTarget target = cli.target("http://" + ip + ":8080/DB_SERVICE/usuario/deletar");
				Form form = new Form();
				form.param("registroUnico", obj.getId() + "-" + obj.getEmail() + "-" + this.d);
				Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
				System.out.println("Respose code: " + response.getStatus());
				if (response.getStatus() == 204) {
					dao.deletar(obj);
				}
			}
		} catch (Exception e) {
			System.out.println("Conexão não estabeleciada: " + e);
		}
	}

}
