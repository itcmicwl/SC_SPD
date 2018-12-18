package tools;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ObjectUtils;

import common.base.KV;
import common.transform.Tx;
import gyqx.platform.po.SysAtom;

public class ListToMapTest {
	public static class TAtom
	{
		private String     id;
		private BigDecimal curVal;
		private String ename;
		private String remark;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public BigDecimal getCurVal() {
			return curVal;
		}
		public void setCurVal(BigDecimal curVal) {
			this.curVal = curVal;
		}
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		@Override
		public String toString() {
			return "TAtom [id=" + id + ", curVal=" + curVal + ", ename=" + ename + "]";
		}
		
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() throws InstantiationException, IllegalAccessException, InvocationTargetException {
		List<SysAtom> atoms = new ArrayList<>();
		for(int i = 0; i< 10; i++)
		{
			SysAtom atom = new SysAtom();
			atom.setId("id" + i);
			atom.setCurVal(new BigDecimal(100 + i));
			atom.setEname("ename" + i);
			atom.setRemark("remark:" + i);
			atoms.add(atom);
		}
		
		HashMap<String,TAtom> map = Tx.copylist(atoms).toMap(TAtom.class);
		map = Tx.copylist(atoms).withKeyName("ename").toMap(TAtom.class);
		
		HashMap<String,SysAtom> map1 = Tx.copylist(atoms).withKeyName("ename").toMap();
		
		
		
		Object[] objs = atoms.toArray();
		
		
		map = Tx.copylist(objs).toMap(TAtom.class);
		map = Tx.copylist(objs).withKeyName("ename").toMap(TAtom.class);
		
		map1 = Tx.converList(objs).toMap();
		
		System.out.println(map1.size());
		
	}

}
