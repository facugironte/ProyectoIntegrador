package com.integrador.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.integrador.exception.DBException;
import com.integrador.model.Genero;
import com.integrador.model.Pelicula;
import com.integrador.repository.PeliculasRepository;

@Repository("peliculasRepository")
public class PeliculasRepositoryImpl implements PeliculasRepository {
	
	private Connection conn;
	
	private void conectar() throws DBException {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/integrador", "root", "1906");
			System.out.println("La conexión se estableció correctamente");
		} catch (SQLException sqlExcex) {
			throw new DBException(DBException.ERROR_1, "No se pudo conectar con la DB", sqlExcex);
		}
	}	
	private void cerrarConexion() throws DBException {
		try {
			conn.close();
			System.out.println("Se cerró la conexión");
		} catch(SQLException exec) {
			throw new DBException(DBException.ERROR_2, "No se pudo cerrar la conexión. Error: " + exec.getMessage(), exec);
		}
	}
	
	//Modificado
	@Override
	public List<Pelicula> getAll() throws DBException {
		
		conectar();
		
		List<Pelicula> peliculas = new ArrayList<Pelicula>();
		
		String query = "SELECT codigo, titulo, url_sitio, url_img FROM peliculas";
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				
				int codigo = rset.getInt(1);
				String titulo = rset.getString(2);
				String url_sitio = rset.getString(3);
				String url_img = rset.getString(4);
				
				List<Genero> generos = getGenerosPeliculaPorCodigo(codigo);
				
				peliculas.add(new Pelicula(codigo, titulo, url_sitio, url_img, generos));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_3, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		
		return peliculas;
	}
	
	private List<Genero> getGenerosPeliculaPorCodigo(int codigo) throws DBException{
		String queryGen = "SELECT g.id_genero, g.genero "
				+ "FROM generos g "
				+ "INNER JOIN pelicula_genero pg ON g.id_genero = pg.id_genero "
				+ "WHERE pg.codigo_pelicula = ?";
		List<Genero> generos = new ArrayList<Genero>();

		try {
			PreparedStatement stmGen = conn.prepareStatement(queryGen);
			stmGen.setInt(1, codigo);
			ResultSet rsetGen = stmGen.executeQuery();
			
			
			while(rsetGen.next()) {
				generos.add(new Genero(rsetGen.getInt(1), rsetGen.getString(2)));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_12, "No fue posible obtener los generos de la pelicula. Error: " + ex.getMessage(), ex);
		}
		
		return generos;
	}

	public Genero getGeneroPorNombre(String nombre) throws DBException {
		conectar();
		
		Genero genero = null;
		
		String query = "SELECT id_genero, genero FROM generos WHERE genero = ?";
		
		
		PreparedStatement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.prepareStatement(query);
			stm.setString(1, nombre);
			rset = stm.executeQuery();
			
			if(rset.next()) {
				genero = new Genero(rset.getInt(1), rset.getString(2));
			} else {
				System.out.println("No fue posible obtener el genero por nombre");
			}
			
			
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_11, "No fue posible obtener el genero por nombre. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return genero;
	}
	
	//Modificado
	@Override
	public Pelicula save(Pelicula pelicula) throws DBException {
		conectar();

		String query = "INSERT INTO peliculas (titulo, url_sitio, url_img) VALUES (?,?,?)";
		PreparedStatement stm = null;
		
		try {
			
			stm = conn.prepareStatement(query);
			stm.setString(1, pelicula.getTitulo());
			stm.setString(2, pelicula.getUrl());
			stm.setString(3, pelicula.getImg());
			
			
			int rowsAffected = stm.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DBException(DBException.ERROR_4, "La pelicula no fue ingresada");
			}
			System.out.println("Pelicula ingresada correctamente.");
			
		} catch(SQLException ex) {
			throw new DBException(DBException.ERROR_4, "No fue posible ingresar la pelicula", ex);
		}
		
		savePeliculasGenero(getCodigoPeliculaPorTitulo(pelicula.getTitulo()),pelicula.getGeneros());
		
		cerrarConexion();
		return null;
	}

	private int getCodigoPeliculaPorTitulo(String titulo) throws DBException {

		int codigo = -1;
		
		String query = "SELECT codigo FROM peliculas WHERE titulo = ?";
		
		
		PreparedStatement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.prepareStatement(query);
			stm.setString(1, titulo);
			rset = stm.executeQuery();
			
			if(rset.next()) {
				codigo = rset.getInt(1);
			} else {
				System.out.println("No fue posible obtener la pelicula por titulo");
			}
			
			
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_14, "No fue posible obtener la pelicula por titulo. Error: " + ex.getMessage(), ex);
		}
		System.out.println(codigo);
		return codigo;
	}
	
	private void savePeliculasGenero(int codigo_pelicula, List<Genero> generos) throws DBException {

		String query = "INSERT INTO pelicula_genero (codigo_pelicula, id_genero) VALUES (?,?)";
		PreparedStatement stm = null;
		
		for(Genero g: generos) {
			try {
				stm = conn.prepareStatement(query);
				stm.setInt(1, codigo_pelicula);
				stm.setInt(2, g.getId());
				
				int rowsAffected = stm.executeUpdate();
				
				if (rowsAffected == 0) {
					throw new DBException(DBException.ERROR_13, "El genero de la pelicula no fue ingresado");
				}
				System.out.println("Genero ingresado correctamente.");
				
	
				
				
			} catch(SQLException ex) {
				throw new DBException(DBException.ERROR_13, "El genero de la pelicula no fue ingresado", ex);
			}
		}

	}
	
	//Modificado
	@Override
	public List<Pelicula> getPeliculasPorTitulo(String titulo) throws DBException {
		conectar();
		
		List<Pelicula> peliculas = new ArrayList<Pelicula>();
		
		String query = "SELECT codigo, titulo, url_sitio, url_img FROM peliculas WHERE LOWER(titulo) LIKE '%" + titulo.toLowerCase() + "%'";
		
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				
				List<Genero> generos = getGenerosPeliculaPorCodigo(rset.getInt(1));
				
				peliculas.add(new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_5, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return peliculas;
	}
	
	//Modificado
	@Override
	public List<Pelicula> getPeliculasPorGenero(String genero) throws DBException {
		conectar();
		
		List<Pelicula> peliculas = new ArrayList<Pelicula>();
		
		String query = "SELECT DISTINCT p.codigo, p.titulo, p.url_sitio, p.url_img "
					+ "FROM peliculas p "
					+ "JOIN pelicula_genero pg ON p.codigo = pg.codigo_pelicula "
					+ "JOIN generos g ON pg.id_genero = g.id_genero "
					+ "WHERE LOWER(g.genero) LIKE '%" + genero.toLowerCase() + "%'";
		
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				List<Genero> generos = getGenerosPeliculaPorCodigo(rset.getInt(1));
				
				peliculas.add(new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_6, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return peliculas;
	}
	
	//Modificado
	@Override
	public Pelicula getPeliculaPorCodigo(Integer codigo) throws DBException {
		conectar();
		
		Pelicula pelicula = null;
		
		String query = "SELECT codigo, titulo, url_sitio, url_img FROM peliculas WHERE codigo = ?";
		
		PreparedStatement stm = null;
		ResultSet rset = null;
		
		try {
			
			stm = conn.prepareStatement(query);
			stm.setInt(1, codigo);

			rset = stm.executeQuery();
			Boolean hayRegistros = rset.next();

			if (!hayRegistros) {
				throw new DBException(DBException.ERROR_7,
						"No pude encontrar al alumno con codigo: " + codigo);
			}
			
			List<Genero> generos = getGenerosPeliculaPorCodigo(rset.getInt(1));
			
			pelicula = new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos);
			
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_7, "No fue posible descargar la pelicula. Error: " + ex.getMessage(), ex);
		}
		cerrarConexion();
		
		return pelicula;
	}
	
	//Modificado
	@Override
	public void deletePeliculaPorCodigo(Integer codigo) throws DBException {
		conectar();
		
		deleteGenerosPorCodigoPelicula(codigo);
		
		String query = "delete from peliculas where codigo = ?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(query);
			stm.setInt(1,codigo);
			
			int rowsAffected = stm.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DBException(DBException.ERROR_8, "No fue posible eliminar la pelicula con el codigo: " + codigo);
			}
			System.out.println("Pelicula eliminada correctamente");
		} catch (SQLException exec) {
			throw new DBException(DBException.ERROR_8,
					"No fue posible eliminar la pelicula. Error: " + exec.getMessage(), exec);

		} finally {
			try {
				stm.close();
			} catch (SQLException exex) {
				System.err.println("No se pudo cerrar el statement. Error: " + exex.getMessage());
			}
		}
		
		cerrarConexion();
		
	}

	private void deleteGenerosPorCodigoPelicula(Integer codigo) throws DBException {
		
		String query = "delete from pelicula_genero where codigo_pelicula = ?";
		PreparedStatement stm = null;
		try {
			stm = conn.prepareStatement(query);
			stm.setInt(1, codigo);
			int rowsAffected = stm.executeUpdate();
			
			if (rowsAffected == 0) {
				System.out.println("La pelicula " + codigo + " no tiene generos.");
			}
			System.out.println("Generos eliminados correctamente");
			
		} catch (SQLException exec) {
			throw new DBException(DBException.ERROR_15,
					"No fue posible eliminar la pelicula. Error: " + exec.getMessage(), exec);

		} finally {
			try {
				stm.close();
			} catch (SQLException exex) {
				System.err.println("No se pudo cerrar el statement. Error: " + exex.getMessage());
			}
		}
		
	}
	@Override
	public void modificarPeliculaPorCodigo(Integer codigo, String titulo, String url_sitio, String url_img, List<Genero> generos) throws DBException {
		conectar();
		
		deleteGenerosPorCodigoPelicula(codigo);
		savePeliculasGenero(codigo, generos);
		
		String query = "UPDATE peliculas set titulo = ?, url_sitio = ?, url_img = ? WHERE codigo = ?";
		
		PreparedStatement stm = null;
		
		try {
			stm = conn.prepareStatement(query); 
			stm.setString(1, titulo);
			stm.setString(2, url_sitio);
			stm.setString(3, url_img);


			stm.setInt(4, codigo);
			
			int rowsAffected = stm.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DBException(DBException.ERROR_9, "No fue posible modificar la pelicula " + codigo);
				
			}
			System.out.println("Pelicula modificada con éxito.");
		} catch(SQLException ex) {
			throw new DBException(DBException.ERROR_9, "No se pudo modificar la pelicula debido al error: " +ex.getMessage(), ex );
		}
		
		cerrarConexion();
		
	}

	@Override
	public List<String> getGeneros() throws DBException {
		conectar();
		
		List<String> generos = new ArrayList<String>();
		
		String query = "SELECT id_genero, genero FROM generos";
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				generos.add(rset.getString(2));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_10, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return generos;
	}


}
