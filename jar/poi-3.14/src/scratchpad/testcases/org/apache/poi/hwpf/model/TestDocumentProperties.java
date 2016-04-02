/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package org.apache.poi.hwpf.model;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;

import junit.framework.TestCase;

import org.apache.poi.hwpf.HWPFDocFixture;
import org.apache.poi.hwpf.model.types.DOPAbstractType;
import org.apache.poi.util.SuppressForbidden;

// TODO: Add DocumentProperties#equals ???

public final class TestDocumentProperties
  extends TestCase
{
  private DocumentProperties _documentProperties = null;
  private HWPFDocFixture _hWPFDocFixture;

  public void testReadWrite()
    throws Exception
  {
    int size = DOPAbstractType.getSize();
    byte[] buf = new byte[size];

    _documentProperties.serialize(buf, 0);

    DocumentProperties newDocProperties =
      new DocumentProperties(buf, 0, size);

    final Field[] fields;
    try {
        fields = AccessController.doPrivileged(new PrivilegedExceptionAction<Field[]>() {
            @Override
            @SuppressForbidden("Test only")
            public Field[] run() throws Exception {
                final Field[] fields = DocumentProperties.class.getSuperclass().getDeclaredFields();
                AccessibleObject.setAccessible(fields, true);
                return fields;
            }
        });
    } catch (PrivilegedActionException pae) {
        throw pae.getException();
    }
    
    for (int x = 0; x < fields.length; x++)
    {
      // JaCoCo Code Coverage adds it's own field, don't look at this one here
      if(fields[x].getName().equals("$jacocoData")) {
    	  continue;
      }

      if (!fields[x].getType().isArray())
      {
        assertEquals(fields[x].get(_documentProperties),
                     fields[x].get(newDocProperties));
      }
      else
      {
    	// ensure that the class was not changed/enhanced, e.g. by code instrumentation like coverage tools
    	assertEquals("Invalid type for field: " + fields[x].getName(), 
    			"[B", fields[x].getType().getName());
    	
        byte[] buf1 = (byte[])fields[x].get(_documentProperties);
        byte[] buf2 = (byte[])fields[x].get(newDocProperties);
        Arrays.equals(buf1, buf2);
      }
    }

  }

  protected void setUp()
    throws Exception
  {
    super.setUp();
    /**@todo verify the constructors*/

    _hWPFDocFixture = new HWPFDocFixture(this, HWPFDocFixture.DEFAULT_TEST_FILE);

    _hWPFDocFixture.setUp();

    _documentProperties = new DocumentProperties(_hWPFDocFixture._tableStream, _hWPFDocFixture._fib.getFcDop(), _hWPFDocFixture._fib.getLcbDop());
  }

  protected void tearDown()
    throws Exception
  {
    _documentProperties = null;
    _hWPFDocFixture.tearDown();

    _hWPFDocFixture = null;
    super.tearDown();
  }

}
