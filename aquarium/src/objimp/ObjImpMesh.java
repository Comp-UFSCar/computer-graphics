package objimp;

import java.util.ArrayList;


public class ObjImpMesh 
{
	public ObjImpMesh()
	  {
	    _vertexList = new ArrayList<float[]>();
	    _texcoordList = new ArrayList<float[]>();
	    _normalList = new ArrayList<float[]>();
	    _faceList = new ArrayList<ObjImpFace>();
	    
	    _material = new ObjImpMaterial();
	  }
	  
	public void setName( String n )
	  {
	    _name = n;
	  }
	  
	public void addVertex( float[] v )
	  {
	    _vertexList.add( v );
	  }

	public void addTexCoord( float[] v )
	  {
	    _texcoordList.add( v );
	  }

	public void addNormal( float[] v )
	  {
	    _normalList.add( v );
	  }

	  // Add faces
	public void addFace( ObjImpFace f )
	  {
	    _faceList.add( f );
	  }
	public void addFace( int a, int b, int c )
	  {
	    _faceList.add( new ObjImpFace(a, b, c) );
	  }
	
	
	public String getName()
	{
		return _name;
	}
	
	public ArrayList<float[]> getVertexList()
	{
		return _vertexList;
	}

	
	public ArrayList<float[]> getNormalList()
	{
		return _normalList;
	}

	public ArrayList<float[]> getTexCoordList()
	{
		return _texcoordList;
	}

	public ArrayList<ObjImpFace> getFaceList()
	{
		return _faceList;
	}

	  
	String _name;
	ObjImpMaterial _material;  
	ArrayList<float[]> _vertexList;
	ArrayList<float[]> _texcoordList;
	ArrayList<float[]> _normalList;
	ArrayList<ObjImpFace> _faceList; 
}
