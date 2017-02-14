package me.netshow.crawler.controller;

import me.netshow.crawler.service.EmpresaService;

public class EmpresaController {
	
	private EmpresaService empresaService = new EmpresaService();
	
	public void controller(){
//		String link = "https://vimeo.com/search?q=gama+academy";
		String link = "https://vimeo.com/search?q=Harrison+Frosch";
		//		String link = "https://vimeo.com/gamaacademy";
		
		empresaService.controller(link);
		
	}
}
