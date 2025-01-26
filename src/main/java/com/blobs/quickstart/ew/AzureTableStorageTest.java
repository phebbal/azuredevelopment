/**
 * @author ParveenBanuHebbal
 */
package com.blobs.quickstart.ew;

import java.util.List;
import java.util.Map;

import com.azure.core.http.rest.PagedIterable;
import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;

public class AzureTableStorageTest {

	public static TableServiceClient getTableServiceClient() {
		String connectionString = "<ConnectionString>";
		TableServiceClient tableServiceClient = new TableServiceClientBuilder().connectionString(connectionString)
				.buildClient();
		return tableServiceClient;
	}

	/**
	 * Step 2 Pass the table to create the table name
	 * 
	 * @param tableName
	 */
	public void createTable(String tableName) {
		TableServiceClient tableServiceClient = getTableServiceClient();
		tableServiceClient.createTableIfNotExists(tableName);
	}

	/**
	 * 
	 * @param tableName
	 * @return TableClient
	 */
	public TableClient getTableClient(String tableName) {
		TableClient tableClient = getTableServiceClient().getTableClient(tableName);
		return tableClient;
	}

	public void addEntity(String tableName, String partitionKey, String rowKey, Map<String, Object> properties) {
		TableEntity entity = new TableEntity(partitionKey, rowKey).setProperties(properties);
		TableClient tableClient = getTableClient(tableName);
		tableClient.createEntity(entity);
	}

	public PagedIterable<TableEntity> getEntityData(List<String> propertiesToSelect, TableClient tableclient) {

		ListEntitiesOptions options = new ListEntitiesOptions();
		if (!propertiesToSelect.isEmpty()) {
			options.setSelect(propertiesToSelect);
		}

		PagedIterable<TableEntity> entities = tableclient.listEntities(options, null, null);
		return entities;

	}

}
