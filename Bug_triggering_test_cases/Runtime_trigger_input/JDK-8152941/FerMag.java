
package FerMag;

/* Andrea Taroni 2016
 * Magazzino Ferramenta
 * V1.0	12/02/2016
 * Legge dai carichi e a gruppi di scarichi genera liste di prelievo che passa alla macchina della ferrmenta.
 * Può passare l'anagrafica ferramenta in macchina se reparto e tipo lista 10=65.8 o 65.9(grande).
 * Rileva le liste attualmente in corso e segnala graficamente dove mettere i pezzi.
 * Tiene aggiornate le liste tra magazzino automatico e AS400 tabella RBDATI.PRFER00F
 * V1.1	19/02/2016
 * Tiene aggiornato un inventario in RBDATI.MAGFE00F e a richiesta crea inventari Excel eventualmente
 * 	accorpando per codice.
 * Gestito il caso di utenti che premono ripetutamente i pulsanti.
 * Mentre faccio un'operazione tengo nell'icona cuore il simbolo dell'operazione.
 * Ottimizza le sequenze operazioni per fare gli scarichi cambiando il minor numero possibile di vassoi.
 * Gestisce i casi di scarichi forzati.
 * V1.2	22/02/2016
 * Prelievo gestito da cassetti specifici tramite il campo STATO MATERIALE.
 * V1.3	23/02/2016
 * Gestito prelievo di ferramenta grande e scelta del cassetto tiene conto dell'impegnato righe precedenti.
 * V1.4	25/02/2016
 * Gestiti ordini a fornitore come liste di versamento.
 * V1.5	29/02/2016
 * Massimo UDC in cui cercare mappato in tabut00f tabgru='FERRA' tabsog='ULTIMO_UDC_PRODUZ'
 * 		02/03/2016
 * Aggiunto piccolo ritocco alle liste ottimizzando spazio descrittivo e ordinandole in macchina per mantenere
 * 	la sequenza per più di 9 liste.
 * V1.6	04/03/2016
 * Aggiunti pulsanti per liste di prelievo ferramenta grande e accessori (reparto 65.9 solo o a parte).
 * Accessorio è un elemento di ferramenta che invece che andare con ferramenta o ferramenta grande va
 * 	imballato a parte, ed è in riga ordine e non accorpato a mobili (magaz livello 3).
 * V1.7	07/03/2016
 * Salta gli scarichi aggiunte (non terminano per '00')
 * V1.8
 * Salta gli scarichi già prodotti campo HCAF01
 * Mette sempre le pilette nella lista Accessori e mai in Ferramenta Grande (campo MAG3K6)
 * V1.9
 * Scrive nel riferimento lista gli scarichi al posto dei numeri ordine.
 * V2.0	11/03/2016
 * Messo tutto su una sola riga di scatole e inserita possibilità di elencare direttamente ordini.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.jacob.com.*;

class FerMag{
	static final String versione="2.0";
	static final JLabel copyright=new JLabel("© Andrea Taroni 2016 - ARBI Arredobagno srl");
	static final Color Colore=new Color(250,250,205);
	static final JLabel raggruppa=new JLabel("Scarichi per volta");
	static final JLabel listOrd=new JLabel("Oppure elenco Ordini da prelevare");
	static final int L_UTILE=1050;
	static final int MAX_GRAF=16;
	static final String lastUdc="Select tabdat from rbdati.tabut00f where tabgru='FERRA' and tabsog='ULTIMO_UDC_PRODUZ'";
	static final String sql1="Select distinct coalesce(m2.mmacod, ma.mmacod) as cod, sum(uriqt1) as qta, urnror as ord, hnusca as sca from rbdati.pctes00f join rbdati.ocexp00f o1 on hnumor=urnror join rbdati.magaz00f ma on urcoda=ma.mmacod left join rbdati.magaz00f m2 on ma.mag3k1=m2.mmacod and m2.mcre10='65' and m2.mlxr10='8' where HCAF01<>'P' and hnucar='£' and coalesce(m2.mcre10,ma.mcre10)='65' and coalesce(m2.mlxr10,ma.mlxr10)='8' and (substr(utree, 7)<>'' or urcoda='ETICMO0' or urcbi in (select urcbi from rbdati.ocexp00f o2 where o1.urnror=o2.urnror and o2.urcbi<>'' and o2.urlive='03')) group by hnusca, urnror, coalesce(m2.mmacod, ma.mmacod) order by hnusca, coalesce(m2.mmacod, ma.mmacod) asc";
	static final String sql1v2="Select distinct coalesce(m2.mmacod, ma.mmacod) as cod, sum(uriqt1) as qta, urnror as ord, hnusca as sca from rbdati.pctes00f join rbdati.ocexp00f o1 on hnumor=urnror join rbdati.magaz00f ma on urcoda=ma.mmacod left join rbdati.magaz00f m2 on ma.mag3k1=m2.mmacod and m2.mcre10='65' and m2.mlxr10='8' where urnror in(£v2) and coalesce(m2.mcre10,ma.mcre10)='65' and coalesce(m2.mlxr10,ma.mlxr10)='8' and (substr(utree, 7)<>'' or urcoda='ETICMO0' or urcbi in (select urcbi from rbdati.ocexp00f o2 where o1.urnror=o2.urnror and o2.urcbi<>'' and o2.urlive='03')) group by hnusca, urnror, coalesce(m2.mmacod, ma.mmacod) order by hnusca, coalesce(m2.mmacod, ma.mmacod) asc";
	static final String sql1g="Select distinct coalesce(m2.mmacod, ma.mmacod) as cod, sum(uriqt1) as qta from rbdati.ocexp00f o1 join rbdati.magaz00f ma on urcoda=ma.mmacod left join rbdati.magaz00f m2 on ma.mag3k1=m2.mmacod and m2.mcre10='65' and m2.mlxr10='9' join rbdati.pctes00f on hnumor=urnror where ma.mag3k6<>'PILETTA' and hcaf01<>'P' and hnucar in(£) and URCODA<>'ETICMO0' AND coalesce(m2.mcre10,ma.mcre10)='65' and coalesce(m2.mlxr10,ma.mlxr10)='9' and (substr(utree, 7)<>'' or urcbi in (select urcbi from rbdati.ocexp00f o2 where o1.urnror=o2.urnror and o2.urcbi<>'' and o2.urlive='03')) group by coalesce(m2.mmacod, ma.mmacod) order by coalesce(m2.mmacod, ma.mmacod) asc";
	static final String sql2g="Select distinct coalesce(m2.mmacod, ma.mmacod) as cod, sum(uriqt1) as qta from rbdati.ocexp00f o1 join rbdati.magaz00f ma on urcoda=ma.mmacod left join rbdati.magaz00f m2 on ma.mag3k1=m2.mmacod and m2.mcre10='65' and m2.mlxr10 in('8', '9') join rbdati.pctes00f on hnumor=urnror where hcaf01<>'P' and URCODA<>'ETICMO0' AND hnucar in(£) and coalesce(m2.mcre10,ma.mcre10)='65' and coalesce(m2.mlxr10,ma.mlxr10) IN('8', '9') and (substr(utree, 7)='' and (urcbi='' or urcbi not in (select urcbi from rbdati.ocexp00f o2 where o1.urnror=o2.urnror and o2.urcbi<>'' and o2.urlive='03'))) group by coalesce(m2.mmacod, ma.mmacod) order by coalesce(m2.mmacod, ma.mmacod) asc";
	static final String sql2="Delete from rbdati.prfer00f where plista='£'";
	static final String sql2b="Delete from rbdati.prfer00f where piqtae>=piqtao";
	static final String sql3="Insert into rbdati.prfer00f(pnumor, pnucar, pnusca, pcoida, piqtao, plista) values ('$ord', '$car', '$sca', '$cod', $qta, '$lista')";
	static final String sql4="Update rbdati.prfer00f set plista='£1' where plista='$lista' and '£2' like '%'||pnumor||'%'";
	static final String sqlA="Select acdpar, ad1par, ad2par, munmis from rbdati.apart00f join rbdati.magaz00f on acdpar=mmacod where (mcre10='65' and mlxr10 IN('8', '9')) or (mcre10='66' and mlxr10='1')";
	static final String connectStr="Provider=vfpoledb;Data Source=M:\\FERRAMENTA\\WIN_ESE7\\files\\;Collating Sequence=general;";
	static final String statoMag="select lis_stator, lis_nome, lis_art, lis_qta, lis_qtae from liste order by lis_nome, lis_rigan asc";
	static final String statoMag2="Select sco_udc, sco_art, sco_giac from SCOMPART ORDER BY sco_art asc";
	static final String delMagFe="Delete from rbdati.magfe00f";
	static final String insMagFe="insert into rbdati.magfe00f (nudc, ncoida, niqtam) values('£1', '£2', £3)";
	static final String delStato="update rbdati.prfer00f set pstat='' where pstat<>''";
	static final String delLista="delete from rbdati.prfer00f where pstat=''";
	static final String delMag="Update rbdati.prfer00f set piqtae=piqtao where plista='£1' and pcoida='£2'";
	static final String aggMag="Update rbdati.prfer00f set pstat='£1' where plista='£2' and pcoida='£3'";
	static final String sql5="Select distinct pnumor, cast(pnusca as int) as pnusca from rbdati.prfer00f where plista='£' order by cast(pnusca as int)";
	static final String sql6="Select ad1par, ad2par, pnumor, pnucar, pnusca, piqtao from rbdati.prfer00f join rbdati.apart00f on acdpar=pcoida where plista='£1' and pcoida='£2'";
	static final String inv="Select £ncoida, ad1par, ad2par, sum(niqtam) as qta from rbdati.MAGFE00F join rbdati.apart00f on acdpar=ncoida group by £ncoida, ad1par, ad2par order by £ncoida";
	static final String cercaCass="Select distinct nudc from rbdati.magfe00f where ncoida='£1' and niqtam>=£2 and nudc<='£3'";
	static final String aggCass="update rbdati.magfe00f set niqtam=niqtam-£1 where ncoida='£2' and nudc='£3'";
	static final String sqlForn="select ofrcai as cod, ofrrif as rif, sum(ofrqto) as qta from rbdati.ofrig00f join rbdati.magaz00f on ofrcai=mmacod where mcre10||mlxr10 in ('658', '659') and ofrnuo='£' group by ofrcai, ofrrif";
	static final String[][] inventarioColonne={{"Articolo","Descrizione","Quantità"},{"Cassetto","Articolo","Descrizione","Quantita'"}};
	static JLabel caricoCorrente,foto,cuore;
	static String ultimoUdcProd,questoCarico="";
	static String cassetti="";
	static JTextField carico=new JTextField("");
	static JTextField oFor=new JTextField("");
	static JTextField scarichi=new JTextField("4");
	static JTextField elenco=new JTextField("");
	static JCheckBox[] scarico;
	static List<String[]> dati=new ArrayList<String[]>();
	static List<String[]> anag=new ArrayList<String[]>();
	static List<String[]> datiMag=new ArrayList<String[]>();
	static List<String[]> magFe=new ArrayList<String[]>();
	static List<String[]> datiOf=new ArrayList<String[]>();
	static List<JLabel> disegniScatole=new ArrayList<JLabel>();
	static List<JLabel> disegni=new ArrayList<JLabel>();
	static List<JLabel> inPrel=new ArrayList<JLabel>();
	static TextArea log;
	static Connection con;
	static Statement stmt;
	static ResultSet rs;
	static Finestra finestra;
	static Image img;
	static JButton cerca,genera,articoli,invent,ordFor,fGrande,accessori;
	static JCheckBox accorpa=new JCheckBox("Accorpato",true);
	static Gestore gestore=new Gestore();
	static JPanel pannello=new JPanel(null);
	static int ordiniNelCarico,nScarichi,cicla,nListe=0;
	static SXSSFWorkbook cartella;
	static private Sheet foglio;
	public static void main(String[] args)throws java.lang.ClassCastException{
		inizializza();
        while(!gestore.azione.equals("Fine")){
        	if(gestore.azione.equals("Cerca Ferramenta"))cerca();
        	else if(gestore.azione.equals("Genera Liste"))genera();
        	else if(gestore.azione.equals("Ferr.Grande"))generaGrande(sql1g,"Ferr.Grande");
        	else if(gestore.azione.equals("Accessori"))generaGrande(sql2g,"Accessori");
        	else if(gestore.azione.equals("Aggiorna Articoli da AS400"))importaArt();
        	else if(gestore.azione.equals("Inventario"))esportaMag();
        	else if(gestore.azione.equals("Importa Ord.Fornitore"))ordFor();
        	else try{Thread.sleep(400);}
        		catch(InterruptedException e){}
        	if(cicla++==3){
        		cicla=0;
        		aggiornaListe();
        	}
        }
	}
	static void cerca() throws java.lang.ClassCastException{
		dati.clear();
		ordiniNelCarico=0;
		nListe=0;
		questoCarico="";
		String sqlCorretto=sql1;
		String listaOrdiniFormattata="";
		if(carico.getText().equals("") && elenco.getText().equals(""))popUp(2,"Carico Mancante","Indicare prima il numero del carico");
		else if(carico.getText().contains(","))popUp(2,"Un solo carico per volta","Stai cercando di generare elenchi di più carichi");
		else try{
			String c="000000";
			System.out.println("Ok1");
			if(!elenco.getText().equals("")){
				System.out.println("Ok2");
				sqlCorretto=sql1v2;
				StringTokenizer tok=new StringTokenizer(elenco.getText(),", ");
				while(tok.hasMoreTokens())listaOrdiniFormattata+=", '"+tok.nextToken()+"'";
				if(listaOrdiniFormattata.length()>1)listaOrdiniFormattata=listaOrdiniFormattata.substring(2,listaOrdiniFormattata.length());
			}
			else{
				questoCarico=carico.getText().trim();
				System.out.println("Ok3");
				c="000000"+Integer.parseInt(questoCarico);
				System.out.println("Ok3-2");
				c=c.substring(c.length()-6,c.length());
			}
			if(Integer.parseInt(scarichi.getText().trim())>MAX_GRAF)popUp(2,"Troppi Scarichi","Per usare la grafica, usare al massimo "+MAX_GRAF+" scatole per volta.");
			System.out.println("Ok4");
			cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Cerca.png"));
			pannello.add(cuore);
			pannello.repaint();
			nScarichi=Integer.parseInt(scarichi.getText().trim());
			questoCarico=carico.getText();
			con=Conn.getConnection();
        	stmt=con.createStatement();
        	rs=stmt.executeQuery(sqlCorretto.replace("£v2",listaOrdiniFormattata).replace("£",c));
        	System.out.println(sqlCorretto.replace("£v2",listaOrdiniFormattata).replace("£",c));
        	String ordineConfronto="";
        	while(rs.next()){
        		String[] dato=new String[4];
        		dato[0]=rs.getString("COD").trim();
        		dato[1]=rs.getString("SCA").trim();
        		dato[2]=rs.getString("ORD").trim();
        		dato[3]=rs.getString("QTA").trim();
        		dato[3]=dato[3].replace(".0000","");
        		dati.add(dato);
        		if(!ordineConfronto.equals(dato[2])){
        			ordineConfronto=dato[2];
        			ordiniNelCarico++;
        		}
        	}
        	rs.close();
        	stmt.close();
        	con.close();
        	nListe=(int)Math.ceil((double)ordiniNelCarico/nScarichi);
        	log.append("\r\nLista elaborata. Trovate "+dati.size()+" righe ferramenta in "+ordiniNelCarico+" scarichi diversi.");
        	log.append("\r\nRaggruppando "+nScarichi+" scarichi per volta, otterremo "+nListe+" Liste di Prelievo");
        }catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		gestore.azione="Attesa";
		pannello.remove(cuore);
	}
	static void ordFor(){
		String of=oFor.getText().trim();
		gestore.azione="Attesa";
		if(oFor.getText().trim().equals(""))popUp(2,"Manca l'ordine a fornitore","Indicare il numero dell'ordine a fornitore");
		else try{
			while(of.length()<6)of="0"+of;
			con=Conn.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(sqlForn.replace("£",of));
			String rif="";
			File miaLista=new File("\\\\arbiclsfiles\\Fabbrica\\Ferramenta\\win_ese7\\files\\import\\lista.imp");
			log.append("\r\nTrasferisco l'ordine a fornitore numero "+of+" alla macchina...");
			while(miaLista.exists())try{Thread.sleep(300);}catch(InterruptedException e){}
			miaLista.createNewFile();
			BufferedWriter br=new BufferedWriter(new FileWriter(miaLista));
			while(rs.next()){
				String[] tmp=new String[2];
				tmp[0]=rs.getString("COD").trim();
				tmp[1]=rs.getString("QTA").trim();
				if(tmp[1].contains("."))tmp[1]=tmp[1].substring(0,tmp[1].indexOf("."));
				tmp[1]=tmp[1]+".00";
				if(rif.equals("")){
					rif=rs.getString("RIF");
					if(rif.trim().equals(""))rif="Ordine a Fornitore "+of;
					else while(rif.contains("  "))rif=rif.replace("  "," ");
					br.write("Ord.For "+of+"|V|"+rif+"|");
				}
				br.write("\r\nOrd.For "+of+"||"+tmp[0]+"|||99|"+tmp[1]+"||||");
			}
			rs.close();
			stmt.close();
			con.close();
			br.flush();
			br.close();
			log.append("\r\nOrdine a fornitore numero "+of+" trasferito.");
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
	}
	static void genera() throws java.lang.ClassCastException{
		cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Fulmine.png"));
		pannello.add(cuore);
		pannello.repaint();
		int processati=0;
		String tmp2=""+nListe;
		for(int i=0;i<nListe;i++){
			List<String[]> lista=new ArrayList<String[]>();
			String ordineConfronto=dati.get(processati)[2];
			int ordiniFatti=0;
			String listaScarichi=dati.get(processati)[1];
			if(listaScarichi.endsWith("00"))listaScarichi=listaScarichi.substring(0,listaScarichi.length()-2);
			String listaOrdini=ordineConfronto;
			while(ordiniFatti<nScarichi && processati<dati.size()){ //accorpo qta x lista prelievo
				String[] listaFer=new String[3];
				listaFer[0]=dati.get(processati)[0];
				listaFer[1]=dati.get(processati)[3];
				if(!ordineConfronto.equals(dati.get(processati)[2])){
					ordineConfronto=dati.get(processati)[2];
					ordiniFatti++;
					if(ordiniFatti<nScarichi){
						listaOrdini+=","+ordineConfronto;
						listaScarichi+=","+dati.get(processati)[1];
						if(listaScarichi.endsWith("00"))listaScarichi=listaScarichi.substring(0,listaScarichi.length()-2);
					}
				}
				if(ordiniFatti<nScarichi){
					try{
						con=Conn.getConnection();
						stmt=con.createStatement();
						stmt.executeUpdate(sql3.replace("$ord",dati.get(processati)[2]).replace("$car",questoCarico).replace("$sca",dati.get(processati)[1]).replace("$cod",dati.get(processati)[0]).replace("$qta",dati.get(processati)[3]));
						stmt.close();
						con.close();
					}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
					boolean found=false;
					for(String[] o:lista)if(o[0].equals(listaFer[0])){
						o[1]=""+(Integer.parseInt(o[1])+Integer.parseInt(listaFer[1]));
						found=true;
					}
					if(!found)lista.add(listaFer);
					processati++;
				}
			} //Ordino la lista per codice
			Collections.sort(lista, new Comparator<String[]>(){
		        public int compare(String[] s1, String[] s2){
		            return  s1[0].compareTo(s2[0]);
		        }
		    });
			try{//scrivo il file di testo della lista
				String tmp1=""+(i+1);
				while(tmp1.length()<tmp2.length())tmp1="0"+tmp1;
				String nomeLista="CAR "+questoCarico+" "+tmp1+"-"+tmp2;
				File miaLista=new File("\\\\arbiclsfiles\\Fabbrica\\Ferramenta\\win_ese7\\files\\import\\lista.imp");
				log.append("\r\nAttendo che il magazzino prelevi la Lista di Prelievo "+tmp1+"/"+tmp2+"...");
				con=Conn.getConnection();
				stmt=con.createStatement();
				stmt.executeUpdate(sql2.replace("£",nomeLista));
				stmt.executeUpdate(sql4.replace("£1",nomeLista).replace("£2",listaOrdini));
				cassetti="";
				for(String[] el:lista){
					rs=stmt.executeQuery(cercaCass.replace("£1",el[0]).replace("£2",el[1]).replace("£3",ultimoUdcProd));
					List<String> cass1=new ArrayList<String>();
					while(rs.next())cass1.add(rs.getString("NUDC").trim());
					rs.close();
					boolean ok=true;
					if(cass1.size()==0)el[2]="1000";
					else if(cass1.size()==1){
						if(!cassetti.contains(cass1.get(0)))cassetti+="§"+cass1.get(0);
						el[2]=cass1.get(0);
					}
					else{
						ok=false;
						for(String s:cass1)if(cassetti.contains(s)){
							ok=true;
							el[2]=s;
						}
						if(!ok){
							cassetti+="§"+cass1.get(0);
							el[2]=cass1.get(0);
						}
					}
				}
				stmt.close();
				con.close();
				Collections.sort(lista, new Comparator<String[]>(){
			        public int compare(String[] s1, String[] s2){
			            return  s1[2].compareTo(s2[2]);
			        }
			    });
				while(miaLista.exists())try{Thread.sleep(300);}catch(InterruptedException e){}
				miaLista.createNewFile();
				BufferedWriter br=new BufferedWriter(new FileWriter(miaLista));
				br.write(nomeLista+"|P|SCAR. "+listaScarichi+"|");
				for(String[] p:lista){
					p[1]=p[1]+".00";
					if(p[2].length()==4)p[2]=p[2].substring(2,4);
					
					br.write("\r\n"+nomeLista+"||"+p[0]+"|||"+p[2]+"|"+p[1]+"||||");
				}
				br.flush();
				br.close();
			}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		}
		log.append("\r\nEsportazione Liste di Prelievo terminata.");
		gestore.azione="Attesa";
		pannello.remove(cuore);
	}
	static void generaGrande(String stringa, String titolo) throws java.lang.ClassCastException{
		cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Fulmine.png"));
		pannello.add(cuore);
		pannello.repaint();
		StringTokenizer carichi=new StringTokenizer(carico.getText(),",");
		String carG="";
		while(carichi.hasMoreTokens()){
			String tmp=carichi.nextToken().trim();
			while(tmp.length()<6)tmp="0"+tmp;
			carG+="'"+tmp+"', ";
		}
		if(carG.length()>1)carG=carG.substring(0,carG.length()-2);
		List<String[]> ferGra=new ArrayList<String[]>();
		try{
			log.append("\r\nCalcolo della Lista di Prelievo "+titolo+" in corso, attendere...");
			con=Conn.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(stringa.replace("£",carG));
			while(rs.next()){
				String[] tmp=new String[3];
				tmp[0]=rs.getString("COD");
				tmp[1]=rs.getString("QTA");
				tmp[2]="0";
				ferGra.add(tmp);
			}
			rs.close();
			stmt.close();
			con.close();
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		Collections.sort(ferGra, new Comparator<String[]>(){
			public int compare(String[] s1, String[] s2){
		        return  s1[0].compareTo(s2[0]);
		    }
		});
		try{//scrivo il file di testo della lista
			String nomeLista=titolo+" "+carico.getText();
			File miaLista=new File("\\\\arbiclsfiles\\Fabbrica\\Ferramenta\\win_ese7\\files\\import\\lista.imp");
			log.append("\r\nAttendo che il magazzino prelevi la Lista di Prelievo "+titolo+"...");
			con=Conn.getConnection();
			stmt=con.createStatement();
			cassetti="";
			for(String[] el:ferGra){
				rs=stmt.executeQuery(cercaCass.replace("£1",el[0]).replace("£2",el[1]).replace("£3",ultimoUdcProd));
				List<String> cass1=new ArrayList<String>();
				while(rs.next())cass1.add(rs.getString("NUDC").trim());
				rs.close();
				boolean ok=true;
				if(cass1.size()==0)el[2]="1000";
				else if(cass1.size()==1){
					if(!cassetti.contains(cass1.get(0)))cassetti+="§"+cass1.get(0);
					el[2]=cass1.get(0);
				}
				else{
					ok=false;
					for(String s:cass1)if(cassetti.contains(s)){
						ok=true;
						el[2]=s;
					}
					if(!ok){
						cassetti+="§"+cass1.get(0);
						el[2]=cass1.get(0);
					}
				}	//sottraggo dal cassetto scelto le quantità impegnate
				stmt.executeUpdate(aggCass.replace("£1",el[1]).replace("£2",el[0]).replace("£3",el[2]));
			}
			stmt.close();
			con.close();
			Collections.sort(ferGra, new Comparator<String[]>(){
			    public int compare(String[] s1, String[] s2){
			        return  s1[2].compareTo(s2[2]);
			    }
			});
			while(miaLista.exists())try{Thread.sleep(300);}catch(InterruptedException e){}
			miaLista.createNewFile();
			BufferedWriter br=new BufferedWriter(new FileWriter(miaLista));
			br.write(nomeLista+"|P|CARICHI: "+carico.getText().trim()+"|");
			for(String[] p:ferGra){
				p[1]=p[1]+".00";
				if(p[2].length()==4)p[2]=p[2].substring(2,4);
				br.write("\r\n"+nomeLista+"||"+p[0]+"|||"+p[2]+"|"+p[1]+"||||");
			}
			br.flush();
			br.close();
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		log.append("\r\nEsportazione Liste di Prelievo "+titolo+" terminata.");
		gestore.azione="Attesa";
		pannello.remove(cuore);
	}
	static void aggiornaListe() throws java.lang.ClassCastException{
		cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Cuore.png"));
		pannello.add(cuore);
		pannello.repaint();
		String listaOra="";
		String artOra="";
		magFe.clear();
		RecordsetJDBC rs = new RecordsetJDBC();
		rs.Open(new Variant(statoMag2), new Variant(connectStr));
		Fields fs2 = rs.getFields();
		while (!rs.getEOF()){
			String[] tmp=new String[fs2.getCount()];
			for(int i=0;i<fs2.getCount();i++){
		        Field f = fs2.getItem(i);
		       	Variant v = f.getValue();
		       	tmp[i]=""+v;
		    }
			magFe.add(tmp);
		   	rs.MoveNext();
		}
		rs.Close();
		rs.Open(new Variant(statoMag), new Variant(connectStr));
		Fields fs = rs.getFields();
		datiMag.clear();
		while (!rs.getEOF()){
			String[] tmp=new String[fs.getCount()];
			for(int i=0;i<fs.getCount();i++){
		        Field f = fs.getItem(i);
		       	Variant v = f.getValue();
		       	tmp[i]=""+v;
		    }
			datiMag.add(tmp);
			if(tmp[0].trim().equals("E")&&listaOra.equals("")){
				listaOra=tmp[1];
				artOra=tmp[2];
			}
		   	rs.MoveNext();
		}
		rs.Close();
		try{
			con=Conn.getConnection();
			stmt=con.createStatement();
			stmt.executeUpdate(delStato);
			stmt.executeUpdate(delMagFe);
			for(String[] elem:datiMag){
				if(elem[3].equals(elem[4]))stmt.executeUpdate(delMag.replace("£1",elem[1]).replace("£2",elem[2]));
				stmt.executeUpdate(aggMag.replace("£1",elem[0]).replace("£2",elem[1]).replace("£3",elem[2]));
			}
			for(String[] elem:magFe){
				stmt.executeUpdate(insMagFe.replace("£1",elem[0]).replace("£2",elem[1]).trim().replace("£3",elem[2].substring(0,elem[2].indexOf("."))));
			}
			stmt.close();
			con.close();
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		if(!listaOra.equals(""))mostraStato(listaOra,artOra);
		else{
			for(JLabel a:inPrel)pannello.remove(a);
			for(JLabel a:disegniScatole)pannello.remove(a);
			for(JLabel a:disegni)pannello.remove(a);
			pannello.repaint();
		}
		pannello.remove(cuore);
	}
	static void mostraStato(String lista, String articolo) throws java.lang.ClassCastException{
		cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Binocolo.png"));
		for(JLabel a:inPrel)pannello.remove(a);
		for(JLabel a:disegniScatole)pannello.remove(a);
		for(JLabel a:disegni)pannello.remove(a);
		inPrel.clear();
		disegniScatole.clear();
		disegni.clear();
		try{
			con=Conn.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql5.replace("£",lista));
			List<String> scatole=new ArrayList<String>();
			while(rs.next()){
				scatole.add(rs.getString("PNUMOR"));
				String sc=rs.getString("PNUSCA").trim();
				if(sc.endsWith("00"))sc=sc.substring(0,sc.length()-2);
				inPrel.add(new JLabel(scatole.get(scatole.size()-1)+" - "+sc));
			}
			if(inPrel.size()<=MAX_GRAF){
				for(int i=0;i<inPrel.size();i++){
					int h=220;
					int l=i;
					if(inPrel.size()>2 && i>=(int)Math.ceil(inPrel.size()/2)){
						h+=300;
						l=l-(int)Math.ceil(inPrel.size()/2);
					}
					l=5+l*(int)Math.ceil(L_UTILE/(int)Math.ceil(inPrel.size()/2));
					inPrel.get(i).setFont(new Font("Times New Roman",Font.BOLD,20));
					pannello.add(inPrel.get(i));
					inPrel.get(i).setBounds(l+20,h+100,300,25);
					disegniScatole.add(new JLabel());
					disegniScatole.get(i).setBounds(l,h,178,93);
					disegniScatole.get(i).setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Box.png"));
					pannello.add(disegniScatole.get(i));
					if(inPrel.size()==1){
						disegni.add(new JLabel());
						disegni.get(i).setBounds(l+200,h,500,625);
						Image a=scalaImmagine(ImageIO.read(new File("//ARBICLSFILES/DominoCad/AmbienteProduzione/FBDisegni/H00"+scatole.get(i).substring(0, 3)+"/H00"+scatole.get(i)+"_pro.jpg")),178,223);
						disegni.get(i).setIcon(new ImageIcon(a));
						pannello.add(disegni.get(i));
					}
				}
				rs=stmt.executeQuery(sql6.replace("£1",lista).replace("£2",articolo));
				boolean first=true;
				while(rs.next()){
					int l=scatole.indexOf(rs.getString("PNUMOR"));
					if(first){
						first=false;
						String desc=rs.getString("AD1PAR")+" "+rs.getString("AD2PAR");
						while(desc.contains("  "))desc=desc.replace("  "," ");
						JLabel a=new JLabel("CARICO "+rs.getString("PNUCAR").trim()+" - "+desc);
						pannello.add(a);
						inPrel.add(a);
						a.setBounds(5,157,1000,25);
						a.setFont(new Font("Courier new",Font.BOLD,24));
					}
					JLabel a=new JLabel(rs.getString("PIQTAO").replace(".000",""));
					disegniScatole.get(l).add(a);
					disegniScatole.get(l).setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/BoxGiallo.png"));
					inPrel.add(a);
					a.setBounds(70,30,100,25);
					a.setFont(new Font("Courier new",Font.BOLD,35));
				}
			}else{
				inPrel.clear();
				inPrel.add(new JLabel("Troppi scarichi per poterli rappresentare coi disegni."));
				inPrel.get(0).setBounds(5,137,1000,25);
				inPrel.get(0).setFont(new Font("Courier new",Font.BOLD,24));
				pannello.add(inPrel.get(0));
			}
			pannello.repaint();
			rs.close();
			stmt.close();
			con.close();
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
	}
	static Image scalaImmagine(Image imma, int l, int h){
	    BufferedImage immaScalata = new BufferedImage(l, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = immaScalata.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(imma, 0, 0, l, h, null);
	    g2.dispose();
	    return immaScalata;
	}
	static void importaArt(){
		cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/As.png"));
		pannello.add(cuore);
		pannello.repaint();
		try{
			con=Conn.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(sqlA);
			anag.clear();
			while(rs.next()){
				String[] art=new String[3];
				art[0]=rs.getString("ACDPAR").trim();
				art[1]=rs.getString("AD1PAR")+rs.getString("AD2PAR");
				while(art[1].contains("  "))art[1]=art[1].replace("  "," ");
				art[2]=rs.getString("MUNMIS").trim();
				anag.add(art);
			}
			rs.close();
			String tmp=ultimoUdcProd;
			rs=stmt.executeQuery(lastUdc);
			if(rs.next())ultimoUdcProd=rs.getString("TABDAT").trim();
			rs.close();
			if(!tmp.equals(ultimoUdcProd))log.append("\r\nVariato ultimo UDC di produzione da "+tmp+" a "+ultimoUdcProd);
			stmt.executeUpdate(delLista);	//pulizia unatantum liste
			stmt.close();
			con.close();
			File miaLista=new File("\\\\arbiclsfiles\\Fabbrica\\Ferramenta\\win_ese7\\files\\import\\articoli.imp");
			log.append("\r\nAttendo che il magazzino prelevi la lista articoli...");
			while(miaLista.exists())try{Thread.sleep(300);}catch(InterruptedException e){}
			miaLista.createNewFile();
			BufferedWriter br=new BufferedWriter(new FileWriter(miaLista));
			for(String[] p:anag){
				br.write(p[0]+"|"+p[1]+"|"+p[2]+"||||V |||0|\r\n");
			}
			br.flush();
			br.close();
			while(miaLista.exists())try{Thread.sleep(300);}catch(InterruptedException e){}
			log.append("\r\nImportazione articoli terminata.");
			gestore.azione="Attesa";
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		pannello.remove(cuore);
	}
	private static int popUp(int icona, String titolo, String messaggio){
		String[] options={"OK"};
		return JOptionPane.showOptionDialog(null, messaggio, titolo,JOptionPane.OK_OPTION,icona,null,options,options[0]);
	}
	static void inizializza() throws java.lang.ClassCastException{
		finestra=new Finestra("Magazzino Ferramenta V"+versione);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    img = toolkit.getImage("//ARBICLSFILES/Fabbrica/Ferramenta/Java/ferra.png");
	    finestra.setIconImage(img);
		cerca=new JButton("Cerca Ferramenta");
		genera=new JButton("Genera Liste");
		articoli=new JButton("Aggiorna Articoli da AS400");
		invent=new JButton("Inventario");
		ordFor=new JButton("Importa Ord.Fornitore");
		fGrande=new JButton("Ferr.Grande");
		accessori=new JButton("Accessori");
		cerca.addActionListener(gestore);
		genera.addActionListener(gestore);
		articoli.addActionListener(gestore);
		invent.addActionListener(gestore);
		ordFor.addActionListener(gestore);
		fGrande.addActionListener(gestore);
		accessori.addActionListener(gestore);
		caricoCorrente=new JLabel("Impostare un carico");
		foto=new JLabel("");
		foto.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/modula.png"));
		log=new TextArea();
		log.setEditable(false);
		log.setBackground(Colore);
		log.setForeground(Color.BLUE);
		log.append("Programma avviato.");
		finestra.getContentPane().add(pannello);
		cuore=new JLabel();
		pannello.add(cerca);pannello.add(genera);pannello.add(caricoCorrente);pannello.add(invent);
		pannello.add(raggruppa);pannello.add(carico);pannello.add(scarichi);pannello.add(foto);
		pannello.add(log);pannello.add(copyright);pannello.add(articoli);pannello.setBackground(Colore);
		pannello.add(accorpa);pannello.add(ordFor);pannello.add(oFor);pannello.add(fGrande);
		pannello.add(accessori);pannello.add(listOrd);pannello.add(elenco);
		articoli.setBounds(685,5,200,25);
		articoli.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/As.png"));
		cerca.setBounds(375,5,170,25);
		cerca.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Cerca.png"));
		genera.setBounds(550,5,130,25);
		genera.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Fulmine.png"));
		raggruppa.setBounds(180,5,110,25);
		scarichi.setBounds(280,5,30,25);
		caricoCorrente.setBounds(5,5,120,25);
		carico.setBounds(125,5,50,25);
		copyright.setBounds(5,800,300,25);
		copyright.setForeground(Color.LIGHT_GRAY);
		log.setBounds(5,32,1000,100);
		foto.setBounds(1150,5,97,77);
		cuore.setBounds(890,0,30,30);
		invent.setBounds(1010,5,120,25);
		invent.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Excel.png"));
		accorpa.setBounds(1020,32,100,25);
		oFor.setBounds(1180,85,75,25);
		ordFor.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Fulmine.png"));
		ordFor.setBounds(1010,85,170,25);
		fGrande.setBounds(1010,110,100,25);
		accessori.setBounds(1110,110,100,25);
		listOrd.setBounds(5,130,200,25);
		elenco.setBounds(210,130,300,25);
		try{
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			SwingUtilities.updateComponentTreeUI(finestra);
		}catch(Exception e){}
		pannello.setVisible(true);
		finestra.addWindowListener(new Ascolta());
		finestra.setVisible(true);
		cicla=0;
		try{
			con=Conn.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(lastUdc);
			if(rs.next())ultimoUdcProd=rs.getString("TABDAT").trim();
			rs.close();
			stmt.close();
			con.close();
			log.append(" Collegamento con AS400 ok. Ultimo UDC dedicato alla produzione: "+ultimoUdcProd);
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
	}
	private static void esportaMag(){
		pannello.add(cuore);
		cuore.setIcon(new ImageIcon("//ARBICLSFILES/Fabbrica/Ferramenta/Java/Excel.png"));
		pannello.repaint();
		log.append("\r\nEsportazione Inventario Excel in corso...");
		int colonne=3+(accorpa.isSelected()?0:1);
		String lira=accorpa.isSelected()?"":"nudc, ";
		JFileChooser FC=new JFileChooser("C:/");
		FileNameExtensionFilter filtro=new FileNameExtensionFilter("File Excel", "xls", "xlsx");
		FC.setFileFilter(filtro);
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(FC);
		}
        catch(ClassNotFoundException e){}
		catch(InstantiationException e1){}
		catch(IllegalAccessException e2){}
		catch(UnsupportedLookAndFeelException e3){}
		int retrival=FC.showSaveDialog(null);
		String percorso;
		if (retrival == JFileChooser.APPROVE_OPTION){
			percorso=FC.getSelectedFile().getPath();
			if(percorso.toUpperCase().endsWith(".XLS"))percorso+="x";
			else if(!percorso.toUpperCase().endsWith(".XLSX"))percorso+=".xlsx";
		}else return;
		cartella=new SXSSFWorkbook(500);
		foglio=cartella.createSheet("Foglio1");
		try{
			con=Conn.getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery(inv.replace("£",lira));
			Row rowhead=foglio.createRow(0);
			for(int j=0;j<colonne;j++)rowhead.createCell(j).setCellValue(inventarioColonne[colonne-3][j]);
			int w=1;
			while(rs.next()){
				Row row=foglio.createRow(w++);
				int i=0;
				if(colonne==4)row.createCell(i++).setCellValue(rs.getString("NUDC"));
				String desc=rs.getString("AD1PAR")+" "+rs.getString("AD2PAR");
				row.createCell(i++).setCellValue(rs.getString("NCOIDA").trim());
				while(desc.contains("  "))desc=desc.replace("  "," ");
				row.createCell(i++).setCellValue(desc);
				row.createCell(i++).setCellValue(rs.getString("QTA").trim());
			}
			FileOutputStream fileOut =  new FileOutputStream(percorso);
    		cartella.write(fileOut);
    		fileOut.flush();
    		fileOut.close();
    		cartella.close();
		}catch(Exception e){if(!(""+e).equals("java.lang.ClassCastException: javax.swing.plaf.nimbus.DerivedColor$UIResource cannot be cast to java.awt.Font"))log.append("\r\n"+e);}
		gestore.azione="Attesa";
		log.append("\r\nEsportazione Inventario Excel terminata.");
	}
}

class Gestore implements ActionListener{
	JButton bot=null;
	public String azione="Attesa";
	public Gestore() {}
	public Gestore(JButton bot) {
	  this.bot = bot;
	}
	public void actionPerformed(ActionEvent e){
	  String bottone = e.getActionCommand();
	  azione=bottone;
	}
}

class Finestra extends JFrame{
	static final long serialVersionUID = 1L;
	static final int MAXx=1265;
	static final int MAXy=850;
	public Finestra(String a){
	  super(a);
	  this.pack();
	  setLocation(0,0);
	  setSize(MAXx,MAXy);
	  setResizable(true);
	}
	  public Finestra(String a, int x, int y){
	  super(a);
	  this.pack();
	  setLocation(0,0);
	  setSize(x,y);
	  setResizable(true);
	}
}

class Ascolta implements WindowListener{
	public Ascolta(){
		super();
	}
	public void windowActivated(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowClosing(WindowEvent e){
	  System.exit(0);
	}
	public void windowClosed(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
}

class Conn {
  public static Connection getConnection() throws Exception {
	Class.forName("com.ibm.as400.access.AS400JDBCDriver");
	Connection c = DriverManager.getConnection("jdbc:as400://10.0.5.220","NCSADMIN","NCSSIGEA");
    return c;
  }
}
