/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.test.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


/**
 * This class represents organization object.
 */
public class Organization implements Serializable{
    /** */
    private static final AtomicLong ID_GEN = new AtomicLong();

    /** Organization ID (indexed). */

    private Long id;

    /** Organization name (indexed). */

    private String name;

    /** Address. */
    private String addr;

    /** Type. */
    private int type;

    public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



	/** Last update time. */
    private Date lastUpdated;
    
    private Double dbFieldValue = 123.0456789;
    
    private BigDecimal dcmFieldValue1;    
    private BigDecimal dcmFieldValue2;
    private BigDecimal dcmFieldValue3;
    private BigDecimal dcmFieldValue4;
    
    private String strFieldValue1 ;
    private String strFieldValue2;
    private String strFieldValue3;
    private String strFieldValue4;
    

    public Double getDbFieldValue() {
		return dbFieldValue;
	}

	public void setDbFieldValue(Double dbFieldValue) {
		this.dbFieldValue = dbFieldValue;
	}

	public BigDecimal getDcmFieldValue1() {
		return dcmFieldValue1;
	}

	public void setDcmFieldValue1(BigDecimal dcmFieldValue1) {
		this.dcmFieldValue1 = dcmFieldValue1;
	}

	public BigDecimal getDcmFieldValue2() {
		return dcmFieldValue2;
	}

	public void setDcmFieldValue2(BigDecimal dcmFieldValue2) {
		this.dcmFieldValue2 = dcmFieldValue2;
	}

	public BigDecimal getDcmFieldValue3() {
		return dcmFieldValue3;
	}

	public void setDcmFieldValue3(BigDecimal dcmFieldValue3) {
		this.dcmFieldValue3 = dcmFieldValue3;
	}

	public BigDecimal getDcmFieldValue4() {
		return dcmFieldValue4;
	}

	public void setDcmFieldValue4(BigDecimal dcmFieldValue4) {
		this.dcmFieldValue4 = dcmFieldValue4;
	}

	public String getStrFieldValue1() {
		return strFieldValue1;
	}

	public void setStrFieldValue1(String strFieldValue1) {
		this.strFieldValue1 = strFieldValue1;
	}

	public String getStrFieldValue2() {
		return strFieldValue2;
	}

	public void setStrFieldValue2(String strFieldValue2) {
		this.strFieldValue2 = strFieldValue2;
	}

	public String getStrFieldValue3() {
		return strFieldValue3;
	}

	public void setStrFieldValue3(String strFieldValue3) {
		this.strFieldValue3 = strFieldValue3;
	}

	public String getStrFieldValue4() {
		return strFieldValue4;
	}

	public void setStrFieldValue4(String strFieldValue4) {
		this.strFieldValue4 = strFieldValue4;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	/**
     * Required for binary deserialization.
     */
    public Organization() {
        // No-op.
    }

    /**
     * @param name Organization name.
     */
    public Organization(String name) {
        id = ID_GEN.incrementAndGet();

        this.name = name;
    }

    /**
     * @param name Name.
     * @param addr Address.
     * @param type Type.
     * @param lastUpdated Last update time.
     */
    public Organization(String name, String addr, int type, Date lastUpdated) {
        id = ID_GEN.incrementAndGet();

        this.name = name;
        this.addr = addr;
        this.type = type;
        this.lastUpdated = lastUpdated;
        
        dcmFieldValue1 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue2 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue3 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue4 = BigDecimal.valueOf(12345.456789);
        
        strFieldValue1 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue2 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue3 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue4 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
    }

    /**
     * @return Organization ID.
     */
    public Long id() {
        return id;
    }

    /**
     * @return Name.
     */
    public String name() {
        return name;
    }

    /**
     * @return Address.
     */
    public String address() {
        return addr;
    }

    /**
     * @return Type.
     */
    public int type() {
        return type;
    }

  

    /** {@inheritDoc} */
    @Override public String toString() {
        return "Organization [id=" + id +
            ", name=" + name +
            ", address=" + addr +
            ", type=" + type +
            ", lastUpdated=" + lastUpdated + ']';
    }
}
