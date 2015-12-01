package libs.modelparser.parser.obj;

import libs.modelparser.Material;
import libs.modelparser.WavefrontObject;
import libs.modelparser.parser.LineParser;

public class MaterialParser extends LineParser 
{
		String materialName = "";

		@Override
		public void parse() {
			materialName = words[1];
		}

		@Override
		public void incoporateResults(WavefrontObject wavefrontObject) {
			Material newMaterial = wavefrontObject.getMaterials().get(materialName);
			wavefrontObject.getCurrentGroup().setMaterial(newMaterial);
			
		}

	

}
