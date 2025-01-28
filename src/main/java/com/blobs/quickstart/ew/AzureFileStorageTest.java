package com.blobs.quickstart.ew;

import com.azure.storage.file.share.ShareClient;
import com.azure.storage.file.share.ShareClientBuilder;
import com.azure.storage.file.share.ShareDirectoryClient;
import com.azure.storage.file.share.ShareFileClient;
import com.azure.storage.file.share.ShareFileClientBuilder;
import com.azure.storage.file.share.models.ShareInfo;
import com.azure.storage.file.share.models.ShareStorageException;

public class AzureFileStorageTest {

	private static final String connectStr;

	static {
		connectStr = "<ConnectionString>";

	}

	public ShareInfo createFileShare(String shareName) {
		ShareClient shareClient = new ShareClientBuilder().connectionString(connectStr).shareName(shareName)
				.buildClient();

		// There are multiple methods are there which can be used. For explaining as for
		// learning purpose I am using create.
		return shareClient.create();

	}

	public void createDirectory(String shareName, String dirName) {

		ShareDirectoryClient dirClient = new ShareFileClientBuilder().connectionString(connectStr).shareName(shareName)
				.resourcePath(dirName).buildDirectoryClient();
		dirClient.create();
	}

	/**
	 * This method is responsible for uploading the file from local to File storage.
	 * This method assumes, dirName and shareName already existing before executing
	 * this
	 * 
	 * @param dirName
	 * @param fileName
	 * @param srcFileName
	 */
	public void uploadFile(String shareName, String dirName, String fileName, String fullpathOfFile) {

		// Example : "testdirph/testfiletoupload.txt" <directoryName>/fileName
		String resourcePath = dirName + "/" + fileName;
		ShareFileClient srcFileClient = new ShareFileClientBuilder().connectionString(connectStr).shareName(shareName)
				.resourcePath(resourcePath).buildFileClient();

		// Example : String srcFileName
		// ="D:\\Workspace\\PersonalPractice\\blob-quickstart\\testfiletoupload.txt";
		// This is full path of the src file.

		try {
			/*
			 * This check is must to ensure the file existing on the share. Else it will
			 * throw exception Failed to upload file to storage. Reasons: Status code 416,
			 * "﻿<?xml version="1.0" encoding="utf-8"?>
			 * <Error><Code>InvalidRange</Code><Message> The range specified is invalid for
			 * the current size of the resource.
			 * 
			 * <?xml version="1.0"
			 * encoding="utf-8"?><Error><Code>ResourceNotFound</Code><Message> The specified
			 * resource does not exist.
			 * 
			 */
			if (!srcFileClient.exists()) {
				System.out.println("File is empty. Creating an empty file in Azure Storage: " + fileName);
				srcFileClient.create(1024);
			}
			srcFileClient.uploadFromFile(fullpathOfFile);
			System.out.println("File is uploaded successfully");
		} catch (ShareStorageException e) {
			e.printStackTrace();
		}

	}

	public void deleteShare(String shareName) {
		ShareClient shareClient = new ShareClientBuilder().connectionString(connectStr).shareName(shareName)
				.buildClient();
		shareClient.delete();
		System.out.println("Completed deleting the share.");
	}
}
