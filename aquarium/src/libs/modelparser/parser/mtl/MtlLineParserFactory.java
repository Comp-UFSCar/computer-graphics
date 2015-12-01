package libs.modelparser.parser.mtl;

//import java.util.Hashtable;
import libs.modelparser.LineParserFactory;
import libs.modelparser.WavefrontObject;
import libs.modelparser.parser.CommentParser;
//import com.obj.parser.DefaultParser;
//import com.obj.parser.LineParser;
import libs.modelparser.parser.mtl.KdMapParser;
import libs.modelparser.parser.mtl.KdParser;
import libs.modelparser.parser.mtl.MaterialParser;




public class MtlLineParserFactory extends LineParserFactory
{
	public MtlLineParserFactory(WavefrontObject object)
	{
		this.object = object;
		parsers.put("newmtl",new MaterialParser());
		parsers.put("Ka",new KaParser());
		parsers.put("Kd",new KdParser());
		parsers.put("Ks",new KsParser());
		parsers.put("Ns",new NsParser());		
		parsers.put("map_Kd",new KdMapParser(object));
		parsers.put("#",new CommentParser());
	}
	
	
		
	
		
	
}
