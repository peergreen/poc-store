package com.peergreen.aetherClient.utils.internal;

import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;

import com.peergreen.aetherClient.utils.AetherUtils;


@Component
@Instantiate
@Provides
public class AetherUtilsImpl implements AetherUtils {

	@Override
	public RepositorySystem newRepositorySystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultRepositorySystemSession newRepositorySystemSession(
			RepositorySystem system) {
		// TODO Auto-generated method stub
		return null;
	}

}
