package tools;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import net.coobird.thumbnailator.Thumbnails;

public class ThumbnailTest 
{
	@Test
	public void test() throws IOException
	{
		File file = new File("d:/14805788827588370.jpg");
		File thumbnailFile = new File("d:/tmp/thumb-14805788827588370.jpg");
		Thumbnails.of(file).size(200, 200).toFile(thumbnailFile);
		
	}

}
