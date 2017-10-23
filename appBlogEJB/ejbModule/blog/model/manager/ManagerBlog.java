package blog.model.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import blog.model.entities.Articulo;
import blog.model.entities.Blog;
import blog.model.entities.Usuario;

@Stateless
@LocalBean
public class ManagerBlog {
	@PersistenceContext(unitName = "blogDS")
	private EntityManager em;
	@EJB
	private ManagerUsuarios managerUsuarios;

	public ManagerBlog() {
	}

	@SuppressWarnings("unchecked")
	public List<Blog> findBlogByUsuario(String idUsuario) {
		String JPQL = "SELECT o FROM Blog o WHERE o.usuario.idUsuario='" + idUsuario + "'";
		Query q;
		List<Blog> lista;
		q = em.createQuery(JPQL);
		lista = q.getResultList();
		return lista;
	}

	
	public void crearBLog(String idUsuario, String nombreBlog, String descripcion, String tituloArticulo,
			String contenido) throws Exception {
		Usuario u = managerUsuarios.findUsuarioById(idUsuario);
		if (u == null)
			throw new Exception("No existe el usuario " + idUsuario);
		// manejo de datos en cascada (maestro y detalle):
		Blog blog = new Blog();
		blog.setDescripcion(descripcion);
		blog.setNombreBlog(nombreBlog);
		blog.setUsuario(u);
		blog.setArticulos(new ArrayList<Articulo>());// lista vacia
		Articulo articulo = new Articulo();
		articulo.setTitulo(tituloArticulo);
		articulo.setContenido(contenido);
		articulo.setLikes(0);
		articulo.setRecaudado(BigDecimal.valueOf(0.0));
		articulo.setActivo(true);
		articulo.setBlog(blog);// relacion bidireccional
		blog.addArticulo(articulo);// relacion bidireccional
		em.persist(blog);
	}
	
	
	public void insertarArticulo(Blog blog, String titulo, String contenido) {
		Articulo articulo = new Articulo();
		articulo.setTitulo(titulo);
		articulo.setContenido(contenido);
		articulo.setBlog(blog);
		em.persist(articulo);
	}
	
	public List<Articulo> findArticulosByBlog(long idBlog) {// buscar todos los
		// articulos de un
		// blog
String JPQL = "SELECT o FROM Articulo o WHERE o.blog.idBlog='" + idBlog + "'"; 
Query q;
List<Articulo> lista;
q = em.createQuery(JPQL);
lista = q.getResultList();
return lista;
}
	public List<Articulo> consultararticulos() {
		String JPQL = "SELECT a FROM Articulo a"; // SELECT * FROM Articulos
		Query q;
		List<Articulo> lista;
		q = em.createQuery(JPQL);
		lista = q.getResultList();
		return lista;
	}
	
//
	public List<Blog> consultarblogs() {
		String JPQL = "SELECT u FROM Blog u"; 
		Query q;
		List<Blog> lista;
		q = em.createQuery(JPQL);
		lista = q.getResultList();
		return lista;
	}

	public void likesyrecuado(Articulo articulo) {
		Articulo encontrado = em.find(Articulo.class, articulo.getIdArticulo());
		encontrado.setLikes(encontrado.getLikes() + 1); 
		encontrado.setRecaudado(BigDecimal.valueOf(encontrado.getLikes() * 0.25)); 
		em.flush();// modificar y guadar
	}
	// EDITAMOS UN BLOG
		public void editarBlog(Blog blog, String nombreBlog, String descripcion) {//
			Blog seleccionado = em.find(Blog.class, blog.getIdBlog());
			seleccionado.setNombreBlog(nombreBlog);
			seleccionado.setDescripcion(descripcion);
			em.flush();
		}

		public void editarArticulo(Articulo articulo, String tituloArticulo, String contenidoArticulo) {
			Articulo seleccionado = em.find(Articulo.class, articulo.getIdArticulo());
			seleccionado.setTitulo(tituloArticulo);
			seleccionado.setContenido(contenidoArticulo);
			em.flush();
		}

		public void eliminarBlog(Blog blog) throws Exception {
			Blog encontrado = em.find(Blog.class, blog.getIdBlog());
			if (!(encontrado.getArticulos().size() > 0))
				em.remove(encontrado);
			throw new Exception("El blog tiene articulos");
		}

		public void eliminarArticulo(Articulo articulo) {
			Articulo encontrado = em.find(Articulo.class, articulo.getIdArticulo());
			em.remove(encontrado);
		}
		// --------------censurar articulo--------------------

		public void censurarArticulo(Articulo articulo) {
			Articulo encontrado = em.find(Articulo.class, articulo.getIdArticulo());
			if (encontrado.getActivo()) {
				encontrado.setActivo(false);
			} else {
				encontrado.setActivo(true);
			}
			em.flush();

		}
	

}