package cs425;
import java.io.*;
import java.util.*;
import java.net.*;

public class Config {
		HashMap<Integer, InetSocketAddress> addrMap;
		HashMap<InetSocketAddress, Integer> idMap;
		int minDelay;
		int maxDelay;
		public Config(HashMap<Integer, InetSocketAddress> addrMap, HashMap<InetSocketAddress, Integer> idMap, int minDelay, int maxDelay) {
			this.addrMap = addrMap;
			this.idMap = idMap;
			this.minDelay = minDelay;
			this.maxDelay = maxDelay;
		}	
	
	public static Config parseConfig(String filename) throws IOException {
		BufferedReader file = new BufferedReader(new FileReader(filename));
		String firstLine = file.readLine();
		if(firstLine == null)
			throw new IOException("Empty Configuration File");
		String[] delays = firstLine.split(" ");
		if(delays.length != 2)
			throw new IOException("Wrong Formatted Configuration File");
		Integer minDelay = Integer.parseInt(delays[0]);
		Integer maxDelay = Integer.parseInt(delays[1]);
		if(minDelay == null || maxDelay == null) 
			throw new IOException("Wrong Formatted Configuration File");
		
		HashMap<Integer, InetSocketAddress> addrMap = new HashMap<>();
		HashMap<InetSocketAddress, Integer> idMap = new HashMap<>();
		String line = file.readLine();
		while(line != null) {
			String[] content = line.split("\\s+");
			if(content.length != 3)
				throw new IOException("Wrong Formatted Configuration File");
			Integer id = Integer.parseInt(content[0]);
			Integer port = Integer.parseInt(content[2]);
			if(id == null || port == null) 
				throw new IOException("Wrong Formatted Configuration File");
			System.out.println("minDelay: " + minDelay + ", maxDelay:" + maxDelay);
			System.out.println("id: " + id + ", ip: " + content[1] + ", port: " + port);
			addrMap.put(id, new InetSocketAddress(content[1], port));
			idMap.put(new InetSocketAddress(content[1], port), id);
			line = file.readLine();
		}
		return new Config(addrMap, idMap, minDelay, maxDelay);
	}
	public static void main(String arg[]) throws IOException {
		Config config = parseConfig("D:\\courses\\uiuc\\coursework\\18spring\\cs425\\mp1\\src\\cs425\\configFile");

	}
}
