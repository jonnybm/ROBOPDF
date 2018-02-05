



import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TelaRobo extends javax.swing.JFrame {
	
	static RoboPDF RoboPDF = new RoboPDF();

	
	static String nome = "";
	static String path = "";
	

    public TelaRobo() {
        initComponents();
        setTitle("ROBO RENOMEAR ARQUIVO");
        
        UIManager.LookAndFeelInfo[] inf = UIManager.getInstalledLookAndFeels();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {


        jbtnBotaoExcel = new javax.swing.JButton();
        jbtnBotaoPDF = new javax.swing.JButton();

        jScrollPane1 = new javax.swing.JScrollPane();
        jtaArea = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);


        jbtnBotaoExcel.setText("PDF ORIGINAL");
        jbtnBotaoExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBotaoExcelActionPerformed(evt);
            }
        });

        
        jbtnBotaoPDF.setText("PDF DESINO");
        jbtnBotaoPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBotaoPDFActionPerformed(evt);
            }
        });

        jtaArea.setEditable(false);
       // jtaArea.setColumns(20);
        jtaArea.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        //jtaArea.setRows(5);
        jScrollPane1.setViewportView(jtaArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        //.addComponent(jbtnBotao1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnBotaoExcel)
                        .addComponent(jbtnBotaoPDF)
                        .addGap(18, 18, 18)))//

                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnBotaoPDF)
                    .addComponent(jbtnBotaoExcel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

                                       

    private void jbtnBotaoExcelActionPerformed(java.awt.event.ActionEvent evt) {                                           
    		buscarFileDialogExcel();

    }    
    
    private void jbtnBotaoPDFActionPerformed(java.awt.event.ActionEvent evt) {                                           
        buscarFileDialogPDF();
    }                                          


                                              

    public static void main(String args[]) {
        (new TelaRobo()).show();
	      jtaArea.setText(""
	    		  +"\n   SIGA O PROCEDIMENTO ABAIXO: \n"
	    		  +"\n   1 - SELECIONE A PASTA ORIGEM \n"
	    		  +"   2 - SELECIONE PASTA DESTINO  \n"
	    		  +"\n   PARA FINALIZAR CLIQUE NO X \n"
	    );
    }

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
//    private javax.swing.JButton jbtnBotao1;
    private javax.swing.JButton jbtnBotaoExcel;
    private javax.swing.JButton jbtnBotaoPDF;
//    private javax.swing.JComboBox jcbxLookAndFeel;
    private static JTextPane jtaArea;
    // End of variables declaration                   


    
    private void buscarFileDialogExcel() {
        try {
            
                FileDialog fd = new FileDialog(this, "Buscar Texto", FileDialog.LOAD);
                fd.setMultipleMode(false);
                fd.show();

                File arquivo = new File(fd.getDirectory() + fd.getFile());

                if (!arquivo.isFile()) {
                    return;
                }
                
                nome = arquivo.getParent();// pegando nome excel
              
                jtaArea.setText(""
      	    		  +"\n  MUITO BEM !!: \n"
      	    		  +"\n  AGORA SELECIONE O PDF \n"
      	    		  +"\n  APÓS SELECIONAR O PDF AGUARDE A MENSAGEM DE SUCESSO. \n"
                	);
                
               // jtaArea.setText("" +"\n \n \n \n SELECIONE O PDF E AGUARDE A MENSAGEM FINALIZANDO");              


                
                
        } catch (Exception e) {
        }
    }
    
    
    private void buscarFileDialogPDF() {
        try {
            
                FileDialog fd = new FileDialog(this, "Buscar Texto", FileDialog.LOAD);
                fd.setMultipleMode(false);
                fd.show();

                File arquivo = new File(fd.getDirectory() + fd.getFile());

                if (!arquivo.isFile()) {
                    return;
                }
                
                
                	 
                	jtaArea.setContentType("text/html"); 
                	jtaArea.setText("<html></body><center><h3><font color=#a70104>RENOMEANDO PDF ...</font>.</h3><img src=\"http://passofundo.ifsul.edu.br/imagens/padrao/aguarde.gif\"><center></body></html>"); 
                	

                	path = arquivo.getParent();
                rodarArquivos(path, nome);
                

                

                
                
        } catch (Exception e) {
        }
    }
    
    
//    private void rodarArquivos(String pasta, String pathExcel) {
//        try {
//        	
//        		System.out.println("pathExcel --> "+pathExcel);
//        		System.out.println("pasta --> "+pasta);
//        		
//             EFBB.excelBB = pathExcel;
//             EFBB.caminho = pasta+"/";
//             
//             EFBB.init();
//        } catch (Exception e) {
//        }
//    }
    
    
    public void rodarArquivos(final String pathRenomearPDF, final String pastaPDF) {
		new Thread() {
			
			@Override
			public void run() {
				
	        	//	System.out.println("pastaPDF --> "+pastaPDF);
	        //	System.out.println("pathRenomearPDF --> "+pathRenomearPDF);
	        		
	        		//MAC IOS e WINDOWS
	        		RoboPDF.caminho = pastaPDF+"/";
	        		RoboPDF.destino = pathRenomearPDF+"/";
	        		
//	        		//WINDOWS
//	        		RoboPDF.caminho = pastaPDF+"\\";
//	        		RoboPDF.destino = pathRenomearPDF+"\\";
		            
	        		
	        		try 
	        		{
		           	 RoboPDF.init();
						
		        } catch (IOException e) 
		        {
						// TODO Auto-generated catch block
		        	 	e.printStackTrace();
				} catch (InterruptedException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
		             

		             
		         	jtaArea.setContentType("text/html"); 
		         	jtaArea.setText("<html></body><center><h2><br><br><font color=#0056ee>FINALIZADO COM SUCESSO!!</font></h2><center></body></html>"); 
		             
	        		
//	        		while(true){
//	             
//		             try 
//		             {
//		            	 	RoboPDF.init();
//		             } catch (IOException e) 
//		             {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						try {
//							Thread.sleep( 20000 );
//						} catch (InterruptedException e1) {
//							// TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//						run();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//		             
//		             
//		             
//		             try {
//		            	 	// ENQUANTO ESTIVER RENORMENANDO NAO FAZER NADA
//			            	 while(RoboPDF.findPDF)
//			            	 {
//			            		 Thread.sleep( 30000 ); //ESPERAR 20 Segundos
//			            	 }	 
//			            	 	Thread.sleep( 3600000 ); //ESPERAR 3 Minutos
//					
//		             } catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						run();
//					}
//	        		}
			}
		}.start();

	}
}
