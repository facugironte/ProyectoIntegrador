package com.integrador.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.integrador.exception.DBException;
import com.integrador.model.Genero;
import com.integrador.model.Pelicula;

@Service("peliculasService")
public interface PeliculasService {
	
	public List<Pelicula> getAll() throws DBException;
	
	public Pelicula save(Pelicula pelicula) throws DBException;

	public List<Pelicula> getPeliculaPorTitulo(String titulo) throws DBException;

	public List<Pelicula> getPeliculaPorGenero(String genero) throws DBException;

	public Pelicula getPeliculaPorCodigo(Integer codigo) throws DBException;

	public void eliminarPeliculaPorCodigo(Integer codigo) throws DBException;

	public Pelicula modificarPeliculaPorCodigo(Integer codigo, String titulo, String url_sitio, String url_img,
			String generos) throws DBException;

	public List<String> getGeneros() throws DBException;

	public Genero getGeneroPorNombre(String g) throws DBException;


}
