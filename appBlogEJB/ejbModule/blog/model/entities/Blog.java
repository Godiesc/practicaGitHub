package blog.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the blog database table.
 * 
 */
@Entity
@Table(name="blog")
@NamedQuery(name="Blog.findAll", query="SELECT b FROM Blog b")
public class Blog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BLOG_IDBLOG_GENERATOR", sequenceName="SEQ_BLOG",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BLOG_IDBLOG_GENERATOR")
	@Column(name="id_blog", unique=true, nullable=false, precision=10)
	private long idBlog;

	@Column(length=300)
	private String descripcion;

	@Column(name="nombre_blog", nullable=false, length=50)
	private String nombreBlog;

	//bi-directional many-to-one association to Articulo
	@OneToMany(mappedBy="blog",cascade=CascadeType.ALL)
	private List<Articulo> articulos;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable=false)
	private Usuario usuario;

	public Blog() {
	}

	public long getIdBlog() {
		return this.idBlog;
	}

	public void setIdBlog(long idBlog) {
		this.idBlog = idBlog;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreBlog() {
		return this.nombreBlog;
	}

	public void setNombreBlog(String nombreBlog) {
		this.nombreBlog = nombreBlog;
	}

	public List<Articulo> getArticulos() {
		return this.articulos;
	}

	public void setArticulos(List<Articulo> articulos) {
		this.articulos = articulos;
	}

	public Articulo addArticulo(Articulo articulo) {
		getArticulos().add(articulo);
		articulo.setBlog(this);

		return articulo;
	}

	public Articulo removeArticulo(Articulo articulo) {
		getArticulos().remove(articulo);
		articulo.setBlog(null);

		return articulo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}