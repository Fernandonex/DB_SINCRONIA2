package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DAOGenerico;
import model.Curso;
import model.Usuario;
import main.Sincronizar;

public class Main {

	public static void main(String[] args) {
		DAOGenerico dao = new DAOGenerico();
//		Sincronizar sincr = new Sincronizar();
		SincronizarCurso sincrM = new SincronizarCurso();
		Gson gson;
		gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		Usuario user = (Usuario) dao.recuperaId(Usuario.class, 47L);
		System.out.println(user.getRegistroUnico());
		String str = gson.toJson(user);
		System.out.println(str);
//		for (int i = 0; i < 10; i++) {
//			dao = new DAOGenerico();
//Curso curso = new Curso();
//			curso.setDescricao("descricao");
//			curso.setNome("CursoFromJson");
//			dao.inserir(curso);
//		}
//		
//		
//		
		/*for (int i = 0; i < 10; i++) {
			dao = new DAOGenerico();
			Usuario user = new Usuario();
			user.setEmail("FromJson@ccc.com");
			user.setNome("FromJson");
			user.setSenha("FromJson");
			user.setUsuario("FromJson");
			user.setStatusSincronizacao("INSERIR");
			user.setRegistroUnico(null);
			Curso cur = (Curso) dao.recuperaId(Curso.class, 5L);
			user.setCurso(cur);
			dao.inserir(user);
		}
		sincrM.sincronizarCliToService();*/

//		 Usuario rec = (Usuario) dao.recuperaId(Usuario.class, 1130L);
//		 rec.setUsuario("USUARIO ALTERADO");
//		 rec.setStatusSincronizacao("ALTERAR");
//		 dao.alterar(rec);
//		 sincrM.sincronizarCliToService();
		
//		List<Usuario> list = new ArrayList<Usuario>();
//		list = dao.listaCondicaoStatus("SINCRONIZADO");
//
//		for (Usuario obj : list) {
//			dao = new DAOGenerico();
//			Usuario user = (Usuario) dao.recuperaId(Usuario.class, obj.getId());
//			user.setStatusSincronizacao("DELETAR");
//			dao.alterar(user);
//		}
//		sincrM.sincronizarCliToService();
		// Usuario user = new Usuario();
		// user.setEmail("Gson@ccc.com");
		// user.setNome("Gson");
		// user.setSenha("Gson");
		// user.setUsuario("Fernando");
		// user.setStatusSincronizacao("INSERIR");
		// user.setRegistroUnico("Gson");
		// Gson gson = new Gson();
		// String userString = gson.toJson(user);
		// System.out.println(userString);
	}

}
