package com.integrador.model;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {
	
	private Integer codigo;
	private String titulo;
	private String url;
	private String img;
	private List<Genero> generos;
	private String generosCadena;
	
	public Pelicula(Integer codigo, String titulo, String url, String img, List<Genero> generos) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.url = url;
		this.img = img;
		this.generos = generos;
		this.generosCadena = this.generosToCadena(generos);
	}
	
	public Pelicula(String titulo, String url, String img, List<Genero> generos) {
		super();
		this.titulo = titulo;
		this.url = url;
		this.img = img;
		this.generos = generos;
		this.generosCadena = generosToCadena(generos);
	}
	
	private String generosToCadena(List<Genero> generos) {
		List<String> generosStrings = new ArrayList<>();
        for (Genero gen : generos) {
        	generosStrings.add(gen.getGenero());
        }
        return String.join(", ", generosStrings);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public List<Genero> getGeneros() {
		return generos;
	}

	public void setGeneros(List<Genero> generos) {
		this.generos = generos;
	}

	public String getGenerosCadena() {
		return generosCadena;
	}

	public void setGenerosCadena(String generosCadena) {
		this.generosCadena = generosCadena;
	}

	
	
	

}