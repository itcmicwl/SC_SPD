package permission;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;

import common.db.SqlUtils;
import common.db.exception.SqlInjectException;
import common.security.matcher.WildcardPathMatcher;
import common.validate.Checker;

public class PathTest {

	@Before
	public void setUp() throws Exception {
	}

	//@Test
	public void test() {
		
		
		AntPathMatcher pathMatcher = new  AntPathMatcher();
		//for(int i = 0; i< 100000;i++){
		// test exact matching
		assertTrue(pathMatcher.match("test", "test"));
		assertTrue(pathMatcher.match("/test", "/test"));
		assertTrue(pathMatcher.match("http://example.org", "http://example.org")); // SPR-14141
		assertFalse(pathMatcher.match("/test.jpg", "test.jpg"));
		assertFalse(pathMatcher.match("test", "/test"));
		assertFalse(pathMatcher.match("/test", "test"));

		// test matching with ?'s
		assertTrue(pathMatcher.match("t?st", "test"));
		assertTrue(pathMatcher.match("??st", "test"));
		assertTrue(pathMatcher.match("tes?", "test"));
		assertTrue(pathMatcher.match("te??", "test"));
		assertTrue(pathMatcher.match("?es?", "test"));
		assertFalse(pathMatcher.match("tes?", "tes"));
		assertFalse(pathMatcher.match("tes?", "testt"));
		assertFalse(pathMatcher.match("tes?", "tsst"));

		// test matching with *'s
		assertTrue(pathMatcher.match("*", "test"));
		assertTrue(pathMatcher.match("test*", "test"));
		assertTrue(pathMatcher.match("test*", "testTest"));
		assertTrue(pathMatcher.match("test/*", "test/Test"));
		assertTrue(pathMatcher.match("test/*", "test/t"));
		assertTrue(pathMatcher.match("test/*", "test/"));
		assertTrue(pathMatcher.match("*test*", "AnothertestTest"));
		assertTrue(pathMatcher.match("*test", "Anothertest"));
		assertTrue(pathMatcher.match("*.*", "test."));
		assertTrue(pathMatcher.match("*.*", "test.test"));
		assertTrue(pathMatcher.match("*.*", "test.test.test"));
		assertTrue(pathMatcher.match("test*aaa", "testblaaaa"));
		assertFalse(pathMatcher.match("test*", "tst"));
		assertFalse(pathMatcher.match("test*", "tsttest"));
		assertFalse(pathMatcher.match("test*", "test/"));
		assertFalse(pathMatcher.match("test*", "test/t"));
		assertFalse(pathMatcher.match("test/*", "test"));
		assertFalse(pathMatcher.match("*test*", "tsttst"));
		assertFalse(pathMatcher.match("*test", "tsttst"));
		assertFalse(pathMatcher.match("*.*", "tsttst"));
		assertFalse(pathMatcher.match("test*aaa", "test"));
		assertFalse(pathMatcher.match("test*aaa", "testblaaab"));

		// test matching with ?'s and /'s
		assertTrue(pathMatcher.match("/?", "/a"));
		assertTrue(pathMatcher.match("/?/a", "/a/a"));
		assertTrue(pathMatcher.match("/a/?", "/a/b"));
		assertTrue(pathMatcher.match("/??/a", "/aa/a"));
		assertTrue(pathMatcher.match("/a/??", "/a/bb"));
		assertTrue(pathMatcher.match("/?", "/a"));

		// test matching with **'s
		assertTrue(pathMatcher.match("/**", "/testing/testing"));
		assertTrue(pathMatcher.match("/*/**", "/testing/testing"));
		assertTrue(pathMatcher.match("/**/*", "/testing/testing"));
		assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla"));
		assertTrue(pathMatcher.match("/bla/**/bla", "/bla/testing/testing/bla/bla"));
		assertTrue(pathMatcher.match("/**/test", "/bla/bla/test"));
		assertTrue(pathMatcher.match("/bla/**/**/bla", "/bla/bla/bla/bla/bla/bla"));
		assertTrue(pathMatcher.match("/bla*bla/test", "/blaXXXbla/test"));
		assertTrue(pathMatcher.match("/*bla/test", "/XXXbla/test"));
		assertFalse(pathMatcher.match("/bla*bla/test", "/blaXXXbl/test"));
		assertFalse(pathMatcher.match("/*bla/test", "XXXblab/test"));
		assertFalse(pathMatcher.match("/*bla/test", "XXXbl/test"));

		assertFalse(pathMatcher.match("/????", "/bala/bla"));
		assertFalse(pathMatcher.match("/**/*bla", "/bla/bla/bla/bbb"));

		assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing/"));
		assertTrue(pathMatcher.match("/*bla*/**/bla/*", "/XXXblaXXXX/testing/testing/bla/testing"));
		assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing"));
		assertTrue(pathMatcher.match("/*bla*/**/bla/**", "/XXXblaXXXX/testing/testing/bla/testing/testing.jpg"));

		assertTrue(pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing/"));
		assertTrue(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing"));
		assertTrue(pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing"));
		assertFalse(pathMatcher.match("*bla*/**/bla/*", "XXXblaXXXX/testing/testing/bla/testing/testing"));

		assertFalse(pathMatcher.match("/x/x/**/bla", "/x/x/x/"));

		assertTrue(pathMatcher.match("/foo/bar/**", "/foo/bar"));

		assertTrue(pathMatcher.match("", ""));

		assertTrue(pathMatcher.match("/{bla}.*", "/testing.html"));     
		//}
	}
	@Test
	public void test1()
	{
		WildcardPathMatcher wwtree = new WildcardPathMatcher();
		
		wwtree.addPath("/a/b/c");
		wwtree.addPath("/b/{abc}/c");
		wwtree.addPath("/b/c/*");
		
		wwtree.addPath("/x/**/ab");
		
		wwtree.addPath("/m/?yz/ab");
		
		wwtree.addPath("/m1/yz/**");
		
		assertTrue(wwtree.match("/a/b/c"));
		assertTrue(wwtree.match("/b/m/c"));
		assertTrue(wwtree.match("/b/c/n"));
		assertTrue(!wwtree.match("/b/n/m"));
		
		
		assertTrue(wwtree.match("x/n/a1/a2/ab"));
		assertTrue(!wwtree.match("x/n/a1/a2/abc"));
		
		assertTrue(wwtree.match("m/xyz/ab"));
		assertTrue(!wwtree.match("m/0yz/abc"));
		assertTrue(!wwtree.match("m/yz/ab"));
		
		assertTrue(wwtree.match("m1/yz/asd"));
//		wwtree.addPath("b/*/c");
//		wwtree.addPath("b/a/c");
//		wwtree.addPath("b/b/b");
//		wwtree.addPath("b/b/*");
		
		//wwtree.addPath("b/**/x");
		
		//assertTrue(wwtree.match("b/bb/c/x"));
//		assertFalse(wwtree.match("a/b/c"));
//		
//		assertTrue(wwtree.match("b/z/c"));
//		
//		assertTrue(wwtree.match("b/b/z"));
//		
//		assertFalse(wwtree.match("z/b/z"));
		
		System.out.println("");
	}
	//@Test
	public void testChecker() throws SqlInjectException
	{
		Assert.isTrue(!Checker.isIdentity("01231z\\8_8.8:2-ddddAS"));
		Assert.isTrue(!Checker.isIdentity("01231z0x8_8.8:2-ddddAS"));
		
		
		String[] ss = new String[]{"wer123","cv-VV","_lkjfWERd098-ddd"};
		System.out.println("in (" + SqlUtils.idListToSql(ss) + ")");
		
		ss = new String[]{"wer123",""};
		System.out.println("in (" + SqlUtils.idListToSql(ss) + ")");
		
		ss = new String[]{};
		System.out.println("in (" + SqlUtils.idListToSql(ss) + ")");
	}

}
