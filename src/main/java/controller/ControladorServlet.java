
package controller;

import facade.EmpresaFacade;
import exceptions.DatosNoCorrectos;
import modelo.Empleado;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Controlador principal de la aplicación.
 * Gestiona las peticiones y conecta el modelo con las vistas.
 */
@WebServlet("/controlador")
public class ControladorServlet extends HttpServlet {

    private final EmpresaFacade empresa = new EmpresaFacade();

    /**
     * Gestiona las peticiones GET.
     *
     * @param request petición recibida.
     * @param response respuesta enviada.
     * @throws ServletException si ocurre un error del servlet.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            request.getRequestDispatcher("/index.jsp")
                    .forward(request, response);
            return;
        }

        try {
            switch (accion) {

                case "alta":
                    request.getRequestDispatcher("/views/altaEmpleado.jsp")
                            .forward(request, response);
                    break;

                case "buscar":
                    request.getRequestDispatcher("/views/buscarEmpleados.jsp")
                            .forward(request, response);
                    break;

                case "salario":
                    request.getRequestDispatcher("/views/salario.jsp")
                            .forward(request, response);
                    break;

                case "listar":
                    ArrayList<Empleado> empleados =
                            empresa.obtenerEmpleados();

                    request.setAttribute("empleados", empleados);

                    request.getRequestDispatcher("/views/empleados.jsp")
                            .forward(request, response);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    break;
            }

        } catch (SQLException e) {
            throw new ServletException("Error al consultar la base de datos.", e);
        }
    }

    /**
     * Gestiona las operaciones enviadas mediante POST.
     *
     * @param request petición recibida.
     * @param response respuesta enviada.
     * @throws ServletException si ocurre un error del servlet.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch (accion) {

            case "alta":
                altaEmpleado(request, response);
                break;

            case "buscar":
                buscarEmpleados(request, response);
                break;

            case "eliminar":
                eliminarEmpleado(request, response);
                break;

            case "salario":
                consultarSalario(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    /**
     * Registra un empleado junto con su nómina.
     *
     * @param request petición con los datos del empleado.
     * @param response respuesta enviada.
     * @throws ServletException si ocurre un error del servlet.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    private void altaEmpleado(HttpServletRequest request,
                              HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String sexo = request.getParameter("sexo");
        String categoriaTexto = request.getParameter("categoria");
        String anyosTexto = request.getParameter("anyos");

        try {
            if (dni == null || dni.trim().isEmpty()
                    || nombre == null || nombre.trim().isEmpty()
                    || sexo == null || !(sexo.equals("F") || sexo.equals("M"))
                    || categoriaTexto == null || anyosTexto == null
                    || categoriaTexto.trim().isEmpty()
                    || anyosTexto.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Completa todos los campos correctamente.");
            }

            int categoria = Integer.parseInt(categoriaTexto);
            int anyos = Integer.parseInt(anyosTexto);

            if (categoria < 1 || anyos < 0) {
                throw new IllegalArgumentException(
                        "La categoría debe ser positiva y los años no pueden ser negativos.");
            }

            Empleado empleado = new Empleado(
                    nombre.trim(),
                    dni.trim(),
                    sexo.charAt(0),
                    categoria,
                    anyos
            );

            empresa.altaEmpleadoConNomina(empleado);

            request.setAttribute("mensaje", "Empleado creado correctamente.");

            request.getRequestDispatcher("/views/resultadoAlta.jsp")
                    .forward(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("mensaje", e.getMessage());

            request.getRequestDispatcher("/views/altaEmpleado.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                request.setAttribute("mensaje",
                        "Ya existe un empleado con ese DNI o hay un conflicto en la base de datos.");

                request.getRequestDispatcher("/views/altaEmpleado.jsp")
                        .forward(request, response);
            } else {
                throw new ServletException("Error al crear el empleado.", e);
            }
        } catch (DatosNoCorrectos e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca empleados según los filtros recibidos.
     *
     * @param request petición con los filtros.
     * @param response respuesta enviada.
     * @throws ServletException si ocurre un error del servlet.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    private void buscarEmpleados(HttpServletRequest request,
                                 HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        String sexo = request.getParameter("sexo");
        String categoria = request.getParameter("categoria");
        String anyos = request.getParameter("anyos");

        try {
            ArrayList<Empleado> empleados = empresa.buscarEmpleados(
                    dni, nombre, sexo, categoria, anyos
            );

            request.setAttribute("empleados", empleados);

            if (empleados.isEmpty()) {
                request.setAttribute("mensaje",
                        "No se han encontrado empleados.");
            }

            request.getRequestDispatcher("/views/resultadosBusqueda.jsp")
                    .forward(request, response);

        } catch (DatosNoCorrectos e) {
            request.setAttribute("mensaje", e.getMessage());

            request.getRequestDispatcher("/views/buscarEmpleados.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(
                    "Error al buscar empleados en la base de datos.", e);
        }
    }

    /**
     * Elimina un empleado y su nómina.
     *
     * @param request petición con el DNI del empleado.
     * @param response respuesta enviada.
     * @throws ServletException si ocurre un error del servlet.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    private void eliminarEmpleado(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");

        if (dni == null || dni.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath()
                    + "/controlador?accion=listar&error=dni");
            return;
        }

        try {
            empresa.eliminarEmpleado(dni.trim());

            response.sendRedirect(request.getContextPath()
                    + "/controlador?accion=listar&eliminado=si");

        } catch (SQLException e) {
            throw new ServletException("Error al eliminar el empleado.", e);
        }
    }

    /**
     * Consulta el sueldo de un empleado mediante su DNI.
     *
     * @param request petición con el DNI.
     * @param response respuesta con el resultado.
     * @throws ServletException si ocurre un error del servlet.
     * @throws IOException si ocurre un error de entrada o salida.
     */
    private void consultarSalario(HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException, IOException {

        String dni = request.getParameter("dni");

        if (dni == null || dni.trim().isEmpty()) {
            request.setAttribute("mensaje", "Introduce un DNI.");

            request.getRequestDispatcher("/views/resultadoSalario.jsp")
                    .forward(request, response);
            return;
        }

        try {
            int sueldo = empresa.obtenerSueldo(dni.trim());

            if (sueldo == -1) {
                request.setAttribute("mensaje",
                        "No se ha encontrado un salario para ese DNI.");
            } else {
                request.setAttribute("dni", dni.trim());
                request.setAttribute("sueldo", sueldo);
            }

            request.getRequestDispatcher("/views/resultadoSalario.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Error al consultar el salario.", e);
        }
    }
}