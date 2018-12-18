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
import java.util.concurrent.atomic.AtomicLong;


/**
 * Person class.
 */
public class Person implements Serializable {
    /** */
    private static final AtomicLong ID_GEN = new AtomicLong();

    /** Person ID (indexed). */

    public Long id;

    /** Organization ID (indexed). */

    public Long orgId;
    
    private Organization org;

    /** First name (not-indexed). */

    public String firstName;
    
    private Double dbFieldValue = 123.0456789;
    
    private BigDecimal dcmFieldValue1;    
    private BigDecimal dcmFieldValue2;
    private BigDecimal dcmFieldValue3;
    private BigDecimal dcmFieldValue4;
    private BigDecimal dcmFieldValue5;
    private BigDecimal dcmFieldValue6;
    private BigDecimal dcmFieldValue7;
    private BigDecimal dcmFieldValue8;
    private BigDecimal dcmFieldValue9;
    
    private String strFieldValue1 ;
    private String strFieldValue2;
    private String strFieldValue3;
    private String strFieldValue4;
    private String strFieldValue5 ;
    private String strFieldValue6;
    private String strFieldValue7;
    private String strFieldValue8;
    private String strFieldValue9 ;
    private String strFieldValue10;
    private String strFieldValue11;
    private String strFieldValue12;
    

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

	public BigDecimal getDcmFieldValue5() {
		return dcmFieldValue5;
	}

	public void setDcmFieldValue5(BigDecimal dcmFieldValue5) {
		this.dcmFieldValue5 = dcmFieldValue5;
	}

	public BigDecimal getDcmFieldValue6() {
		return dcmFieldValue6;
	}

	public void setDcmFieldValue6(BigDecimal dcmFieldValue6) {
		this.dcmFieldValue6 = dcmFieldValue6;
	}

	public BigDecimal getDcmFieldValue7() {
		return dcmFieldValue7;
	}

	public void setDcmFieldValue7(BigDecimal dcmFieldValue7) {
		this.dcmFieldValue7 = dcmFieldValue7;
	}

	public BigDecimal getDcmFieldValue8() {
		return dcmFieldValue8;
	}

	public void setDcmFieldValue8(BigDecimal dcmFieldValue8) {
		this.dcmFieldValue8 = dcmFieldValue8;
	}

	public BigDecimal getDcmFieldValue9() {
		return dcmFieldValue9;
	}

	public void setDcmFieldValue9(BigDecimal dcmFieldValue9) {
		this.dcmFieldValue9 = dcmFieldValue9;
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

	public String getStrFieldValue5() {
		return strFieldValue5;
	}

	public void setStrFieldValue5(String strFieldValue5) {
		this.strFieldValue5 = strFieldValue5;
	}

	public String getStrFieldValue6() {
		return strFieldValue6;
	}

	public void setStrFieldValue6(String strFieldValue6) {
		this.strFieldValue6 = strFieldValue6;
	}

	public String getStrFieldValue7() {
		return strFieldValue7;
	}

	public void setStrFieldValue7(String strFieldValue7) {
		this.strFieldValue7 = strFieldValue7;
	}

	public String getStrFieldValue8() {
		return strFieldValue8;
	}

	public void setStrFieldValue8(String strFieldValue8) {
		this.strFieldValue8 = strFieldValue8;
	}

	public String getStrFieldValue9() {
		return strFieldValue9;
	}

	public void setStrFieldValue9(String strFieldValue9) {
		this.strFieldValue9 = strFieldValue9;
	}

	public String getStrFieldValue10() {
		return strFieldValue10;
	}

	public void setStrFieldValue10(String strFieldValue10) {
		this.strFieldValue10 = strFieldValue10;
	}

	public String getStrFieldValue11() {
		return strFieldValue11;
	}

	public void setStrFieldValue11(String strFieldValue11) {
		this.strFieldValue11 = strFieldValue11;
	}

	public String getStrFieldValue12() {
		return strFieldValue12;
	}

	public void setStrFieldValue12(String strFieldValue12) {
		this.strFieldValue12 = strFieldValue12;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public long getKey() {
		return key;
	}

	public void setKey(long key) {
		this.key = key;
	}

	/** Last name (not indexed). */

    public String lastName;

    /** Resume text (create LUCENE-based TEXT index for this field). */

    public String resume;

    /** Salary (indexed). */

    public double salary;

    /** Custom cache key to guarantee that person is always collocated with its organization. */
    private long  key;

    /**
     * Default constructor.
     */
    public Person() {
        // No-op.
    }

    /**
     * Constructs person record.
     *
     * @param org       Organization.
     * @param firstName First name.
     * @param lastName  Last name.
     * @param salary    Salary.
     * @param resume    Resume text.
     */
    public Person(Organization org, String firstName, String lastName, double salary, String resume) {
        // Generate unique ID for this person.
        id = ID_GEN.incrementAndGet();

        orgId = org.id();
        
        this.org = org;

        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.resume = resume;
        
        dcmFieldValue1 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue2 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue3 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue4 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue5 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue6 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue7 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue8 = BigDecimal.valueOf(12345.456789);
        dcmFieldValue9 = BigDecimal.valueOf(12345.456789);
        
        strFieldValue1 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue2 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue3 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue4 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue5 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue6 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue7 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue8 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue9 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue10 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue11 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        strFieldValue12 = "中国人民。。123中国人民。。123中国人民。。123中国人民。。123中国人民。。123";
        
    }

    /**
     * Constructs person record.
     *
     * @param id Person ID.
     * @param orgId Organization ID.
     * @param firstName First name.
     * @param lastName Last name.
     * @param salary    Salary.
     * @param resume    Resume text.
     */
    public Person(Long id, Long orgId, String firstName, String lastName, double salary, String resume) {
        this.id = id;
        this.orgId = orgId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.resume = resume;
    }

    /**
     * Constructs person record.
     *
     * @param id Person ID.
     * @param firstName First name.
     * @param lastName Last name.
     */
    public Person(Long id, String firstName, String lastName) {
        this.id = id;

        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets cache affinity key. Since in some examples person needs to be collocated with organization, we create
     * custom affinity key to guarantee this collocation.
     *
     * @return Custom affinity key to guarantee that person is always collocated with organization.
     */
   

    /**
     * {@inheritDoc}
     */
    @Override public String toString() {
        return "Person [id=" + id +
                ", orgId=" + orgId +
                ", lastName=" + lastName +
                ", firstName=" + firstName +
                ", salary=" + salary +
                ", resume=" + resume + ']';
    }

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
}
