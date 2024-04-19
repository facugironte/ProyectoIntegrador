package com.integrador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.integrador.exception.DBException;
import com.integrador.model.Pelicula;
import com.integrador.service.PeliculasService;

@Controller
@RequestMapping("/peliculas")
public class PeliculasController {
	
	@Autowired
	@Qualifier("peliculasService")
	private PeliculasService peliculasService;
	
	@GetMapping("/consultar-peliculas")
	public String mostrarPeliculas(Model model) throws DBException {
		List<Pelicula> peliculas = peliculasService.getAll();
		
		model.addAttribute("peliculas", peliculas);
		return("home");
	}
	
	@GetMapping("/alta-pelicula")
	public String altaPeliculaView(){return "alta-pelicula";}
	
	@GetMapping("/buscar-peliculas")
	public String buscarPeliculaView() {return "buscar-peliculas";}
	
	
	@PostMapping("/save-pelicula")
	public String altaPelicula(@RequestParam String titulo, @RequestParam String url_sitio, @RequestParam String url_img, @RequestParam String generos) throws DBException{
		
		Pelicula pelicula = new Pelicula(titulo, url_sitio, url_img, generos);
		
		peliculasService.save(pelicula);
		
		return "redirect:/peliculas/consultar-peliculas";
	}
	
	@PostMapping("/buscar-peliculas-por-titulo")
	public String buscarPeliculaPorTitulo(@RequestParam String titulo, Model model) throws DBException {
		
		List<Pelicula> peliculasEncontradas = peliculasService.getPeliculaPorTitulo(titulo);
	
		model.addAttribute("peliculas", peliculasEncontradas);
		
		return "peliculas-resultado";
	}
	
	@PostMapping("/buscar-peliculas-por-genero")
	public String buscarPeliculaPorGenero(@RequestParam String genero, Model model) throws DBException {
		
		List<Pelicula> peliculasEncontradas = peliculasService.getPeliculaPorGenero(genero);
	
		model.addAttribute("peliculas", peliculasEncontradas);
		
		return "peliculas-resultado";
	}
	
	@PostMapping("/buscar-pelicula-por-codigo")
	public String buscarPeliculaPorCodigo(@RequestParam Integer codigo, Model model) throws DBException {
		
		Pelicula pelicula = peliculasService.getPeliculaPorCodigo(codigo);
	
		model.addAttribute("pelicula", pelicula);
		
		return "pelicula-resultado";
	}
	
	@PostMapping("/eliminar-pelicula")
	public String eliminarPeliculaPorCodigo(@RequestParam String codigo) throws DBException {
				
		System.out.println(codigo);
		peliculasService.eliminarPeliculaPorCodigo(Integer.valueOf(codigo));
		
		return "redirect:/peliculas/consultar-peliculas";
	}
	
	@GetMapping("/modificar-pelicula")
	public String modificarPeliculaView(@RequestParam String codigo, Model model) throws DBException {
				
		Pelicula pelicula = peliculasService.getPeliculaPorCodigo(Integer.valueOf(codigo));
		
		model.addAttribute("pelicula", pelicula);
		
		return "modificar-pelicula";
	}
	
	@PostMapping("/save-modificar-pelicula")
	public String modificarPelicula(@RequestParam String codigo,@RequestParam String titulo, @RequestParam String url_sitio, @RequestParam String url_img, @RequestParam String generos, Model model) throws DBException {
		
		Pelicula pelicula = peliculasService.modificarPeliculaPorCodigo(Integer.valueOf(codigo), titulo, url_sitio, url_img, generos);
		
		model.addAttribute("pelicula", pelicula);
		
		return "redirect:/peliculas/consultar-peliculas";
	}
	
}
