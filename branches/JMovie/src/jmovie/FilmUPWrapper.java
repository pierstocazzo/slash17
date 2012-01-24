package jmovie;

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
			Parser p = new Parser("http://filmup.leonardo.it/cgi-bin/search.cgi?" +
					"ps=100&fmt=long&q=" + titoloCercato + "&ul=%25%2Fsc_%25&x=47&y=12&m=all&wf=0020&wm=sub&sy=0");
			NodeFilter f1 = new NodeFilter() {
				private static final long serialVersionUID = 5051888049562297225L;
				@Override
				public boolean accept(Node node) {
					if (node instanceof LinkTag && ((LinkTag) node).getChildCount() > 0 
							&& ((LinkTag) node).getChild(0).toHtml().contains("Scheda")) {
						return true;
					}
					if (node instanceof Text && node.toHtml().contains("Anno:")) {
						return true;
					}
					return false;
				}
			};
			NodeList l1 = p.extractAllNodesThatMatch(f1);
			for (int i = 0; i < l1.size(); i++) {
				Node n = l1.elementAt(i);
				
				if (n instanceof LinkTag) {
					LinkTag lt = (LinkTag) n;
					
					String text = lt.getChildrenHTML();
					text = clean(text);
					
					Pattern pt = Pattern.compile(".*FilmUP - Scheda: (.*).*");
					Matcher m = pt.matcher(text);
	
					if (m.find()) {
						String titolo = m.group(1);
						String url = lt.getLink();
						res.add(new FilmUPResult(titolo, url));
					}
				} else {
					String text = n.toHtml();
					Pattern pt = Pattern.compile(".*Anno: ([0-9][0-9][0-9][0-9]).*Regia: (.*) Sito ufficiale:.*");
					Matcher m = pt.matcher(text);
	
					if (m.find()) {
						String anno = m.group(1);
						String regista = m.group(2);
						res.get(res.size()-1).setAnno(anno);
						res.get(res.size()-1).setRegista(regista);
					}
				}
			}
		} catch (ParserException e) {
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
