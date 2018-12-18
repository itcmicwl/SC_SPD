package gyqx.platform.sys.controller;

import java.util.Map;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="sys/entity")
public class EntityUtilsController 
{
	
	@RequestMapping(value="{name}/get/{id}")
	public Object get(@PathVariable String name, @PathVariable String id)
	{		
		return null;
	}
	@RequestMapping(value="{name}/insert")
	public Object insert(@PathVariable String name, Map map)
	{
		return null;
	}
	@RequestMapping(value="{name}/update")
	public Object update(@PathVariable String name, Map map)
	{
		return null;
	}

}
