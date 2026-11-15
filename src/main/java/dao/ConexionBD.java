
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestiona las conexiones con la base de datos.
 * Utiliza el patrón Singleton para tener una única instancia.
 */
public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/calculanomina";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456";

    private static final ConexionBD INSTANCIA = new ConexionBD();

    /**
     * Constructor privado para impedir crear otras instancias.
     */
    private ConexionBD() {
    }

    /**
     * Devuelve la única instancia de ConexionBD.
     *
     * @return instancia única de ConexionBD
     */
    public static ConexionBD getInstancia() {
        return INSTANCIA;
    }

    /**
     * Abre una nueva conexión con la base de datos.
     *
     * @return conexión con MySQL
     * @throws SQLException si no se puede establecer la conexión
     */
    public Connection abrirConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "No se encuentra el controlador JDBC de MySQL en Tomcat", e
            );
        }

        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}