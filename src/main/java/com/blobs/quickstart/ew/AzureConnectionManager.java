/**
 * @author ParveenBanuHebbal
 */
package com.blobs.quickstart.ew;

import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;

public class AzureConnectionManager {

	private static AzureConnectionManager instance;
	private final TableServiceClient tableServiceClient;

	private AzureConnectionManager(String connectingString) {
		tableServiceClient = new TableServiceClientBuilder().connectionString(connectingString).buildClient();
	}

	public static synchronized AzureConnectionManager getInstance(String connectionString) {
		if (instance == null) {
			instance = new AzureConnectionManager(connectionString);
		}
		return instance;
	}

	public TableServiceClient getTableServiceClient() {
		return tableServiceClient;
	}
}
