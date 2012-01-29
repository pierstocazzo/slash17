package jmovie;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FilmUPWrapper {

	public static ArrayList<FilmUPResult> query(String titoloCercato) {
		ArrayList<FilmUPResult> res = new ArrayList<FilmUPResult>();
		try {
			titoloCercato = titoloCercato.replaceAll(" ", "+");
			
			Document doc = Jsoup.connect("http://filmup.leonardo.it/cgi-bin/search.cgi?ps=100&fmt=long&q=" + titoloCercato + "&ul=%25%2Fsc_%25&x=47&y=12&m=all&wf=0020&wm=sub&sy=0").get();

			Elements dls = doc.getElementsByTag("dl");
			for (Element dl : dls) {
				System.out.println(dl);
				Element link = dl.getElementsByTag("a").first();
				if (link.text().contains("FilmUP - Scheda: ")) {
					Element td = dl.getElementsByTag("td").first();
					String info = td.text();
//					System.out.println(info);
					
					Pattern pt = Pattern.compile("FilmUP.com (.*) Titolo originale.* Anno: (.{4}).* Regia: (.*) Sito ufficiale.*");
					Matcher m = pt.matcher(info);
	
					if (m.find()) {
						String titolo = m.group(1);
						System.out.print(titolo + " - ");
						String anno = m.group(2);
						System.out.print(anno + " - ");
						String regia = m.group(3);
						System.out.print(regia + " - URL: ");
						String url = link.attr("href");
						System.out.println(url);
						FilmUPResult fpr = new FilmUPResult(titolo, regia, anno, url);
						res.add(fpr);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
//	private static String clean(String s) {
//		s = s.replaceAll("<[^>]*>", "");
//		s = s.replaceAll("&#38;", "&");
//		return s;
//	}
}
