package com.integrador;

import java.util.List;

import com.integrador.exception.DBException;
import com.integrador.model.Pelicula;
import com.integrador.repository.PeliculasRepository;
import com.integrador.repository.impl.PeliculasRepositoryImpl;
 
public class test {

	public static void main(String[] args) throws DBException {
		PeliculasRepository pr = new PeliculasRepositoryImpl();
		
		List<Pelicula> pelis = pr.getAll();

		for(Pelicula p: pelis) {
			System.out.print(p.getTitulo() + ": ");
			System.out.println(p.getGenerosCadena());
		}
	}

}
