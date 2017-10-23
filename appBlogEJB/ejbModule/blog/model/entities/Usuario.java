package blog.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_usuario", unique=true, nullable=false, length=10)
	private String idUsuario;

	private Boolean activo;

	@Column(nullable=false, length=10)
	private String clave;

	@Column(nullable=false, length=50)
	private String correo;

	//bi-directional many-to-one association to Blog
	@OneToMany(mappedBy="usuario")
	private List<Blog> blogs;

	public Usuario() {
	}

	public String getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public List<Blog> getBlogs() {
		return this.blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	public Blog addBlog(Blog blog) {
		getBlogs().add(blog);
		blog.setUsuario(this);

		return blog;
	}

	public Blog removeBlog(Blog blog) {
		getBlogs().remove(blog);
		blog.setUsuario(null);

		return blog;
	}

}