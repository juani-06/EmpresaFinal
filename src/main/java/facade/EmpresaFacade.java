package facade;

import dao.EmpleadosDAO;
import dao.NominasDAO;
import exceptions.DatosNoCorrectos;
import modelo.Empleado;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Fachada que centraliza las operaciones de empleados y nóminas.
 */
public class EmpresaFacade {

    /**
     * Obtiene todos los empleados.
     *
     * @return lista de empleados.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public ArrayList<Empleado> obtenerEmpleados() throws SQLException {
        return EmpleadosDAO.obtenerEmpleados();
    }

    /**
     * Da de alta un empleado.
     *
     * @param empleado empleado que se va a registrar.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void altaEmpleado(Empleado empleado) throws SQLException {
        EmpleadosDAO.altaEmpleado(empleado);
    }

    /**
     * Da de alta un empleado y crea su nómina.
     *
     * @param empleado empleado que se va a registrar.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void altaEmpleadoConNomina(Empleado empleado) throws SQLException {
        EmpleadosDAO.altaEmpleadoConNomina(empleado);
    }

    /**
     * Busca empleados aplicando los filtros indicados.
     *
     * @param dni DNI que se quiere buscar.
     * @param nombre nombre que se quiere buscar.
     * @param sexo sexo del empleado.
     * @param categoria categoría del empleado.
     * @param anyos años trabajados.
     * @return lista de empleados encontrados.
     * @throws SQLException si ocurre un error en la base de datos.
     * @throws DatosNoCorrectos si los datos no son correctos.
     */
    public ArrayList<Empleado> buscarEmpleados(
            String dni, String nombre, String sexo,
            String categoria, String anyos)
            throws SQLException, DatosNoCorrectos {

        return EmpleadosDAO.buscarEmpleados(dni, nombre, sexo, categoria, anyos);
    }

    /**
     * Actualiza el nombre de un empleado.
     *
     * @param dni DNI del empleado.
     * @param nombre nuevo nombre.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void actualizarNombre(String dni, String nombre) throws SQLException {
        EmpleadosDAO.actualizarNombre(dni, nombre);
    }

    /**
     * Actualiza el sexo de un empleado.
     *
     * @param dni DNI del empleado.
     * @param sexo nuevo sexo.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void actualizarSexo(String dni, char sexo) throws SQLException {
        EmpleadosDAO.actualizarSexo(dni, sexo);
    }

    /**
     * Actualiza la categoría de un empleado.
     *
     * @param dni DNI del empleado.
     * @param categoria nueva categoría.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void actualizarCategoria(String dni, int categoria) throws SQLException {
        EmpleadosDAO.actualizarCategoria(dni, categoria);
    }

    /**
     * Actualiza los años trabajados de un empleado.
     *
     * @param dni DNI del empleado.
     * @param anyos nuevos años trabajados.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void actualizarAnyos(String dni, int anyos) throws SQLException {
        EmpleadosDAO.actualizarAnyos(dni, anyos);
    }

    /**
     * Actualiza el DNI de un empleado y el de su nómina.
     *
     * @param dniActual DNI actual.
     * @param dniNuevo nuevo DNI.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void actualizarDni(String dniActual, String dniNuevo) throws SQLException {
        EmpleadosDAO.actualizarDni(dniActual, dniNuevo);
    }

    /**
     * Elimina un empleado y su nómina.
     *
     * @param dni DNI del empleado.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void eliminarEmpleado(String dni) throws SQLException {
        EmpleadosDAO.eliminarEmpleado(dni);
    }

    /**
     * Consulta el sueldo de un empleado.
     *
     * @param dni DNI del empleado.
     * @return sueldo del empleado o -1 si no existe.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public int obtenerSueldo(String dni) throws SQLException {
        return NominasDAO.obtenerSueldo(dni);
    }

    /**
     * Actualiza el sueldo de un empleado.
     *
     * @param empleado empleado cuyo sueldo se va a actualizar.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void actualizarSueldo(Empleado empleado) throws SQLException {
        NominasDAO.actualizarSueldo(empleado);
    }

    /**
     * Crea una nómina para un empleado.
     *
     * @param empleado empleado al que pertenece la nómina.
     * @throws SQLException si ocurre un error en la base de datos.
     */
    public void altaNomina(Empleado empleado) throws SQLException {
        NominasDAO.altaNomina(empleado);
    }
}
