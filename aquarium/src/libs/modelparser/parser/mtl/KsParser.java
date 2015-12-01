package libs.modelparser.parser.mtl;

import libs.modelparser.Vertex;
import libs.modelparser.Material;
import libs.modelparser.WavefrontObject;
import libs.modelparser.parser.LineParser;


public class KsParser extends LineParser {

	Vertex ks = null;
	
	@Override
	public void incoporateResults(WavefrontObject wavefrontObject) {
		Material currentMaterial = wavefrontObject.getCurrentMaterial() ;
		currentMaterial.setKs(ks);

	}

	@Override
	public void parse() {
		ks = new Vertex();
		
		try
		{
			ks.setX(Float.parseFloat(words[1]));
			ks.setY(Float.parseFloat(words[2]));
			ks.setZ(Float.parseFloat(words[3]));
		}
		catch(Exception e)
		{
			throw new RuntimeException("VertexParser Error");
		}
	}

}
