package me.netshow.crawler.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import me.netshow.crawler.entity.Empresa;
import me.netshow.crawler.service.WriteFile;

public class EmpresaService {


	List <String> linkList = new ArrayList<String>();
	List <String> blackList = new  ArrayList<String>();

	public void controller(String link){

		linkList.add(link);


		while(linkList.size() > 0){
			String site = this.connect(linkList.get(0));
			
			if(linkList.size() > 1){
				System.out.println("próximo: "+linkList.get(1));
			}
			
			List<Empresa> companies = new ArrayList<>();
			List<String> companiesString = this.getCompanies(site);
			companies = this.organize(companiesString);

			blackList.add(linkList.get(0));

			this.changeList(companiesString);
			//			this.getLinks(site);
			
			WriteFile writeFile = new WriteFile();
			writeFile.write(companies);
			
			
			linkList.remove(0);
			
			System.out.println(linkList.size());
		}
	}

	private void changeList(List<String> empresasString){

		String padrao = "https://vimeo.com/";
		
		for(String empresa:empresasString){
			empresa = empresa.replaceAll(" ", "+");
			if(!blackList.contains(empresa)){
				linkList.add(padrao+"search?q="+empresa);
			}

		}
	}

	private List<Empresa> organize(List<String> empresasString){
		String padrao = "https://vimeo.com/";

		List<Empresa> empresas = new ArrayList<Empresa>();

		for(String empresaString: empresasString){

			Empresa empresa = new Empresa();
			empresa.setName(empresaString);

			empresaString = empresaString.replaceAll(" ", "");

			String site = this.connect(padrao+empresaString);
			
				if(!site.equals("")){
					System.out.println(empresa.getName());
					empresa.setVideosNumber(this.getNumber(site));
				}else{
					empresa.setVideosNumber(1);
				}
			empresas.add(empresa);
		}
		return empresas;

	}

	private int getNumber(String site){
		String [] quantidades = site.split("\"videos\"");

		int num = 0;
		
		System.out.println("quantidade: "+quantidades.length +"\n valor: ");
		
		for(int i = 1; i < quantidades.length; i++){
			int end = quantidades[i].indexOf("\"likes\"");

			if(end>0 ){
				
				String valor = quantidades[i].substring(2, end-2).replaceAll(",", "");
				
				valor = "222";
				if(valor.contains(".")){
					String temp = valor;
					temp = temp.replaceAll("K", "");
					double numTemp = Double.parseDouble(temp) * 1000;
					
					int intValue = (int)numTemp;
					valor = ""+intValue;
				}
				valor = valor.replaceAll("K", "000");
				num = Integer.parseInt(valor);
				return num;
			}
		}
		return num;
	}

//
//	private void getLinks(String site){
//		String [] links = site.split("\"link\"");
//
//		for(int i = 0; i < links.length;i++){
//
//			int end =0;
//			if(links[i].contains("\"duration\"")){
//				end = links[i].indexOf("\"duration\"");
//			}else{
//				end = links[i].indexOf("\"pictures\"");
//			}
//
//
//			if(end > 0){
//				//				System.out.println(links[i].substring(2, end-2));
//				//				
//				if(!linkList.contains(links[i].substring(2, end-2).replaceAll(Pattern.quote("\\"), "")) &&
//						!links[i].contains("DOCTYPE") && !links[i].contains("search_id")){
//					linkList.add(links[i].substring(2, end-2).replaceAll(Pattern.quote("\\"), ""));
//				}
//
//			}
//		}
//	}

	private List<String> getCompanies(String site){

		String [] companies = site.split("\"user\"");

		List<String> values = new ArrayList<String>();


		for(int i = 2; i < companies.length;i++){

			int end = companies[i].indexOf("\"link\"");

			if(end > 0){

				if(!values.contains(companies[i].substring(10,end-2))){
					values.add(companies[i].substring(10, end-2));
				}

			}

		}


		return values;
	}


	private String connect(String link){
		String jsonReturn = "";
		try {	
			URL url = new URL(link);

			URLConnection urlConnection = url.openConnection();

			urlConnection.addRequestProperty("User-Agent", 
					"Mozilla/48.0 (compatible; MSIE 6.0; Windows NT 5.0)");

			InputStreamReader inputStreamReader= new InputStreamReader(urlConnection.getInputStream(), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String inputLine;

			while((inputLine = bufferedReader.readLine()) != null){
				jsonReturn += (inputLine + "\n");
			}

			bufferedReader.close();
			inputStreamReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}


		return jsonReturn;
	}
}
