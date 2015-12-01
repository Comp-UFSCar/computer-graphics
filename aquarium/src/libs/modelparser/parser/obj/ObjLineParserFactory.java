package libs.modelparser.parser.obj;

//import java.util.Hashtable;
import libs.modelparser.LineParserFactory;
import libs.modelparser.NormalParser;
import libs.modelparser.WavefrontObject;
import libs.modelparser.parser.CommentParser;
import libs.modelparser.parser.mtl.MaterialFileParser;


public class ObjLineParserFactory extends LineParserFactory{


	
	public ObjLineParserFactory(WavefrontObject object)
	{
		this.object = object;
		parsers.put("v",new VertexParser());
		parsers.put("vn",new NormalParser());
		parsers.put("vp",new FreeFormParser());
		parsers.put("vt",new TextureCooParser());
		parsers.put("f",new FaceParser(object));
		parsers.put("#",new CommentParser());
		parsers.put("mtllib",new MaterialFileParser(object));
		parsers.put("usemtl",new MaterialParser());
		parsers.put("g",new GroupParser());
	}

	
}
