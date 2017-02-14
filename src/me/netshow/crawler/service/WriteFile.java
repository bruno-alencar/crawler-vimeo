package me.netshow.crawler.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;

import me.netshow.crawler.entity.Empresa;

public class WriteFile {
	
	public void write(List<Empresa> empresa){
		
		JSONArray  jsonArray = new JSONArray(empresa);
		
		File arquivo = new File("dados.txt");
		try(
			FileWriter fw = new FileWriter(arquivo, true) ){
		    fw.write(jsonArray.toString()+",");
		    fw.flush();
		}catch(IOException ex){
		  ex.printStackTrace();
		}
	}
}
