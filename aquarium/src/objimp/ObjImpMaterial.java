package objimp;

public class ObjImpMaterial 
{
	public ObjImpMaterial()
	  {
		  //_ambient = new float[3];
		  //_diffuse = new float[3];
		  //_specular = new float[3];
		  _texId = -1;
	  }
	  
	  float[] _ambient;
	  float[] _diffuse;
	  float[] _specular;
	  float _shininess;
	  int _texId; 
}
