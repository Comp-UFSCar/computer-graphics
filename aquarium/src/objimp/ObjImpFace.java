package objimp;

public class ObjImpFace 
{
	public ObjImpFace() 
	  {
	    _a = _b = _c = 0;
	  }

	public ObjImpFace( int a, int b, int c ) 
	  {
	    this._a = a;
	    this._b = b;
	    this._c = c;
	    this._matId = -1;
	  }

	public ObjImpFace( int a, int b, int c, int matId ) 
	  {
	    this._a = a;
	    this._b = b;
	    this._c = c;
	    this._matId = matId;
	  }

	  int _a, _b, _c;  // vertex
	  int _na, _nb, _nc;  // normal
	  int _ta, _tb, _tc;  // texcoord

	  int _matId; 
}