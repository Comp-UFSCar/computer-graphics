package libs.modelparser.parser.mtl;

import libs.modelparser.Material;
import libs.modelparser.WavefrontObject;
import libs.modelparser.parser.LineParser;

public class MaterialParser extends LineParser {

	String materialName = "";
	
	@Override
	public void incoporateResults(WavefrontObject wavefrontObject) 
	{
		Material newMaterial = new Material(materialName);
		
		wavefrontObject.getMaterials().put(materialName,newMaterial);
		wavefrontObject.setCurrentMaterial(newMaterial);
	}

	@Override
	public void parse() {
		materialName = words[1];
	}

}
