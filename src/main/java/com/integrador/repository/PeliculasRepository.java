package com.integrador.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.integrador.exception.DBException;
import com.integrador.model.Genero;
import com.integrador.model.Pelicula;

@Repository("peliculasRepository")
public interface PeliculasRepository {
	
	public List<Pelicula> getAll() throws DBException;
	
	public Pelicula save(Pelicula pelicula) throws DBException;

	public List<Pelicula> getPeliculasPorTitulo(String titulo) throws DBException;

	public List<Pelicula> getPeliculasPorGenero(String genero) throws DBException;

	public Pelicula getPeliculasPorCodigo(Integer codigo) throws DBException;

	public void eliminarPeliculaPorCodigo(Integer codigo) throws DBException;

	public void modificarPeliculaPorCodigo(Integer codigo, String titulo, String url_sitio, String url_img, String generos) throws DBException;

	public List<String> getGeneros() throws DBException;

	public Genero getGeneroPorNombre(String nombre) throws DBException;

}
