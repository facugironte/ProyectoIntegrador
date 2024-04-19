package com.integrador.model;

import java.util.List;

public class Pelicula {
	
	private Integer codigo;
	private String titulo;
	private String url;
	private String img;
	private String generos;
	
	public Pelicula(Integer codigo, String titulo, String url, String img, String generos) {
		super();
		this.codigo = codigo;
		this.titulo = titulo;
		this.url = url;
		this.img = img;
		this.generos = generos;
	}
	
	public Pelicula(String titulo, String url, String img, String generos) {
		super();
		this.titulo = titulo;
		this.url = url;
		this.img = img;
		this.generos = generos;
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

	public String getGeneros() {
		return generos;
	}

	public void setGeneros(String generos) {
		this.generos = generos;
	}
	
	
	

}