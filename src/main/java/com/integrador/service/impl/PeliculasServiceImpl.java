package com.integrador.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.integrador.exception.DBException;
import com.integrador.model.Genero;
import com.integrador.model.Pelicula;
import com.integrador.repository.PeliculasRepository;
import com.integrador.service.PeliculasService;

@Service("peliculasService")
public class PeliculasServiceImpl implements PeliculasService {
	
	@Autowired
	@Qualifier("peliculasRepository")
	private PeliculasRepository peliculasRepository;

	@Override
	public List<Pelicula> getAll() throws DBException {
		return peliculasRepository.getAll();
	}

	@Override
	public Pelicula save(Pelicula pelicula) throws DBException {
		return peliculasRepository.save(pelicula);
	}

	@Override
	public List<Pelicula> getPeliculaPorTitulo(String titulo) throws DBException {
		return peliculasRepository.getPeliculasPorTitulo(titulo);
	}

	@Override
	public List<Pelicula> getPeliculaPorGenero(String genero) throws DBException {
		return peliculasRepository.getPeliculasPorGenero(genero);
	}

	@Override
	public Pelicula getPeliculaPorCodigo(Integer codigo) throws DBException {
		return peliculasRepository.getPeliculasPorCodigo(codigo);
	}

	@Override
	public void eliminarPeliculaPorCodigo(Integer codigo) throws DBException {
		peliculasRepository.deletePeliculaPorCodigo(codigo);
		
	}

	@Override
	public Pelicula modificarPeliculaPorCodigo(Integer codigo, String titulo, String url_sitio, String url_img,
			List<Genero> generos) throws DBException {
		
		peliculasRepository.modificarPeliculaPorCodigo(codigo, titulo, url_sitio, url_img, generos);
		
		return peliculasRepository.getPeliculasPorCodigo(codigo);
	}

	@Override
	public List<String> getGeneros() throws DBException {
		return peliculasRepository.getGeneros();
	}

	@Override
	public Genero getGeneroPorNombre(String g) throws DBException {
		return peliculasRepository.getGeneroPorNombre(g);
	}

}
