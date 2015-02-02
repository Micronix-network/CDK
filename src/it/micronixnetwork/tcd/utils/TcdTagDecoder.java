package it.micronixnetwork.tcd.utils;

import java.util.StringTokenizer;

public class TcdTagDecoder {

	private String tag = "";

	private String numeroDocumento = "unknow";
	private String dataDocumento = "unknow";
	private String codiceSoggetto = "unknow";
	private String codiceFatturazione = "unknow";
	private String codiceAgente = "unknow";
	private String codiceCorriere = "unknow";

	public TcdTagDecoder() {
		super();
	}

	public TcdTagDecoder(String tag) {
		super();
		this.tag = tag;
		this.decode();
	}

	private void decode(){

		// il tag inizia con '<TCDTAG> ' e finisce con '</TCDTAG>'
		// verifica tag

		//<TCDTAG>DATA_NUMERO_CODICEDESTINATARIO</TCDTAG>
		//                    ^________________^____ = codice soggetto
		//             ^____^_______________________ = mumero documento
		//        ^__^______________________________ = data documento

		if (tag.startsWith("<TCDTAG>") && tag.endsWith("</TCDTAG>")){
			tag = tag.replace("<TCDTAG>", "").replace("</TCDTAG>", ""); // elimino i tag
			tag = tag.replace("__", "_ _");
			StringTokenizer st = new StringTokenizer(tag,"_");
			int tn = 1;
			while (st.hasMoreTokens()){

				String token = st.nextToken().trim();
				switch (tn++) {
				case 1:
					dataDocumento = token;
					break;
				case 2:
					numeroDocumento = token;
					break;
				case 3:
					codiceSoggetto = token;
					codiceFatturazione = token;
					break;
				case 4:

					break;
				case 5:

					break;
				case 6:

					break;
				case 7:
					break;
				case 8:
					break;

				default:
					break;
				}

			}

		} else {
			// invalid tag!
		}

	}


	public void setTag(String tag) {
		this.tag = tag;
		this.decode();
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public String getDataDocumento() {
		return dataDocumento;
	}

	public String getCodiceSoggetto() {
		return codiceSoggetto;
	}

	public String getCodiceFatturazione() {
		return codiceFatturazione;
	}

	public String getCodiceAgente() {
		return codiceAgente;
	}

	public String getCodiceCorriere() {
		return codiceCorriere;
	}





}
