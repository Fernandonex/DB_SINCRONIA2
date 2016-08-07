package main;

import dao.DAOGenerico;
import model.Curso;
import model.Usuario;

public class MainUsuario {

	public static void main(String[] args) {
		DAOGenerico daoGenerico = new DAOGenerico();
		Usuario usuario = new Usuario();
		SincronizarUsuario sincronizarUsuario = new SincronizarUsuario();

		for (int i = 0; i < 3; i++) {
			daoGenerico = new DAOGenerico();
			Usuario user = new Usuario();
			user.setEmail("Fernando@ccc.com");
			user.setNome("Fernando");
			user.setSenha("Fernando");
			user.setUsuario("Fernando");
			user.setStatusSincronizacao("INSERIR");
			user.setRegistroUnico(null);
			Curso cur = (Curso) daoGenerico.recuperaId(Curso.class, 2L);
			user.setCurso(cur);
			daoGenerico.inserir(user);
		}
		sincronizarUsuario.sincronizarCliToService();
	}

}
