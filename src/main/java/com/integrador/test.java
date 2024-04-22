package com.integrador;

import java.util.List;

import com.integrador.exception.DBException;
import com.integrador.impl.PeliculasRepositoryImpl;
import com.integrador.model.Pelicula;
import com.integrador.repository.PeliculasRepository;
 
public class test {

	public static void main(String[] args) throws DBException {
		PeliculasRepository pr = new PeliculasRepositoryImpl();
		
		/*List<Pelicula> pelis =  pr.getAll();
		 */

		List<Pelicula> pelis = pr.getPeliculasPorGenero("Drama");

		for(Pelicula p: pelis) {
			System.out.println(p.getGeneros());
		}
	}

}
