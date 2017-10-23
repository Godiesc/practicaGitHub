package blog.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the articulo database table.
 * 
 */
@Entity
@Table(name="articulo")
@NamedQuery(name="Articulo.findAll", query="SELECT a FROM Articulo a")
public class Articulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ARTICULO_IDARTICULO_GENERATOR", sequenceName="SEQ_ARTICULO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ARTICULO_IDARTICULO_GENERATOR")
	@Column(name="id_articulo", unique=true, nullable=false, precision=10)
	private long idArticulo;

	private Boolean activo;

	@Column(length=500)
	private String contenido;

	private Integer likes;

	@Column(precision=10, scale=2)
	private BigDecimal recaudado;

	@Column(nullable=false, length=50)
	private String titulo;

	//bi-directional many-to-one association to Blog
	@ManyToOne
	@JoinColumn(name="id_blog", nullable=false)
	private Blog blog;

	public Articulo() {
	}

	public long getIdArticulo() {
		return this.idArticulo;
	}

	public void setIdArticulo(long idArticulo) {
		this.idArticulo = idArticulo;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getContenido() {
		return this.contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public BigDecimal getRecaudado() {
		return this.recaudado;
	}

	public void setRecaudado(BigDecimal recaudado) {
		this.recaudado = recaudado;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Blog getBlog() {
		return this.blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

}