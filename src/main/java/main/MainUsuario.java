package main;

import dao.DAOGenerico;
import model.Curso;
import model.Usuario;

public class MainUsuario {

	public static void main(String[] args) {
		DAOGenerico daoGenerico = new DAOGenerico();
		Usuario usuario = new Usuario();
		SincronizarUsuario sincronizarUsuario = new SincronizarUsuario();

		daoGenerico = new DAOGenerico();
		Usuario user = new Usuario();
		user.setEmail("NovoComID@ccc.com");
		user.setNome("Fernando");
		user.setSenha("Fernando");
		user.setUsuario("Fernando");
		user.setStatusSincronizacao("INSERIR");
		user.setRegistroUnico(null);
		Curso cur = (Curso) daoGenerico.recuperaId(Curso.class, 1250L);
		user.setCurso(cur);
		sincronizarUsuario.cadastrarUsuario(user);
	}

}
