
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



package org.apache.poi;


import junit.framework.TestCase;

import java.io.*;

import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hwpf.HWPFTestDataSamples;
import org.apache.poi.poifs.filesystem.*;

/**
 * Tests that POIDocument correctly loads and saves the common
 *  (hspf) Document Properties
 *
 * This is part 2 of 2 of the tests - it only does the POIDocuments
 *  which are part of the scratchpad (not main)
 *
 * @author Nick Burch (nick at torchbox dot com)
 */
public final class TestPOIDocumentScratchpad extends TestCase {
	// The POI Documents to work on
	private POIDocument doc;
	private POIDocument doc2;

	/**
	 * Set things up, using a PowerPoint document and
	 *  a Word Document for our testing
	 */
	public void setUp() throws Exception {
		doc = new HSLFSlideShowImpl(POIDataSamples.getSlideShowInstance().openResourceAsStream("basic_test_ppt_file.ppt"));

		doc2 = HWPFTestDataSamples.openSampleFile("test2.doc");
	}

	public void testReadProperties() {
		// We should have both sets
		assertNotNull(doc.getDocumentSummaryInformation());
		assertNotNull(doc.getSummaryInformation());

		// Check they are as expected for the test doc
		assertEquals("Hogwarts", doc.getSummaryInformation().getAuthor());
		assertEquals(10598, doc.getDocumentSummaryInformation().getByteCount());
	}

	public void testReadProperties2() {
		// Check again on the word one
		assertNotNull(doc2.getDocumentSummaryInformation());
		assertNotNull(doc2.getSummaryInformation());

		assertEquals("Hogwarts", doc2.getSummaryInformation().getAuthor());
		assertEquals("", doc2.getSummaryInformation().getKeywords());
		assertEquals(0, doc2.getDocumentSummaryInformation().getByteCount());
	}

	public void testWriteProperties() throws Exception {
		// Just check we can write them back out into a filesystem
		NPOIFSFileSystem outFS = new NPOIFSFileSystem();
		doc.writeProperties(outFS);

		// Should now hold them
		assertNotNull(outFS.createDocumentInputStream("\005SummaryInformation"));
		assertNotNull(outFS.createDocumentInputStream("\005DocumentSummaryInformation"));
	}

    public void testWriteReadProperties() throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

    	// Write them out
    	NPOIFSFileSystem outFS = new NPOIFSFileSystem();
    	doc.writeProperties(outFS);
    	outFS.writeFilesystem(baos);

    	// Create a new version
    	ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    	POIFSFileSystem inFS = new POIFSFileSystem(bais);

    	// Check they're still there
    	doc.directory = inFS.getRoot();
    	doc.readProperties();

    	// Delegate test
    	testReadProperties();
    }
}
