import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class RoboPDF {
	
	
	private static String banco = "CEF";  // BB ou CEF
	
	public static String caminho = "";  // BB ou CEF
	public static String destino = "";  // BB ou CEF
	public static String gravarValor = ""; 	
	public static String gravarCJ = ""; 	
	//public static String gravarCJCEF = "";
	public static Boolean findPDF = false;
	
	public static int apagarTerceiraTentativaCorrompido = 0;



	public static void main(String[] args) throws IOException, InterruptedException {

			
			visualizarArquivos();
			
			
            }

	public static void init() throws IOException, InterruptedException {
		findPDF = true;
		
		visualizarArquivos();
		
	}
	
	
	  public static void visualizarArquivos() throws IOException, InterruptedException {
		  
		  System.out.println("LENDO ORIGEM : "+caminho);
		  System.out.println("LENDO DESTINO: "+destino);

		  	String caminhoNovo = "";
		  	String caminhoNew = "";
		  	String caminhoNewBB = "";

		  	//caminho = "/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/PDFBB/";
		  	//destino = "/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/PDFBB/";
		  	String arquivoPDF = "";
		  
		  
		  	File file = new File(caminho);
			File afile[] = file.listFiles();
			int i = 0;
			
			for (int j = afile.length; i < j; i++) {
				
				File arquivos = afile[i];
				arquivoPDF = caminho+arquivos.getName();
				//System.out.println(arquivos.getName());
				
				if(arquivos.getName().indexOf ("pdf") >= 0) {
				
					//Recebe e Le Texto do PDF
					String texto = extraiTextoDoPDF(arquivoPDF);
					
					//System.out.println("CONTEUDO: "+texto);
					
					
					if(!texto.equals("NAOePDF") || texto != "NAOePDF") 
					{
						//Recebe o Conteudo do PDF e coloca em Array com quebra de Linha
						String linhas[] = texto.split("\n");
						
				        					        			
						//Trata o PDF lendo linha a linha do array
						String textoret = trataPDF(linhas);
						
						// System.out.println("CONTEUDO: "+textoret);
						
						//visualizarArquivos();
						
						
						//caminhoNovo = destino+textoret.toUpperCase();
						caminhoNovo="";
						
						caminhoNovo = caminhoNovo.concat(destino);
						caminhoNovo = caminhoNovo.concat(textoret.toUpperCase());
						//caminhoNovo = caminhoNovo+".pdf";
						
//						//System.out.println(caminhoNovo);
//						caminhoNovo.replace("\\", "");
//						caminhoNovo.replace("//", "");
//						//System.out.println(caminhoNovo);
						
						
						//LER CARACTER POR CARACTER PARA PODER FUNCIONAR NO WINDOWS  (CAMOMINHO NOME E VALOR)
						caminhoNew = "";
						
						Scanner s = new Scanner(caminhoNovo);
						
						String nome = s.nextLine();
						
						for(int i1 = 0; i1 < nome.length(); i1++) 
						{
						  //System.out.println(nome.charAt(i1));
						  
						  caminhoNew = caminhoNew+nome.charAt(i1);
						  
						  if((i1+1 == nome.length()) ) 
						  {
							  
							  caminhoNew = caminhoNew+"  "+gravarValor+"  "+gravarCJ;  
							  
//							if(banco.equals("BB"))
//						  	{
//								caminhoNew = caminhoNew+" "+gravarValor+" "+gravarCJ; 
//								
//						  	}
//							else if(banco.equals("CEF"))
//							{
//								//caminhoNew = caminhoNew+" "+gravarValor+".pdf";
//								caminhoNew = caminhoNew+" "+gravarValor+" "+gravarCJ;
//								
//							}	
							  
							  
						  }
						}
						
						//LER CARACTER POR CARACTER PARA PODER FUNCIONAR NO WINDOWS  (PATH + PDF DO BB)
						caminhoNewBB = "";
						
						//if(banco.equals("BB"))
					  	//{
							s = new Scanner(caminhoNew);
							
							nome = s.nextLine();
							
							for(int i1 = 0; i1 < nome.length(); i1++) 
							{
							  //System.out.println(nome.charAt(i1));
							  
								caminhoNewBB = caminhoNewBB+nome.charAt(i1);
							  
							  if((i1+1 == nome.length()) ) 
							  {
								  
								//if(banco.equals("BB"))
							  	//{
									caminhoNewBB = caminhoNewBB+".pdf"; 
							  	//}
							  }
							}
							caminhoNew = caminhoNewBB;
					  //	}
						
						
						System.out.println("VAI RENOMEAR PARA: "+caminhoNew+"\n" );
						
						arquivos.renameTo(new File(caminhoNew));
												
						Thread.sleep( 200 );
						
//						  if(arquivoPDF.indexOf ("DS_Store") <= 0) //Se arquivo for diferente de arquivo de sistema que nao precosa ser analizado
//						  {
//							  arquivos.renameTo(new File(destino, textoret.toUpperCase()+".pdf"));
//						  }						
					}
					

				}
				//Renomeia os arquivos.  
				//arquivos.renameTo(new File("/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/PDF", textoret+".pdf"));
			} 	
			findPDF = false;
	  	}
  

    		
		  public static String extraiTextoDoPDF(String caminho) throws InterruptedException {
			  
			  //ARQUIVOS OCULTOS MAC
			  if(caminho.indexOf ("DS_Store") >= 0) {
				  
				  return "NAOePDF";
			  }

			  //ARQUIVOS OCULTOS WINDOWS
			  if(caminho.indexOf ("desktop") >= 0 || caminho.indexOf ("desktop.ini") >= 0) {
				  
				  return "NAOePDF";
			  }

			  // SE NAO FOR ARQUIVO PDF
			  if(caminho.indexOf ("pdf") < 0) {
				  return "NAOePDF";
			  }
			  
			  
			    PDDocument pdfDocument = null;
			    
		        boolean ok = true;
		        do {
				    try {
				      pdfDocument = PDDocument.load(caminho);
				      PDFTextStripper stripper = new PDFTextStripper();
				      String texto = stripper.getText(pdfDocument);
				      return texto;
				    } 
				    catch (IOException e) 
				    {
				    		System.out.println("ERRO - LEITURA DO PDF:"+ caminho); 
				    		
				    		Thread.sleep( 5000 );				    		
				    		
				    		ok = false;
				    		
			    			File f = new File(caminho);
			    			f.delete();
			    			
			    			System.out.println("APAGOU ARQUIVO CORROMPIDO : "+ caminho);
				    		
//				    		apagarTerceiraTentativaCorrompido ++;
//				    		
//				    		if(apagarTerceiraTentativaCorrompido == 3)
//				    		{
//				    			apagarTerceiraTentativaCorrompido = 0;
//				    		}	
				    		
				    		//throw new RuntimeException(e);
			    			findPDF = false;
				    		break;
				    		//System.exit(0);
				    } 
				    finally 
				    {
				    		if (pdfDocument != null) 
				    			try 
				    			{
				    				pdfDocument.close();
				    			} 
				    			catch (IOException e) 
				    			{
				    				System.out.println("ERRO FECHAR LEITURA DO PDF");
				    				ok = false;
				    			}
				    }
		        
				//    return "NAOePDF";
		        	}
		        	while (!ok);
		        		return "NAOePDF";
		  		}  
				    //return "NAOePDF";
		  
	
		  

		  
		  
		  
		  public static String trataPDF(String[] linhas) 
		  {
			  
			  String ret = "";
			  String retorno = "";
			  
			  
				  //if(linhas[15].equals("------------------------------------------------") || linhas[15].trim() == "------------------------------------------------" || linhas[15] == "------------------------------------------------") //(banco.equals("BB")) // Banco do Brasil
			  		if(banco.equals("BB"))
			  		{
					  System.out.println("EXTRATO BANCO DO BRASIL");
					  
						  
					  for (int i = 0; i < linhas.length; i++) 
					  {
						
						  if(i == 2) //LINHA 2 Primeira Parte - PEGA a CONTA JUDICIAL
				          {
				            		//ret = linhas[i];				            	
				            		
				            		//ret = ret.substring(26, ret.length() -12);  //FAZ SUBSTRING PARA TIRAR O NUMERO DE PARCELA
							  
							  	gravarCJ	= "";
				            		
				            		gravarCJ = linhas[i];
				            		gravarCJ = gravarCJ.substring(26, gravarCJ.length() -12);  //FAZ SUBSTRING PARA TIRAR O NUMERO DE PARCELA
				            		gravarCJ = gravarCJ.replace("C", "");
				            		gravarCJ = gravarCJ.replace("c", "");
				            		gravarCJ = gravarCJ.replace("P", "");
				            		gravarCJ = gravarCJ.replace("p", "");
				            		gravarCJ =  semAcento(gravarCJ.trim());
				            		//System.out.println("RETT 1 = " + gravarCJ);
				            		
				          }
						  
						  
						  if(i == 8) //Linha 8 Caixa pega o Nome 
				          {
				            		ret = linhas[i] ;
				            		//ret = linhas[i] +" CJ"+ret
				            		//System.out.println("RETT 2 = " + ret);
				          }
						  
						//System.out.println("CONTEDUDO "+linhas[i]);

						  
				          if(linhas.length-1 == i)//é o ultimo  Registro?
				          {
				        	  	gravarValor = "";	
				        	  
				        	  	if(!linhas[i].equals("Voltar")) //Existem extratoq eu aultima linha tem o descricao voltar 
				        	  	{
				        	  		//ret = ret +" "+linhas[i];
				        	  		gravarValor = linhas[i];
				        	  	}
				        	  	else
				        	  	{
				        	  		//ret = ret +" "+linhas[i-1];
				        	  		gravarValor = linhas[i-1];
				        	  	}	
				        	  	
				        	  	gravarValor = gravarValor.replace("C", "");
				        	  	gravarValor = gravarValor.replace("c", "");
				        	  	gravarValor = gravarValor.replace("Autor          :", "");
				        	  	gravarValor = gravarValor.replace("         Saldo do período             ", "");
				        	  	gravarValor = gravarValor.replace("          Saldo do período            ", "");
				        	  	gravarValor = gravarValor.replace("    Saldo do periodo    ", "");
				        	  	gravarValor = gravarValor.replace("/", "");
				        	  	gravarValor = gravarValor.replace("                ", "");
				        	  	gravarValor = gravarValor.replace("Reclamante", "");
				        	  	gravarValor = gravarValor.replace(":", "");
				        	  	gravarValor = gravarValor.replace("     ", "");
				        	  	gravarValor = gravarValor.replace("     Saldo do periodo ", "");
				        	  	gravarValor = gravarValor.replace("Autor", "");
				        	  	gravarValor = gravarValor.replace("     Saldo do periodo    ", "");
				        	  	gravarValor = gravarValor.replace("     Saldo do periodo    ", "");
				        	  	gravarValor = gravarValor.replace("Saldo do periodo", "");
				        	  	gravarValor = gravarValor.replaceAll("[^\\p{ASCII}]", "");
				        	  	gravarValor = gravarValor.replaceAll("//", "");
				        	  	gravarValor = gravarValor.replaceAll("/", "");
				        	  	gravarValor = gravarValor.replaceAll("#", "");
				        	  	gravarValor = gravarValor.replaceAll("Saldodoperodo", "");
				        	  	gravarValor = gravarValor.replaceAll("Saldo do período", "");
				        	  	gravarValor = gravarValor.replaceAll("Saldo do perodo", "");
					        	gravarValor = gravarValor.replaceAll("      ", "");
					        	gravarValor = gravarValor.replace("CONTA JUDICIAL", "");
					        	gravarValor =  semAcento(gravarValor.trim());
					        	
					        	if(gravarValor.equals("0,00"))
					  				gravarValor = "ZERO";
				          }     
	            
				          //System.out.println("RETT 3 = " + ret);	
				            
						 ret = ret.replace("Autor          :", "");
						 ret = ret.replace("         Saldo do período             ", "");
						 ret = ret.replace("          Saldo do período            ", "");
						 ret = ret.replace("    Saldo do periodo    ", "");
						 ret = ret.replace("/", "");
						 ret = ret.replace("                ", "");
						 ret = ret.replace("Reclamante", "");
						 ret = ret.replace(":", "");
						 ret = ret.replace("     ", "");
						 ret = ret.replace("     Saldo do periodo ", "");
						 ret = ret.replace("Autor", "");
						 ret = ret.replace("     Saldo do periodo    ", "");
						 ret = ret.replace("     Saldo do periodo    ", "");
						 ret = ret.replace("Saldo do periodo", "");
			           	ret = ret.replaceAll("[^\\p{ASCII}]", "");
			           	ret = ret.replaceAll("//", "");
			        		ret = ret.replaceAll("/", "");
			        		ret = ret.replaceAll("#", "");
			        		ret = ret.replaceAll("Saldodoperodo", "");
			        		ret = ret.replaceAll("Saldo do período", "");
			        		ret = ret.replaceAll("Saldo do perodo", "");
			        		ret = ret.replaceAll("      ", "");
			        		ret = ret.replace("CONTA JUDICIAL", "");
			        		
			        		//System.out.println("CONTEDUDO ret "+ret);
			                
			        		
						 
					  }
					  //RETORNA BB
					  ret =  semAcento(ret);
					  return ret;
				}
				// SE NAO É PDF CAIXA  
				else if(banco.equals("CEF")) //Caixa Economica Federal
				{
					  
					System.out.println("EXTRATO CAIXA ECONOMICA");
					  
					  for (int i = 0; i < linhas.length; i++) // Le array normal
					  {
						  
						  
						  
				          if(i == 20 && linhas[i].length() >= 8) //PEGANDO CONTA JUDICIAL
				          {
				        	  		gravarCJ = "";
				        	  		gravarCJ = linhas[i];
				           		//System.out.println("gravarValor---------"+gravarCJ);
				           		gravarCJ = gravarCJ.substring(17, gravarCJ.length());  //FAZ SUBSTRING PARA TIRAR O NUMERO DE PARCELA DA STRING
//				           		
				          	// System.out.println("ret-2---------"+gravarCJ);
//				           		
				          }

						  
				          if(i == 25) //PEGANDO O NOME
				          {
				           		//ret = linhas[i];
				           		//ret = linhas[i] +"  CJ"+ ret;
				        	  		ret = "";
				        	  		ret =  linhas[i]; 
				           		
				           		ret = ret.replaceAll("[^\\p{ASCII}]", "");
				           		ret = ret.replaceAll("//", "");
				           		ret = ret.replaceAll("/", "");
				           		ret = ret.replaceAll("#", "");
				           		ret = ret.replaceAll("Saldodoperodo", "");
					        		ret = ret.replaceAll("Saldodoperodo", "");
					        		ret = ret.replaceAll("Anterio", "");

					        		ret = ret.replaceAll("..-", "");
				        	  		ret = ret.replaceAll("Anterio", "");
				        	  		ret = ret.replaceAll("DEP.DINH", "");
				        	  		ret = ret.replaceAll("DEB.AUTOR", "");
				        	  		ret = ret.replace("Remunerao", "");
				        	  		
				        	  		ret = ret.replace("1", "");
				        	  		ret = ret.replace("2", "");
				        	  		ret = ret.replace("3", "");
				        	  		ret = ret.replace("4", "");
				        	  		ret = ret.replace("5", "");
				        	  		ret = ret.replace("6", "");
				        	  		ret = ret.replace("7", "");
				        	  		ret = ret.replace("8", "");
				        	  		ret = ret.replace("9", "");
				        	  		ret = ret.replace("0", "");
				        	  		ret = ret.replace(".", "");
					        		// System.out.println("ret---------->"+ret);
				           		
				          }
				          
				         // System.out.println(linhas.length);
				         // System.out.println(i);
				          
				          String[] array ;
				          
				          //PEGANDO O VALOR
				          if(linhas.length-2 == i)//é o penultimo  Registro?
				          {
				        	  			
				        	  		if(!ret.matches("[0-9]"))//contem apenas numero?
				        	        {
					        	  		
				        	  			
				        	  			
				        	  			String[] Valor = null;
					        	  		Valor =  linhas[i].substring(5, linhas[i].length()-1).split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha
				        	  			
					        	  		array =  linhas[i].substring(5, linhas[i].length()-1).split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha	
					        	  		
				        	  			//se a penultima linha conter a palavra "Emis" siginifica que nao e a lina que tem valor voltar mais 2 linas
					        	  		if(Valor[1].substring(0, 4).equals("Emis")) {
					        	  			
					        	  			Valor =  linhas[i-3].substring(5, linhas[i-3].length()-1).split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha
				        	  			
					        	  			System.out.println("Valor---------->"+Valor[1]);
					        	  			System.out.println("Valor---------->"+Valor[2]);
					        	  			
					        	  		}

					        	  		
					        	  		
					        	  		String retornando = "";
					        	  		String ArrayValor = "";
					        	  		retorno = "";
					        	  		
					        	  		gravarValor = "";
					        	  		gravarValor = Valor[2];
					        	  		gravarValor = gravarValor.replace("CRED JUROS", "");
					        	  		gravarValor = gravarValor.replace("CRED", "");
					        	  		gravarValor = gravarValor.replace("DB", "");
					        	  		gravarValor = gravarValor.replace(" JURO", "");
					        	  		gravarValor = gravarValor.replaceAll("[^\\p{ASCII}]", "");
					        	  		gravarValor = gravarValor.replaceAll("#", "");
					        	  		gravarValor = gravarValor.replaceAll("/", "_");
					        	  		gravarValor = gravarValor.replaceAll("Anterio", "");
					        	  		gravarValor = gravarValor.replaceAll("DEP.DINH", "");
					        	  		gravarValor = gravarValor.replaceAll("DEB.AUTOR", "");
						  			gravarValor = gravarValor.replace("Remunerao", "");

					        	  		
						  			if(gravarValor.equals("0,00"))
						  				gravarValor = "ZERO";
						  			
				        	  			//System.out.println("gravarValor :>"+gravarValor);

				        	        }
				        	  		else {
				        	  			ret =  ret; //se sim para nao dar erro envia apenas o nome
				        	  		}
				        	  		
				          }

					  }
					  //RETORNA CAIXA
					  
					//  System.out.println("RETORNAR__________>: "+ret);	  
					  
					  ret =  semAcento(ret);
					  return ret;
				}	
				  
				  ret =  semAcento(ret);
				  
				  
			  	
			  	return ret;
			  
		  }
		  
		  public static String semAcento(String str) {
		        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD); 
		        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		        return pattern.matcher(nfdNormalizedString).replaceAll("");
		    }

		  
		  
		  public static String clonandoPDF(String texto) {			  
	          
		        FileWriter arquivo;  
		          
		        try {  
		            arquivo = new FileWriter(new File("/Volumes/HD/workspace/RoboPFD/Arquivo.txt"));  
		            arquivo.write(texto);  
		            arquivo.close();  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        } catch (Exception e) {  
		            e.printStackTrace();  
		        }  
			  
			  return "";
			  
		  }

}
