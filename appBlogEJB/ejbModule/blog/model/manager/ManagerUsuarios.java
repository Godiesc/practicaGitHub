package blog.model.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import blog.model.entities.Usuario;
import blog.model.util.ModelUtil;

/** * Session Bean implementation class ManagerUsuarios */
@Stateless
@LocalBean
public class ManagerUsuarios {
	@PersistenceContext(unitName = "blogDS")
	private EntityManager em;

	@EJB
	ManagerBlog managerblog;

	public ManagerUsuarios() {
	}

	public boolean comprobarUsuario(String idUsuario, String clave) throws Exception {
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u == null)
			throw new Exception("No existe el usuario " + idUsuario);
		if (!u.getActivo())
			throw new Exception("El usuario no está activo.");
		if (u.getClave().equals(clave))
			return true;
		throw new Exception("Contraseña no válida.");
	}
	

	public Usuario findUsuarioById(String idUsuario) {
		Usuario u = em.find(Usuario.class, idUsuario);
		return u;
	}

	
	public void registrarNuevoBlogger(String idUsuario, String clave, String confirmacionClave, String correo)
			throws Exception {
		if (ModelUtil.isEmpty(idUsuario))
			throw new Exception("Debe especificar un ID de usuario.");
		// verificamos la contraseña:
		if (ModelUtil.isEmpty(clave))
			throw new Exception("Debe especificar una clave.");
		if (!clave.equals(confirmacionClave))
			throw new Exception("No coinciden la clave y la confirmación.");
		// verificamos correo:
		if (ModelUtil.isEmpty(correo))
			throw new Exception("Debe especificar un correo válido.");

		Usuario u = findUsuarioById(idUsuario);
		if (u != null)
			throw new Exception("Ya existe un usuario " + idUsuario);
		// finalmente se puede guardar el nuevo usuario
		u = new Usuario();
		u.setIdUsuario(idUsuario);
		u.setClave(clave);
		u.setCorreo(correo);
		u.setActivo(true);
		em.persist(u);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listaUsuarios() {

		String JPQL = "SELECT t FROM Tabtecnico t";
		Query q;
		List<Usuario> lista;
		q = em.createQuery(JPQL);
		lista = q.getResultList();
		int encontrado = 0; 

		for (Usuario usuario : lista) { // recorremos lista usuarios
			int index = 0; // variable index(posicion)
			if (usuario.getIdUsuario().equals("admin")) {// condición si
															// idusuario=admin
				encontrado = index;// guarda index(posicion)
			}
			index++;// recorre toda la lista
		}
		lista.remove(encontrado);// quitamos el administrador
		return lista;
	}

	public List<Usuario> findBloggers() {
		String JPQL = "SELECT u FROM Usuario u";// select * from Usuario;
		Query q;
		List<Usuario> lista;
		q = em.createQuery(JPQL);
		lista = q.getResultList();
		int encontrado = 0; // variable inicial para encontrar el index del
							// administrador

		for (Usuario usuario : lista) { // recorremos lista usuarios
			int index = 0; // variable index(posicion)
			if (usuario.getIdUsuario().equals("admin")) {
				encontrado = index;// guarda index(posicion)
			}
			
			index++;// recorre toda la lista
		}
		lista.remove(encontrado);// quitamos el administrador
		return lista;
	}
	

	public void eliminarBloggerById(String idUsuario) throws Exception {
		if (!(managerblog.findBlogByUsuario(idUsuario).size() > 0)) {
			em.remove(findUsuarioById(idUsuario));
		} else {
			throw new Exception("El usuario tiene blogs");
		}
	}
}
