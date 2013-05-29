package com.peergreen.aether.client.internal;

import java.io.File;
import java.util.HashMap;

import com.peergreen.aether.client.IFeaturesPersistence;


/**
 * Class to handle feature's persistence relative functionalities.<br />
 * Default implementation of FeaturePersistence interface.
 * <p>
 * Provides methods to:
 * <ul>
 * 		<li>retrieve feature's metadata</li>
 * 		<li>retrieve feature's binary</li>
 * 		<li>add a feature to the staging repository</li>
 * 		<li>add a feature to the local repository</li>
 * </ul>
 */
public class FeaturePersistenceDefault implements IFeaturesPersistence {

	/**
	 * Method to recover feature's metadata from its id
	 * 
	 * @param id feature's id
	 * @return collection containing all feature's metadata
	 */
	@Override
	public HashMap<String, String> getMetadata(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to recover feature's metadata from its information
	 * 
	 * @param groupId feature's group id
	 * @param artifactId feature's artifact id
	 * @param version feature's version
	 * @return collection containing all feature's metadata
	 */
	@Override
	public HashMap<String, String> getMetadata(String groupId,
			String artifactId, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to recover feature's binary from its id
	 * 
	 * @param id
	 * @return feature's binary
	 */
	@Override
	public File getFeature(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to recover feature's binary from its information
	 * 
	 * @param groupId feature's group id
	 * @param artifactId feature's artifact id
	 * @param version feature's version
	 * @return feature's binary
	 */
	@Override
	public File getFeature(String groupId, String artifactId, String version) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method to add a feature to the staging repository
	 * 
	 * @param groupId feature's groupId
	 * @param artifactId feature's artifactId
	 * @param version feature's version
	 * @param description feature's description
	 * @param category feature's category
	 * @param requirements requirements list
	 * @param capabilities exported capabilities list
	 * @param properties feature's additional properties
	 * @param feature feature's binary
	 */
	@Override
	public void addToStaging(String groupId, String artifactId, String version,
			String description, HashMap<String, String> properties, File feature) {
		// TODO Auto-generated method stub

	}

	/**
	 * Method to add a feature to the staging repository
	 * 
	 * @param groupId feature's groupId
	 * @param artifactId feature's artifactId
	 * @param version feature's version
	 * @param description feature's description
	 * @param category feature's category
	 * @param requirements requirements list
	 * @param capabilities exported capabilities list
	 * @param properties feature's additional properties
	 * @param feature feature's binary
	 */
	@Override
	public void addToLocal(String groupId, String artifactId, String version,
			String description, HashMap<String, String> properties, File feature) {
		// TODO Auto-generated method stub

	}

}
