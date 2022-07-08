package game;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class infReader {
	public static String ReadFile(String Path) {
		File f = new File(Path);
		File p = new File("");
		String pth = p.getAbsolutePath();
		String content = "";
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(pth+"/bin/game/"+f.getPath()));
			content = new String(encoded, Charset.defaultCharset());
			
			//content = decrypt(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	public static String decrypt(String msg) {
		String[] ns = msg.split("\\n");
		String d = ns[ns.length - 1];
		char[] dc = d.toCharArray();
		int[] dx = new int[dc.length];
		for (int i = 0; i < dc.length; i++) {
			dx[i] = (Integer.parseInt(Integer.toBinaryString(dc[i]), 2) - 7) / 7;
		}
		String nu = "";
		for (int i = 0; i < ns.length - 1; i++) {
			nu += ns[i];
		}
		//decode nu
		char[] nc = nu.toCharArray();
		String[] nx = new String[nc.length];
 		for (int i = 0; i < nx.length; i++) {
			nx[i] = Integer.toBinaryString(Integer.parseInt(Integer.toBinaryString(nc[i]), 2) / dx[i]);
			while(nx[i].length() < 4) {
				nx[i] = "0" + nx[i];
			}
		}
 		String res = "";
 		for (int i = 0; i < nx.length / 2; i ++) {
 			int v = Integer.parseInt(nx[i*2] + nx[i*2+1], 2);
 			String v2 = String.valueOf((char) v);
 			res += v2;
 		}
 		
 		return res;
	}
}
