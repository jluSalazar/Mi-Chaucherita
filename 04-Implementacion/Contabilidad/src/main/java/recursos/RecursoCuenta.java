package recursos;

import java.util.List;

import jakarta.websocket.server.PathParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import modelo.dao.CuentaDAO;
import modelo.entidades.Cuenta;

@Path("/cuenta")
public class RecursoCuenta {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cuenta> getPersonas() {
		
		CuentaDAO cuentaDAO = new CuentaDAO();
		return cuentaDAO.getAll();
	}
	
	@Path("/{id}")
	@GET
	//El id es un dato primitivo por ello no se pone el consumes
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON) // Usar JSON si es el formato de producción
	public Cuenta getCuentaByID(@PathParam("id") int id) {
		CuentaDAO cuentaDAO = new CuentaDAO();
		return cuentaDAO.getByID(id);
	}

	@Path("/update")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean actualizarPersona(Cuenta c) {
		CuentaDAO cuentaDAO = new CuentaDAO();
		cuentaDAO.update(c);
		return true;
	}
}
