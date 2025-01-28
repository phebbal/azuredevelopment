/**
 * @author ParveenBanuHebbal
 */
package com.blobs.quickstart.ew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.azure.core.http.rest.PagedIterable;
import com.azure.data.tables.models.TableEntity;

public class App {

	public static void main(String[] args) {

		App app = new App();
		// Uncomment the code to test AzureTableStoageWorkflow
		// app.testAzureTableStorage();

		// Uncomment the code to test AzureFileStorageTest
		// app.testAzureFileStorageTest();

		// After testing the File Storage Please clean the share to avoid billing
		app.deleteAzureFileStorage();

	}

	public void testAzureTableStorage() {
		AzureTableStorageTest client = new AzureTableStorageTest();

		// Create Table Name Employee
		client.createTable("Employee");

		// Add Entity to the table
		Map<String, Object> properties = new HashMap<>();
		properties.put("Name", "Mark Mic");
		properties.put("EmpCode", 001);
		properties.put("Department", "IT");
		properties.put("Phone", "0132323232323");

		client.addEntity("Employee", "Department", "EmpCode", properties);

		// Get the Data from the table.
		List<String> propertiesToSelect = new ArrayList<>();
		propertiesToSelect.add("Name");
		propertiesToSelect.add("EmpCode");
		propertiesToSelect.add("Department");
		propertiesToSelect.add("Phone");

		PagedIterable<TableEntity> entities = client.getEntityData(propertiesToSelect,
				client.getTableClient("Employee"));

		for (TableEntity entity1 : entities) {
			Map<String, Object> myData = entity1.getProperties();
			System.out.printf("%s:,%s: %s:,%s:", myData.get("Name"), myData.get("EmpCode"), myData.get("Department"),
					myData.get("Phone"));
		}

	}

	/**
	 * 
	 */
	public void testAzureFileStorageTest() {
		AzureFileStorageTest fileStorage = new AzureFileStorageTest();

		/*
		 * Create a ShareName in the Azure File Storage
		 */
		String shareName = "test123";
		fileStorage.createFileShare(shareName);

		/*
		 * Create Director for the given shareName on the Azure file Share
		 */
		String directoryName = "testdirph";
		fileStorage.createDirectory(shareName, directoryName);

		/*
		 * Upload File from local to Azure File Store.
		 */
		String fullpathOfFile = "D:\\Workspace\\PersonalPractice\\blob-quickstart\\testfiletoupload.txt";
		String fileName = "testfiletoupload.txt";

		fileStorage.uploadFile(shareName, directoryName, fileName, fullpathOfFile);
	}

	public void deleteAzureFileStorage() {
		AzureFileStorageTest fileStorage = new AzureFileStorageTest();
		String shareName = "test123";
		fileStorage.deleteShare(shareName);
	}
}
