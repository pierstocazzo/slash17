package jmovie;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Text;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class FilmUPWrapper {

	public static ArrayList<FilmUPResult> query(String titoloCercato) {
		ArrayList<FilmUPResult> res = new ArrayList<FilmUPResult>();
		try {
			
//			URL urla = new URL("http://filmup.leonardo.it/cgi-bin/search.cgi?" +
//					"ps=100&fmt=long&q=" + titoloCercato + "&ul=%25%2Fsc_%25&x=47&y=12&m=all&wf=0020&wm=sub&sy=0");
//			BufferedInputStream page = new BufferedInputStream(urla.openStream());
//			
//			String htmlPage = "";
//			int c;
//			while ((c = page.read()) != -1) {
//				htmlPage += (char) c;
//			}
			
			String htmlPage = "<DL> <DT>1 \n\n\n\n <a class=\"filmup\" href=\"http://filmup.leonardo.it/sc_hulk2.htm\" TARGET=\"_blank\">"+
		    "FilmUP - Scheda: L'incredibile <span style=\"color: #f00; font-weight: bold;\">Hulk</span></a>" +
		    "<DT>2 \n\n\n\n <a class=\"filmup\" href=\"http://filmup.leonardo.it/sc_hulk.htm\" TARGET=\"_blank\">"+
		    "FilmUP - Scheda: <span style=\"color: #f00; font-weight: bold;\">Hulk</span></a>" +
		    "</DL>";
			
			int begin = htmlPage.indexOf("<DL>");
			int end = htmlPage.lastIndexOf("</DL>");
			htmlPage = htmlPage.substring(begin, end);
			htmlPage = htmlPage.replaceAll("(\n|\r)", " ");
			System.out.println(htmlPage);
			String regExpTitoloURL = ".*<DT>.*<a class=\"filmup\" href=\"(.*)\" TARGET=\"_blank\">.*FilmUP - Scheda: (.*)</a>.*";
			
			Pattern pt = Pattern.compile(regExpTitoloURL);
			Matcher m = pt.matcher(htmlPage);

			if (m.find()) {
				String url = m.group(1);
				String titolo = m.group(2);
				titolo = clean(titolo);
				System.out.println(url + " - " + titolo);
//				res.add(new FilmUPResult(titolo, url));
				System.out.println(m.end());
				System.out.println(m.regionEnd());
				m.region(m.end(), m.regionEnd());
				while (m.matches()) {
					m.region(m.end(), m.regionEnd());
					url = m.group(1);
					titolo = m.group(2);
					titolo = clean(titolo);
					System.out.println(url + " - " + titolo);
				}
			}
//			String regExpAnnoRegista = ".*Anno: ([0-9][0-9][0-9][0-9]).*Regia: (.*) Sito ufficiale:.*";
//			m = pt.matcher(htmlPage);
//
//			if (m.find()) {
//				String anno = m.group(1);
//				String regista = m.group(2);
//				res.get(res.size()-1).setAnno(anno);
//				res.get(res.size()-1).setRegista(regista);
//			}
//			Parser p = new Parser("http://filmup.leonardo.it/cgi-bin/search.cgi?" +
//					"ps=100&fmt=long&q=" + titoloCercato + "&ul=%25%2Fsc_%25&x=47&y=12&m=all&wf=0020&wm=sub&sy=0");
//			NodeFilter f1 = new NodeFilter() {
//				private static final long serialVersionUID = 5051888049562297225L;
//				@Override
//				public boolean accept(Node node) {
//					if (node instanceof LinkTag && ((LinkTag) node).getChildCount() > 0 
//							&& ((LinkTag) node).getChild(0).toHtml().contains("Scheda")) {
//						return true;
//					}
//					if (node instanceof Text && node.toHtml().contains("Anno:")) {
//						return true;
//					}
//					return false;
//				}
//			};
//			NodeList l1 = p.extractAllNodesThatMatch(f1);
//			for (int i = 0; i < l1.size(); i++) {
//				Node n = l1.elementAt(i);
//				
//				if (n instanceof LinkTag) {
//					LinkTag lt = (LinkTag) n;
//					
//					String text = lt.getChildrenHTML();
//					text = clean(text);
//					
//					Pattern pt = Pattern.compile(".*FilmUP - Scheda: (.*).*");
//					Matcher m = pt.matcher(text);
//	
//					if (m.find()) {
//						String titolo = m.group(1);
//						String url = lt.getLink();
//						res.add(new FilmUPResult(titolo, url));
//					}
//				} else {
//					String text = n.toHtml();
//					Pattern pt = Pattern.compile(".*Anno: ([0-9][0-9][0-9][0-9]).*Regia: (.*) Sito ufficiale:.*");
//					Matcher m = pt.matcher(text);
//	
//					if (m.find()) {
//						String anno = m.group(1);
//						String regista = m.group(2);
//						res.get(res.size()-1).setAnno(anno);
//						res.get(res.size()-1).setRegista(regista);
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private static String clean(String s) {
		s = s.replaceAll("<[^>]*>", "");
		s = s.replaceAll("&#38;", "&");
		return s;
	}
}
