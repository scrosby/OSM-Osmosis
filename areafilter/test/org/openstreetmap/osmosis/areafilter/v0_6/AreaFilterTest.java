// This software is released into the Public Domain.  See copying.txt for details.
package org.openstreetmap.osmosis.areafilter.v0_6;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.openstreetmap.osmosis.core.Osmosis;

import data.util.DataFileUtilities;


/**
 * Tests the area filter implementation.
 */
public class AreaFilterTest {

	private DataFileUtilities fileUtils = new DataFileUtilities();
	
	
	/**
	 * A basic test verifying that the area filter includes all data when the complete planet is selected.
	 * 
	 * @throws IOException
	 *             if any file operations fail.
	 */
	@Test
	public void testEntirePlanet() throws IOException {
		File inputFile;
		File expectedOutputFile;
		File actualOutputFile;
		
		// Generate input files.
		inputFile = fileUtils.getDataFile("v0_6/areafilter-in.osm");
		expectedOutputFile = fileUtils.getDataFile("v0_6/areafilter-out-whole.osm");
		actualOutputFile = File.createTempFile("test", ".osm");
		
		// Load the database with a dataset.
		Osmosis.run(
			new String [] {
				"-q",
				"--read-xml-0.6",
				inputFile.getPath(),
				"--bounding-box",
				"--tag-sort-0.6",
				"--write-xml-0.6",
				actualOutputFile.getPath()
			}
		);
		
		// Validate that the output file matches the input file.
		fileUtils.compareFiles(expectedOutputFile, actualOutputFile);
		
		// Success so delete the output file.
		actualOutputFile.delete();
	}
	
	
	/**
	 * Performs a standard bounding box filter.
	 * 
	 * @throws IOException
	 *             if any file operations fail.
	 */
	@Test
	public void testBboxFilterStandard() throws IOException {
		File inputFile;
		File expectedOutputFile;
		File actualOutputFile;
		
		// Generate input files.
		inputFile = fileUtils.getDataFile("v0_6/areafilter-in.osm");
		expectedOutputFile = fileUtils.getDataFile("v0_6/areafilter-out-standard.osm");
		actualOutputFile = File.createTempFile("test", ".osm");
		
		// Load the database with a dataset.
		Osmosis.run(
			new String [] {
				"-q",
				"--read-xml-0.6",
				inputFile.getPath(),
				"--bounding-box", "left=-10", "top=10", "right=10", "bottom=-10",
				"--tag-sort-0.6",
				"--write-xml-0.6",
				actualOutputFile.getPath()
			}
		);
		
		// Validate that the output file matches the input file.
		fileUtils.compareFiles(expectedOutputFile, actualOutputFile);
		
		// Success so delete the output file.
		actualOutputFile.delete();
	}

	/**
	 * Performs a standard bounding box filter with the cascadingRelations feature added.
	 * 
	 * @throws IOException
	 *             if any file operations fail.
	 */
	@Test
	public void testBboxFilterCascadingRelations() throws IOException {
		File inputFile;
		File expectedOutputFile;
		File actualOutputFile;
		
		// Generate input files.
		inputFile = fileUtils.getDataFile("v0_6/areafilter-in.osm");
		expectedOutputFile = fileUtils.getDataFile("v0_6/areafilter-out-cascadingrelations.osm");
		actualOutputFile = File.createTempFile("test", ".osm");
		
		// Load the database with a dataset.
		Osmosis.run(
			new String [] {
				"-q",
				"--read-xml-0.6",
				inputFile.getPath(),
				"--bounding-box", "cascadingRelations=yes", "left=-10", "top=10", "right=10", "bottom=-10",
				"--tag-sort-0.6",
				"--write-xml-0.6",
				actualOutputFile.getPath()
			}
		);
		
		// Validate that the output file matches the input file.
		fileUtils.compareFiles(expectedOutputFile, actualOutputFile);
		
		// Success so delete the output file.
		actualOutputFile.delete();
	}
	
	
	/**
	 * Performs a bounding box filter with the completeWays option enabled.
	 * 
	 * @throws IOException
	 *             if any file operations fail.
	 */
	@Test
	public void testBboxFilterCompleteWays() throws IOException {
		File inputFile;
		File expectedOutputFile;
		File actualOutputFile;
		
		// Generate input files.
		inputFile = fileUtils.getDataFile("v0_6/areafilter-in.osm");
		expectedOutputFile = fileUtils.getDataFile("v0_6/areafilter-out-completeways.osm");
		actualOutputFile = File.createTempFile("test", ".osm");
		
		// Load the database with a dataset.
		Osmosis.run(
			new String [] {
				"-q",
				"--read-xml-0.6",
				inputFile.getPath(),
				"--bounding-box", "completeWays=yes", "left=-10", "top=10", "right=10", "bottom=-10",
				"--tag-sort-0.6",
				"--write-xml-0.6",
				actualOutputFile.getPath()
			}
		);
		
		// Validate that the output file matches the input file.
		fileUtils.compareFiles(expectedOutputFile, actualOutputFile);
		
		// Success so delete the output file.
		actualOutputFile.delete();
	}
	
	
	/**
	 * Performs a bounding box filter with the completeRelations option enabled.
	 * 
	 * @throws IOException
	 *             if any file operations fail.
	 */
	@Test
	public void testBboxFilterCompleteRelations() throws IOException {
		File inputFile;
		File expectedOutputFile;
		File actualOutputFile;
		
		// Generate input files.
		inputFile = fileUtils.getDataFile("v0_6/areafilter-in.osm");
		expectedOutputFile = fileUtils.getDataFile("v0_6/areafilter-out-completerelations.osm");
		actualOutputFile = File.createTempFile("test", ".osm");
		
		// Load the database with a dataset.
		Osmosis.run(
			new String [] {
				"-q",
				"--read-xml-0.6",
				inputFile.getPath(),
				"--bounding-box", "completeRelations=yes", "left=-10", "top=10", "right=10", "bottom=-10",
				"--tag-sort-0.6",
				"--write-xml-0.6",
				actualOutputFile.getPath()
			}
		);
		
		// Validate that the output file matches the input file.
		fileUtils.compareFiles(expectedOutputFile, actualOutputFile);
		
		// Success so delete the output file.
		actualOutputFile.delete();
	}
	
	
	/**
	 * Performs a bounding box filter with the clipIncompleteEntities option enabled.
	 * 
	 * @throws IOException
	 *             if any file operations fail.
	 */
	@Test
	public void testBboxFilterClipIncompleteEntities() throws IOException {
		File inputFile;
		File expectedOutputFile;
		File actualOutputFile;
		
		// Generate input files.
		inputFile = fileUtils.getDataFile("v0_6/areafilter-in.osm");
		expectedOutputFile = fileUtils.getDataFile("v0_6/areafilter-out-clipincompleteentities.osm");
		actualOutputFile = File.createTempFile("test", ".osm");
		
		// Load the database with a dataset.
		Osmosis.run(
			new String [] {
				"-q",
				"--read-xml-0.6",
				inputFile.getPath(),
				"--bounding-box", "clipIncompleteEntities=yes", "left=-10", "top=10", "right=10", "bottom=-10",
				"--tag-sort-0.6",
				"--write-xml-0.6",
				actualOutputFile.getPath()
			}
		);
		
		// Validate that the output file matches the input file.
		fileUtils.compareFiles(expectedOutputFile, actualOutputFile);
		
		// Success so delete the output file.
		actualOutputFile.delete();
	}
}
