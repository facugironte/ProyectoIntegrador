package com.integrador;

import java.util.ArrayList;
import java.util.List;

import com.integrador.exception.DBException;
import com.integrador.model.Genero;
import com.integrador.model.Pelicula;
import com.integrador.repository.PeliculasRepository;
import com.integrador.repository.impl.PeliculasRepositoryImpl;
 
public class test {

	public static void main(String[] args) throws DBException {
		PeliculasRepository pr = new PeliculasRepositoryImpl();
		
		/*List<Pelicula> pelis = pr.getAll();

		for(Pelicula p: pelis) {
			System.out.print(p.getTitulo() + ": ");
			System.out.println(p.getGenerosCadena());
		}*/
		
		/*List<Genero> generos = new ArrayList<>();
		
		generos.add(pr.getGeneroPorNombre("Drama"));
		generos.add(pr.getGeneroPorNombre("Acción"));
		
		
		Pelicula peli = new Pelicula("pelicula prueba", "url", "url_img", generos);
		
		pr.save(peli);*/
		
		List<Genero> generosNuevos = new ArrayList<Genero>();
		
		generosNuevos.add(pr.getGeneroPorNombre("Aventura"));
		
		pr.modificarPeliculaPorCodigo(13, "Fallout", "https://www.imdb.com/title/tt12637874/?ref_=ls_mv_desc", "fallout.webp", generosNuevos);


	}

}
