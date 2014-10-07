import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class testMarineControlJproxyIntArrayToInt {

	int a[] = {0x00,0x00,0x00,0x00};
	
	
	//Filled bit patterns
	int b[] = {0xFF,0x00,0x00,0x00};
	int c[] = {0x00,0xFF,0x00,0x00};
	int d[] = {0x00,0x00,0xFF,0x00};
	int e[] = {0x00,0x00,0x00,0xFF};	
	int f[] = {0xFF,0xFF,0x00,0x00};
	int g[] = {0xFF,0xFF,0xFF,0x00};
	int h[] = {0xFF,0xFF,0xFF,0xFF};	
	int i[] = {0x00,0x00,0xFF,0xFF};
	int j[] = {0x00,0xFF,0xFF,0xFF};
	
	//Alternating 1010 patterns
	int k[] = {0xA,0x00,0x00,0x00};
	int l[] = {0x00,0x0A,0x00,0x00};
	int m[] = {0x00,0x00,0x0A,0x00};
	int n[] = {0x00,0x00,0x00,0x0A};	
	int o[] = {0xA,0x0A,0x00,0x00};
	int p[] = {0xA,0x0A,0x0A,0x00};
	int q[] = {0xA,0x0A,0x0A,0x0A};	
	int r[] = {0x00,0x00,0x0A,0x0A};
	int s[] = {0x00,0x0A,0x0A,0x0A};
	
	//Escape characters
	int t[] = {0xF0,0x00,0x00,0x00};
	int u[] = {0x00,0xF0,0x00,0x00};
	int v[] = {0x00,0x00,0xF0,0x00};
	int x[] = {0x00,0x00,0x00,0xF0};	
	int y[] = {0xF0,0xF0,0x00,0x00};
	int z[] = {0xF0,0xF0,0xF0,0x00};
	int aa[] = {0xF0,0xF0,0xF0,0xF0};	
	int ab[] = {0x00,0x00,0xF0,0xF0};
	int ac[] = {0x00,0xF0,0xF0,0xF0};
	
	
	//Escape characters and other ones mixed
	int ad[] = {0xF0,0xAA,0xAA,0xAA};
	int ae[] = {0xAA,0xF0,0xAA,0xAA};
	int af[] = {0xAA,0xAA,0xF0,0xAA};
	int ag[] = {0xAA,0xAA,0xAA,0xF0};	
	int ah[] = {0xF0,0xF0,0xAA,0xAA};
	int ai[] = {0xF0,0xF0,0xF0,0xAA};
	int aj[] = {0xF0,0xF0,0xF0,0xF0};	
	int ak[] = {0xAA,0xAA,0xF0,0xF0};
	int al[] = {0xAA,0xF0,0xF0,0xF0};
	

	
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		//-16777216
		assertEquals("Content should be zero", 0x00000000, MarineControlJProxy.intArrayToInt(a));
		assertEquals("Number interpreted wrong" + b, 0x000000FF, MarineControlJProxy.intArrayToInt(b));
		assertEquals("Number interpreted wrong" + c, 0x0000FF00, MarineControlJProxy.intArrayToInt(c));
		assertEquals("Number interpreted wrong" + d, 0x00FF0000, MarineControlJProxy.intArrayToInt(d));
		assertEquals("Number interpreted wrong" + e, 0xFF000000, MarineControlJProxy.intArrayToInt(e));
		assertEquals("Number interpreted wrong" + f, 0x0000FFFF, MarineControlJProxy.intArrayToInt(f));
		assertEquals("Number interpreted wrong" + g, 0x00FFFFFF, MarineControlJProxy.intArrayToInt(g));
		assertEquals("Number interpreted wrong" + h, 0xFFFFFFFF, MarineControlJProxy.intArrayToInt(h));
		assertEquals("Number interpreted wrong" + i, 0xFFFF0000, MarineControlJProxy.intArrayToInt(i));
		assertEquals("Number interpreted wrong" + j, 0xFFFFFF00, MarineControlJProxy.intArrayToInt(j));
		assertEquals("Number interpreted wrong" + k, 0x0000000A, MarineControlJProxy.intArrayToInt(k));
		assertEquals("Number interpreted wrong" + l, 0x00000A00, MarineControlJProxy.intArrayToInt(l));
		assertEquals("Number interpreted wrong" + m, 0x000A0000, MarineControlJProxy.intArrayToInt(m));
		assertEquals("Number interpreted wrong" + n, 0x0A000000, MarineControlJProxy.intArrayToInt(n));
		assertEquals("Number interpreted wrong" + o, 0x00000A0A, MarineControlJProxy.intArrayToInt(o));
		assertEquals("Number interpreted wrong" + p, 0x000A0A0A, MarineControlJProxy.intArrayToInt(p));
		assertEquals("Number interpreted wrong" + q, 0x0A0A0A0A, MarineControlJProxy.intArrayToInt(q));
		assertEquals("Number interpreted wrong" + r, 0x0A0A0000, MarineControlJProxy.intArrayToInt(r));
		assertEquals("Number interpreted wrong" + s, 0x0A0A0A00, MarineControlJProxy.intArrayToInt(s));

		assertEquals("Number interpreted wrong" + t, 0x000000F0, MarineControlJProxy.intArrayToInt(t));
		assertEquals("Number interpreted wrong" + u, 0x0000F000, MarineControlJProxy.intArrayToInt(u));
		assertEquals("Content should be zero", 0x00F00000, MarineControlJProxy.intArrayToInt(v));
		assertEquals("Content should be zero", 0xF0000000, MarineControlJProxy.intArrayToInt(x));
		assertEquals("Content should be zero", 0x0000F0F0, MarineControlJProxy.intArrayToInt(y));
		assertEquals("Content should be zero", 0x00F0F0F0, MarineControlJProxy.intArrayToInt(z));
		assertEquals("Content should be zero", 0xF0F0F0F0, MarineControlJProxy.intArrayToInt(aa));
		assertEquals("Content should be zero", 0xF0F00000, MarineControlJProxy.intArrayToInt(ab));
		assertEquals("Content should be zero", 0xF0F0F000, MarineControlJProxy.intArrayToInt(ac));


		assertEquals("Content should be zero", 0xAAAAAAF0, MarineControlJProxy.intArrayToInt(ad));
		assertEquals("Content should be zero", 0xAAAAF0AA, MarineControlJProxy.intArrayToInt(ae));
		assertEquals("Content should be zero", 0xAAF0AAAA, MarineControlJProxy.intArrayToInt(af));
		assertEquals("Content should be zero", 0xF0AAAAAA, MarineControlJProxy.intArrayToInt(ag));
		assertEquals("Content should be zero", 0xAAAAF0F0, MarineControlJProxy.intArrayToInt(ah));
		assertEquals("Content should be zero", 0xAAF0F0F0, MarineControlJProxy.intArrayToInt(ai));
		assertEquals("Content should be zero", 0xF0F0F0F0, MarineControlJProxy.intArrayToInt(aj));
		assertEquals("Content should be zero", 0xF0F0AAAA, MarineControlJProxy.intArrayToInt(ak));
		assertEquals("Content should be zero", 0xF0F0F0AA, MarineControlJProxy.intArrayToInt(al));

		
		
	}

}
