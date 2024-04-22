package com.integrador.impl;

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
	
	
	@Override
	public List<Pelicula> getAll() throws DBException {
		
		conectar();
		
		List<Pelicula> peliculas = new ArrayList<Pelicula>();
		
		String query = "SELECT codigo, titulo, url_sitio, url_img, generos FROM Peliculas";
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				
				List<Genero> generos = new ArrayList<Genero>();
				
				String[] generosIDs = rset.getString(5).split(", ");
				
				for(String gid: generosIDs) {
					generos.add(getGeneroPorNombre(gid));
				}
				
				peliculas.add(new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_3, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		
		return peliculas;
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
			throw new DBException(DBException.ERROR_12, "No fue posible obtener el genero por nombre. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return genero;
	}
	
	private Genero getGeneroPorID(int id) throws DBException {
		conectar();
		
		Genero genero;
		
		String query = "SELECT id_genero, genero FROM generos WHERE id_genero = ?";
		
		
		PreparedStatement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.prepareStatement(query);
			stm.setInt(1, id);
			rset = stm.executeQuery(query);
			
			genero = new Genero(rset.getInt(1), rset.getString(2));
			
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_11, "No fue posible obtener el genero. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return genero;
	}

	@Override
	public Pelicula save(Pelicula pelicula) throws DBException {
		conectar();

		String query = "INSERT INTO peliculas (titulo, url_sitio, url_img, generos) VALUES (?,?,?,?)";
		PreparedStatement stm = null;
		
		try {
			
			stm = conn.prepareStatement(query);
			stm.setString(1, pelicula.getTitulo());
			stm.setString(2, pelicula.getUrl());
			stm.setString(3, pelicula.getImg());
			
			List<String> generos = new ArrayList<String>();
			
			for(Genero g: pelicula.getGeneros()) {
				generos.add(g.getGenero());
			}
			
			String generosCadena = String.join(", ", generos);
			
			stm.setString(4, generosCadena);
			
			int rowsAffected = stm.executeUpdate();
			
			if (rowsAffected == 0) {
				throw new DBException(DBException.ERROR_4, "La pelicula no fue ingresada");
			}
			System.out.println("Pelicula ingresada correctamente.");
			
		} catch(SQLException ex) {
			throw new DBException(DBException.ERROR_4, "No fue posible ingresar la pelicula", ex);
		}
		
		cerrarConexion();
		return null;
	}

	@Override
	public List<Pelicula> getPeliculasPorTitulo(String titulo) throws DBException {
		conectar();
		
		List<Pelicula> peliculas = new ArrayList<Pelicula>();
		
		String query = "SELECT codigo, titulo, url_sitio, url_img, generos FROM peliculas WHERE LOWER(titulo) LIKE '%" + titulo.toLowerCase() + "%'";
		
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				List<Genero> generos = new ArrayList<Genero>();
				
				String[] generosIDs = rset.getString(5).split(", ");

				
				for(String gid: generosIDs) {
					generos.add(getGeneroPorNombre(gid));
				}
				
				peliculas.add(new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_5, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return peliculas;
	}

	@Override
	public List<Pelicula> getPeliculasPorGenero(String genero) throws DBException {
		conectar();
		
		List<Pelicula> peliculas = new ArrayList<Pelicula>();
		
		String query = "SELECT codigo, titulo, url_sitio, url_img, generos FROM peliculas WHERE LOWER(generos) LIKE '%" + genero.toLowerCase() + "%'";
		
		
		Statement stm = null;
		ResultSet rset = null;
		
		try {
			stm = conn.createStatement();
			rset = stm.executeQuery(query);
			
			while(rset.next()) {
				List<Genero> generos = new ArrayList<Genero>();
				
				String[] generosIDs = rset.getString(5).split(", ");

				
				for(String gid: generosIDs) {
					generos.add(getGeneroPorID(Integer.valueOf(gid)));
				}
				
				peliculas.add(new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos));
			}
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_6, "No fue posible descargar las peliculas. Error: " + ex.getMessage(), ex);
		}
		
		cerrarConexion();
		return peliculas;
	}

	@Override
	public Pelicula getPeliculasPorCodigo(Integer codigo) throws DBException {
		conectar();
		
		String query = "SELECT codigo, titulo, url_sitio, url_img, generos FROM peliculas WHERE codigo = ?";
		
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
			
			List<Genero> generos = new ArrayList<Genero>();
			
			String[] generosIDs = rset.getString(5).split(", ");

			for(String gid: generosIDs) {
				generos.add(getGeneroPorNombre(gid));
			}
			
			Pelicula pelicula = new Pelicula(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), generos);
			cerrarConexion();
			return pelicula;
			
		} catch (SQLException ex) {
			throw new DBException(DBException.ERROR_7, "No fue posible descargar la pelicula. Error: " + ex.getMessage(), ex);
		}
		
	}

	@Override
	public void eliminarPeliculaPorCodigo(Integer codigo) throws DBException {
		conectar();
		
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

	@Override
	public void modificarPeliculaPorCodigo(Integer codigo, String titulo, String url_sitio, String url_img, List<Genero> generos) throws DBException {
		conectar();
		
		String query = "UPDATE peliculas set titulo = ?, url_sitio = ?, url_img = ?, generos = ? WHERE codigo = ?";
		
		PreparedStatement stm = null;
		
		try {
			stm = conn.prepareStatement(query); 
			stm.setString(1, titulo);
			stm.setString(2, url_sitio);
			stm.setString(3, url_img);

			List<String> generosStrings = new ArrayList<>();

			for(Genero g: generos) {
				generosStrings.add(g.getGenero());
			}

			stm.setString(4, String.join(", ", generosStrings));
			stm.setInt(5, codigo);
			
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
