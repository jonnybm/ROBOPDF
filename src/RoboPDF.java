import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class RoboPDF {
	
	
	private static String banco = "RECURSAL";  // BB , CEF OU RECURSAL
	
	public static String caminho = "";  // BB ou CEF
	public static String destino = "";  // BB ou CEF
	public static String gravarValor = ""; 	
	public static String gravarCJ = ""; 	
	public static String parcela = ""; 	
	public static String anoMesBB = " ";
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
		  	String concatenar = "";

		  	//caminho = "/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/PDFBB/";
		  	//destino = "/Volumes/HD/BackupJonny/Projetos/EficienciaFinanceira/PDFBB/";
		  	String arquivoPDF = "";
		  	String texto = "";
		  
		  
		  	File file = new File(caminho);
			File afile[] = file.listFiles();
			int i = 0;
			
			for (int j = afile.length; i < j; i++) {
				
				texto = "";
				
				File arquivos = afile[i];
				arquivoPDF = caminho+arquivos.getName();
				//System.out.println(arquivos.getName());
				
				if(arquivos.getName().indexOf ("pdf") >= 0 || arquivos.getName().indexOf ("doc") >= 0) {
				
					
					//SE FOR BB OU CAIXA O EXTRATO E EM PDF
					if(banco.equals("BB") || banco.equals("CEF"))
						 texto = extraiTextoDoPDF(arquivoPDF);
					else if (banco.equals("RECURSAL"))// SE FOR RECURSAL O EXTRATO E EM WORD
						texto = extraiTextoDoWORD(arquivoPDF);
					
					//System.out.println("CONTEUDO: "+texto);
					
					
					if(!texto.equals("NAOeDOCVALIDO") || texto != "NAOeDOCVALIDO") 
					{
						//Recebe o Conteudo do PDF e coloca em Array com quebra de Linha
						String linhas[] = texto.split("\n");
						
				        					        			
						//Trata o PDF lendo linha a linha do array
						String textoret = trataRetornoPDFeWord(linhas);
						
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
  
							  	nome = null;	
							  	s = null;
							  	
							  	s = new Scanner(anoMesBB);								
								nome = s.nextLine();
							  
								for(int i11 = 0; i11 < nome.length(); i11++) 
								{
								  
								  caminhoNew = caminhoNew+nome.charAt(i11);
								  
								  if((i11+1 == nome.length()) ) 
								  {
									  	caminhoNew = caminhoNew+" ";
									  	
									  	nome = null;	
									  	s = null;

									  	
									  	s = new Scanner(gravarValor);								
										nome = s.nextLine();
										
										
									  
										for(int i111 = 0; i111 < nome.length(); i111++) 
										{
										  
										  caminhoNew = caminhoNew+nome.charAt(i111);
										  
										  if((i111+1 == nome.length()) ) 
										  {
											  
											   	caminhoNew = caminhoNew+" ";
											   	
											  	nome = null;	
											  	s = null;

												
											   	s = new Scanner(gravarCJ);								
												nome = s.nextLine();
											  
												for(int i1111 = 0; i1111 < nome.length(); i1111++) 
												{
												  caminhoNew = caminhoNew+nome.charAt(i1111);
												  
												  if((i1111+1 == nome.length()) ) 
												  {
													  break;
												  }
												}
											break;
										  }
										}
									break;
								  }
							 
								}
							  
							  
								System.out.println("CAMINHO TODO 3: "+caminhoNew+"\n" );
							  
							//  caminhoNew = caminhoNew+anoMesBB+"  "+gravarValor+"  "+gravarCJ; 
							 // System.out.println("CAMINHO TODO 3: "+caminhoNew+"\n" );
							  
							  
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
								  
								if(banco.equals("CEF"))  // SE FOR POR MAIS UM PARAMETRO VAI TER QUE FICAR COMO IF DE BAIXO
							  	{
									caminhoNewBB = caminhoNewBB+".pdf"; 
							  	}
								else if(banco.equals("BB"))
								{
									caminhoNewBB = caminhoNewBB+parcela; //por causa disto aqui tive que fazer mais um scanner abaixo
								}	
								if(banco.equals("RECURSAL"))  // SE FOR POR MAIS UM PARAMETRO VAI TER QUE FICAR COMO IF DE BAIXO
							  	{
									caminhoNewBB = caminhoNewBB+".doc"; 
							  	}
									
							  }
							}
							
							
							if(banco.equals("BB")) //LER TUDO DE NOVO SO PARA POR A EXTENSAO .PDF
							{	
								s = new Scanner(caminhoNewBB);
								
								caminhoNewBB = "";
								
								nome = s.nextLine();
								
								for(int i1 = 0; i1 < nome.length(); i1++) 
								{
								  //System.out.println(nome.charAt(i1));
								  
									caminhoNewBB = caminhoNewBB+nome.charAt(i1);
									
									//TIRAR ESTA LINHA PARA VOLTAR A GRAVAR COMO NOME/CJ/SALDO/PARCELA
									//caminhoNewBB = caminhoNewBB.trim();
									
								  if((i1+1 == nome.length()) ) 
								  {
										caminhoNewBB = caminhoNewBB+".pdf";
										
										//TIRAR ESTA LINHA PARA VOLTAR A GRAVAR COMO NOME/CJ/SALDO/PARCELA										
										//caminhoNewBB = caminhoNewBB.trim();
								  }
								}
							}
							
							
							
							caminhoNew = caminhoNewBB;
					  //	}
						
							//caminhoNew = caminhoNew.replace("._", "");
						
						System.out.println("VAI RENOMEAR PARA: "+caminhoNew+"\n" );
						
						arquivos.renameTo(new File(caminhoNew));
												
						Thread.sleep( 100 );
						
						
						
//						while(!f.exists()) {   
//						    // Aguarde 5 segundos   
//							System.out.println("ARQUIVO NAO ENCONTRADO NO STORAGE AGUARDE A TRANSFERENCIA"+arquivoPDF+"\n" );
//							
//						    Thread.sleep(2000);
//						}
						
						//APAGA LOCAL APOIS TER TRASNFERIDO
						
						//VAI APAGAR O ARQUIVO LOCAL 
//						System.out.println("APAGANDO LOCAL "+arquivos+"\n" );
//						System.out.println("MANTEVE DESTINO "+caminhoNew+"\n" );
						
						//ARQUIVO DE DESTINO EXISTE JA O ARQUIVO ? SE JA EXISTE APAGA LOCAL
						File f = new File(caminhoNew);
					    if(f.exists())
						{
			    				//APAGAR LOCAL NAO GUARDAR MAIS NEM CEF NEM BB
							
					    		arquivos.delete();
						}	
						
						

						
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
				  
				  return "NAOeDOCVALIDO";
			  }

			  //ARQUIVOS OCULTOS WINDOWS
			  if(caminho.indexOf ("desktop") >= 0 || caminho.indexOf ("desktop.ini") >= 0) {
				  
				  return "NAOeDOCVALIDO";
			  }

			  // SE NAO FOR ARQUIVO PDF
			  if(caminho.indexOf ("pdf") < 0) {
				  return "NAOeDOCVALIDO";
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
		        
				//    return "NAOeDOCVALIDO";
		        	}
		        		while (!ok);
		        			return "NAOeDOCVALIDO";
		  		}  
				    //return "NAOeDOCVALIDO";
		  
	
		  
		  	//EXTRAI INFORMACOES CAIXA RECURSAL EM WORD
			 public static String extraiTextoDoWORD(String caminho) throws IOException {
				 BufferedReader br = new BufferedReader(new FileReader(caminho)); 
				 String linha = "";
				 
				  // SE NAO FOR ARQUIVO PDF
				  if(caminho.indexOf ("doc") < 0) {
					  return "NAOeDOCVALIDO";
				  }
				 
				 
			     while (br.ready()) {
			          linha = linha + "\n" +br.readLine();
			          //System.out.println(linha);

			     }
			
			     String linhas[] = linha.split("\n");
				//System.out.println("CONTEUDO :>"+linha + " ]");

			     
			     
			     for (int i = 0; i < linhas.length; i++) 
				  {
					  
					  String ret = linhas[i];
					  
					//System.out.println("CONTEUDO :>"+ret + " ["+i+"]");
				  }
			     
			     
			     br.close();
				return linha;
			 }

		  
		  
		  
		  public static String trataRetornoPDFeWord(String[] linhas) 
		  {
			  
			  String ret = "";
			  String retorno = "";
			  boolean EXTRATOFALTACAMPOS = false;
			  
			  
			  
			  
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
				            		//TIRAR ESTA LINHA PARA VOLTAR A GRAVAR COMO NOME/CJ/SALDO/PARCELA E DESCOMENTAR A DE CIMA
				            		//gravarCJ = gravarCJ.substring(16, gravarCJ.length() -12);  //FAZ SUBSTRING PARA TIRAR O NUMERO DE PARCELA
				            		gravarCJ = gravarCJ.replace("C", "");
				            		gravarCJ = gravarCJ.replace("c", "");
				            		gravarCJ = gravarCJ.replace("P", "");
				            		gravarCJ = gravarCJ.replace("p", "");
				            		gravarCJ = gravarCJ.replace("    ", "");
				            		gravarCJ =  semAcento(gravarCJ.trim());
				            		//System.out.println("RETT 1 = "+ gravarCJ);
				            		
				          }
						  
						  if(i == 2) //LINHA 2 Segunda Parte - NUMERO DA PARCELA
				          {
			            		//SETANDO A PARCELA
							  parcela = linhas[i];

							  
							  //parcela = "  Parcela "+ parcela.substring(parcela.length() - 2);  //FAZ SUBSTRING PARA PRIMEIRA PARTE DA CONTA JUDICIAL 
							  parcela = "  Parcela "+  parcela.substring(parcela.length() - 3,parcela.length());  //FAZ SUBSTRING PARA PRIMEIRA PARTE DA CONTA JUDICIAL
							//  System.out.println("RPARCELA = "+ parcela);							 
							  
							  
							  //TIRAR ESTA LINHA PARA VOLTAR A GRAVAR COMO NOME/CJ/SALDO/PARCELA E DESCOMENTAR A DE CIMA
							  //parcela = "-"+parcela.substring(parcela.length() - 2);  //FAZ SUBSTRING PARA PRIMEIRA PARTE DA CONTA JUDICIAL
				          }
						  
						  
						  if(i == 8) //Linha 8 Caixa pega o Nome 
				          {
							  ret = linhas[i] ;
							  //TIRAR ESTA LINHA PARA VOLTAR A GRAVAR COMO NOME/CJ/SALDO/PARCELA E DESCOMENTAR A DE CIMA	
							  //ret ="";
				            		//ret = linhas[i] +" CJ"+ret
				            		//System.out.println("NOME = " + ret);
				          }
						  
						  if(i == 18) //LINHA 18 PARA PEGAR O MES E O ANO DO EXTRATO
				          {
				            	
							//  System.out.println("VALOR DO DEPOSITO :|"+linhas[i]+"|");  
//							anoMesBB = linhas[i];
//
							  anoMesBB = "_"+linhas[i].substring(6,8)+linhas[i].substring(3, 5);
							//System.out.println("MES + ANO :|"+	anoMesBB);  
							
//				            	getSetBB.setDataMesConsulta(ret.substring(3, 5));
//				            	System.out.println("MES DO DEPOSITO :|"+	getSetBB.getDataMesConsulta()+"|");  
//				            	
//				            	getSetBB.setDataAnoConsulta("20"+ret.substring(6, 8));
//				            	System.out.println("ANO DO DEPOSITO :|"+	getSetBB.getDataAnoConsulta()+"|"); 
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
				        	  	
				            	String[] parts = null;
			            	 	parts = gravarValor.split(" ");
			            		
			            	 	//PEGA O VALOR ULTIMO CONTEUDO
			            	 	gravarValor = parts[parts.length-1];
			            	 	gravarValor = gravarValor.replaceAll("C", "");
			            	 	gravarValor = gravarValor.replaceAll("c", "");
			            	 	gravarValor = gravarValor.replaceAll("D", "");
			            	 	gravarValor = gravarValor.replaceAll("d", "");
				        	  	
				        	  	
				        	  	System.out.println("gravarValor ret "+gravarValor);
				        	  	

					        	
					        	
					        	if(gravarValor.equals("0,00"))
					  				gravarValor = "ZERO";
					        	
					        	if(gravarValor.equals("0.00"))
					  				gravarValor = "ZERO";
					       //else
					        		//TIRAR ESTA EO ELSE DE CIMA LINHA PARA VOLTAR A GRAVAR COMO NOME/CJ/SALDO/PARCELA E DESCOMENTAR A DE CIMA
					        		//gravarValor = "";	
					        		
				          }     
	            
				          //System.out.println("RETT 3 = " + ret);	
				            
						 ret = ret.replace("Autor          :", "");
						 ret = ret.replace("         Saldo do período             ", "");
						 ret = ret.replace("          Saldo do período            ", "");
						 ret = ret.replace("    Saldo do periodo    ", "");
						 ret = ret.replace("/", "");
						 ret = ret.replace("                ", "");
						 ret = ret.replace("Reclamante", "");
						 ret = ret.replace(":", "");
						 ret = ret.replace("     ", "");
						 ret = ret.replace("     Saldo do periodo ", "");
						 ret = ret.replace("Autor", "");
						 ret = ret.replace("     Saldo do periodo    ", "");
						 ret = ret.replace("     Saldo do periodo    ", "");
						 ret = ret.replace("Saldo do periodo", "");
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
						  
						 // System.out.println("LINHA PDF  = " + linhas[i] +"  Linha:" + i );
						  
						  
						 //CASE SEJA UM EXTRA COM POSICAO ERRADA DE CONTA JUDICAL 
						if(i == 18)							
		        	        {
							if( linhas[18].length()  == 20 || linhas[18].length()  == 21)  // No Mac 20 e no Windows 21 
							{
								EXTRATOFALTACAMPOS = false;
								//System.out.println("EXTRATOFALTACAMPOS = false <-------"+ linhas[18].length());
							}
							else
							{
								//System.out.println("EXTRATOFALTACAMPOS = TRUE <-------"+ linhas[18].length());
								EXTRATOFALTACAMPOS = true;
		        	        
							}
		        	        }  
 
						  
				          //if(i == 20 && linhas[i].length() >= 8) //PEGANDO CONTA JUDICIAL
				        	  if(i == 20) //PEGANDO CONTA JUDICIAL
				          {
				        	  		gravarCJ = "";
				        	  		gravarCJ = linhas[i];
//				           		System.out.println("CONTA JUDICIAL---------"+gravarCJ);
//				           		System.out.println("CONTA gravarCJ.substring(0,2)---------"+gravarCJ.substring(0,2));
				        	  		
								if(EXTRATOFALTACAMPOS && linhas[19].length() >= 8)//Quantidade é igual a quantidade de uma CJ tamanho 20? && a Conta Judicial da linha 19 tem mais que 8 digitos caracterizando assim uma conta juridica
				        	        {
									gravarCJ = linhas[19];
									gravarCJ = gravarCJ.substring(17, gravarCJ.length());
									//System.out.println("CONTEM NAO CONTEM APENAS NUMERO <-------"+ linhas[19]);
									//System.out.println("CONTEM NAO CONTEM APENAS NUMERO TAMNHO <-------"+ linhas[19].length());
				        	        }
								else if(linhas[i].length() >= 8) // Linha 20  conta judicial devera ter mais que 8 digitos
								{
									gravarCJ = gravarCJ.substring(17, gravarCJ.length());  //FAZ SUBSTRING PARA TIRAR O NUMERO DE PARCELA DA STRING
									
									//System.out.println("CONTEM APENAS NUMERO <------"+ linhas[i]);
								}	
				        	  		
				        	  		
//				        	  		// Se retornar informacao errada como o saldo e nao a CJ deixar em branco
//				        	  		if(gravarCJ.substring(0,2).equals("R$"))
//				        	  				gravarCJ = linhas[19];
//				        	  		else
//				        	  			gravarCJ = gravarCJ.substring(17, gravarCJ.length());  //FAZ SUBSTRING PARA TIRAR O NUMERO DE PARCELA DA STRING
//				           		
				          	 
				        	  		gravarCJ = gravarCJ.replaceAll("/", "");
				        	  		gravarCJ = gravarCJ.replaceAll("//", "");
				        	  		gravarCJ = gravarCJ.replaceAll("///", "");
				        	  		gravarCJ = gravarCJ.replaceAll(" /// ", "");
				        	  		gravarCJ = gravarCJ.replaceAll(" // ", "");
				        	  		gravarCJ = gravarCJ.replaceAll(" / ", "");
				        	  		
				        	  		//System.out.println("ret-2---------"+gravarCJ);
//				           		
				          }

						  
				          if(i == 25) //PEGANDO O NOME
				          {
				           		//ret = linhas[i];
				           		//ret = linhas[i] +"  CJ"+ ret;
				        	  		ret = "";
				        	  		
				        	  		if(EXTRATOFALTACAMPOS)
				        	        {
				        	  			ret =  linhas[24];	
				        	        }
				        	  		else
				        	  		{	
				        	  			ret =  linhas[i]; 
				        	  		}
				        	  		
				        	  		
				        	  		ret = ret.replaceAll("\\$", "");
				        	  		ret = ret.replaceAll("$", "");
				        	  		ret = ret.replaceAll("R$", "");
				        	  		ret = ret.replaceAll("\\R$", "");
				        	  		ret = ret.replaceAll("(R$)", "NAO FOI POSSIVEL PEGAR O NOME");
				        	  		ret = ret.replaceAll("\\R$", "");
				        	  		ret = ret.replaceAll("\\(", "");
				        	  		ret = ret.replaceAll("\\)", "");
				           		ret = ret.replaceAll("[^\\p{ASCII}]", "");
				           		ret = ret.replaceAll("/", "");
				           		ret = ret.replaceAll("//", "");
				           		ret = ret.replaceAll("///", "");
				           		ret = ret.replaceAll(" /// ", "");
				           		ret = ret.replaceAll(" // ", "");
				           		ret = ret.replaceAll(" / ", "");

				           		ret = ret.replaceAll("#", "");
				           		ret = ret.replaceAll("Saldodoperodo", "");
					        		ret = ret.replaceAll("Saldodoperodo", "");
					        		ret = ret.replaceAll("Anterio", "");
					        		ret = ret.replaceAll("Saldo Data do Movimento Valor Documento Histrico", "    ");
					        		

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
				        	  		
				        	  		//System.out.println("ret---------->"+ret);
				           		
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
				        	  			
					        	  		//array =  linhas[i].substring(5, linhas[i].length()-1).split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha	
					        	  		
//					        	  		System.out.println("QTDE---------->"+Valor.length);
//					        	  		System.out.println("Valor---------->"+Valor[0]);
//					        	  		System.out.println("Valor---------->"+Valor[1]);
//					        	  		System.out.println("Valor---------->"+Valor[2]);
					        	  		
					        	  		//se a penultima linha conter a palavra "Emis" siginifica que nao e a lina que tem valor voltar mais 2 linas
					        	  		if(Valor[1].substring(0, 4).equals("Emis")) {
					        	  			
					        	  			Valor =  linhas[i-3].substring(5, linhas[i-3].length()-1).split(" ");// Pega a linha e Tira a Data que contem incialmente na linha faz Splito para pegar o segundo valor da Linha
				        	  			
//					        	  			System.out.println("Valor---------->"+Valor[1]);
//					        	  			System.out.println("Valor---------->"+Valor[2]);
					        	  			
					        	  		}

					        	  		
					        	  		
					        	  		String retornando = "";
					        	  		String ArrayValor = "";
					        	  		retorno = "";
					        	  		
					        	  		gravarValor = "";
					        	  		//if(Valor.length == 4)
					        	  		//	gravarValor = Valor[2];
					        	  		//else
					        	  		//	gravarValor = Valor[1];
					        	  		
					        	  		gravarValor = Valor[2];
					        	  		
					        	  		

					        	  		gravarValor = gravarValor.replace("Saldo Anterior", "");
					        	  		gravarValor = gravarValor.replace("DB T UNIAO", "");
					        	  		gravarValor = gravarValor.replace("DEB", "");
					        	  		gravarValor = gravarValor.replace("LEV.ALVAR", "");
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
						  			gravarValor = gravarValor.replace("r", "");
						  			gravarValor = gravarValor.replace("Saldo","");

					        	  		
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
			  	// SE NAO É PDF CAIXA  
				else if(banco.equals("RECURSAL")) //Caixa Economica Federal
				{
					  
					System.out.println("EXTRATO CAIXA RECURSAL");
					  
					  for (int i = 0; i < linhas.length; i++) // Le array normal
					  {
						  
						  //System.out.println("LINHA PDF  = " + linhas[i] +"  Linha:" + i );
						  
				          if(i == 4) //PEGANDO CONTA JUDICIAL
				          {
				        	  		gravarCJ = "";
				        	  		gravarCJ = linhas[i];
				        	  		

				        	  		//PEGANDO APENAS NUMERO INTEIRO DA STRING PARA RESGATAR O DODIGO DO EMPREDADO
				           		String numeros = gravarCJ;
				           		Pattern p = Pattern.compile("[0-9]+");
				           		Matcher m = p.matcher(numeros);

				           		StringBuilder nroExtraidos = new StringBuilder();
				           		while (m.find()) {
				           		    nroExtraidos.append(m.group().trim());
				           		}
				           		gravarCJ = nroExtraidos.toString();
				           		//System.out.println("CODIGO EMPREGADO---------"+gravarCJ);
				           		
				           		
				           		
				           		
				        	  		
				        	  		//System.out.println("ret-2---------"+gravarCJ);
//				           		
				          }

						  
				          if(i == 4) //PEGANDO O NOME
				          {
				           		//ret = linhas[i];
				           		//ret = linhas[i] +"  CJ"+ ret;
				        	  		ret = "";
				        	  		ret =  linhas[i]; 
				        	  		
				        	  		ret = ret.replaceAll("CODIGO EMPREGADO--------- COD.EMPRG. :","");
				        	  		ret = ret.replaceAll("COD.EMPRG. :","");
				        	  		ret = ret.replaceAll(gravarCJ,"");
				        	  		ret = ret.replaceAll("DEP","");
				        	  		ret = ret.trim();
				        	  		
				        	  		//System.out.println("NOME EMPREGADO---------"+ret);
				        	  		//ret =  linhas[i].split("");
				        	  		
				        	  		
				        	  		
				        	  		
				        	  		
				        	  		//System.out.println("ret---------->"+ret);
				           		
				          }
				          
				          
				          String[] array ;
				          
				          //PEGANDO O VALOR
				          if(linhas.length-1 == i)//é o Ultimo  Registro?
				          {
				        	  			
				        	  		if(!ret.matches("[0-9]"))//contem apenas numero?
				        	        {
					        	  		
					        	  		
					        	  		String retornando = "";
					        	  		String ArrayValor = "";
					        	  		retorno = "";
					        	  		
					        	  		gravarValor = "";
					        	  		
					        	  		gravarValor = linhas[i];
					        	  		
					        	  		

					        	  		gravarValor = gravarValor.replace("TOTAL SALDO DISPONIVEL", "");
					        	  		gravarValor = gravarValor.trim();

					        	  		
						  			if(gravarValor.equals("0,00"))
						  				gravarValor = "ZERO";
						  			
				        	  			//System.out.println("TOTAL SALDO DISPONIVEL :>>>>>>>>"+gravarValor);

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