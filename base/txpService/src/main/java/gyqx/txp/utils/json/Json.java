package gyqx.txp.utils.json;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.component.file.GenericFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Json 
{
	private static Map<String,ObjectMapper> map = new HashMap<>();
	static
	{
		ObjectMapper mapper = new ObjectMapper();
		setMapperMap(mapper,false,false);
		
		mapper = new ObjectMapper();
		setMapperMap(mapper,false,true);
		
		mapper = new ObjectMapper();
		setMapperMap(mapper,true,false);
		
		mapper = new ObjectMapper();
		setMapperMap(mapper,true,true);
		
	}
	public static Object readObject(Object obj) throws Exception
	{
		if(obj instanceof String)
			return read((String)obj,Object.class,true);
		
		if(obj instanceof byte[])
		{
			return readObject((byte[])obj);
		}
		if(obj instanceof GenericFile)
		{
			readObject((GenericFile)obj);
		}
		if(obj instanceof InputStream ){
			return readObject((InputStream)obj);
		}
		if(obj instanceof Reader){
			return readObject((Reader)obj);
		}
		if(obj instanceof DataInput)
		{
			return readObject((DataInput)obj);
		}
		return null;
	}
	public static Object readObject(GenericFile gfile) throws  Exception
	{
		return readObject(FileUtils.readFileToString((File) gfile.getFile(),"utf-8"));
	}
	public static Object readObject(String data) throws Exception
	{
		return read(data,Object.class,true);
	}
	
	public static Object readObject(byte[] data) throws Exception
	{
		return read(data,Object.class,true);
	}
	
	public static Object readObject(InputStream is) throws Exception
	{
		return read(is,Object.class,true);
	}
	
	public static Object readObject(Reader reader) throws Exception
	{
		return read(reader,Object.class,true);
	}
	
	public static Object readObject(DataInput input) throws Exception
	{
		return read(input,Object.class,true);
	}
	public static Map<String,Object> readMap(String json) throws Exception
	{
		return read(json, Map.class ,false );
	}
	public static <T> T read(String json,Class<T> cls) throws Exception
	{
		return read(json, cls,false );
	}
	public static String write(Object obj) throws JsonProcessingException
	{
		return write(obj,false,false);
	}
	
	public static String writeObject(Object obj) throws JsonProcessingException
	{
		return write(obj,true,true);
	}
	
	public static byte[] writeObjectToBytes(Object obj) throws JsonProcessingException
	{
		return writeToBytes(obj,true,true);
	}
	
	public static <T> T read(String json,Class<T> cls,boolean bTyping) throws Exception
	{
		ObjectMapper mapper = getMapper(bTyping,false);		

		return mapper.readValue(json, cls );
	}
	public static <T> T read(byte[] data,Class<T> cls,boolean bTyping) throws Exception
	{
		ObjectMapper mapper = getMapper(bTyping,false);
		
		return mapper.readValue(data, cls );
	}
	public static <T> T read(InputStream is,Class<T> cls,boolean bTyping) throws Exception
	{
		ObjectMapper mapper =getMapper(bTyping,false);		

		return mapper.readValue(is, cls );
	}
	
	public static <T> T read(Reader reader,Class<T> cls,boolean bTyping) throws Exception
	{
		ObjectMapper mapper = getMapper(bTyping,false);		

		return mapper.readValue(reader, cls );
	}
	
	public static <T> T read(DataInput input,Class<T> cls,boolean bTyping) throws Exception
	{
		ObjectMapper mapper =  getMapper(bTyping,false);
		if(bTyping)
			mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);

		return mapper.readValue(input, cls );
	}
	
	public static String write(Object obj,boolean bTyping,boolean bIndent) throws JsonProcessingException
	{
		ObjectMapper mapper = getMapper(bTyping,bIndent);
		if(bTyping){
			mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);			
		}
		if(bIndent)
		{
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		
		return mapper.writeValueAsString(obj);
	}
	private static ObjectMapper setMapperMap(ObjectMapper mapper,boolean bTyping,boolean bIndent)
	{
		if(bTyping){
			mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);			
		}
		if(bIndent)
		{
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
		}
		map.put("" + bTyping + bIndent, mapper);
		return mapper;
	}
	private static ObjectMapper getMapper(boolean bTyping,boolean bIndent)
	{
		return map.get("" + bTyping + bIndent);
	}
	
	public static byte[] writeToBytes(Object obj,boolean bTyping,boolean bIndent) throws JsonProcessingException
	{
		ObjectMapper mapper = getMapper(bTyping,bIndent);		
		return mapper.writeValueAsBytes(obj);
	}

}
