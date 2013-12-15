package com.fellow.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HtmlTools {

	private static final Map symbols = new HashMap();

	static {
		symbols.put("&nbsp;", "&#160;");
		symbols.put("&amp;", "&#38;");
		symbols.put("&quot;", "&#34;");
		// finance
		symbols.put("&cent;", "&#162;");
		symbols.put("&euro;", "&#8364;");
		symbols.put("&pound;", "&#163;");
		symbols.put("&yen;", "&#165;");
		// signs
		symbols.put("&copy;", "&#169;");
		symbols.put("&reg;", "&#174;");
		symbols.put("&trade;", "&#8482;");
		symbols.put("&permil;", "&#8240;");
		symbols.put("&micro;", "&#181;");
		symbols.put("&middot;", "&#183;");
		symbols.put("&bull;", "&#8226;");
		symbols.put("&hellip;", "&#8230;");
		symbols.put("&prime;", "&#8242;");
		symbols.put("&Prime;", "&#8243;");
		symbols.put("&sect;", "&#167;");
		symbols.put("&para;", "&#182;");
		symbols.put("&szlig;", "&#223;");
		// quotations
		symbols.put("&lsaquo;", "&#8249;");
		symbols.put("&rsaquo;", "&#8250;");
		symbols.put("&laquo;", "&#171;");
		symbols.put("&raquo;", "&#187;");
		symbols.put("&lsquo;", "&#8216;");
		symbols.put("&rsquo;", "&#8217;");
		symbols.put("&ldquo;", "&#8220;");
		symbols.put("&rdquo;", "&#8221;");
		symbols.put("&sbquo;", "&#8218;");
		symbols.put("&bdquo;", "&#8222;");
		symbols.put("&lt;", "&#60;");
		symbols.put("&gt;", "&#62;");
		symbols.put("&le;", "&#8804;");
		symbols.put("&ge;", "&#8805;");
		symbols.put("&ndash;", "&#8211;");
		symbols.put("&mdash;", "&#8212;");
		symbols.put("&macr;", "&#175;");
		symbols.put("&oline;", "&#8254;");
		symbols.put("&curren;", "&#164;");
		symbols.put("&brvbar;", "&#166;");
		symbols.put("&uml;", "&#168;");
		symbols.put("&iexcl;", "&#161;");
		symbols.put("&iquest;", "&#191;");
		symbols.put("&circ;", "&#710;");
		symbols.put("&tilde;", "&#732;");
		symbols.put("&deg;", "&#176;");
		symbols.put("&minus;", "&#8722;");
		symbols.put("&plusmn;", "&#177;");
		symbols.put("&divide;", "&#247;");
		symbols.put("&frasl;", "&#8260;");
		symbols.put("&times;", "&#215;");
		symbols.put("&sup1;", "&#185;");
		symbols.put("&sup2;", "&#178;");
		symbols.put("&sup3;", "&#179;");
		symbols.put("&frac14;", "&#188;");
		symbols.put("&frac12;", "&#189;");
		symbols.put("&frac34;", "&#190;");
		// math / logical
		symbols.put("&fnof;", "&#402;");
		symbols.put("&int;", "&#8747;");
		symbols.put("&sum;", "&#8721;");
		symbols.put("&infin;", "&#8734;");
		symbols.put("&radic;", "&#8730;");
		symbols.put("&sim;", "&#8764;");
		symbols.put("&cong;", "&#8773;");
		symbols.put("&asymp;", "&#8776;");
		symbols.put("&ne;", "&#8800;");
		symbols.put("&equiv;", "&#8801;");
		symbols.put("&isin;", "&#8712;");
		symbols.put("&notin;", "&#8713;");
		symbols.put("&ni;", "&#8715;");
		symbols.put("&prod;", "&#8719;");
		symbols.put("&and;", "&#8743;");
		symbols.put("&or;", "&#8744;");
		symbols.put("&not;", "&#172;");
		symbols.put("&cap;", "&#8745;");
		symbols.put("&cup;", "&#8746;");
		symbols.put("&part;", "&#8706;");
		symbols.put("&forall;", "&#8704;");
		symbols.put("&exist;", "&#8707;");
		symbols.put("&empty;", "&#8709;");
		symbols.put("&nabla;", "&#8711;");
		symbols.put("&lowast;", "&#8727;");
		symbols.put("&prop;", "&#8733;");
		symbols.put("&ang;", "&#8736;");
		// undefined
		symbols.put("&acute;", "&#180;");
		symbols.put("&cedil;", "&#184;");
		symbols.put("&ordf;", "&#170;");
		symbols.put("&ordm;", "&#186;");
		symbols.put("&dagger;", "&#8224;");
		symbols.put("&Dagger;", "&#8225;");
		// alphabetical special chars
		symbols.put("&Agrave;", "&#192;");
		symbols.put("&Aacute;", "&#193;");
		symbols.put("&Acirc;", "&#194;");
		symbols.put("&Atilde;", "&#195;");
		symbols.put("&Auml;", "&#196;");
		symbols.put("&Aring;", "&#197;");
		symbols.put("&AElig;", "&#198;");
		symbols.put("&Ccedil;", "&#199;");
		symbols.put("&Egrave;", "&#200;");
		symbols.put("&Eacute;", "&#201;");
		symbols.put("&Ecirc;", "&#202;");
		symbols.put("&Euml;", "&#203;");
		symbols.put("&Igrave;", "&#204;");
		symbols.put("&Iacute;", "&#205;");
		symbols.put("&Icirc;", "&#206;");
		symbols.put("&Iuml;", "&#207;");
		symbols.put("&ETH;", "&#208;");
		symbols.put("&Ntilde;", "&#209;");
		symbols.put("&Ograve;", "&#210;");
		symbols.put("&Oacute;", "&#211;");
		symbols.put("&Ocirc;", "&#212;");
		symbols.put("&Otilde;", "&#213;");
		symbols.put("&Ouml;", "&#214;");
		symbols.put("&Oslash;", "&#216;");
		symbols.put("&OElig;", "&#338;");
		symbols.put("&Scaron;", "&#352;");
		symbols.put("&Ugrave;", "&#217;");
		symbols.put("&Uacute;", "&#218;");
		symbols.put("&Ucirc;", "&#219;");
		symbols.put("&Uuml;", "&#220;");
		symbols.put("&Yacute;", "&#221;");
		symbols.put("&Yuml;", "&#376;");
		symbols.put("&THORN;", "&#222;");
		symbols.put("&agrave;", "&#224;");
		symbols.put("&aacute;", "&#225;");
		symbols.put("&acirc;", "&#226;");
		symbols.put("&atilde;", "&#227;");
		symbols.put("&auml;", "&#228;");
		symbols.put("&aring;", "&#229;");
		symbols.put("&aelig;", "&#230;");
		symbols.put("&ccedil;", "&#231;");
		symbols.put("&egrave;", "&#232;");
		symbols.put("&eacute;", "&#233;");
		symbols.put("&ecirc;", "&#234;");
		symbols.put("&euml;", "&#235;");
		symbols.put("&igrave;", "&#236;");
		symbols.put("&iacute;", "&#237;");
		symbols.put("&icirc;", "&#238;");
		symbols.put("&iuml;", "&#239;");
		symbols.put("&eth;", "&#240;");
		symbols.put("&ntilde;", "&#241;");
		symbols.put("&ograve;", "&#242;");
		symbols.put("&oacute;", "&#243;");
		symbols.put("&ocirc;", "&#244;");
		symbols.put("&otilde;", "&#245;");
		symbols.put("&ouml;", "&#246;");
		symbols.put("&oslash;", "&#248;");
		symbols.put("&oelig;", "&#339;");
		symbols.put("&scaron;", "&#353;");
		symbols.put("&ugrave;", "&#249;");
		symbols.put("&uacute;", "&#250;");
		symbols.put("&ucirc;", "&#251;");
		symbols.put("&uuml;", "&#252;");
		symbols.put("&yacute;", "&#253;");
		symbols.put("&thorn;", "&#254;");
		symbols.put("&yuml;", "&#255;");
		symbols.put("&Alpha;", "&#913;");
		symbols.put("&Beta;", "&#914;");
		symbols.put("&Gamma;", "&#915;");
		symbols.put("&Delta;", "&#916;");
		symbols.put("&Epsilon;", "&#917;");
		symbols.put("&Zeta;", "&#918;");
		symbols.put("&Eta;", "&#919;");
		symbols.put("&Theta;", "&#920;");
		symbols.put("&Iota;", "&#921;");
		symbols.put("&Kappa;", "&#922;");
		symbols.put("&Lambda;", "&#923;");
		symbols.put("&Mu;", "&#924;");
		symbols.put("&Nu;", "&#925;");
		symbols.put("&Xi;", "&#926;");
		symbols.put("&Omicron;", "&#927;");
		symbols.put("&Pi;", "&#928;");
		symbols.put("&Rho;", "&#929;");
		symbols.put("&Sigma;", "&#931;");
		symbols.put("&Tau;", "&#932;");
		symbols.put("&Upsilon;", "&#933;");
		symbols.put("&Phi;", "&#934;");
		symbols.put("&Chi;", "&#935;");
		symbols.put("&Psi;", "&#936;");
		symbols.put("&Omega;", "&#937;");
		symbols.put("&alpha;", "&#945;");
		symbols.put("&beta;", "&#946;");
		symbols.put("&gamma;", "&#947;");
		symbols.put("&delta;", "&#948;");
		symbols.put("&epsilon;", "&#949;");
		symbols.put("&zeta;", "&#950;");
		symbols.put("&eta;", "&#951;");
		symbols.put("&theta;", "&#952;");
		symbols.put("&iota;", "&#953;");
		symbols.put("&kappa;", "&#954;");
		symbols.put("&lambda;", "&#955;");
		symbols.put("&mu;", "&#956;");
		symbols.put("&nu;", "&#957;");
		symbols.put("&xi;", "&#958;");
		symbols.put("&omicron;", "&#959;");
		symbols.put("&pi;", "&#960;");
		symbols.put("&rho;", "&#961;");
		symbols.put("&sigmaf;", "&#962;");
		symbols.put("&sigma;", "&#963;");
		symbols.put("&tau;", "&#964;");
		symbols.put("&upsilon;", "&#965;");
		symbols.put("&phi;", "&#966;");
		symbols.put("&chi;", "&#967;");
		symbols.put("&psi;", "&#968;");
		symbols.put("&omega;", "&#969;");
		// symbols
		symbols.put("&alefsym;", "&#8501;");
		symbols.put("&piv;", "&#982;");
		symbols.put("&real;", "&#8476;");
		symbols.put("&thetasym;", "&#977;");
		symbols.put("&upsih;", "&#978;");
		symbols.put("&weierp;", "&#8472;");
		symbols.put("&image;", "&#8465;");
		// arrows
		symbols.put("&larr;", "&#8592;");
		symbols.put("&uarr;", "&#8593;");
		symbols.put("&rarr;", "&#8594;");
		symbols.put("&darr;", "&#8595;");
		symbols.put("&harr;", "&#8596;");
		symbols.put("&crarr;", "&#8629;");
		symbols.put("&lArr;", "&#8656;");
		symbols.put("&uArr;", "&#8657;");
		symbols.put("&rArr;", "&#8658;");
		symbols.put("&dArr;", "&#8659;");
		symbols.put("&hArr;", "&#8660;");
		symbols.put("&there4;", "&#8756;");
		symbols.put("&sub;", "&#8834;");
		symbols.put("&sup;", "&#8835;");
		symbols.put("&nsub;", "&#8836;");
		symbols.put("&sube;", "&#8838;");
		symbols.put("&supe;", "&#8839;");
		symbols.put("&oplus;", "&#8853;");
		symbols.put("&otimes;", "&#8855;");
		symbols.put("&perp;", "&#8869;");
		symbols.put("&sdot;", "&#8901;");
		symbols.put("&lceil;", "&#8968;");
		symbols.put("&rceil;", "&#8969;");
		symbols.put("&lfloor;", "&#8970;");
		symbols.put("&rfloor;", "&#8971;");
		symbols.put("&lang;", "&#9001;");
		symbols.put("&rang;", "&#9002;");
		symbols.put("&loz;", "&#9674;");
		symbols.put("&spades;", "&#9824;");
		symbols.put("&clubs;", "&#9827;");
		symbols.put("&hearts;", "&#9829;");
		symbols.put("&diams;", "&#9830;");
		symbols.put("&ensp;", "&#8194;");
		symbols.put("&emsp;", "&#8195;");
		symbols.put("&thinsp;", "&#8201;");
		symbols.put("&zwnj;", "&#8204;");
		symbols.put("&zwj;", "&#8205;");
		symbols.put("&lrm;", "&#8206;");
		symbols.put("&rlm;", "&#8207;");
		symbols.put("&shy;", "&#173;");

		//
		symbols.put("<", "&#60;");
	}

	public static String htmlEncode(String content) {
		if (content != null && !"".equals(content.trim())) {

			Iterator keyIter = symbols.keySet().iterator();
			String key = null;
			int index = -1;
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				index = content.indexOf(key);
				if (index != -1) {
					content = content
							.replaceAll(key, (String) symbols.get(key));
				}
			}
		}
		return content;
	}

	/**
	 * Ìæ»»&
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceAllAMP(String content) {
		StringBuffer sb = new StringBuffer();
		if (content != null && !"".equals(content.trim())) {
			char tempChar = ' ';
			char nextTempChar = ' ';
			for (int i = 0; i < content.length(); i++) {
				if (i < content.length() - 1) {
					tempChar = content.charAt(i);
					nextTempChar = content.charAt(i + 1);
					if (tempChar == '&' && nextTempChar != '#') {
						sb.append("&#38;");
					} else {
						sb.append(tempChar);
					}
				} else if (i == (content.length() - 1)) {
					tempChar = content.charAt(i);
					if (tempChar == '&') {
						sb.append("&#38;");
					} else {
						sb.append(tempChar);
					}
				}

			}
		}
		return sb.toString();
	}

	public static String htmlDecode(String content) {
		return null;
	}
}
