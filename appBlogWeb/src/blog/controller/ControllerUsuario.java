package blog.controller;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import blog.model.entities.Usuario;
import blog.model.manager.ManagerUsuarios;
import blog.model.util.ModelUtil;
import blog.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerUsuario {
	private String idUsuario;
	private String clave;
	private String correo;
	private String confirmacionClave;
	private boolean confirmadoLogin;
	@EJB
	private ManagerUsuarios managerUsuarios;

	public String actionLogin() {
		try {
			confirmadoLogin = managerUsuarios.comprobarUsuario(idUsuario, clave);
			JSFUtil.crearMensajeInfo("Login correcto"); // verificamos si el
														// acceso es con admin:
			if (idUsuario.equals("admin")) {
				return "admin/index";
			}
			return "blog/index";
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	public String actionvisitante() {
		return "visitante/index";

	}


	public String actionRegistrarBlogger() {
		try {
			managerUsuarios.registrarNuevoBlogger(idUsuario, clave, confirmacionClave, correo);
			JSFUtil.crearMensajeInfo("Nuevo blogger registrado.");
			return "blog/index";
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String actionSalir() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	public void actionComprobarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			String path = ec.getRequestPathInfo();
			System.out.println("getRequestContextPath(): " + ec.getRequestContextPath());
			System.out.println("getRequestPathInfo(): " + path);
			System.out.println("Id usuario: " + idUsuario);
			if (path.equals("/login.xhtml"))
				return;
			if (ModelUtil.isEmpty(idUsuario))
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			if (!confirmadoLogin) {
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			} else {
				if (idUsuario.equals("admin")) {
					if (!path.contains("/admin/"))
						ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
					else
						return;
				}
				if (!path.contains("/blog/"))
					ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
///////////////////DEBER////////////
	
public List<Usuario> actionListaBloggers() {
return managerUsuarios.findBloggers();
}

public void actionEliminarBlogger(String idUsuario) {
try {
managerUsuarios.eliminarBloggerById(idUsuario);
JSFUtil.crearMensajeInfo("Blogger Eliminado");
} catch (Exception e) {
JSFUtil.crearMensajeError(e.getMessage());
JSFUtil.crearMensajeError("No se elimino Blogger");
}

}


	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getConfirmacionClave() {
		return confirmacionClave;
	}

	public void setConfirmacionClave(String confirmacionClave) {
		this.confirmacionClave = confirmacionClave;
	}

	public boolean isConfirmadoLogin() {
		return confirmadoLogin;
	}

	public void setConfirmadoLogin(boolean confirmadoLogin) {
		this.confirmadoLogin = confirmadoLogin;
	}

	public ManagerUsuarios getManagerUsuarios() {
		return managerUsuarios;
	}

	public void setManagerUsuarios(ManagerUsuarios managerUsuarios) {
		this.managerUsuarios = managerUsuarios;
	}
}